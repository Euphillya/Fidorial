package fr.euphyllia.fidorial.server.entity.mob.creature;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class Donkey extends Mob {

    public static final float MAX_HEALTH = 15f;

    public Donkey(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.DONKEY, world, location, MAX_HEALTH);
    }
}
