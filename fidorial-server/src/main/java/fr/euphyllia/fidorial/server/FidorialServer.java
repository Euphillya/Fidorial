package fr.euphyllia.fidorial.server;

import dev.faststats.ErrorTracker;
import dev.faststats.Metrics;
import fr.euphyllia.fidorial.api.Server;
import fr.euphyllia.fidorial.api.scheduler.RegionizedScheduler;
import fr.euphyllia.fidorial.api.world.fluid.FluidManager;
import fr.euphyllia.fidorial.auth.EncryptionUtils;
import fr.euphyllia.fidorial.auth.MojangSessionService;
import fr.euphyllia.fidorial.server.command.CommandManager;
import fr.euphyllia.fidorial.server.command.ConsoleCommandReader;
import fr.euphyllia.fidorial.server.entity.player.PlayerInventoryStorage;
import fr.euphyllia.fidorial.server.metrics.FidorialContext;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.network.NettyServer;
import fr.euphyllia.fidorial.server.protocol.ProtocolConstants;
import fr.euphyllia.fidorial.server.protocol.ProtocolMap;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.registry.Registries;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import fr.euphyllia.fidorial.server.schedulers.ThreadedChunkWorker;
import fr.euphyllia.fidorial.server.schedulers.ThreadedRegionRegionizer;
import fr.euphyllia.fidorial.server.world.BlockStateRegistry;
import fr.euphyllia.fidorial.server.world.FlatWorld;
import fr.euphyllia.fidorial.server.world.WorldManager;
import fr.euphyllia.fidorial.server.world.fluid.FluidEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.security.KeyPair;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class FidorialServer implements Server {

    public static final Logger LOGGER = LoggerFactory.getLogger(FidorialServer.class);
    public static final ErrorTracker ERROR_TRACKER = ErrorTracker.contextAware();
    private static FidorialServer INSTANCE;
    private final int port;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final KeyPair keyPair = EncryptionUtils.generateServerKeyPair();
    private final MojangSessionService sessionService = new MojangSessionService();
    private final ProtocolMap protocolMap = ProtocolMap.load();
    private final Registries registries = Registries.load();
    private final CommandManager commandManager = new CommandManager();
    private final ThreadedRegionRegionizer regionizer = new ThreadedRegionRegionizer(
            Math.max(2, Runtime.getRuntime().availableProcessors() / 2)
    );
    private final ScheduledExecutorService saveWorldScheduler = Executors.newScheduledThreadPool(1);
    private final PlayerInventoryStorage playerInventoryStorage =
            new PlayerInventoryStorage(Path.of("world/player"), false);
    private final Set<ClientConnection> playerConnections = ConcurrentHashMap.newKeySet();
    private final ThreadedChunkWorker chunkWorker = new ThreadedChunkWorker(
            Math.max(2, Runtime.getRuntime().availableProcessors() / 2)
    );
    private final String tokenFast = "6c8c21fe427163e998ea50f54a0ce855";
    private NettyServer network;
    private WorldManager worldManager;
    private FidorialContext metricsContext;
    private FluidEngine fluidEngine;

    public FidorialServer(int port) {
        this.port = port;
        INSTANCE = this;
    }

    public static FidorialServer getInstance() {
        return INSTANCE;
    }

    public void start() throws Exception {
        if (!running.compareAndSet(false, true)) return;
        LOGGER.info("Demarrage de Fidorial (Minecraft {} / protocole {})",
                minecraftVersion(), protocolVersion());
        metricsContext = new FidorialContext.Factory(tokenFast)
                .errorTrackerService(ERROR_TRACKER)
                .metrics(Metrics.Factory::create)
                .create();
        this.worldManager = WorldManager.openOrCreate(Path.of("world"), FlatWorld.MIN_Y, FlatWorld.HEIGHT);
        this.fluidEngine = new FluidEngine(worldManager, regionizer,
                new BlockStateRegistry(), this::broadcast);
        this.network = new NettyServer(this, port);
        this.network.bind();
        LOGGER.info("En ecoute sur le port {}", port);
        new ConsoleCommandReader(commandManager, running::get).start();
        metricsContext.ready();
        saveWorldScheduler.scheduleAtFixedRate(() -> {
            try {
                worldManager.saveDirty();
            } catch (IOException exception) {
                LOGGER.error(exception.getMessage());
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    @Override
    public void shutdown() {
        if (!running.compareAndSet(true, false)) return;
        LOGGER.info("Arret de Fidorial...");
        if (metricsContext != null) {
            metricsContext.shutdown();
        }
        if (network != null) network.shutdown();
        if (worldManager != null) {
            try {
                worldManager.close();
            } catch (Exception e) {
                LOGGER.error("Sauvegarde du monde", e);
            }
        }
        regionizer.shutdown();
        chunkWorker.shutdown();
    }

    public ThreadedChunkWorker getThreadedChunkWorker() {
        return chunkWorker;
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
    public boolean isRunning() {
        return running.get();
    }

    @Override
    public FluidManager fluids() {
        return fluidEngine;
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

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public WorldManager worldManager() {
        return worldManager;
    }

    public PlayerInventoryStorage playerInventoryStorage() {
        return playerInventoryStorage;
    }

    public void addPlayerConnection(ClientConnection connection) {
        playerConnections.add(connection);
    }

    public void removePlayerConnection(ClientConnection connection) {
        playerConnections.remove(connection);
    }

    public void broadcast(ClientboundPacket packet) {
        for (ClientConnection connection : playerConnections) {
            connection.send(packet);
        }
    }

    public int getPlayerCount() {
        return playerConnections.size();
    }
}