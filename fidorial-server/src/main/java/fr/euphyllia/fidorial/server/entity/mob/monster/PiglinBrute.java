package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class PiglinBrute extends Mob {

    public static final float MAX_HEALTH = 50f;

    public PiglinBrute(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.PIGLIN_BRUTE, world, location, MAX_HEALTH);
    }
}
