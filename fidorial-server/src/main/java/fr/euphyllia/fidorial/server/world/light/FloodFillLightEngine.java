package fr.euphyllia.fidorial.server.world.light;

import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.fidorial.world.ChunkPos;
import fr.fidorial.world.light.LightType;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class FloodFillLightEngine implements LightEngine {

    private static final int MAX_LEVEL = 15;

    // -X, +X, -Z, +Z, -Y, +Y.
    private static final int[] DX = {-1, 1, 0, 0, 0, 0};
    private static final int[] DZ = {0, 0, -1, 1, 0, 0};
    private static final int[] DY = {0, 0, 0, 0, -1, 1};

    private final int minY;
    private final int maxY;

    public FloodFillLightEngine(final int minY, final int height) {
        this.minY = minY;
        this.maxY = minY + height;
    }

    private long packPos(final int x, final int y, final int z) {
        final long yOff = (y - minY) & 0xFFFL;
        return ((long) (x & 0x3FFFFFF) << 38) | ((long) (z & 0x3FFFFFF) << 12) | yOff;
    }

    private int unpackX(final long v) {
        final int x = (int) ((v >> 38) & 0x3FFFFFFL);
        return (x << 6) >> 6;
    }

    private int unpackZ(final long v) {
        final int z = (int) ((v >> 12) & 0x3FFFFFFL);
        return (z << 6) >> 6;
    }

    private int unpackY(final long v) {
        return (int) (v & 0xFFFL) + minY;
    }

    @Override
    public Set<Long> checkBlock(final int x, final int y, final int z, final LightAccess access) {
        final Set<Long> dirtyChunks = new HashSet<>();
        final int[] skyRange = updateHeightmap(access, x, y, z, dirtyChunks);
        for (final LightType type : LightType.values()) {
            checkBlockForType(type, x, y, z, access, dirtyChunks);
        }
        if (skyRange != null) {
            for (int cy = skyRange[0]; cy <= skyRange[1]; cy++) {
                if (cy == y) {
                    continue; // already handled above
                }
                checkBlockForType(LightType.SKY, x, cy, z, access, dirtyChunks);
            }
        }
        return dirtyChunks;
    }

    private int @Nullable [] updateHeightmap(final LightAccess access, final int x, final int y, final int z, final Set<Long> dirtyChunks) {
        final ChunkLightData data = access.lightAt(x >> 4, z >> 4);
        if (data == null) {
            return null;
        }
        final int lx = x & 15;
        final int lz = z & 15;
        final int top = data.topOpaqueY(lx, lz);
        final boolean occludes = BlockLightProperties.occludes(access.blockAt(x, y, z));

        if (occludes) {
            if (y > top) {
                data.setTopOpaqueY(lx, lz, y);
                dirtyChunks.add(ChunkPos.chunkKey(x >> 4, z >> 4));
                return top + 1 <= y - 1 ? new int[]{top + 1, y - 1} : null;
            }
        } else if (y == top) {
            int newTop = minY - 1;
            for (int cy = y - 1; cy >= minY; cy--) {
                if (BlockLightProperties.occludes(access.blockAt(x, cy, z))) {
                    newTop = cy;
                    break;
                }
            }
            data.setTopOpaqueY(lx, lz, newTop);
            dirtyChunks.add(ChunkPos.chunkKey(x >> 4, z >> 4));
            return newTop + 1 <= top ? new int[]{newTop + 1, top} : null;
        }
        return null;
    }

    private void checkBlockForType(
            final LightType type, final int x, final int y, final int z,
            final LightAccess access, final Set<Long> dirtyChunks) {
        final ChunkLightData data = access.lightAt(x >> 4, z >> 4);
        if (data == null) {
            return;
        }

        final LongIntQueue decrease = new LongIntQueue();
        final LongIntQueue increase = new LongIntQueue();

        final int oldLevel = data.get(type, x, y, z);
        if (oldLevel > 0) {
            data.set(type, x, y, z, 0);
            dirtyChunks.add(ChunkPos.chunkKey(x >> 4, z >> 4));
            decrease.push(x, y, z, oldLevel);
        }

        propagateDecrease(access, type, decrease, increase, dirtyChunks);

        final int newSourceLevel = sourceLevel(type, x, y, z, access);
        if (newSourceLevel > 0) {
            data.set(type, x, y, z, newSourceLevel);
            dirtyChunks.add(ChunkPos.chunkKey(x >> 4, z >> 4));
            increase.push(x, y, z, newSourceLevel);
        }

        for (int dir = 0; dir < 6; dir++) {
            final int nx = x + DX[dir];
            final int ny = y + DY[dir];
            final int nz = z + DZ[dir];
            if (ny < minY || ny >= maxY) {
                continue;
            }
            final ChunkLightData nd = access.lightAt(nx >> 4, nz >> 4);
            if (nd == null) {
                continue;
            }
            final int lvl = nd.get(type, nx, ny, nz);
            if (lvl > 1) {
                increase.push(nx, ny, nz, lvl);
            }
        }

        propagateIncrease(access, type, increase, dirtyChunks);
    }

    private int sourceLevel(final LightType type, final int x, final int y, final int z, final LightAccess access) {
        if (type == LightType.BLOCK) {
            return BlockLightProperties.emission(access.blockAt(x, y, z));
        }

        if (BlockLightProperties.occludes(access.blockAt(x, y, z))) {
            return 0;
        }

        final ChunkLightData data = access.lightAt(x >> 4, z >> 4);
        final int top = data == null ? minY - 1 : data.topOpaqueY(x & 15, z & 15);
        return y > top ? MAX_LEVEL : 0;
    }

    private void propagateIncrease(
            final LightAccess access, final LightType type, final LongIntQueue queue, final Set<Long> dirtyChunks) {
        while (!queue.isEmpty()) {
            final int x = queue.pollX();
            final int y = queue.pollY();
            final int z = queue.pollZ();
            final int level = queue.pollLevel();

            final ChunkLightData self = access.lightAt(x >> 4, z >> 4);
            if (self == null || self.get(type, x, y, z) != level) {
                continue;
            }

            for (int dir = 0; dir < 6; dir++) {
                final int nx = x + DX[dir];
                final int ny = y + DY[dir];
                final int nz = z + DZ[dir];
                if (ny < minY || ny >= maxY) {
                    continue;
                }
                final ChunkLightData target = access.lightAt(nx >> 4, nz >> 4);
                if (target == null) {
                    continue;
                }

                final int current = target.get(type, nx, ny, nz);
                if (current >= level - 1) {
                    continue;
                }

                final BlockState state = access.blockAt(nx, ny, nz);
                if (BlockLightProperties.occludes(state)) {
                    continue;
                }

                final int targetLevel = level - Math.max(1, BlockLightProperties.opacity(state));
                if (targetLevel > current) {
                    target.set(type, nx, ny, nz, targetLevel);
                    dirtyChunks.add(ChunkPos.chunkKey(nx >> 4, nz >> 4));
                    if (targetLevel > 1) {
                        queue.push(nx, ny, nz, targetLevel);
                    }
                }
            }
        }
    }

    private void propagateDecrease(
            final LightAccess access, final LightType type, final LongIntQueue decreaseQueue,
            final LongIntQueue increaseQueue, final Set<Long> dirtyChunks) {
        while (!decreaseQueue.isEmpty()) {
            final int x = decreaseQueue.pollX();
            final int y = decreaseQueue.pollY();
            final int z = decreaseQueue.pollZ();
            final int oldLevel = decreaseQueue.pollLevel();

            for (int dir = 0; dir < 6; dir++) {
                final int nx = x + DX[dir];
                final int ny = y + DY[dir];
                final int nz = z + DZ[dir];
                if (ny < minY || ny >= maxY) {
                    continue;
                }
                final ChunkLightData target = access.lightAt(nx >> 4, nz >> 4);
                if (target == null) {
                    continue;
                }

                final int neighbourLevel = target.get(type, nx, ny, nz);
                if (neighbourLevel == 0) {
                    continue;
                }

                final BlockState state = access.blockAt(nx, ny, nz);
                final int decrement = Math.max(1, BlockLightProperties.opacity(state));
                final int targetLevel = oldLevel - decrement;

                if (neighbourLevel <= targetLevel) {
                    target.set(type, nx, ny, nz, 0);
                    dirtyChunks.add(ChunkPos.chunkKey(nx >> 4, nz >> 4));
                    decreaseQueue.push(nx, ny, nz, neighbourLevel);
                } else {
                    increaseQueue.push(nx, ny, nz, neighbourLevel);
                }
            }
        }
    }

    @Override
    public void relight(final Set<Long> chunks, final LightAccess access) {
        if (chunks.isEmpty()) {
            return;
        }
        for (final long key : chunks) {
            final ChunkLightData data = access.lightAt((int) (key >> 32), (int) key);
            if (data != null) {
                data.clear();
            }
        }

        computeSky(chunks, access);
        computeBlock(chunks, access);
    }

    private void computeSky(final Set<Long> chunks, final LightAccess access) {
        final LongQueue queue = new LongQueue();

        for (final long key : chunks) {
            final int chunkX = (int) (key >> 32);
            final int chunkZ = (int) key;
            final ChunkLightData data = access.lightAt(chunkX, chunkZ);
            if (data == null) {
                continue;
            }
            final int baseX = chunkX << 4;
            final int baseZ = chunkZ << 4;

            for (int lx = 0; lx < 16; lx++) {
                for (int lz = 0; lz < 16; lz++) {
                    final int worldX = baseX + lx;
                    final int worldZ = baseZ + lz;
                    int sky = MAX_LEVEL;
                    int topOpaque = minY - 1;
                    for (int y = maxY - 1; y >= minY; y--) {
                        final BlockState block = access.blockAt(worldX, y, worldZ);
                        if (BlockLightProperties.occludes(block)) {
                            topOpaque = y;
                            break;
                        }
                        if (sky <= 0) {
                            break;
                        }
                        data.set(LightType.SKY, worldX, y, worldZ, sky);
                        if (sky == MAX_LEVEL) {
                            queue.add(packPos(worldX, y, worldZ));
                        }
                        final int opacity = BlockLightProperties.opacity(block);
                        if (opacity > 0) {
                            sky = Math.max(0, sky - opacity);
                            queue.add(packPos(worldX, y, worldZ));
                        }
                    }
                    data.setTopOpaqueY(lx, lz, topOpaque);
                }
            }
        }

        seedBorders(chunks, access, LightType.SKY, queue);

        propagate(chunks, access, LightType.SKY, queue);
    }

    private void computeBlock(final Set<Long> chunks, final LightAccess access) {
        final LongQueue queue = new LongQueue();

        for (final long key : chunks) {
            final int chunkX = (int) (key >> 32);
            final int chunkZ = (int) key;
            final ChunkLightData data = access.lightAt(chunkX, chunkZ);
            if (data == null) {
                continue;
            }
            final int baseX = chunkX << 4;
            final int baseZ = chunkZ << 4;

            for (int lx = 0; lx < 16; lx++) {
                for (int lz = 0; lz < 16; lz++) {
                    for (int y = minY; y < maxY; y++) {
                        final BlockState block = access.blockAt(baseX + lx, y, baseZ + lz);
                        final int emission = BlockLightProperties.emission(block);
                        if (emission > 0) {
                            final int worldX = baseX + lx;
                            final int worldZ = baseZ + lz;
                            data.set(LightType.BLOCK, worldX, y, worldZ, emission);
                            queue.add(packPos(worldX, y, worldZ));
                        }
                    }
                }
            }
        }

        seedBorders(chunks, access, LightType.BLOCK, queue);

        propagate(chunks, access, LightType.BLOCK, queue);
    }

    private void seedBorders(final Set<Long> chunks, final LightAccess access, final LightType type, final LongQueue queue) {
        for (final long key : chunks) {
            final int chunkX = (int) (key >> 32);
            final int chunkZ = (int) key;

            addBorderColumn(chunks, access, type, queue, chunkX - 1, chunkZ, 15, -1);
            addBorderColumn(chunks, access, type, queue, chunkX + 1, chunkZ, 0, -1);
            addBorderColumn(chunks, access, type, queue, chunkX, chunkZ - 1, -1, 15);
            addBorderColumn(chunks, access, type, queue, chunkX, chunkZ + 1, -1, 0);
        }
    }

    private void addBorderColumn(
            final Set<Long> chunks, final LightAccess access, final LightType type, final LongQueue queue,
            final int neighborChunkX, final int neighborChunkZ, final int fixedLocalX, final int fixedLocalZ) {
        if (chunks.contains(ChunkPos.chunkKey(neighborChunkX, neighborChunkZ))) {
            return;
        }
        final ChunkLightData data = access.lightAt(neighborChunkX, neighborChunkZ);
        if (data == null) {
            return;
        }

        final int baseX = neighborChunkX << 4;
        final int baseZ = neighborChunkZ << 4;
        final int xStart = Math.max(fixedLocalX, 0);
        final int xEnd = fixedLocalX >= 0 ? fixedLocalX : 15;
        final int zStart = Math.max(fixedLocalZ, 0);
        final int zEnd = fixedLocalZ >= 0 ? fixedLocalZ : 15;
        for (int lx = xStart; lx <= xEnd; lx++) {
            for (int lz = zStart; lz <= zEnd; lz++) {
                final int worldX = baseX + lx;
                final int worldZ = baseZ + lz;
                for (int y = minY; y < maxY; y++) {
                    final int level = data.get(type, worldX, y, worldZ);
                    if (level > 1) {
                        queue.add(packPos(worldX, y, worldZ));
                    }
                }
            }
        }
    }

    private void propagate(final Set<Long> chunks, final LightAccess access, final LightType type, final LongQueue queue) {
        while (!queue.isEmpty()) {
            final long packed = queue.poll();
            final int x = unpackX(packed);
            final int y = unpackY(packed);
            final int z = unpackZ(packed);

            final ChunkLightData source = access.lightAt(x >> 4, z >> 4);
            if (source == null) {
                continue;
            }
            final int level = source.get(type, x, y, z);
            if (level <= 1) {
                continue;
            }

            for (int dir = 0; dir < 6; dir++) {
                final int nx = x + DX[dir];
                final int ny = y + DY[dir];
                final int nz = z + DZ[dir];
                if (ny < minY || ny >= maxY) {
                    continue;
                }
                final long neighborKey = ChunkPos.chunkKey(nx >> 4, nz >> 4);
                if (!chunks.contains(neighborKey)) {
                    continue;
                }
                final ChunkLightData target = access.lightAt(nx >> 4, nz >> 4);
                if (target == null) {
                    continue;
                }
                final BlockState block = access.blockAt(nx, ny, nz);
                if (BlockLightProperties.occludes(block)) {
                    continue;
                }
                final int decrement = 1 + BlockLightProperties.opacity(block);
                final int candidate = level - decrement;
                if (candidate <= 0) {
                    continue;
                }
                if (candidate > target.get(type, nx, ny, nz)) {
                    target.set(type, nx, ny, nz, candidate);
                    queue.add(packPos(nx, ny, nz));
                }
            }
        }
    }

    private static final class LongQueue {
        private long[] data = new long[1024];
        private int head;
        private int tail;

        void add(final long value) {
            if (tail == data.length) {
                grow();
            }
            data[tail++] = value;
        }

        long poll() {
            return data[head++];
        }

        boolean isEmpty() {
            return head == tail;
        }

        private void grow() {
            if (head > 0) {
                final int size = tail - head;
                System.arraycopy(data, head, data, 0, size);
                head = 0;
                tail = size;
                if (tail < data.length) {
                    return;
                }
            }
            final long[] bigger = new long[data.length * 2];
            System.arraycopy(data, 0, bigger, 0, data.length);
            data = bigger;
        }
    }
}
