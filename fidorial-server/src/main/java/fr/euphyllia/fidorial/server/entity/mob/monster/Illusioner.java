package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;

public final class Illusioner extends Mob {

    public static final float MAX_HEALTH = 32f;

    public Illusioner(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.ILLUSIONER, world, location, MAX_HEALTH);
    }
}
