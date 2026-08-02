package fr.fidorial.event.entity;

import fr.fidorial.entity.Entity;
import fr.fidorial.event.Cancellable;
import fr.fidorial.event.Event;
import org.jspecify.annotations.Nullable;

public class EntityDamageEvent implements Event, Cancellable {

    private final Entity entity;
    private final @Nullable Entity damager;
    private float damage;
    private boolean cancelled;

    public EntityDamageEvent(final Entity entity, final @Nullable Entity damager, final float damage) {
        this.entity = entity;
        this.damager = damager;
        this.damage = damage;
    }

    public Entity entity() {
        return entity;
    }

    /**
     * @return the entity that caused the damage, or {@code null} for environmental damage
     */
    public @Nullable Entity damager() {
        return damager;
    }

    /**
     * @return the damage about to be dealt, in half-hearts
     */
    public float damage() {
        return damage;
    }

    /**
     * @param damage the damage to deal instead; {@code <= 0} makes the hit harmless
     */
    public void setDamage(final float damage) {
        this.damage = damage;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}