package fr.euphyllia.fidorial.server.entity.mob.creature;

import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Panda extends Mob {

    public static final float MAX_HEALTH = 20f;

    public Panda(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.PANDA, world, location, MAX_HEALTH);
    }
}
