package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Vindicator extends Mob {

    public static final float MAX_HEALTH = 24f;

    public Vindicator(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.VINDICATOR, world, location, MAX_HEALTH);
    }
}
