package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class Ravager extends Mob {

    public static final float MAX_HEALTH = 100f;

    public Ravager(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.RAVAGER, world, location, MAX_HEALTH);
    }
}
