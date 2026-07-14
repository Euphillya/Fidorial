package fr.euphyllia.fidorial.server.entity.mob;

import fr.euphyllia.fidorial.api.entity.EntityType;
import fr.euphyllia.fidorial.api.entity.LivingEntity;
import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.entity.AbstractEntity;

import java.util.UUID;

public abstract class Mob extends AbstractEntity implements LivingEntity {

    private final float maxHealth;
    private volatile float health;

    protected Mob(int entityId, UUID uuid, EntityType type, World world, Location location, float maxHealth) {
        super(entityId, uuid, type, world, location);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    @Override
    public final float health() {
        return health;
    }

    @Override
    public final void setHealth(float health) {
        this.health = Math.clamp(health, 0f, maxHealth);
        if (this.health == 0f) {
            onDeath();
        }
    }

    @Override
    public final float maxHealth() {
        return maxHealth;
    }

    protected void onDeath() {
        remove();
    }
}
