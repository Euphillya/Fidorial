package fr.euphyllia.fidorial.server.entity.mob.creature;

import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class WanderingTrader extends Mob {

    public static final float MAX_HEALTH = 20f;

    public WanderingTrader(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.WANDERING_TRADER, world, location, MAX_HEALTH);
    }
}
