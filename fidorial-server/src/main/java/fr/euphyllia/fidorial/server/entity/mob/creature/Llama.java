package fr.euphyllia.fidorial.server.entity.mob.creature;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class Llama extends Mob {

    public static final float MAX_HEALTH = 15f;

    public Llama(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.LLAMA, world, location, MAX_HEALTH);
    }
}
