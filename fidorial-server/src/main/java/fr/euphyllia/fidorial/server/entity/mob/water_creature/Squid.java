package fr.euphyllia.fidorial.server.entity.mob.water_creature;

import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Squid extends Mob {

    public static final float MAX_HEALTH = 10f;

    public Squid(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.SQUID, world, location, MAX_HEALTH);
    }
}
