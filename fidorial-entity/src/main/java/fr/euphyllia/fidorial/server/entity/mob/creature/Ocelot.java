package fr.euphyllia.fidorial.server.entity.mob.creature;

import fr.euphyllia.fidorial.server.entity.Category;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.AbstractMob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class Ocelot extends AbstractMob implements Category.Neutral {

    public static final float MAX_HEALTH = 10f;

    public Ocelot(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.OCELOT, world, location, MAX_HEALTH);
    }
}
