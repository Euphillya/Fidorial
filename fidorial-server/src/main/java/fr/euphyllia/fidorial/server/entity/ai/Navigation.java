package fr.euphyllia.fidorial.server.entity.ai;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.fidorial.entity.ai.Path;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.Location;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

public class Navigation {

    private static final double WAYPOINT_REACHED_SQ = 0.35 * 0.35;

    private static final int REPATH_COOLDOWN_TICKS = 10;

    private static final double REPATH_TARGET_MOVED_SQ = 2.0 * 2.0;
    private static final int MAX_NODES = 768;

    private final ServerWorld world;
    private final AtomicReference<@Nullable PathResult> pendingResult = new AtomicReference<>();

    private @Nullable Path path;
    private int waypointIndex;
    private @Nullable BlockPos requestedGoal;

    private long age;
    private long lastRequestTick = -REPATH_COOLDOWN_TICKS;
    private boolean requestInFlight;

    private double lastDistanceSq = Double.MAX_VALUE;
    private int stuckTicks;

    public Navigation(ServerWorld world) {
        this.world = world;
    }

    private static double distanceSq(BlockPos a, BlockPos b) {
        double dx = a.x() - b.x();
        double dy = a.y() - b.y();
        double dz = a.z() - b.z();
        return dx * dx + dy * dy + dz * dz;
    }

    public void moveTo(Location from, BlockPos goal) {
        if (requestInFlight) {
            return;
        }
        boolean sameGoal = requestedGoal != null && distanceSq(requestedGoal, goal) < REPATH_TARGET_MOVED_SQ;
        if (sameGoal && path != null) {
            return;
        }
        if (age - lastRequestTick < REPATH_COOLDOWN_TICKS) {
            return;
        }

        BlockPos start = new BlockPos((int) Math.floor(from.x()),
                (int) Math.floor(from.y()), (int) Math.floor(from.z()));
        requestedGoal = goal;
        lastRequestTick = age;
        requestInFlight = FidorialServer.getInstance().aiWorker().submit(() ->
                pendingResult.set(new PathResult(AStarPathfinder.find(world, start, goal, MAX_NODES))));
    }

    public void tick(double x, double z) {
        age++;
        PathResult result = pendingResult.getAndSet(null);
        if (result != null) {
            requestInFlight = false;
            path = result.path();
            waypointIndex = 0;
            lastDistanceSq = Double.MAX_VALUE;
            stuckTicks = 0;
        }
        if (path == null) {
            return;
        }

        BlockPos waypoint = currentWaypoint();
        if (waypoint == null) {
            return;
        }
        double dx = waypoint.x() + 0.5 - x;
        double dz = waypoint.z() + 0.5 - z;
        double distSq = dx * dx + dz * dz;
        if (distSq < WAYPOINT_REACHED_SQ) {
            waypointIndex++;
            lastDistanceSq = Double.MAX_VALUE;
            stuckTicks = 0;
            if (waypointIndex >= path.waypoints().size()) {
                stop();
            }
            return;
        }
        if (distSq >= lastDistanceSq - 1.0E-4) {
            if (++stuckTicks > 40) {
                stop();
            }
        } else {
            lastDistanceSq = distSq;
            stuckTicks = 0;
        }
    }

    public @Nullable BlockPos currentWaypoint() {
        if (path == null || waypointIndex >= path.waypoints().size()) {
            return null;
        }
        return path.waypoints().get(waypointIndex);
    }

    public boolean isNavigating() {
        return currentWaypoint() != null || requestInFlight;
    }

    public void stop() {
        path = null;
        waypointIndex = 0;
        requestedGoal = null;
        stuckTicks = 0;
        lastDistanceSq = Double.MAX_VALUE;
    }

    private record PathResult(@Nullable Path path) {
    }
}
