package fr.euphyllia.fidorial.server.schedulers;

import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundLightUpdatePacket;
import fr.euphyllia.fidorial.server.world.ChunkNetworkSerializer;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.fidorial.world.ChunkPos;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;

public class LightUpdateDispatcher {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(LightUpdateDispatcher.class);

    private final Executor worker;
    private final Consumer<ClientboundPacket> broadcaster;
    private final ChunkNetworkSerializer serializer;
    private final Function<Key, @Nullable ServerWorld> worldLookup;

    private final Map<Key, Set<Long>> pending = new ConcurrentHashMap<>();
    private final Set<Key> scheduled = ConcurrentHashMap.newKeySet();

    public LightUpdateDispatcher(
            final Executor worker,
            final Consumer<ClientboundPacket> broadcaster,
            final ChunkNetworkSerializer serializer,
            final Function<Key, @Nullable ServerWorld> worldLookup
    ) {
        this.worker = worker;
        this.broadcaster = broadcaster;
        this.serializer = serializer;
        this.worldLookup = worldLookup;
    }

    public void queueBlockChange(final Key world, final int x, final int y, final int z) {
        enqueueArea(world, x >> 4, z >> 4);
    }

    public void queueChunkLoad(final Key world, final int chunkX, final int chunkZ) {
        enqueueArea(world, chunkX, chunkZ);
    }

    private void enqueueArea(final Key world, final int centerChunkX, final int centerChunkZ) {
        final Set<Long> set = pending.computeIfAbsent(world, k -> ConcurrentHashMap.newKeySet());
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                set.add(ChunkPos.chunkKey(centerChunkX + dx, centerChunkZ + dz));
            }
        }
        schedule(world);
    }

    private void schedule(final Key world) {
        if (scheduled.add(world)) {
            worker.execute(() -> drain(world));
        }
    }

    private void drain(final Key world) {
        try {
            final Set<Long> batch = pending.remove(world);
            if (batch != null && !batch.isEmpty()) {
                final ServerWorld serverWorld = worldLookup.apply(world);
                if (serverWorld != null) {
                    final Set<Long> relit = serverWorld.relightChunks(batch);
                    for (final long key : relit) {
                        final ChunkColumn column = serverWorld.loadedColumn((int) (key >> 32), (int) key);
                        if (column != null) {
                            broadcaster.accept(new ClientboundLightUpdatePacket(serializer, column));
                        }
                    }
                }
            }
        } catch (final Throwable t) {
            LOGGER.error("Lighting recalculation impossible for world {}", world, t);
        } finally {
            scheduled.remove(world);
        }

        final Set<Long> stragglers = pending.get(world);
        if (stragglers != null && !stragglers.isEmpty()) {
            schedule(world);
        }
    }
}
