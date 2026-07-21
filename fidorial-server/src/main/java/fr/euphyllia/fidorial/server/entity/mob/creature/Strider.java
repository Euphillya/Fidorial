package fr.euphyllia.fidorial.server.entity.mob.creature;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class Strider extends Mob {

    public static final float MAX_HEALTH = 20f;

    public Strider(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.STRIDER, world, location, MAX_HEALTH);
    }
}
