package fr.euphyllia.fidorial.server.entity.mob.water_creature;

import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;

import java.util.UUID;

public final class Axolotl extends Mob {

    public static final float MAX_HEALTH = 14f;

    public Axolotl(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.AXOLOTL, world, location, MAX_HEALTH);
    }
}
