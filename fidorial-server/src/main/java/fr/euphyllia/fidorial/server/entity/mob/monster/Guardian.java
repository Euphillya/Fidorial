package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Guardian extends Mob {

    public static final float MAX_HEALTH = 30f;

    public Guardian(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.GUARDIAN, world, location, MAX_HEALTH);
    }
}
