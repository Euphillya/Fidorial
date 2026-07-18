package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Wither extends Mob {

    public static final float MAX_HEALTH = 300f;

    public Wither(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.WITHER, world, location, MAX_HEALTH);
    }
}
