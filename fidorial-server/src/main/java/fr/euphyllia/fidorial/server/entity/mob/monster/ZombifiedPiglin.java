package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.server.entity.Category;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class ZombifiedPiglin extends Mob implements Category.Monster {

    public static final float MAX_HEALTH = 20f;

    public ZombifiedPiglin(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.ZOMBIFIED_PIGLIN, world, location, MAX_HEALTH);
    }
}
