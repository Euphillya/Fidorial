package fr.euphyllia.fidorial.server.entity.mob;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.AbstractEntity;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSoundPacket;
import fr.fidorial.entity.EntityType;
import fr.fidorial.entity.LivingEntity;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import net.kyori.adventure.sound.Sound;

import java.util.UUID;

public abstract class Mob extends AbstractEntity implements LivingEntity {

    private final float maxHealth;
    private volatile float health;

    protected Mob(final int entityId, final UUID uuid, final EntityType type, final World world, final Location location, final float maxHealth) {
        super(entityId, uuid, type, world, location);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    @Override
    public final float health() {
        return health;
    }

    @Override
    public final void setHealth(final float health) {
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

    protected final void playSound(final Sound.Type type, final Sound.Source source, final float volume, final float pitch) {
        final Location loc = location();
        sendToTrackers(new ClientboundSoundPacket(
                Sound.sound(type, source, volume, pitch), loc.x(), loc.y(), loc.z()));
    }
}
