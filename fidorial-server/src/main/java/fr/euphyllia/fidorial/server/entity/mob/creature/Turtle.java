package fr.euphyllia.fidorial.server.entity.mob.creature;

import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Turtle extends Mob {

    public static final float MAX_HEALTH = 30f;

    public Turtle(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.TURTLE, world, location, MAX_HEALTH);
    }
}
