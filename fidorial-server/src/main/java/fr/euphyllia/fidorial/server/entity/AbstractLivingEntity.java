package fr.euphyllia.fidorial.server.entity;

import fr.fidorial.entity.EntityType;
import fr.fidorial.entity.LivingEntity;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractLivingEntity extends AbstractEntity implements LivingEntity {

    public static final int DEATH_TICKS = 20;
    private volatile float maxHealth;
    private volatile float health;
    private volatile float absorption;
    private volatile float lastDamage;
    private volatile int fireTicks;
    private final AtomicInteger deathTicks = new AtomicInteger(-1);

    protected AbstractLivingEntity(
            final int entityId,
            final UUID uuid,
            final EntityType type,
            final World world,
            final Location location,
            final float maxHealth) {
        super(entityId, uuid, type, world, location);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    @Override
    public final float health() {
        return health;
    }

    @Override
    public void setHealth(final float health) {
        this.health = Math.clamp(health, 0f, maxHealth);
    }

    @Override
    public final float maxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(final float maxHealth) {
        this.maxHealth = Math.max(1f, maxHealth);
        setHealth(health);
    }

    @Override
    public final float absorptionAmount() {
        return absorption;
    }

    @Override
    public void setAbsorptionAmount(final float absorption) {
        this.absorption = Math.max(0f, absorption);
    }

    @Override
    public final int fireTicks() {
        return fireTicks;
    }

    @Override
    public void setFireTicks(final int ticks) {
        this.fireTicks = Math.max(0, ticks);
    }

    public final float lastDamage() {
        return lastDamage;
    }

    public final void setLastDamage(final float lastDamage) {
        this.lastDamage = lastDamage;
    }


    public final boolean isDying() {
        return deathTicks.get() >= 0;
    }

    public final void startDeathAnimation() {
        if (deathTicks.get() < 0) {
            deathTicks.set(DEATH_TICKS);
        }
    }

    protected void tickLiving(final long currentTick) {
        if (deathTicks.get() > 0 && deathTicks.decrementAndGet() == 0) {
            onDeathAnimationFinished();
        }
    }

    protected void onDeathAnimationFinished() {
        remove();
    }
}
