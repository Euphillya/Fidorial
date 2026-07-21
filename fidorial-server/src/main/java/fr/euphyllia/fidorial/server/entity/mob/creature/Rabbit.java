package fr.euphyllia.fidorial.server.entity.mob.creature;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class Rabbit extends Mob {

    public static final float MAX_HEALTH = 3f;

    public Rabbit(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.RABBIT, world, location, MAX_HEALTH);
    }
}
