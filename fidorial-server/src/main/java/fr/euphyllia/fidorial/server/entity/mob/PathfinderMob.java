package fr.euphyllia.fidorial.server.entity.mob;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.ai.BlockView;
import fr.euphyllia.fidorial.server.entity.ai.GoalSelector;
import fr.euphyllia.fidorial.server.entity.ai.Navigation;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundEntityPositionSyncPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundMoveEntityPosPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundMoveEntityPosRotPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundMoveEntityRotPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundRotateHeadPacket;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.fidorial.entity.EntityType;
import fr.fidorial.entity.GameMode;
import fr.fidorial.world.ChunkPos;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public abstract class PathfinderMob extends Mob {

    private static final double GRAVITY = 0.08;
    private static final double VERTICAL_DRAG = 0.98;
    private static final double GROUND_FRICTION = 0.6;
    private static final double JUMP_VELOCITY = 0.42;
    private static final double MAX_FALL_SPEED = 3.0;

    private static final double STEP_INTERVAL = 1.5;

    private static final double HALF_WIDTH = 0.3;
    private static final int POSITION_SYNC_INTERVAL = 100;
    private static final int TARGET_SCAN_INTERVAL = 10;
    private static final double MAX_RELATIVE_DELTA = 7.9;

    protected final GoalSelector goals = new GoalSelector();
    protected final Navigation navigation;

    private @Nullable ServerPlayer target;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    private boolean onGround;
    private double stepDistance;
    private float yaw;
    private float pitch;
    private double moveSpeed;

    private double sentX;
    private double sentY;
    private double sentZ;
    private float sentYaw;
    private float sentPitch;
    private float sentHeadYaw;
    private int ticksSinceSync;

    protected PathfinderMob(final int entityId, final UUID uuid, final EntityType type, final World world,
                            final Location location, final float maxHealth) {
        super(entityId, uuid, type, world, location, maxHealth);
        this.navigation = new Navigation(serverWorld());
        this.yaw = location.yaw();
        this.pitch = location.pitch();
        this.sentX = location.x();
        this.sentY = location.y();
        this.sentZ = location.z();
        this.sentYaw = yaw;
        this.sentPitch = pitch;
        this.sentHeadYaw = yaw;
    }

    protected final ServerWorld serverWorld() {
        return (ServerWorld) world();
    }

    @Override
    public final FidorialServer server() {
        return FidorialServer.getInstance();
    }

    @Override
    public void tick(final long currentTick) {
        if (isRemoved() || isDead()) {
            return;
        }

        if (currentTick % TARGET_SCAN_INTERVAL == 0) {
            updateTarget();
        } else if (target != null && !isValidTarget(target, dropRangeSq())) {
            target = null;
        }

        moveSpeed = 0.0;
        goals.tick();

        final Location before = location();
        applyPhysics();
        final Location after = location();

        final ChunkPos fromChunk = before.chunk();
        final ChunkPos toChunk = after.chunk();
        if (!fromChunk.equals(toChunk)) {
            serverWorld().entityManager().moved(this, fromChunk, toChunk);
            server().regionizer().moveTicket(serverWorld().dimension().id(), fromChunk, toChunk);
        }

        syncToClients();

        if (after.y() < serverWorld().minY() - 64) {
            server().despawnEntity(this);
        }
    }

    @Override
    protected void onDeath() {
        navigation.stop();
        goals.stopAll();
        server().despawnEntity(this);
    }

    protected double followRange() {
        return 16.0;
    }

    private double dropRangeSq() {
        final double range = followRange() * 1.25;
        return range * range;
    }

    private void updateTarget() {
        double bestDistSq =
                target != null && isValidTarget(target, dropRangeSq()) ? distanceSqTo(target) : Double.MAX_VALUE;
        ServerPlayer best = bestDistSq == Double.MAX_VALUE ? null : target;

        final double acquireSq = followRange() * followRange();
        for (final var entity : serverWorld().entities()) {
            if (!(entity instanceof final ServerPlayer player) || player == best) {
                continue;
            }
            if (!isValidTarget(player, acquireSq)) {
                continue;
            }
            final double distSq = distanceSqTo(player);
            if (distSq < bestDistSq) {
                bestDistSq = distSq;
                best = player;
            }
        }
        target = best;
    }

    private boolean isValidTarget(final ServerPlayer player, final double maxDistSq) {
        if (player.isRemoved() || player.isDead()) {
            return false;
        }
        final GameMode mode = player.gameMode();
        if (mode == GameMode.CREATIVE || mode == GameMode.SPECTATOR) {
            return false;
        }
        return player.world() == world() && distanceSqTo(player) <= maxDistSq;
    }

    public final @Nullable ServerPlayer target() {
        return target;
    }

    public final double distanceSqTo(final ServerPlayer player) {
        final Location self = location();
        final Location other = player.location();
        final double dx = self.x() - other.x();
        final double dy = self.y() - other.y();
        final double dz = self.z() - other.z();
        return dx * dx + dy * dy + dz * dz;
    }

    public final boolean hasLineOfSightTo(final ServerPlayer player) {
        final Location self = location();
        final Location other = player.location();
        return BlockView.hasLineOfSight(serverWorld(),
                self.x(), self.y() + 1.2, self.z(),
                other.x(), other.y() + 1.5, other.z());
    }

    public final void setMoveSpeed(final double speed) {
        this.moveSpeed = speed;
    }

    public final double velocityX() {
        return this.velocityX;
    }

    public final double velocityY() {
        return this.velocityY;
    }

    public final double velocityZ() {
        return this.velocityZ;
    }

    public final void setVelocity(final double x, final double y, final double z) {
        this.velocityX = x;
        this.velocityY = y;
        this.velocityZ = z;
    }

    public final boolean onGround() {
        return this.onGround;
    }

    public final void setOnGround(final boolean onGround) {
        this.onGround = onGround;
    }

    public final Navigation navigation() {
        return navigation;
    }

    public final void lookAt(final double x, final double y, final double z) {
        final Location self = location();
        final double dx = x - self.x();
        final double dy = y - (self.y() + 1.2);
        final double dz = z - self.z();
        final double horizontal = Math.sqrt(dx * dx + dz * dz);
        if (horizontal > 1.0E-4 || Math.abs(dy) > 1.0E-4) {
            this.yaw = (float) Math.toDegrees(Math.atan2(-dx, dz));
            this.pitch = (float) Math.toDegrees(-Math.atan2(dy, horizontal));
        }
    }

    public final void lookAt(final ServerPlayer player) {
        final Location other = player.location();
        lookAt(other.x(), other.y() + 1.5, other.z());
    }

    private void applyPhysics() {
        final Location current = location();
        final double x = current.x();
        final double y = current.y();
        final double z = current.z();

        navigation.tick(x, z);

        double inputX = 0.0;
        double inputZ = 0.0;
        final var waypoint = navigation.currentWaypoint();
        if (waypoint != null && moveSpeed > 0.0) {
            final double dx = waypoint.x() + 0.5 - x;
            final double dz = waypoint.z() + 0.5 - z;
            final double length = Math.sqrt(dx * dx + dz * dz);
            if (length > 1.0E-4) {
                inputX = dx / length * moveSpeed;
                inputZ = dz / length * moveSpeed;
                this.yaw = (float) Math.toDegrees(Math.atan2(-dx, dz));
                this.pitch = 0f;
            }
        }

        velocityX = velocityX * GROUND_FRICTION + inputX;
        velocityZ = velocityZ * GROUND_FRICTION + inputZ;
        if (Math.abs(velocityX) < 1.0E-3) velocityX = 0.0;
        if (Math.abs(velocityZ) < 1.0E-3) velocityZ = 0.0;

        velocityY = (velocityY - GRAVITY) * VERTICAL_DRAG;
        velocityY = Math.max(velocityY, -MAX_FALL_SPEED);

        if (velocityY < 0.0 && !onGround) {
            velocityY *= fallDrag();
        }

        double newX = x + velocityX;
        final boolean blockedX = velocityX != 0.0 && isBoxBlocked(newX, y, z);
        if (blockedX) {
            newX = x;
        }

        double newZ = z + velocityZ;
        final boolean blockedZ = velocityZ != 0.0 && isBoxBlocked(newX, y, newZ);
        if (blockedZ) {
            newZ = z;
        }

        if ((blockedX || blockedZ) && onGround && velocityY <= 0.0 && !isBoxBlocked(x, y + 1.0, z)) {
            velocityY = JUMP_VELOCITY;
        }
        if (blockedX) velocityX = 0.0;
        if (blockedZ) velocityZ = 0.0;

        double newY = y + velocityY;
        if (velocityY < 0.0) {
            if (isBoxBlocked(newX, newY, newZ)) {
                newY = Math.floor(newY) + 1.0;
                if (isBoxBlocked(newX, newY, newZ)) {
                    newY = y; // coince : ne pas s'enfoncer
                }
                velocityY = 0.0;
                onGround = true;
            } else {
                onGround = false;
            }
        } else if (velocityY > 0.0) {
            onGround = false;
            if (isBoxBlocked(newX, newY + height() - 1.0, newZ)) {
                newY = y;
                velocityY = 0.0;
            }
        } else {
            onGround = isBoxBlocked(newX, newY - 0.001, newZ);
        }

        if (onGround) {
            final double stepDx = newX - x;
            final double stepDz = newZ - z;
            stepDistance += Math.sqrt(stepDx * stepDx + stepDz * stepDz);
            if (stepDistance >= STEP_INTERVAL) {
                stepDistance = 0.0;
                onStep();
            }

        }

        if (newX != x || newY != y || newZ != z
                || yaw != current.yaw() || pitch != current.pitch()) {
            setLocation(new Location(newX, newY, newZ, yaw, pitch));
        }
    }

    protected double height() {
        return 1.7;
    }

    protected double fallDrag() {
        return 1.0;
    }

    protected void onStep() {
    }


    private boolean isBoxBlocked(final double x, final double y, final double z) {
        final int minBlockY = (int) Math.floor(y);
        final int maxBlockY = (int) Math.floor(y + height() - 0.01);
        final ServerWorld world = serverWorld();
        for (int blockY = minBlockY; blockY <= maxBlockY; blockY++) {
            if (!BlockView.isPassable(world, (int) Math.floor(x - HALF_WIDTH), blockY, (int) Math.floor(z - HALF_WIDTH))
                    || !BlockView.isPassable(
                            world, (int) Math.floor(x + HALF_WIDTH), blockY, (int) Math.floor(z - HALF_WIDTH))
                    || !BlockView.isPassable(
                            world, (int) Math.floor(x - HALF_WIDTH), blockY, (int) Math.floor(z + HALF_WIDTH))
                    || !BlockView.isPassable(
                            world,
                            (int) Math.floor(x + HALF_WIDTH),
                            blockY,
                            (int) Math.floor(z + HALF_WIDTH))
                    ) {
                return true;
            }
        }
        return false;
    }

    private void syncToClients() {
        final Location current = location();
        final double dx = current.x() - sentX;
        final double dy = current.y() - sentY;
        final double dz = current.z() - sentZ;
        final boolean moved = Math.abs(dx) + Math.abs(dy) + Math.abs(dz) > 1.0 / 4096.0;
        final boolean rotated = Math.abs(yaw - sentYaw) > 1.0f || Math.abs(pitch - sentPitch) > 1.0f;
        ticksSinceSync++;

        final boolean needsAbsoluteSync = ticksSinceSync >= POSITION_SYNC_INTERVAL
                || Math.abs(dx) > MAX_RELATIVE_DELTA
                || Math.abs(dy) > MAX_RELATIVE_DELTA
                || Math.abs(dz) > MAX_RELATIVE_DELTA;

        if (needsAbsoluteSync && (moved || rotated || ticksSinceSync >= POSITION_SYNC_INTERVAL)) {
            server().broadcast(new ClientboundEntityPositionSyncPacket(
                    entityId(),
                    current.x(),
                    current.y(),
                    current.z(),
                    velocityX,
                    velocityY,
                    velocityZ,
                    yaw,
                    pitch,
                    onGround));
            sentX = current.x();
            sentY = current.y();
            sentZ = current.z();
            sentYaw = yaw;
            sentPitch = pitch;
            ticksSinceSync = 0;
        } else if (moved) {
            final short qx = (short) Math.round(dx * 4096.0);
            final short qy = (short) Math.round(dy * 4096.0);
            final short qz = (short) Math.round(dz * 4096.0);
            if (rotated) {
                server().broadcast(new ClientboundMoveEntityPosRotPacket(entityId(), qx, qy, qz, yaw, pitch, onGround));
                sentYaw = yaw;
                sentPitch = pitch;
            } else {
                server().broadcast(new ClientboundMoveEntityPosPacket(entityId(), qx, qy, qz, onGround));
            }
            sentX += qx / 4096.0;
            sentY += qy / 4096.0;
            sentZ += qz / 4096.0;
        } else if (rotated) {
            server().broadcast(new ClientboundMoveEntityRotPacket(entityId(), yaw, pitch, onGround));
            sentYaw = yaw;
            sentPitch = pitch;
        }

        if (Math.abs(yaw - sentHeadYaw) > 1.0f) {
            server().broadcast(new ClientboundRotateHeadPacket(entityId(), yaw));
            sentHeadYaw = yaw;
        }
    }
}
