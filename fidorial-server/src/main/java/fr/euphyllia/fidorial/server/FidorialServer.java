package fr.euphyllia.fidorial.server;

import fr.euphyllia.fidorial.api.Server;
import fr.euphyllia.fidorial.api.scheduler.RegionizedScheduler;
import fr.euphyllia.fidorial.auth.EncryptionUtils;
import fr.euphyllia.fidorial.auth.MojangSessionService;
import fr.euphyllia.fidorial.server.command.CommandManager;
import fr.euphyllia.fidorial.server.network.NettyServer;
import fr.euphyllia.fidorial.server.protocol.ProtocolConstants;
import fr.euphyllia.fidorial.server.protocol.ProtocolMap;
import fr.euphyllia.fidorial.server.registry.Registries;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import fr.euphyllia.fidorial.server.region.ThreadedRegionizer;
import fr.euphyllia.fidorial.server.world.FlatWorld;
import fr.euphyllia.fidorial.server.world.WorldManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.KeyPair;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class FidorialServer implements Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(FidorialServer.class);

    private final int port;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final KeyPair keyPair = EncryptionUtils.generateServerKeyPair();
    private final MojangSessionService sessionService = new MojangSessionService();
    private final ProtocolMap protocolMap = ProtocolMap.load();
    private final Registries registries = Registries.load();
    private final CommandManager commandManager = new CommandManager();
    private final ThreadedRegionizer regionizer = new ThreadedRegionizer(
            Math.max(2, Runtime.getRuntime().availableProcessors() / 2));
    private final ScheduledExecutorService saveWorldScheduler = Executors.newScheduledThreadPool(1);
    private NettyServer network;
    private WorldManager worldManager;

    public FidorialServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        if (!running.compareAndSet(false, true)) return;
        LOGGER.info("Demarrage de Fidorial (Minecraft {} / protocole {})",
                minecraftVersion(), protocolVersion());
        this.worldManager = WorldManager.openOrCreate(
                java.nio.file.Path.of("world"), FlatWorld.MIN_Y, FlatWorld.HEIGHT);
        this.network = new NettyServer(this, port);
        this.network.bind();
        LOGGER.info("En ecoute sur le port {}", port);

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
        if (network != null) network.shutdown();
        if (worldManager != null) {
            try {
                worldManager.close();
            } catch (Exception e) {
                LOGGER.error("Sauvegarde du monde", e);
            }
        }
        regionizer.shutdown();
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

    public RegistryHolder registrySnapshot() {
        return registries.snapshot();
    }

    public RegistryHolder dynamicRegistries() {
        return registries.dynamic();
    }

    public ThreadedRegionizer regionizer() {
        return regionizer;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public WorldManager worldManager() {
        return worldManager;
    }
}