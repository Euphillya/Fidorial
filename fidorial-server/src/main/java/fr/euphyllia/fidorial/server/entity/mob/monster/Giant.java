package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class Giant extends Mob {

    public static final float MAX_HEALTH = 100f;

    public Giant(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.GIANT, world, location, MAX_HEALTH);
    }
}
