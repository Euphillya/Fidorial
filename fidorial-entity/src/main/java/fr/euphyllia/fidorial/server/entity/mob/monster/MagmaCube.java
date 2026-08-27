package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.server.entity.Category;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.AbstractMob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class MagmaCube extends AbstractMob implements Category.Monster {

    public static final float MAX_HEALTH = 16f;

    public MagmaCube(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.MAGMA_CUBE, world, location, MAX_HEALTH);
    }
}
