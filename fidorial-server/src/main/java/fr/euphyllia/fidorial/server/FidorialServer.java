package fr.euphyllia.fidorial.server;

import fr.euphyllia.fidorial.api.Server;
import fr.euphyllia.fidorial.api.scheduler.RegionizedScheduler;
import fr.euphyllia.fidorial.auth.EncryptionUtils;
import fr.euphyllia.fidorial.auth.MojangSessionService;
import fr.euphyllia.fidorial.server.network.NettyServer;
import fr.euphyllia.fidorial.server.protocol.ProtocolConstants;
import fr.euphyllia.fidorial.server.protocol.ProtocolMap;
import fr.euphyllia.fidorial.server.protocol.RegistrySnapshot;
import fr.euphyllia.fidorial.server.region.ThreadedRegionizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;
import java.util.concurrent.atomic.AtomicBoolean;

public final class FidorialServer implements Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(FidorialServer.class);

    private final int port;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final KeyPair keyPair = EncryptionUtils.generateServerKeyPair();
    private final MojangSessionService sessionService = new MojangSessionService();
    private final ProtocolMap protocolMap = ProtocolMap.load();
    private final RegistrySnapshot registrySnapshot = RegistrySnapshot.load();
    private final ThreadedRegionizer regionizer = new ThreadedRegionizer(
            Math.max(2, Runtime.getRuntime().availableProcessors() / 2));
    private NettyServer network;

    public FidorialServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        if (!running.compareAndSet(false, true)) return;
        LOGGER.info("Demarrage de Fidorial (Minecraft {} / protocole {})",
                minecraftVersion(), protocolVersion());
        this.network = new NettyServer(this, port);
        this.network.bind();
        LOGGER.info("En ecoute sur le port {}", port);
    }

    @Override
    public void shutdown() {
        if (!running.compareAndSet(true, false)) return;
        LOGGER.info("Arret de Fidorial...");
        if (network != null) network.shutdown();
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

    public RegistrySnapshot registrySnapshot() {
        return registrySnapshot;
    }

    public ThreadedRegionizer regionizer() {
        return regionizer;
    }
}
