package fr.euphyllia.fidorial.server.entity.mob.water_creature;

import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Tadpole extends Mob {

    public static final float MAX_HEALTH = 6f;

    public Tadpole(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.TADPOLE, world, location, MAX_HEALTH);
    }
}
