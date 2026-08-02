package fr.euphyllia.fidorial.server.combat;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.AbstractEntity;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.euphyllia.fidorial.server.entity.mob.MovingMob;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play.ClientboundEntityEventPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play.ClientboundHurtAnimationPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play.ClientboundPlayerCombatKillPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play.ClientboundSetEntityMotionPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play.ClientboundSetHealthPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play.ClientboundSoundPacket;
import fr.fidorial.combat.CombatService;
import fr.fidorial.entity.Entity;
import fr.fidorial.entity.GameMode;
import fr.fidorial.entity.LivingEntity;
import fr.fidorial.entity.Player;
import fr.fidorial.event.entity.EntityDamageEvent;
import fr.fidorial.event.player.PlayerAttackEntityEvent;
import fr.fidorial.event.player.PlayerDeathEvent;
import fr.fidorial.inventory.ItemStack;
import fr.fidorial.sound.SoundEvents;
import fr.fidorial.world.Location;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

public final class CombatEngine implements CombatService {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(CombatEngine.class);
    private final FidorialServer server;

    private static final double ATTACK_RANGE_SQ = 36.0;

    public static final double BASE_KNOCKBACK = 0.4;
    private static final double KNOCKBACK_UP = 0.4;

    private static final byte ENTITY_EVENT_DEATH = 3;

    private static final int FOOD_LEVEL = 20;
    private static final float SATURATION = 5f;


    public CombatEngine(final FidorialServer server) {
        this.server = server;
    }

    @Override
    public boolean attack(final Player attacker, final Entity target) {
        if (!(attacker instanceof final ServerPlayer player) || !(target instanceof final AbstractEntity victim)) {
            return false;
        }
        if (!(victim instanceof final LivingEntity living) || !canAttack(player, victim)) {
            return false;
        }


        final ItemStack weapon = heldItem(player);
        final float strength = 1; // TODO: calculate strength based on weapon and player stats

        final float damage = 1; // TODO: calculate damage based on weapon and player stats
        final double knockback = strength > 0.9f ? attackKnockback(player) : attackKnockback(player) * 0.5;

        final PlayerAttackEntityEvent event =
                server.events().post(new PlayerAttackEntityEvent(player, victim, damage, knockback));
        if (event.isCancelled()) {
            return false;
        }

        final boolean hit = damage(living, event.damage(), player, event.knockback());
        player.playSound(Sound.sound(
                hit ? (strength > 0.9f ? SoundEvents.PLAYER_ATTACK_STRONG : SoundEvents.PLAYER_ATTACK_WEAK)
                        : SoundEvents.PLAYER_ATTACK_NODAMAGE,
                Sound.Source.PLAYER,
                1.0f,
                1.0f));
        return hit;
    }

    @Override
    public boolean damage(final LivingEntity target, final float amount, @Nullable final Entity source, final double knockback) {
        if (amount <= 0f || target.isRemoved() || target.isDead()) {
            return false;
        }
        if (!(target instanceof final fr.fidorial.combat.Damageable damageable)) {
            return false;
        }
        if (target instanceof final ServerPlayer player && isProtected(player)) {
            return false;
        }

        final AbstractEntity attacker = source instanceof final AbstractEntity entity ? entity : null;

        final EntityDamageEvent event = server.events().post(new EntityDamageEvent(target, attacker, amount));
        if (event.isCancelled()) {
            return false;
        }

        return switch (target) {
            case final ServerPlayer player -> hurtPlayer(player, event.damage(), attacker, knockback);
            case final Mob mob -> hurtMob(mob, event.damage(), attacker, knockback);
            default -> false;
        };
    }

    @Override
    public void kill(final LivingEntity target, @Nullable final Entity killer) {
        final AbstractEntity source = killer instanceof final AbstractEntity entity ? entity : null;
        switch (target) {
            case final ServerPlayer player -> die(player, source);
            case final Mob mob -> {
                mob.setHealth(0f);
            }
            default -> {
            }
        }
    }

    @Override
    public float attackDamage(final Player attacker) {
        return 1; // TODO: calculate damage based on weapon and player stats
    }

    @Override
    public double attackKnockback(final Player attacker) {
        return BASE_KNOCKBACK;
    }

    @Override
    public boolean pvpEnabled() {
        return server.config().pvp();
    }

    private boolean hurtPlayer(
            final ServerPlayer target, final float amount, final @Nullable AbstractEntity source, final double knockback) {
        target.setHealth(target.health() - amount);
        target.connection().send(new ClientboundSetHealthPacket(target.health(), FOOD_LEVEL, SATURATION));

        final Location to = target.location();
        final float knockYaw = source == null ? to.yaw() : hurtYaw(source.location(), to);
        if (source != null && knockback > 0.0) {
            final double[] push = knockbackVector(source.location(), to, knockback);
            target.connection()
                    .send(new ClientboundSetEntityMotionPacket(
                            target.entityId(), push[0], KNOCKBACK_UP, push[1]));
        }

        server.broadcastNear(
                target.world(),
                to.x(),
                to.y(),
                to.z(),
                new ClientboundHurtAnimationPacket(target.entityId(), knockYaw));
        server.broadcastNear(
                target.world(),
                to.x(),
                to.y(),
                to.z(),
                new ClientboundSoundPacket(
                        Sound.sound(SoundEvents.PLAYER_HURT, Sound.Source.PLAYER, 1.0f, 1.0f),
                        to.x(),
                        to.y(),
                        to.z()));

        if (target.health() <= 0f) {
            die(target, source);
        }

        return true;
    }

    private boolean hurtMob(
            final Mob target, final float amount, final @Nullable AbstractEntity source, final double knockback) {
        if (source != null && knockback > 0.0 && target instanceof final MovingMob moving) {
            final double[] push = knockbackVector(source.location(), target.location(), knockback);
            moving.setVelocity(moving.velocityX() + push[0], KNOCKBACK_UP, moving.velocityZ() + push[1]);
        }
        target.setHealth(target.health() - amount);
        return true;
    }

    private void die(final ServerPlayer player, final @Nullable AbstractEntity killer) {
        if (player.health() <= 0f) {
            return;
        }
        player.setHealth(0f);
        player.connection().send(new ClientboundSetHealthPacket(0f, FOOD_LEVEL, SATURATION));

        final Component defaultMessage = deathMessage(player, killer);
        final PlayerDeathEvent event =
                server.events().post(new PlayerDeathEvent(player, killer, defaultMessage));

        player.sendToTrackers(new ClientboundEntityEventPacket(player.entityId(), ENTITY_EVENT_DEATH));
        player.connection().send(new ClientboundEntityEventPacket(player.entityId(), ENTITY_EVENT_DEATH));
        player.playSound(Sound.sound(SoundEvents.PLAYER_DEATH, Sound.Source.PLAYER, 1.0f, 1.0f));

        final Component message = event.deathMessage();
        player.connection()
                .send(new ClientboundPlayerCombatKillPacket(
                        player.entityId(), message == null ? Component.empty() : message));

        if (message != null) {
            for (final ServerPlayer online : server.players()) {
                online.sendMessage(message);
            }
            LOGGER.info(message);
        }
    }

    private boolean canAttack(final ServerPlayer player, final AbstractEntity victim) {
        if (player == victim || player.isRemoved() || victim.isRemoved() || player.isDead()) {
            return false;
        }
        if (player.world() != victim.world()) {
            return false;
        }
        if (player.location().distanceSquared(victim.location()) > ATTACK_RANGE_SQ) {
            LOGGER.debug("{} attacks {} out of reach (ignored)", player.name(), victim);
            return false;
        }
        if (victim instanceof final ServerPlayer other) {
            return pvpEnabled() && !isProtected(other);
        }
        return true;
    }

    private static boolean isProtected(final ServerPlayer player) {
        final GameMode mode = player.gameMode();
        return mode == GameMode.CREATIVE || mode == GameMode.SPECTATOR;
    }

    private static ItemStack heldItem(final Player player) {
        if (player instanceof final ServerPlayer serverPlayer) {
            return serverPlayer.inventory().get(serverPlayer.selectedSlot());
        }
        return ItemStack.EMPTY;
    }

    private static Component deathMessage(final ServerPlayer player, final @Nullable AbstractEntity killer) {
        final Component victim = player.displayName();
        if (killer == null) {
            return Component.translatable("death.attack.generic", victim);
        }
        if (killer instanceof ServerPlayer) {
            return Component.translatable("death.attack.player", victim, killer.displayName());
        }
        return Component.translatable("death.attack.mob", victim, killer.displayName());
    }

    private static double [] knockbackVector(
            final Location from, final Location to, final double strength) {
        final double dx = to.x() - from.x();
        final double dz = to.z() - from.z();
        final double length = Math.sqrt(dx * dx + dz * dz);
        if (length <= 1.0E-4) {
            return new double[]{0.0, 0.0};
        }
        return new double[]{dx / length * strength, dz / length * strength};
    }

    private static float hurtYaw(final Location from, final Location to) {
        final double dx = to.x() - from.x();
        final double dz = to.z() - from.z();
        return (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
    }
}
