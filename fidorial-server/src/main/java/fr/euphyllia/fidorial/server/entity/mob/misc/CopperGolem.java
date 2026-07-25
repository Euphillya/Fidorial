package fr.euphyllia.fidorial.server.entity.mob.misc;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class CopperGolem extends Mob {

    public static final float MAX_HEALTH = 12f;

    public CopperGolem(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.COPPER_GOLEM, world, location, MAX_HEALTH);
    }
}
