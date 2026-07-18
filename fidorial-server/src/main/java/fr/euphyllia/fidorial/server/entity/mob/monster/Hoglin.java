package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Hoglin extends Mob {

    public static final float MAX_HEALTH = 40f;

    public Hoglin(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.HOGLIN, world, location, MAX_HEALTH);
    }
}
