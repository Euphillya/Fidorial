package fr.euphyllia.fidorial.server.entity.mob.creature;

import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Sheep extends Mob {

    public static final float MAX_HEALTH = 8f;

    public Sheep(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.SHEEP, world, location, MAX_HEALTH);
    }
}
