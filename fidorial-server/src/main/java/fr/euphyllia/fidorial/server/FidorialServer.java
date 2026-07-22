package fr.euphyllia.fidorial.server;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.faststats.ErrorTracker;
import dev.faststats.Metrics;
import fr.euphyllia.fidorial.auth.EncryptionUtils;
import fr.euphyllia.fidorial.auth.MojangSessionService;
import fr.euphyllia.fidorial.server.command.CommandManager;
import fr.euphyllia.fidorial.server.command.ConsoleSender;
import fr.euphyllia.fidorial.server.command.brigadier.argument.builtin.TranslatableExceptions;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypes;
import fr.euphyllia.fidorial.server.console.command.ConsoleCommandReader;
import fr.euphyllia.fidorial.server.entity.AbstractEntity;
import fr.euphyllia.fidorial.server.entity.EntityIdAllocator;
import fr.euphyllia.fidorial.server.entity.EntityManager;
import fr.euphyllia.fidorial.server.entity.EntityTickHandler;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.euphyllia.fidorial.server.entity.player.storage.NbtPlayerDataStorage;
import fr.euphyllia.fidorial.server.entity.player.storage.NbtPlayerInventoryStorage;
import fr.euphyllia.fidorial.server.event.SimpleEventBus;
import fr.euphyllia.fidorial.server.metrics.FidorialContext;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.network.NettyServer;
import fr.euphyllia.fidorial.server.permission.DefaultPermissions;
import fr.euphyllia.fidorial.server.permission.FidorialPermissionService;
import fr.euphyllia.fidorial.server.permission.OperatorList;
import fr.euphyllia.fidorial.server.plugin.JavaPluginManager;
import fr.euphyllia.fidorial.server.protocol.ProtocolConstants;
import fr.euphyllia.fidorial.server.protocol.ProtocolMap;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundAddEntityPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundBlockUpdatePacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundRemoveEntitiesPacket;
import fr.euphyllia.fidorial.server.registry.Registries;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import fr.euphyllia.fidorial.server.schedulers.AiWorker;
import fr.euphyllia.fidorial.server.schedulers.ThreadedChunkWorker;
import fr.euphyllia.fidorial.server.schedulers.ThreadedRegionRegionizer;
import fr.euphyllia.fidorial.server.service.SimpleServiceRegistry;
import fr.euphyllia.fidorial.server.translation.BuiltInTranslationStore;
import fr.euphyllia.fidorial.server.world.BlockEditService;
import fr.euphyllia.fidorial.server.world.BlockStateRegistry;
import fr.euphyllia.fidorial.server.world.FlatChunkGenerator;
import fr.euphyllia.fidorial.server.world.FlatWorld;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.ServiceBackedChunkGenerator;
import fr.euphyllia.fidorial.server.world.WorldConstants;
import fr.euphyllia.fidorial.server.world.WorldManager;
import fr.euphyllia.fidorial.server.world.block.VanillaBlockRegistry;
import fr.euphyllia.fidorial.server.world.entity.EntitySpawnBridge;
import fr.euphyllia.fidorial.server.world.fluid.FluidEngine;
import fr.euphyllia.fidorial.server.world.weather.WeatherEngine;
import fr.fidorial.Server;
import fr.fidorial.command.CommandRegistry;
import fr.fidorial.entity.Player;
import fr.fidorial.event.EventBus;
import fr.fidorial.event.server.ServerStartedEvent;
import fr.fidorial.event.server.ServerStoppingEvent;
import fr.fidorial.permission.PermissionService;
import fr.fidorial.plugin.PluginManager;
import fr.fidorial.scheduler.RegionizedScheduler;
import fr.fidorial.service.ServicePriority;
import fr.fidorial.service.ServiceRegistry;
import fr.fidorial.status.Favicon;
import fr.fidorial.storage.player.PlayerDataStorage;
import fr.fidorial.storage.player.PlayerInventoryStorage;
import fr.fidorial.translation.TranslationStore;
import fr.fidorial.world.World;
import fr.fidorial.world.block.Blocks;
import fr.fidorial.world.fluid.FluidManager;
import fr.fidorial.world.weather.WeatherManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.MINI_MESSAGE;
import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public final class FidorialServer implements Server {

    public static final ComponentLogger LOGGER = getLogger(FidorialServer.class);
    private static final ErrorTracker ERROR_TRACKER = ErrorTracker.contextAware();

    private static @Nullable FidorialServer instance;

    private final ServerConfig config = ServerConfig.load();
    private final AtomicBoolean running = new AtomicBoolean(false);

    private final KeyPair keyPair = EncryptionUtils.generateServerKeyPair();
    private final MojangSessionService sessionService = new MojangSessionService();
    private final VanillaBlockRegistry blockRegistry = bootstrapBlocks();
    private final BlockStateRegistry blockStateRegistry = new BlockStateRegistry(blockRegistry);
    private final EntityIdAllocator entityIds = new EntityIdAllocator();
    private final EntityManager entityManager = new EntityManager();
    private final SimpleEventBus events = new SimpleEventBus();
    private final ServiceRegistry services = new SimpleServiceRegistry();
    private final Set<ClientConnection> connections = ConcurrentHashMap.newKeySet();
    private final BuiltInTranslationStore builtInTranslationStore = new BuiltInTranslationStore();

    private final ProtocolMap protocolMap = ProtocolMap.load();
    private final Registries registries = Registries.load();
    private @Nullable CommandManager commandManager;

    private final ThreadedRegionRegionizer regionizer = new ThreadedRegionRegionizer(config.regionWorkers());
    private final ThreadedChunkWorker chunkWorker = new ThreadedChunkWorker(config.chunkWorkers());
    private final AiWorker aiWorker = new AiWorker(config.aiWorkers());
    private final ScheduledExecutorService autoSave = Executors.newSingleThreadScheduledExecutor(
            r -> Thread.ofPlatform().name("fidorial-autosave").unstarted(r));

    private final NbtPlayerInventoryStorage defaultInventoryStorage =
            new NbtPlayerInventoryStorage(config.worldPath().resolve("player"), false);
    private final NbtPlayerDataStorage defaultPlayerDataStorage =
            new NbtPlayerDataStorage(config.worldPath().resolve("player"), false);
    private final WorldManager worldManager =
            WorldManager.openOrCreate(config.worldPath(), blockStateRegistry, FlatWorld.MIN_Y, FlatWorld.HEIGHT);
    private final FluidEngine fluidEngine =
            new FluidEngine(worldManager, regionizer, blockStateRegistry, this::broadcast);
    private final WeatherEngine weatherEngine = new WeatherEngine(worldManager.levelData(), this::broadcast);
    private final BlockEditService blockEdits = new BlockEditService(
            blockStateRegistry,
            (pos, stateId) -> broadcast(new ClientboundBlockUpdatePacket(pos, stateId)),
            fluidEngine::notifyBlockChanged);
    private final JavaPluginManager pluginManager = new JavaPluginManager(this, events, services, config.pluginsPath());
    private final OperatorList operators = new OperatorList(Path.of("ops.json"));
    private final NettyServer network = new NettyServer(this, config.port());
    private final FidorialContext metrics = new FidorialContext.Factory("6c8c21fe427163e998ea50f54a0ce855")
            .errorTrackerService(ERROR_TRACKER)
            .metrics(Metrics.Factory::create)
            .create();
    private final ConsoleSender console = new ConsoleSender(this);
    private @Nullable Iterable<? extends net.kyori.adventure.audience.Audience> adventure$audiences;

    private @Nullable Favicon favicon = loadFavicon();
    private Component description = MINI_MESSAGE.deserialize(config.motd());
    private int maxPlayers = config.maxPlayers();

    public FidorialServer() throws IOException {
        if (instance != null) {
            throw new IllegalStateException("FidorialServer is already initialized");
        }
        instance = this;
    }

    public static FidorialServer getInstance() {
        return Objects.requireNonNull(instance, "FidorialServer is not initialized");
    }

    private static VanillaBlockRegistry bootstrapBlocks() {
        VanillaBlockRegistry registry = new VanillaBlockRegistry();
        Blocks.bootstrap(registry);
        LOGGER.info(
                "Loaded {} block types from vanilla report", registry.types().size());
        return registry;
    }

    public void start() throws Exception {
        if (!running.compareAndSet(false, true)) {
            return;
        }
        LOGGER.info("Demarrage de Fidorial (Minecraft {} / protocole {})", minecraftVersion(), protocolVersion());
        try {
            metrics.ready();
            loadData();
            openWorlds();
            regionizer.registerTickHandler(new EntityTickHandler(worldManager));
            registerDefaultServices();
            loadPlugins();
            network.bind();
            startAutoSave();
            console.setLocale(Locale.getDefault());
            new ConsoleCommandReader(commandManager, running::get).start();
            pluginManager.enableAll();
            events.post(new ServerStartedEvent(this));
            LOGGER.info("En ecoute sur le port {}", config.port());
        } catch (Exception e) {
            LOGGER.error("Demarrage interrompu, arret en cours", e);
            shutdown();
            throw e;
        }
    }

    @Override
    public void shutdown() {
        if (!running.compareAndSet(true, false)) {
            return;
        }
        LOGGER.info("Arret de Fidorial...");
        events.post(new ServerStoppingEvent(this));
        closeQuietly("plugins", pluginManager::close);
        closeQuietly("reseau", network::shutdown);
        closeQuietly("auto-save", autoSave::shutdownNow);
        closeQuietly("ia", aiWorker::shutdown);
        closeQuietly("regions", regionizer::shutdown);
        closeQuietly("chunks", chunkWorker::shutdown);
        closeQuietly("meteo", weatherEngine::close);
        closeQuietly("monde", worldManager::close);
        closeQuietly("metriques", metrics::shutdown);
        LOGGER.info("Arret termine");
    }

    private @Nullable Favicon loadFavicon() {
        final Path serverIcon = Path.of("server-icon.png");
        if (Files.isRegularFile(serverIcon)) try {
            return Favicon.read(serverIcon);
        } catch (Exception e) {
            LOGGER.warn("Could not load server icon", e);
        }
        return null;
    }

    private void loadData() {
        TranslationStore.setStore(builtInTranslationStore);
        CommandSyntaxException.BUILT_IN_EXCEPTIONS = new TranslatableExceptions();
        ArgumentTypes.bootstrap();
        commandManager = new CommandManager();
        operators.load();
    }

    private void openWorlds() {
        worldManager.setChunkLoader(chunkWorker);
        worldManager.setEntityBridge(entityIds::allocate, new EntitySpawnBridge() {
            @Override
            public void onEntityAppear(AbstractEntity entity) {
                if (entity instanceof Mob && entity.world() instanceof ServerWorld world) {
                    regionizer.addTicket(world.dimension().id(), entity.chunk());
                }
                broadcast(ClientboundAddEntityPacket.of(entity));
            }

            @Override
            public void onEntityDisappear(AbstractEntity entity) {
                if (entity instanceof Mob && entity.world() instanceof ServerWorld world) {
                    regionizer.removeTicket(world.dimension().id(), entity.chunk());
                }
                broadcast(new ClientboundRemoveEntitiesPacket(entity.entityId()));
            }
        });
        worldManager.setDefaultGenerator(new ServiceBackedChunkGenerator(
                services,
                FlatChunkGenerator.cobblestone(WorldConstants.MIN_Y, WorldConstants.HEIGHT),
                WorldConstants.MIN_Y,
                WorldConstants.HEIGHT));
        weatherEngine.start();
    }

    private void registerDefaultServices() {
        services.register(PermissionService.class, new FidorialPermissionService(this), this, ServicePriority.LOWEST);
        services.register(FluidManager.class, fluidEngine, this, ServicePriority.LOWEST);
        services.register(WeatherManager.class, weatherEngine, this, ServicePriority.LOWEST);
        services.register(BlockEditService.class, blockEdits, this, ServicePriority.LOWEST);
        services.register(CommandManager.class, commandManager, this, ServicePriority.LOWEST);
        services.register(PlayerInventoryStorage.class, defaultInventoryStorage, this, ServicePriority.LOWEST);
        services.register(PlayerDataStorage.class, defaultPlayerDataStorage, this, ServicePriority.LOWEST);
    }

    private void loadPlugins() throws IOException {
        DefaultPermissions.registerCorePermissions(pluginManager);
        pluginManager.loadAll();
    }

    private void startAutoSave() {
        autoSave.scheduleAtFixedRate(
                () -> {
                    try {
                        worldManager.saveDirty();
                        int n = worldManager.unloadUnusedChunks();
                        if (n > 0) LOGGER.debug("{} chunks décharges", n);
                    } catch (IOException e) {
                        LOGGER.error("Sauvegarde periodique impossible", e);
                    } catch (Throwable t) {
                        LOGGER.error("Sauvegarde periodique en echec inattendu", t);
                    }
                },
                config.autoSaveSeconds(),
                config.autoSaveSeconds(),
                TimeUnit.SECONDS);
    }

    private void closeQuietly(String what, ThrowingRunnable action) {
        try {
            action.run();
        } catch (Throwable t) {
            LOGGER.error("Arret du sous-systeme '{}' en erreur", what, t);
        }
    }

    private void invalidateAudiences() {
        adventure$audiences = null;
    }

    @Override
    public Iterable<? extends net.kyori.adventure.audience.Audience> audiences() {
        if (this.adventure$audiences == null) {
            this.adventure$audiences = com.google.common.collect.Iterables.concat(
                    java.util.Collections.singleton(console), onlinePlayers());
        }
        return this.adventure$audiences;
    }

    @Override
    public String getName() {
        return "Fidorial";
    }

    @Override
    public String minecraftVersion() {
        return ProtocolConstants.MINECRAFT_VERSION;
    }

    @Override
    public int protocolVersion() {
        return ProtocolConstants.PROTOCOL_VERSION;
    }

    @Override
    public RegionizedScheduler scheduler() {
        return regionizer;
    }

    @Override
    public EventBus events() {
        return events;
    }

    @Override
    public Optional<Favicon> favicon() {
        return Optional.ofNullable(favicon);
    }

    @Override
    public void favicon(final Favicon favicon) {
        this.favicon = favicon;
    }

    @Override
    public Component description() {
        return description;
    }

    @Override
    public void description(final Component description) {
        this.description = description;
    }

    @Override
    public int maxPlayers() {
        return maxPlayers;
    }

    @Override
    public void maxPlayers(final int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    @Override
    public ServiceRegistry services() {
        return services;
    }

    public OperatorList operators() {
        return operators;
    }

    public PluginManager plugins() {
        return pluginManager;
    }

    @Override
    public Collection<? extends World> worlds() {
        return worldManager.worlds();
    }

    @Override
    public Optional<? extends World> world(Key key) {
        return worlds().stream().filter(w -> w.key().equals(key)).findFirst();
    }

    @Override
    public Collection<? extends Player> onlinePlayers() {
        return connections.stream()
                .map(ClientConnection::player)
                .filter(Objects::nonNull)
                .map(p -> (Player) p)
                .toList();
    }

    @Override
    public Optional<? extends Player> player(UUID uuid) {
        return onlinePlayers().stream().filter(p -> p.uuid().equals(uuid)).findFirst();
    }

    @Override
    public Optional<? extends Player> player(String name) {
        return onlinePlayers().stream()
                .filter(p -> p.name().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public boolean isRunning() {
        return running.get();
    }

    public ServerConfig config() {
        return config;
    }

    public KeyPair keyPair() {
        return keyPair;
    }

    public MojangSessionService sessionService() {
        return sessionService;
    }

    public ProtocolMap protocolMap() {
        return protocolMap;
    }

    public Registries registries() {
        return registries;
    }

    public RegistryHolder dynamicRegistries() {
        return registries.dynamic();
    }

    public ThreadedRegionRegionizer regionizer() {
        return regionizer;
    }

    public ThreadedChunkWorker chunkWorker() {
        return chunkWorker;
    }

    public AiWorker aiWorker() {
        return aiWorker;
    }

    public CommandManager commandManager() {
        return commandManager;
    }

    @Override
    public CommandRegistry commands() {
        return commandManager;
    }

    public WorldManager worldManager() {
        return worldManager;
    }

    public PlayerInventoryStorage playerInventoryStorage() {
        return services.find(PlayerInventoryStorage.class).orElse(defaultInventoryStorage);
    }

    public PlayerDataStorage playerDataStorage() {
        return services.find(PlayerDataStorage.class).orElse(defaultPlayerDataStorage);
    }

    public BlockStateRegistry blockStateRegistry() {
        return blockStateRegistry;
    }

    public ConsoleSender getConsole() {
        return console;
    }

    public WeatherEngine weatherEngine() {
        return weatherEngine;
    }

    public BlockEditService blockEdits() {
        return blockEdits;
    }

    public EntityIdAllocator entityIds() {
        return entityIds;
    }

    public EntityManager entityManager() {
        return entityManager;
    }

    public void spawnEntity(AbstractEntity entity) {
        if (!(entity.world() instanceof ServerWorld world)) {
            throw new IllegalArgumentException("Entite sans monde serveur : " + entity);
        }

        world.addEntity(entity);
        entityManager.add(entity);

        if (entity instanceof Mob) {
            regionizer.addTicket(world.dimension().id(), entity.chunk());
        }

        broadcast(ClientboundAddEntityPacket.of(entity));
    }

    public void despawnEntity(AbstractEntity entity) {
        if (entity.world() instanceof ServerWorld world) {
            world.removeEntity(entity);

            if (entity instanceof Mob) {
                regionizer.removeTicket(world.dimension().id(), entity.chunk());
            }
        }

        entityManager.remove(entity);

        entity.remove();

        broadcast(new ClientboundRemoveEntitiesPacket(entity.entityId()));
    }

    public void addPlayerConnection(ClientConnection connection) {
        connections.add(connection);
        invalidateAudiences();
    }

    public void removePlayerConnection(ClientConnection connection) {
        connections.remove(connection);
        invalidateAudiences();
    }

    public void broadcast(ClientboundPacket packet) {
        for (ClientConnection connection : connections) {
            connection.send(packet);
        }
    }

    @Override
    public int playerCount() {
        return connections.size();
    }

    @Override
    public TranslationStore translationStore() {
        return TranslationStore.current();
    }

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws Exception;
    }
}
