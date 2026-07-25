package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class Creaking extends Mob {

    public static final float MAX_HEALTH = 1f;

    public Creaking(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.CREAKING, world, location, MAX_HEALTH);
    }
}
