package fr.euphyllia.fidorial.server.entity.mob.creature;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class Bee extends Mob {

    public static final float MAX_HEALTH = 10f;

    public Bee(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.BEE, world, location, MAX_HEALTH);
    }
}
