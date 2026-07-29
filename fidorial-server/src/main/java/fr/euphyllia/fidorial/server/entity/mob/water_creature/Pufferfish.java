package fr.euphyllia.fidorial.server.entity.mob.water_creature;

import fr.euphyllia.fidorial.server.entity.Category;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class Pufferfish extends Mob implements Category.Neutral {

    public static final float MAX_HEALTH = 3f;

    public Pufferfish(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.PUFFERFISH, world, location, MAX_HEALTH);
    }
}
