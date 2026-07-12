package fr.euphyllia.fidorial.server.world;

public record BlockPos(int x, int y, int z) {

    public BlockPos relative(Direction face) {
        return new BlockPos(x + face.dx(), y + face.dy(), z + face.dz());
    }

    public int chunkX() {
        return x >> 4;
    }

    public int chunkZ() {
        return z >> 4;
    }

    public int localX() {
        return x & 15;
    }

    public int localZ() {
        return z & 15;
    }

    public enum Direction {
        DOWN(0, -1, 0),
        UP(0, 1, 0),
        NORTH(0, 0, -1),
        SOUTH(0, 0, 1),
        WEST(-1, 0, 0),
        EAST(1, 0, 0);

        private static final Direction[] BY_ID = values();

        private final int dx;
        private final int dy;
        private final int dz;

        Direction(int dx, int dy, int dz) {
            this.dx = dx;
            this.dy = dy;
            this.dz = dz;
        }

        public static Direction byId(int id) {
            return (id >= 0 && id < BY_ID.length) ? BY_ID[id] : UP;
        }

        public int dx() {
            return dx;
        }

        public int dy() {
            return dy;
        }

        public int dz() {
            return dz;
        }
    }
}
