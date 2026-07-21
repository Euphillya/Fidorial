package fr.euphyllia.fidorial.server.entity.ai;

import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.fidorial.entity.ai.Path;
import fr.fidorial.world.BlockPos;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class AStarPathfinder {

    private static final int[][] CARDINALS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private static final int MAX_DROP = 3;

    private AStarPathfinder() {
    }


    public static @Nullable Path find(ServerWorld world, BlockPos start, BlockPos goal, int maxNodes) {
        BlockPos from = snapToGround(world, start);
        BlockPos to = snapToGround(world, goal);
        if (from == null) {
            return null;
        }
        if (to == null) {
            to = goal;
        }
        if (from.equals(to)) {
            return null;
        }

        Map<Long, Node> nodes = new HashMap<>();
        PriorityQueue<Node> open = new PriorityQueue<>();

        Node startNode = new Node(from.x(), from.y(), from.z(), null, 0.0, heuristic(from, to));
        nodes.put(pack(from.x(), from.y(), from.z()), startNode);
        open.add(startNode);

        Node best = startNode;
        int visited = 0;

        while (!open.isEmpty() && visited < maxNodes) {
            Node current = open.poll();
            if (current.closed) {
                continue;
            }
            current.closed = true;
            visited++;

            if (current.h < best.h) {
                best = current;
            }
            if (current.x == to.x() && current.y == to.y() && current.z == to.z()) {
                return buildPath(current, true);
            }

            for (int[] dir : CARDINALS) {
                int nx = current.x + dir[0];
                int nz = current.z + dir[1];
                int ny = stepHeight(world, current.x, current.y, current.z, nx, nz);
                if (ny == Integer.MIN_VALUE) {
                    continue;
                }
                double moveCost = 1.0 + verticalCost(ny - current.y);
                double g = current.g + moveCost;
                long key = pack(nx, ny, nz);
                Node neighbor = nodes.get(key);
                if (neighbor == null) {
                    neighbor = new Node(nx, ny, nz, current,
                            g, heuristic(new BlockPos(nx, ny, nz), to));
                    nodes.put(key, neighbor);
                    open.add(neighbor);
                } else if (!neighbor.closed && g < neighbor.g) {
                    neighbor.g = g;
                    neighbor.parent = current;
                    open.add(neighbor); // re-insertion, l'ancienne entree sera ignoree via closed/g
                }
            }
        }

        return best == startNode ? null : buildPath(best, false);
    }

    private static int stepHeight(ServerWorld world, int x, int y, int z, int nx, int nz) {
        if (isStandable(world, nx, y, nz)) {
            return y;
        }

        if (BlockView.isPassable(world, x, y + 2, z) && isStandable(world, nx, y + 1, nz)) {
            return y + 1;
        }

        if (BlockView.isPassable(world, nx, y, nz) && BlockView.isPassable(world, nx, y + 1, nz)) {
            for (int drop = 1; drop <= MAX_DROP; drop++) {
                int ny = y - drop;
                if (!BlockView.isPassable(world, nx, ny, nz)) {
                    return Integer.MIN_VALUE;
                }
                if (isStandable(world, nx, ny, nz)) {
                    return ny;
                }
            }
        }
        return Integer.MIN_VALUE;
    }

    private static boolean isStandable(ServerWorld world, int x, int y, int z) {
        return BlockView.isPassable(world, x, y, z)
                && BlockView.isPassable(world, x, y + 1, z)
                && BlockView.isSolidGround(world, x, y - 1, z);
    }

    private static @Nullable BlockPos snapToGround(ServerWorld world, BlockPos pos) {
        for (int dy = 0; dy >= -MAX_DROP; dy--) {
            if (isStandable(world, pos.x(), pos.y() + dy, pos.z())) {
                return new BlockPos(pos.x(), pos.y() + dy, pos.z());
            }
        }
        for (int dy = 1; dy <= 2; dy++) {
            if (isStandable(world, pos.x(), pos.y() + dy, pos.z())) {
                return new BlockPos(pos.x(), pos.y() + dy, pos.z());
            }
        }
        return null;
    }

    private static double verticalCost(int dy) {
        if (dy > 0) {
            return 0.5;
        }
        return dy < 0 ? -dy * 0.1 : 0.0;
    }

    private static double heuristic(BlockPos a, BlockPos b) {
        double dx = a.x() - b.x();
        double dy = a.y() - b.y();
        double dz = a.z() - b.z();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    private static @Nullable Path buildPath(Node end, boolean reachedGoal) {
        List<BlockPos> waypoints = new ArrayList<>();
        for (Node node = end; node.parent != null; node = node.parent) {
            waypoints.add(new BlockPos(node.x, node.y, node.z));
        }
        if (waypoints.isEmpty()) {
            return null;
        }
        List<BlockPos> ordered = waypoints.reversed();
        return new Path(ordered, reachedGoal);
    }

    private static long pack(int x, int y, int z) {
        return ((long) (x & 0x3FFFFFF) << 38) | ((long) (z & 0x3FFFFFF) << 12) | (y & 0xFFF);
    }

    private static final class Node implements Comparable<Node> {
        final int x;
        final int y;
        final int z;
        final double h;
        @Nullable Node parent;
        double g;
        boolean closed;

        Node(int x, int y, int z, @Nullable Node parent, double g, double h) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.parent = parent;
            this.g = g;
            this.h = h;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(g + h, other.g + other.h);
        }
    }
}
