package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class WitherSkeleton extends Mob {

    public static final float MAX_HEALTH = 20f;

    public WitherSkeleton(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.WITHER_SKELETON, world, location, MAX_HEALTH);
    }
}
