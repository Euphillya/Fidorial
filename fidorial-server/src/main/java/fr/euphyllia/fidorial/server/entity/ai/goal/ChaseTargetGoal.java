package fr.euphyllia.fidorial.server.entity.ai.goal;

import fr.fidorial.entity.ai.Goal;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.Location;
import fr.euphyllia.fidorial.server.entity.mob.PathfinderMob;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;

public final class ChaseTargetGoal implements Goal {

    private final PathfinderMob mob;
    private final int priority;
    private final double speed;

    public ChaseTargetGoal(PathfinderMob mob, int priority, double speed) {
        this.mob = mob;
        this.priority = priority;
        this.speed = speed;
    }

    @Override
    public int priority() {
        return priority;
    }

    @Override
    public boolean canStart() {
        return mob.target() != null;
    }

    @Override
    public boolean shouldContinue() {
        return mob.target() != null;
    }

    @Override
    public void stop() {
        mob.navigation().stop();
    }

    @Override
    public void tick() {
        ServerPlayer target = mob.target();
        if (target == null) {
            return;
        }
        Location goal = target.location();
        mob.navigation().moveTo(mob.location(),
                new BlockPos((int) Math.floor(goal.x()), (int) Math.floor(goal.y()),
                        (int) Math.floor(goal.z())));
        mob.setMoveSpeed(speed);

        if (mob.distanceSqTo(target) < 36.0) {
            mob.lookAt(target);
        }
    }
}
