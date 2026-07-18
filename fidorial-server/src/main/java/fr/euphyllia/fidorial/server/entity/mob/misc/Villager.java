package fr.euphyllia.fidorial.server.entity.mob.misc;

import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Villager extends Mob {

    public static final float MAX_HEALTH = 20f;

    public Villager(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.VILLAGER, world, location, MAX_HEALTH);
    }
}
