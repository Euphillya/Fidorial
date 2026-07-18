package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class ElderGuardian extends Mob {

    public static final float MAX_HEALTH = 80f;

    public ElderGuardian(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.ELDER_GUARDIAN, world, location, MAX_HEALTH);
    }
}
