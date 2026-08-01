package fr.fidorial.event.player;

import fr.fidorial.entity.Entity;
import fr.fidorial.entity.Player;
import fr.fidorial.event.Cancellable;

public final class PlayerAttackEntityEvent implements PlayerEvent, Cancellable {

    private final Player player;
    private final Entity target;

    private float damage;
    private double knockback;
    private boolean cancelled;

    public PlayerAttackEntityEvent(final Player player, final Entity target, final float damage, final double knockback) {
        this.player = player;
        this.target = target;
        this.damage = damage;
        this.knockback = knockback;
    }
    
    @Override
    public Player player() {
        return player;
    }

    public Entity target() {
        return target;
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

    /**
     * @return the horizontal knockback strength about to be applied
     */
    public double knockback() {
        return knockback;
    }

    /**
     * @param knockback the knockback strength to apply instead; {@code 0} disables it
     */
    public void setKnockback(final double knockback) {
        this.knockback = knockback;
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