package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class Evoker extends Mob {

    public static final float MAX_HEALTH = 24f;

    public Evoker(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.EVOKER, world, location, MAX_HEALTH);
    }
}
