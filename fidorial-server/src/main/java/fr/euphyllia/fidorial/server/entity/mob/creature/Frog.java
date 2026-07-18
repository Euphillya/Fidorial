package fr.euphyllia.fidorial.server.entity.mob.creature;

import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Frog extends Mob {

    public static final float MAX_HEALTH = 10f;

    public Frog(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.FROG, world, location, MAX_HEALTH);
    }
}
