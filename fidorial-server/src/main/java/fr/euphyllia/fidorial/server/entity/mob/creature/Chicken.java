package fr.euphyllia.fidorial.server.entity.mob.creature;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.ai.goal.LookAtTargetGoal;
import fr.euphyllia.fidorial.server.entity.ai.goal.RandomStrollGoal;
import fr.euphyllia.fidorial.server.entity.mob.PathfinderMob;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.ChickenVariant;
import fr.fidorial.registry.keys.ChickenVariantKeys;
import fr.fidorial.sound.SoundEvents;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import net.kyori.adventure.sound.Sound;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class Chicken extends PathfinderMob {

    public static final float MAX_HEALTH = 4f;

    private static final double HITBOX_HEIGHT = 0.7;

    private static final double FLUTTER_FALL_DRAG = 0.6;

    private static final int EGG_MIN_TICKS = 6000;
    private static final int EGG_MAX_TICKS = 12000;

    private static final int AMBIENT_CHANCE = 240;

    private static final double STROLL_SPEED = 0.10;

    private TypedKey<ChickenVariant> variant = ChickenVariantKeys.TEMPERATE;
    private int eggTimer = nextEggDelay();


    public Chicken(final int entityId, final World world, final Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.CHICKEN, world, location, MAX_HEALTH);

        goals.add(new RandomStrollGoal(this, 1, STROLL_SPEED));
        goals.add(new LookAtTargetGoal(this, 2, 6.0));
    }

    private static float voicePitch() {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        return (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f;
    }

    private static int nextEggDelay() {
        return ThreadLocalRandom.current().nextInt(EGG_MIN_TICKS, EGG_MAX_TICKS + 1);
    }

    @Override
    public void tick(final long currentTick) {
        super.tick(currentTick);
        if (isRemoved() || isDead()) {
            return;
        }

        if (ThreadLocalRandom.current().nextInt(AMBIENT_CHANCE) == 0) {
            playSound(SoundEvents.CHICKEN_AMBIENT, Sound.Source.NEUTRAL, 1.0f, voicePitch());
        }

        if (--eggTimer <= 0) {
            playSound(SoundEvents.CHICKEN_EGG, Sound.Source.NEUTRAL, 1.0f, voicePitch());
            eggTimer = nextEggDelay();
        }
    }

    @Override
    protected void onStep() {
        playSound(SoundEvents.CHICKEN_STEP, Sound.Source.NEUTRAL, 0.15f, 1.0f);
    }


    public void hurt(final float amount) {
        if (isRemoved() || isDead()) {
            return;
        }
        final float remaining = health() - amount;
        if (remaining > 0f) {
            playSound(SoundEvents.CHICKEN_HURT, Sound.Source.NEUTRAL, 1.0f, voicePitch());
        }
        setHealth(remaining);
    }

    @Override
    protected void onDeath() {
        playSound(SoundEvents.CHICKEN_DEATH, Sound.Source.NEUTRAL, 1.0f, voicePitch());
        super.onDeath();
    }

    @Override
    protected double height() {
        return HITBOX_HEIGHT;
    }

    @Override
    protected double fallDrag() {
        return FLUTTER_FALL_DRAG;
    }

    public TypedKey<ChickenVariant> variant() {
        return variant;
    }

    public void setVariant(final TypedKey<ChickenVariant> variant) {
        this.variant = variant;
    }
}
