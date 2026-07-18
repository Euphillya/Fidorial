package fr.euphyllia.fidorial.api.entity.ai;

import fr.euphyllia.fidorial.api.world.BlockPos;

import java.util.List;

public record Path(List<BlockPos> waypoints, boolean reachesGoal) {

    public Path {
        waypoints = List.copyOf(waypoints);
    }

    public BlockPos target() {
        return waypoints.getLast();
    }
}
