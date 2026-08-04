package fr.euphyllia.fidorial.server.entity.mob;

import fr.euphyllia.fidorial.server.entity.AbstractLivingEntity;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play.ClientboundEntityEventPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play.ClientboundSoundEntityPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play.ClientboundSoundPacket;
import fr.fidorial.combat.DamageSource;
import fr.fidorial.entity.EntityType;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import net.kyori.adventure.sound.Sound;
import org.jspecify.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Mob extends AbstractLivingEntity {

    private static final byte ENTITY_EVENT_DEATH = 3;

    protected Mob(final int entityId, final UUID uuid, final EntityType type, final World world, final Location location, final float maxHealth) {
        super(entityId, uuid, type, world, location, maxHealth);
    }

    @Override
    public final void setHealth(final float health) {
        super.setHealth(health);
        if (health() <= 0f && !isDying()) {
            onDeath();
        }
    }

    protected void onDeath() {
        sendToTrackers(new ClientboundEntityEventPacket(entityId(), ENTITY_EVENT_DEATH));
        startDeathAnimation();
    }

    protected Sound.@Nullable Type hurtSound() {
        return null;
    }

    protected Sound.@Nullable Type deathSound() {
        return null;
    }

    protected float voicePitch() {
        return 0.8f + ThreadLocalRandom.current().nextFloat() * 0.4f;
    }

    public float attackDamage() {
        return 0f;
    }

    protected float soundVolume() {
        return 1.0f;
    }

    public void playHurtSound() {
        final Sound.Type sound = hurtSound();
        if (sound != null) {
            playSound(sound, soundVolume(), voicePitch());
        }
    }

    public void onHurt(final DamageSource source, final float amount) {
    }

    protected final void playPositionalSound(final Sound.Type type, final float volume, final float pitch) {
        final Location loc = location();
        sendToTrackers(new ClientboundSoundPacket(
                Sound.sound(type, soundSource(), volume, pitch), loc.x(), loc.y(), loc.z()));
    }

    protected final void playSound(final Sound.Type type, final float volume, final float pitch) {
        sendToTrackers(new ClientboundSoundEntityPacket(
                Sound.sound(type, soundSource(), volume, pitch), entityId()));
    }
}
