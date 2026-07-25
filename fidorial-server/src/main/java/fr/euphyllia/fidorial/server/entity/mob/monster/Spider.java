package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class Spider extends Mob {

    public static final float MAX_HEALTH = 16f;

    public Spider(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.SPIDER, world, location, MAX_HEALTH);
    }
}
