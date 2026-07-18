package fr.euphyllia.fidorial.server.entity.mob.water_creature;

import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Cod extends Mob {

    public static final float MAX_HEALTH = 3f;

    public Cod(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.COD, world, location, MAX_HEALTH);
    }
}
