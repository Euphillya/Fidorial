package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Silverfish extends Mob {

    public static final float MAX_HEALTH = 8f;

    public Silverfish(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.SILVERFISH, world, location, MAX_HEALTH);
    }
}
