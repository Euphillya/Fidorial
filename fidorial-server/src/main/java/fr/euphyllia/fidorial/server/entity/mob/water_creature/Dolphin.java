package fr.euphyllia.fidorial.server.entity.mob.water_creature;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class Dolphin extends Mob {

    public static final float MAX_HEALTH = 10f;

    public Dolphin(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.DOLPHIN, world, location, MAX_HEALTH);
    }
}
