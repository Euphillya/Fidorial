package fr.fidorial.combat;

import fr.fidorial.entity.Entity;
import fr.fidorial.entity.LivingEntity;
import fr.fidorial.entity.Player;
import org.jspecify.annotations.Nullable;

public interface CombatService {

    /**
     * @param attacker the player who swung
     * @param target   the entity being hit
     * @return {@code true} if damage was actually dealt
     * @since 0.1.0
     */
    boolean attack(Player attacker, Entity target);

    /**
     * @param target    the entity taking the hit
     * @param amount    damage in half-hearts; values {@code <= 0} are ignored
     * @param source    the entity responsible, or {@code null} for environmental damage
     * @param knockback horizontal knockback strength, {@code 0} for none
     * @return {@code true} if health was actually removed
     * @since 0.1.0
     */
    boolean damage(LivingEntity target, float amount, @Nullable Entity source, double knockback);

    /**
     * @param target the entity to kill
     * @param killer the entity credited with the kill, or {@code null}
     * @since 0.1.0
     */
    void kill(LivingEntity target, @Nullable Entity killer);

    /**
     * @param attacker the attacking player
     * @return the damage this player deals with a bare-handed hit
     * @since 0.1.0
     */
    float attackDamage(Player attacker);

    /**
     * @param attacker the attacking player
     * @return the knockback strength applied by this player's hits
     * @since 0.1.0
     */
    double attackKnockback(Player attacker);

    /**
     * @return {@code true} when players are allowed to damage each other
     * @since 0.1.0
     */
    boolean pvpEnabled();
}
