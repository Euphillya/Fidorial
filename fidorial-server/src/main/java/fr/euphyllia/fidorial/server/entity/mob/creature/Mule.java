package fr.euphyllia.fidorial.server.entity.mob.creature;

import fr.euphyllia.fidorial.server.entity.Category;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class Mule extends Mob implements Category.Neutral {

    public static final float MAX_HEALTH = 15f;

    public Mule(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.MULE, world, location, MAX_HEALTH);
    }
}
