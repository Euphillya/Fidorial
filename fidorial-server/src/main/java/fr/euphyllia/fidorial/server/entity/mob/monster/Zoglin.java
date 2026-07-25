package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class Zoglin extends Mob {

    public static final float MAX_HEALTH = 40f;

    public Zoglin(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.ZOGLIN, world, location, MAX_HEALTH);
    }
}
