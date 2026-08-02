package fr.fidorial.event.entity;

import fr.fidorial.entity.Entity;
import fr.fidorial.event.Cancellable;
import fr.fidorial.event.Event;

public class EntityDamageEvent implements Event, Cancellable {

    private final Entity entity;
    private final Entity damager;
    private final float damage;
    private boolean cancelled;

    public EntityDamageEvent(final Entity entity, final Entity damager, final float damage) {
        this.entity = entity;
        this.damager = damager;
        this.damage = damage;
    }

    public Entity entity() {
        return entity;
    }

    public Entity damager() {
        return damager;
    }

    public float damage() {
        return damage;
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