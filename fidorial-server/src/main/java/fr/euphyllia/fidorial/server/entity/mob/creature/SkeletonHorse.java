package fr.euphyllia.fidorial.server.entity.mob.creature;

import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class SkeletonHorse extends Mob {

    public static final float MAX_HEALTH = 15f;

    public SkeletonHorse(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.SKELETON_HORSE, world, location, MAX_HEALTH);
    }
}
