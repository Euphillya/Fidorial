package fr.euphyllia.fidorial.server.schedulers;

import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play.ClientboundLightUpdatePacket;
import fr.euphyllia.fidorial.server.world.ChunkNetworkSerializer;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.ChunkPos;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    private final Map<Key, Queue<BlockPos>> pendingBlocks = new ConcurrentHashMap<>();
    private final Set<Key> scheduledBlocks = ConcurrentHashMap.newKeySet();
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
        final long[] impacted = impactArea(x >> 4, z >> 4);
        increment(world, impacted);
        pendingBlocks.computeIfAbsent(world, k -> new ConcurrentLinkedQueue<>()).add(new BlockPos(x, y, z));
        scheduleBlocks(world);
    }

    public void queueChunkLoad(final Key world, final int chunkX, final int chunkZ) {
        final Set<Long> set = pendingChunks.computeIfAbsent(world, k -> ConcurrentHashMap.newKeySet());
        final java.util.List<Long> added = new java.util.ArrayList<>(9);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                final long key = ChunkPos.chunkKey(chunkX + dx, chunkZ + dz);
                if (set.add(key)) {
                    added.add(key);
                }
            }
        }
        if (!added.isEmpty()) {
            increment(world, added.stream().mapToLong(Long::longValue).toArray());
        }
        scheduleChunks(world);
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
                BlockPos drained;
                while ((drained = queue.poll()) != null) {
                    processed++;
                    decrement(world, impactArea(drained.x() >> 4, drained.z() >> 4));
                }
                return;
            }

            final Set<Long> dirtyChunks = new HashSet<>();
            BlockPos pos;
            while ((pos = queue.poll()) != null && processed < 4096) {
                processed++;
                dirtyChunks.addAll(serverWorld.checkBlockLight(pos.x(), pos.y(), pos.z()));
                decrement(world, impactArea(pos.x() >> 4, pos.z() >> 4));
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
                    final Set<Long> dirty = serverWorld.relightChunks(batch);
                    for (final long key : dirty) {
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
                decrement(world, batch.stream().mapToLong(Long::longValue).toArray());
            }
        }
        final Set<Long> stragglers = pendingChunks.get(world);
        if (stragglers != null && !stragglers.isEmpty()) {
            scheduleChunks(world);
        }
    }

    private void scheduleBlocks(final Key world) {
        if (scheduledBlocks.add(world)) worker.execute(() -> drainBlocks(world));
    }

    private void scheduleChunks(final Key world) {
        if (scheduledChunks.add(world)) worker.execute(() -> drainChunks(world));
    }

    private static long[] impactArea(final int chunkX, final int chunkZ) {
        final long[] keys = new long[9];
        int i = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                keys[i++] = ChunkPos.chunkKey(chunkX + dx, chunkZ + dz);
            }
        }
        return keys;
    }

    private WorldLightState stateFor(final Key world) {
        return states.computeIfAbsent(world, _ -> new WorldLightState());
    }

    private void increment(final Key world, final long[] chunkKeys) {
        final WorldLightState state = stateFor(world);
        synchronized (state) {
            for (final long key : chunkKeys) {
                state.refCounts.addTo(key, 1);
            }
        }
    }

    private void decrement(final Key world, final long[] chunkKeys) {
        final WorldLightState state = states.get(world);
        if (state == null) return;
        synchronized (state) {
            for (final long key : chunkKeys) {
                final int count = state.refCounts.get(key);
                if (count == 0) continue;
                if (count <= 1) {
                    state.refCounts.remove(key);
                    final CompletableFuture<Void> waiter = state.waiters.remove(key);
                    if (waiter != null) waiter.complete(null);
                } else {
                    state.refCounts.put(key, count - 1);
                }
            }
        }
    }

    public CompletableFuture<Void> flush(final Key world, final Iterable<Long> chunkKeys) {
        final WorldLightState state = states.get(world);
        if (state == null) {
            return CompletableFuture.completedFuture(null);
        }
        final List<CompletableFuture<Void>> futures = new ArrayList<>();
        synchronized (state) {
            for (final long key : chunkKeys) {
                if (state.refCounts.containsKey(key)) {
                    futures.add(state.waiters.computeIfAbsent(key, k -> new CompletableFuture<>()));
                }
            }
        }
        return futures.isEmpty()
                ? CompletableFuture.completedFuture(null)
                : CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    private static final class WorldLightState {
        private final Long2IntOpenHashMap refCounts = new Long2IntOpenHashMap();
        private final Long2ObjectOpenHashMap<CompletableFuture<Void>> waiters = new Long2ObjectOpenHashMap<>();

        WorldLightState() {
            refCounts.defaultReturnValue(0);
        }
    }
}
