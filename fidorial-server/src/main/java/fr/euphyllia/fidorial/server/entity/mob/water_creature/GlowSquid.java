package fr.euphyllia.fidorial.server.entity.mob.water_creature;

import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class GlowSquid extends Mob {

    public static final float MAX_HEALTH = 10f;

    public GlowSquid(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.GLOW_SQUID, world, location, MAX_HEALTH);
    }
}
