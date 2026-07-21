package fr.euphyllia.fidorial.server.entity.mob.creature;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class Sniffer extends Mob {

    public static final float MAX_HEALTH = 14f;

    public Sniffer(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.SNIFFER, world, location, MAX_HEALTH);
    }
}
