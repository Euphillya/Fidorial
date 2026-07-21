package fr.euphyllia.fidorial.server.entity.mob.monster;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.ai.goal.ChaseTargetGoal;
import fr.euphyllia.fidorial.server.entity.ai.goal.LookAtTargetGoal;
import fr.euphyllia.fidorial.server.entity.ai.goal.RandomStrollGoal;
import fr.euphyllia.fidorial.server.entity.mob.PathfinderMob;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundCreeperStatePacket;
import fr.euphyllia.fidorial.server.world.Explosion;
import fr.fidorial.entity.ai.Goal;
import fr.fidorial.sound.SoundEvents;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.util.UUID;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public final class Creeper extends PathfinderMob {

    public static final float MAX_HEALTH = 20f;
    private static final ComponentLogger LOGGER = getLogger(Creeper.class);

    private static final int FUSE_TICKS = 30;

    private static final double SWELL_RANGE_SQ = 3.0 * 3.0;
    private static final double DEFUSE_RANGE_SQ = 7.0 * 7.0;
    private static final float EXPLOSION_POWER = 3.0f;
    private static final double CHASE_SPEED = 0.16;
    private static final double STROLL_SPEED = 0.09;

    private int swell;
    private boolean primed;

    public Creeper(int entityId, World world, Location location) {
        super(entityId, UUID.randomUUID(), EntityTypes.CREEPER, world, location, MAX_HEALTH);

        goals.add(new SwellGoal());
        goals.add(new ChaseTargetGoal(this, 1, CHASE_SPEED));
        goals.add(new RandomStrollGoal(this, 2, STROLL_SPEED));
        goals.add(new LookAtTargetGoal(this, 3, 8.0));
    }

    @Override
    public void tick(long currentTick) {
        super.tick(currentTick);
        if (isRemoved()) {
            return;
        }
        if (primed) {
            if (++swell >= FUSE_TICKS) {
                explode();
            }
        } else if (swell > 0) {
            swell--;
        }
    }

    private void explode() {
        Location center = location();
        setPrimed(false);
        server().despawnEntity(this);
        Explosion.explode(serverWorld(), center, EXPLOSION_POWER, this);
    }

    @Override
    protected void onDeath() {
        setPrimed(false);
        playSound(SoundEvents.CREEPER_DEATH, Sound.Source.HOSTILE, 1.0f, 1.0f);
        super.onDeath();
    }

    private void setPrimed(boolean primed) {
        if (this.primed == primed) {
            return;
        }
        this.primed = primed;
        server().broadcast(new ClientboundCreeperStatePacket(entityId(), primed));
        if (primed) {
            playSound(SoundEvents.CREEPER_PRIMED, Sound.Source.HOSTILE, 1.0f, 0.5f);
        }
    }

    public void hurt(float amount) {
        if (isRemoved() || isDead()) {
            return;
        }
        float remaining = health() - amount;
        if (remaining > 0f) {
            playSound(SoundEvents.CREEPER_HURT, Sound.Source.HOSTILE, 1.0f, 1.0f);
        }
        setHealth(remaining);
    }

    private final class SwellGoal implements Goal {

        @Override
        public int priority() {
            return 0;
        }

        @Override
        public boolean canStart() {
            ServerPlayer target = target();
            return target != null && distanceSqTo(target) <= SWELL_RANGE_SQ && hasLineOfSightTo(target);
        }

        @Override
        public boolean shouldContinue() {
            ServerPlayer target = target();
            return target != null && distanceSqTo(target) <= DEFUSE_RANGE_SQ && hasLineOfSightTo(target);
        }

        @Override
        public void start() {
            navigation().stop();
            setPrimed(true);
        }

        @Override
        public void stop() {
            setPrimed(false);
        }

        @Override
        public void tick() {
            ServerPlayer target = target();
            if (target != null) {
                lookAt(target);
            }
        }
    }
}
