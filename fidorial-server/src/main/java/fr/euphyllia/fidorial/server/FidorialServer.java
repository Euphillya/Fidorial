package fr.euphyllia.fidorial.server;

import dev.faststats.ErrorTracker;
import dev.faststats.Metrics;
import fr.euphyllia.fidorial.server.entity.EntityManager;
import fr.euphyllia.fidorial.server.schedulers.AiWorker;
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
import fr.fidorial.storage.player.PlayerDataStorage;
import fr.fidorial.storage.player.PlayerInventoryStorage;
import fr.fidorial.translation.TranslationStore;
import fr.fidorial.world.World;
import fr.fidorial.world.block.Blocks;
import fr.fidorial.world.fluid.FluidManager;
import fr.fidorial.world.weather.WeatherManager;
import fr.euphyllia.fidorial.auth.EncryptionUtils;
import fr.euphyllia.fidorial.auth.MojangSessionService;
import fr.euphyllia.fidorial.server.command.CommandManager;
import fr.euphyllia.fidorial.server.command.ConsoleCommandReader;
import fr.euphyllia.fidorial.server.command.ConsoleSender;
import fr.euphyllia.fidorial.server.entity.AbstractEntity;
import fr.euphyllia.fidorial.server.entity.EntityIdAllocator;
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
import fr.euphyllia.fidorial.server.schedulers.ThreadedChunkWorker;
import fr.euphyllia.fidorial.server.schedulers.ThreadedRegionRegionizer;
import fr.euphyllia.fidorial.server.service.SimpleServiceRegistry;
import fr.euphyllia.fidorial.server.translation.BuiltInTranslationStore;
import fr.euphyllia.fidorial.server.world.*;
import fr.euphyllia.fidorial.server.world.block.VanillaBlockRegistry;
import fr.euphyllia.fidorial.server.world.fluid.FluidEngine;
import fr.euphyllia.fidorial.server.world.weather.WeatherEngine;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.io.IOException;
import java.nio.file.Path;
import java.security.KeyPair;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public final class FidorialServer implements Server {

    public static final ComponentLogger LOGGER = getLogger(FidorialServer.class);
    private static final ErrorTracker ERROR_TRACKER = ErrorTracker.contextAware();

    private static volatile FidorialServer instance;

    private final ServerConfig config;
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

    private ProtocolMap protocolMap;
    private Registries registries;
    private CommandManager commandManager;

    private ThreadedRegionRegionizer regionizer;
    private ThreadedChunkWorker chunkWorker;
    private AiWorker aiWorker;
    private ScheduledExecutorService autoSave;

    private NbtPlayerInventoryStorage defaultInventoryStorage;
    private NbtPlayerDataStorage defaultPlayerDataStorage;
    private WorldManager worldManager;
    private FluidEngine fluidEngine;
    private WeatherEngine weatherEngine;
    private BlockEditService blockEdits;
    private JavaPluginManager pluginManager;
    private OperatorList operators;
    private NettyServer network;
    private FidorialContext metrics;
    private ConsoleSender console;
    private Iterable<? extends net.kyori.adventure.audience.Audience> adventure$audiences;

    public FidorialServer(ServerConfig config) {
        this.config = config;
    }

    public static FidorialServer getInstance() {
        return instance;
    }

    private static VanillaBlockRegistry bootstrapBlocks() {
        VanillaBlockRegistry registry = new VanillaBlockRegistry();
        Blocks.bootstrap(registry);
        LOGGER.info("Loaded {} block types from vanilla report", registry.types().size());
        return registry;
    }

    public void start() throws Exception {
        if (!running.compareAndSet(false, true)) {
            return;
        }
        instance = this;
        LOGGER.info("Demarrage de Fidorial (Minecraft {} / protocole {})",
                minecraftVersion(), protocolVersion());
        try {
            startMetrics();
            loadData();
            startSchedulers();
            openWorlds();
            regionizer.registerTickHandler(new EntityTickHandler(worldManager));
            registerDefaultServices();
            loadPlugins();
            openNetwork();
            startAutoSave();
            console = ConsoleSender.INSTANCE;
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
        closeQuietly("plugins", () -> {
            if (pluginManager != null) pluginManager.close();
        });
        closeQuietly("reseau", () -> {
            if (network != null) network.shutdown();
        });
        closeQuietly("auto-save", () -> {
            if (autoSave != null) autoSave.shutdownNow();
        });
        closeQuietly("ia", () -> {
            if (aiWorker != null) aiWorker.shutdown();
        });
        closeQuietly("regions", () -> {
            if (regionizer != null) regionizer.shutdown();
        });
        closeQuietly("chunks", () -> {
            if (chunkWorker != null) chunkWorker.shutdown();
        });
        closeQuietly("meteo", () -> {
            if (weatherEngine != null) weatherEngine.close();
        });
        closeQuietly("monde", () -> {
            if (worldManager != null) worldManager.close();
        });
        closeQuietly("metriques", () -> {
            if (metrics != null) metrics.shutdown();
        });
        LOGGER.info("Arret termine");
        System.exit(0);
    }

    private void startMetrics() {
        metrics = new FidorialContext.Factory("6c8c21fe427163e998ea50f54a0ce855")
                .errorTrackerService(ERROR_TRACKER)
                .metrics(Metrics.Factory::create)
                .create();
        metrics.ready();
    }

    private void loadData() {
        protocolMap = ProtocolMap.load();
        registries = Registries.load();
        TranslationStore.setStore(builtInTranslationStore);
        commandManager = new CommandManager();
        operators = new OperatorList(Path.of("ops.json"));
        operators.load();
        defaultInventoryStorage = new NbtPlayerInventoryStorage(config.worldPath().resolve("player"), false);
        defaultPlayerDataStorage = new NbtPlayerDataStorage(config.worldPath().resolve("player"), false);
    }

    private void startSchedulers() {
        regionizer = new ThreadedRegionRegionizer(config.regionWorkers());
        chunkWorker = new ThreadedChunkWorker(config.chunkWorkers());
        aiWorker = new AiWorker(config.aiWorkers());
    }

    private void openWorlds() throws IOException {
        worldManager = WorldManager.openOrCreate(config.worldPath(), blockStateRegistry,
                FlatWorld.MIN_Y, FlatWorld.HEIGHT);
        worldManager.setChunkLoader(chunkWorker);
        worldManager.setDefaultGenerator(new ServiceBackedChunkGenerator(services, FlatChunkGenerator.cobblestone(
                WorldConstants.MIN_Y, WorldConstants.HEIGHT),
                WorldConstants.MIN_Y, WorldConstants.HEIGHT));
        fluidEngine = new FluidEngine(worldManager, regionizer, blockStateRegistry, this::broadcast);
        weatherEngine = new WeatherEngine(worldManager.levelData(), this::broadcast);
        weatherEngine.start();
        blockEdits = new BlockEditService(blockStateRegistry,
                (pos, stateId) -> broadcast(new ClientboundBlockUpdatePacket(pos, stateId)),
                fluidEngine::notifyBlockChanged);
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
        pluginManager = new JavaPluginManager(this, events, services, config.pluginsPath());
        DefaultPermissions.registerCorePermissions(pluginManager);
        pluginManager.loadAll();
    }

    private void openNetwork() throws InterruptedException {
        network = new NettyServer(this, config.port());
        network.bind();
    }

    private void startAutoSave() {
        autoSave = Executors.newSingleThreadScheduledExecutor(
                r -> Thread.ofPlatform().name("fidorial-autosave").unstarted(r));
        autoSave.scheduleAtFixedRate(() -> {
            try {
                worldManager.saveDirty();
                int n = worldManager.unloadUnusedChunks();
                if (n > 0) LOGGER.debug("{} chunks décharges", n);
            } catch (IOException e) {
                LOGGER.error("Sauvegarde periodique impossible", e);
            } catch (Throwable t) {
                LOGGER.error("Sauvegarde periodique en echec inattendu", t);
            }
        }, config.autoSaveSeconds(), config.autoSaveSeconds(), TimeUnit.SECONDS);
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
            this.adventure$audiences = com.google.common.collect.Iterables.concat(java.util.Collections.singleton(console), onlinePlayers());
        }
        return this.adventure$audiences;
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
        return worldManager == null ? List.of() : worldManager.worlds();
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
        return onlinePlayers().stream().filter(p -> p.name().equalsIgnoreCase(name)).findFirst();
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
            regionizer.addTicket(
                    world.dimension().id(),
                    entity.chunk()
            );
        }

        broadcast(ClientboundAddEntityPacket.of(entity));
    }

    public void despawnEntity(AbstractEntity entity) {
        if (entity.world() instanceof ServerWorld world) {
            world.removeEntity(entity);

            if (entity instanceof Mob) {
                regionizer.removeTicket(
                        world.dimension().id(),
                        entity.chunk()
                );
            }
        }

        entityManager.remove(entity);

        entity.remove();

        broadcast(
                new ClientboundRemoveEntitiesPacket(
                        entity.entityId()
                )
        );
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
