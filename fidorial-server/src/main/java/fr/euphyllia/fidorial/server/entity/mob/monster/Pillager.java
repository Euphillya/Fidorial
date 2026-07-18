package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Pillager extends Mob {

    public static final float MAX_HEALTH = 24f;

    public Pillager(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.PILLAGER, world, location, MAX_HEALTH);
    }
}
