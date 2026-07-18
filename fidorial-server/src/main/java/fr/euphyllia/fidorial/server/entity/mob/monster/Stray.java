package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Stray extends Mob {

    public static final float MAX_HEALTH = 20f;

    public Stray(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.STRAY, world, location, MAX_HEALTH);
    }
}
