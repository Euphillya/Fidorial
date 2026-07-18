package fr.euphyllia.fidorial.server.entity.mob.creature;

import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Camel extends Mob {

    public static final float MAX_HEALTH = 32f;

    public Camel(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.CAMEL, world, location, MAX_HEALTH);
    }
}
