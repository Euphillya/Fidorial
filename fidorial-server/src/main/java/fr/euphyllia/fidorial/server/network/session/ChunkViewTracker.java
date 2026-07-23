package fr.euphyllia.fidorial.server.network.session;

import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundForgetLevelChunkPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundLevelChunkWithLightPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSetChunkCacheCenterPacket;
import fr.euphyllia.fidorial.server.schedulers.ThreadedChunkWorker;
import fr.euphyllia.fidorial.server.world.ChunkNetworkSerializer;
import fr.euphyllia.fidorial.server.world.ChunkViewSource;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.fidorial.world.ChunkPos;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.LongConsumer;

public final class ChunkViewTracker implements ChunkViewSource {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(ChunkViewTracker.class);

    private final ClientConnection connection;
    private final ThreadedChunkWorker chunkWorker;
    private final ServerWorld world;
    private final ChunkNetworkSerializer serializer;
    private final int radius;

    private final Object lock = new Object();
    private final Set<Long> sent = new HashSet<>();
    private final Set<Long> pending = new HashSet<>();

    private int centerX;
    private int centerZ;

    public ChunkViewTracker(
            ClientConnection connection,
            ThreadedChunkWorker chunkWorker,
            ServerWorld world,
            ChunkNetworkSerializer serializer,
            int radius
    ) {
        this.connection = connection;
        this.chunkWorker = chunkWorker;
        this.world = world;
        this.serializer = serializer;
        this.radius = radius;
    }

    private static long key(int chunkX, int chunkZ) {
        return ((long) chunkZ << 32) | (chunkX & 0xFFFFFFFFL);
    }

    private static void emit(Set<Long> source, LongConsumer keys) {
        for (long key : source) {
            int cx = (int) key;
            int cz = (int) (key >> 32);
            keys.accept(ServerWorld.chunkKey(cx, cz));
        }
    }

    public void init(ChunkPos center) {
        synchronized (lock) {
            centerX = center.x();
            centerZ = center.z();
        }
        connection.send(new ClientboundSetChunkCacheCenterPacket(center.x(), center.z()));
        stream(center.x(), center.z());
    }

    public boolean moveTo(int chunkX, int chunkZ) {
        synchronized (lock) {
            if (chunkX == centerX && chunkZ == centerZ) {
                return false;
            }
            centerX = chunkX;
            centerZ = chunkZ;
        }
        connection.send(new ClientboundSetChunkCacheCenterPacket(chunkX, chunkZ));
        stream(chunkX, chunkZ);
        return true;
    }

    private void stream(int centerX, int centerZ) {
        forgetOutOfRange(centerX, centerZ);
        requestInRange(centerX, centerZ);
    }

    private void forgetOutOfRange(int centerX, int centerZ) {
        synchronized (lock) {
            Iterator<Long> it = sent.iterator();
            while (it.hasNext()) {
                long key = it.next();
                int cx = (int) key;
                int cz = (int) (key >> 32);
                if (!inRange(cx, cz, centerX, centerZ)) {
                    connection.send(new ClientboundForgetLevelChunkPacket(cx, cz));
                    it.remove();
                }
            }
        }
    }

    private void requestInRange(int centerX, int centerZ) {
        for (int r = 0; r <= radius; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != r) {
                        continue;
                    }
                    requestChunk(centerX + dx, centerZ + dz);
                }
            }
        }
    }

    private void requestChunk(int cx, int cz) {
        long key = key(cx, cz);
        synchronized (lock) {
            if (sent.contains(key) || !pending.add(key)) {
                return;
            }
        }
        chunkWorker.loadAsync(world, cx, cz).whenComplete((column, error) -> onLoaded(cx, cz, column, error));
    }

    private void onLoaded(int cx, int cz, ChunkColumn column, @Nullable Throwable error) {
        long key = key(cx, cz);
        synchronized (lock) {
            pending.remove(key);
            if (error != null) {
                LOGGER.error("Chargement du chunk {},{} impossible pour {}", cx, cz, connection.username(), error);
                return;
            }

            if (!inRange(cx, cz, centerX, centerZ) || !sent.add(key)) {
                return;
            }
        }
        connection.send(new ClientboundLevelChunkWithLightPacket(serializer, column));
    }

    private boolean inRange(int cx, int cz, int centerX, int centerZ) {
        return Math.abs(cx - centerX) <= radius && Math.abs(cz - centerZ) <= radius;
    }

    public ChunkPos center() {
        synchronized (lock) {
            return new ChunkPos(centerX, centerZ);
        }
    }

    public ServerWorld world() {
        return world;
    }

    @Override
    public void collectViewedChunks(LongConsumer keys) {
        synchronized (lock) {
            emit(sent, keys);
            emit(pending, keys);
        }
    }

    public int sentCount() {
        synchronized (lock) {
            return sent.size();
        }
    }
}
