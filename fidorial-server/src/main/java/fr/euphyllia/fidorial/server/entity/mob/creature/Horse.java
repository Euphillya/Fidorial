package fr.euphyllia.fidorial.server.entity.mob.creature;

import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Horse extends Mob {

    public static final float MAX_HEALTH = 15f;

    public Horse(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.HORSE, world, location, MAX_HEALTH);
    }
}
