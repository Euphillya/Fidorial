package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class CaveSpider extends Mob {

    public static final float MAX_HEALTH = 12f;

    public CaveSpider(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.CAVE_SPIDER, world, location, MAX_HEALTH);
    }
}
