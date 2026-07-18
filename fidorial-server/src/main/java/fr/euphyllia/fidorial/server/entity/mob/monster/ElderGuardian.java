package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class ElderGuardian extends Mob {

    public static final float MAX_HEALTH = 80f;

    public ElderGuardian(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.ELDER_GUARDIAN, world, location, MAX_HEALTH);
    }
}
