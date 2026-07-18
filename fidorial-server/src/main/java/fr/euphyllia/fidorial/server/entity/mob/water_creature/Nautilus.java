package fr.euphyllia.fidorial.server.entity.mob.water_creature;

import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Nautilus extends Mob {

    public static final float MAX_HEALTH = 15f;

    public Nautilus(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.NAUTILUS, world, location, MAX_HEALTH);
    }
}
