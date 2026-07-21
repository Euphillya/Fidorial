package fr.euphyllia.fidorial.server.entity.mob.ambient;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;


public final class Bat extends Mob {

    public static final float MAX_HEALTH = 6f;

    public Bat(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.BAT, world, location, MAX_HEALTH);
    }
}
