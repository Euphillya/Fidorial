package fr.euphyllia.fidorial.server.entity.ai;

import fr.fidorial.world.Chunk;
import fr.euphyllia.fidorial.server.world.ServerChunk;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;

import java.util.Set;

public class BlockView {

    private static final Set<String> PASSABLE = Set.of( // Todo : Remplacer par key
            "minecraft:water"
    );

    private BlockView() {
    }

    public static BlockState blockAt(ServerWorld world, int x, int y, int z) {
        if (y < world.minY() || y >= world.minY() + world.height()) {
            return BlockState.AIR;
        }
        Chunk chunk = world.getChunkIfLoaded(x >> 4, z >> 4);
        if (!(chunk instanceof ServerChunk serverChunk)) {
            return null;
        }
        return serverChunk.column().getBlock(x & 15, y, z & 15);
    }

    public static boolean isPassable(ServerWorld world, int x, int y, int z) {
        BlockState state = blockAt(world, x, y, z);
        return state != null && isPassable(state);
    }

    public static boolean isPassable(BlockState state) {
        if (state.isAir()) {
            return true;
        }
        String name = state.name();
        return PASSABLE.contains(name);
    }

    public static boolean isSolidGround(ServerWorld world, int x, int y, int z) {
        BlockState state = blockAt(world, x, y, z);
        return state != null && !isPassable(state);
    }

    public static boolean hasLineOfSight(ServerWorld world,
                                         double fromX, double fromY, double fromZ,
                                         double toX, double toY, double toZ) {
        double dx = toX - fromX;
        double dy = toY - fromY;
        double dz = toZ - fromZ;
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (distance < 1.0E-6 || distance > 32.0) {
            return distance <= 32.0;
        }
        int steps = (int) Math.ceil(distance * 2.0);
        double stepX = dx / steps;
        double stepY = dy / steps;
        double stepZ = dz / steps;
        double x = fromX;
        double y = fromY;
        double z = fromZ;
        int lastBx = Integer.MIN_VALUE;
        int lastBy = Integer.MIN_VALUE;
        int lastBz = Integer.MIN_VALUE;

        for (int i = 0; i < steps; i++) {
            x += stepX;
            y += stepY;
            z += stepZ;
            int bx = (int) Math.floor(x);
            int by = (int) Math.floor(y);
            int bz = (int) Math.floor(z);
            if (bx == lastBx && by == lastBy && bz == lastBz) {
                continue;
            }
            lastBx = bx;
            lastBy = by;
            lastBz = bz;
            if (!isPassable(world, bx, by, bz)) {
                return false;
            }
        }
        return true;
    }
}
