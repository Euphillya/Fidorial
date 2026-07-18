package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Vex extends Mob {

    public static final float MAX_HEALTH = 14f;

    public Vex(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.VEX, world, location, MAX_HEALTH);
    }
}
