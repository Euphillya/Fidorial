package fr.euphyllia.fidorial.server.schedulers;

import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play.ClientboundLightUpdatePacket;
import fr.euphyllia.fidorial.server.world.ChunkNetworkSerializer;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.ChunkPos;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;

public class LightUpdateDispatcher {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(LightUpdateDispatcher.class);

    private final Executor worker;
    private final Consumer<ClientboundPacket> broadcaster;
    private final ChunkNetworkSerializer serializer;
    private final Function<Key, @Nullable ServerWorld> worldLookup;

    private final Map<Key, WorldLightState> states = new ConcurrentHashMap<>();

    // incremental
    private final Map<Key, Queue<BlockPos>> pendingBlocks = new ConcurrentHashMap<>();
    private final Set<Key> scheduledBlocks = ConcurrentHashMap.newKeySet();
    // full
    private final Map<Key, Set<Long>> pendingChunks = new ConcurrentHashMap<>();
    private final Set<Key> scheduledChunks = ConcurrentHashMap.newKeySet();

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
        increment(world, 1);
        pendingBlocks.computeIfAbsent(world, k -> new ConcurrentLinkedQueue<>()).add(new BlockPos(x, y, z));
        scheduleBlocks(world);
    }

    public void queueChunkLoad(final Key world, final int chunkX, final int chunkZ) {
        final Set<Long> set = pendingChunks.computeIfAbsent(world, k -> ConcurrentHashMap.newKeySet());
        int added = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (set.add(ChunkPos.chunkKey(chunkX + dx, chunkZ + dz))) {
                    added++;
                }
            }
        }
        if (added > 0) {
            increment(world, added);
        }
        scheduleChunks(world);
    }

    private void scheduleBlocks(final Key world) {
        if (scheduledBlocks.add(world)) {
            worker.execute(() -> drainBlocks(world));
        }
    }

    private void scheduleChunks(final Key world) {
        if (scheduledChunks.add(world)) {
            worker.execute(() -> drainChunks(world));
        }
    }

    private void drainBlocks(final Key world) {
        int processed = 0;
        try {
            final Queue<BlockPos> queue = pendingBlocks.get(world);
            if (queue == null || queue.isEmpty()) {
                return;
            }
            final ServerWorld serverWorld = worldLookup.apply(world);
            if (serverWorld == null) {
                while (queue.poll() != null) {
                    processed++;
                }
                return;
            }

            final Set<Long> dirtyChunks = new HashSet<>();
            BlockPos pos;
            while ((pos = queue.poll()) != null && processed < 4096) {
                processed++;
                dirtyChunks.addAll(serverWorld.checkBlockLight(pos.x(), pos.y(), pos.z()));
            }

            for (final long key : dirtyChunks) {
                final ChunkColumn column = serverWorld.loadedColumn((int) (key >> 32), (int) key);
                if (column != null) {
                    broadcaster.accept(new ClientboundLightUpdatePacket(serializer, column));
                }
            }
        } catch (final Throwable t) {
            LOGGER.error("Lighting recalculation impossible for world {}", world, t);
        } finally {
            scheduledBlocks.remove(world);
            if (processed > 0) {
                decrement(world, processed);
            }
        }
        final Queue<BlockPos> stragglers = pendingBlocks.get(world);
        if (stragglers != null && !stragglers.isEmpty()) {
            scheduleBlocks(world);
        }
    }

    private void drainChunks(final Key world) {
        Set<Long> batch = null;
        try {
            batch = pendingChunks.remove(world);
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
            scheduledChunks.remove(world);
            if (batch != null && !batch.isEmpty()) {
                decrement(world, batch.size());
            }
        }

        final Set<Long> stragglers = pendingChunks.get(world);
        if (stragglers != null && !stragglers.isEmpty()) {
            scheduleChunks(world);
        }
    }

    private WorldLightState stateFor(final Key world) {
        return states.computeIfAbsent(world, k -> new WorldLightState());
    }

    private void increment(final Key world, final int n) {
        final WorldLightState state = stateFor(world);
        synchronized (state) {
            if (state.pending == 0) {
                state.idle = new CompletableFuture<>();
            }
            state.pending += n;
        }
    }

    private void decrement(final Key world, final int n) {
        final WorldLightState state = states.get(world);
        if (state == null) {
            return;
        }
        synchronized (state) {
            state.pending -= n;
            if (state.pending <= 0) {
                if (state.pending < 0) {
                    LOGGER.warn("Light pending count went negative for world {}, resetting", world);
                    state.pending = 0;
                }
                state.idle.complete(null);
            }
        }
    }

    public CompletableFuture<Void> flush(final Key world) {
        final WorldLightState state = states.get(world);
        if (state == null) {
            return CompletableFuture.completedFuture(null);
        }
        synchronized (state) {
            return state.idle;
        }
    }

    private static final class WorldLightState {
        private int pending;
        private CompletableFuture<Void> idle = CompletableFuture.completedFuture(null);
    }
}
