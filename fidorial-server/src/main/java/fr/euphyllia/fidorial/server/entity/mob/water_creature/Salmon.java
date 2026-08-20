package fr.euphyllia.fidorial.server.entity.mob.water_creature;

import fr.euphyllia.fidorial.server.entity.Category;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.AbstractMob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class Salmon extends AbstractMob implements Category.Neutral {

    public static final float MAX_HEALTH = 3f;

    public Salmon(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.SALMON, world, location, MAX_HEALTH);
    }
}
