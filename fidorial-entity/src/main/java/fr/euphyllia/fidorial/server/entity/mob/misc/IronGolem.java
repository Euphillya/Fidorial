package fr.euphyllia.fidorial.server.entity.mob.misc;

import fr.euphyllia.fidorial.server.entity.Category;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.AbstractMob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class IronGolem extends AbstractMob implements Category.Neutral {

    public static final float MAX_HEALTH = 100f;

    public IronGolem(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.IRON_GOLEM, world, location, MAX_HEALTH);
    }
}
