package fr.euphyllia.fidorial.server.entity.mob;

import fr.fidorial.entity.EntityType;
import fr.fidorial.entity.GameMode;
import fr.fidorial.world.ChunkPos;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.ai.BlockView;
import fr.euphyllia.fidorial.server.entity.ai.GoalSelector;
import fr.euphyllia.fidorial.server.entity.ai.Navigation;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.*;
import fr.euphyllia.fidorial.server.world.ServerWorld;

import java.util.UUID;

public abstract class PathfinderMob extends Mob {

    private static final double GRAVITY = 0.08;
    private static final double VERTICAL_DRAG = 0.98;
    private static final double GROUND_FRICTION = 0.6;
    private static final double JUMP_VELOCITY = 0.42;
    private static final double MAX_FALL_SPEED = 3.0;

    private static final double HALF_WIDTH = 0.3;
    private static final int POSITION_SYNC_INTERVAL = 100;
    private static final int TARGET_SCAN_INTERVAL = 10;
    private static final double MAX_RELATIVE_DELTA = 7.9;

    protected final GoalSelector goals = new GoalSelector();
    protected final Navigation navigation;

    private ServerPlayer target;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    private boolean onGround;
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


    protected PathfinderMob(int entityId, UUID uuid, EntityType type, World world,
                            Location location, float maxHealth) {
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
    public void tick(long currentTick) {
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

        Location before = location();
        applyPhysics();
        Location after = location();

        ChunkPos fromChunk = before.chunk();
        ChunkPos toChunk = after.chunk();
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
        double range = followRange() * 1.25;
        return range * range;
    }

    private void updateTarget() {
        double bestDistSq = target != null && isValidTarget(target, dropRangeSq())
                ? distanceSqTo(target)
                : Double.MAX_VALUE;
        ServerPlayer best = bestDistSq == Double.MAX_VALUE ? null : target;

        double acquireSq = followRange() * followRange();
        for (var entity : serverWorld().entities()) {
            if (!(entity instanceof ServerPlayer player) || player == best) {
                continue;
            }
            if (!isValidTarget(player, acquireSq)) {
                continue;
            }
            double distSq = distanceSqTo(player);
            if (distSq < bestDistSq) {
                bestDistSq = distSq;
                best = player;
            }
        }
        target = best;
    }

    private boolean isValidTarget(ServerPlayer player, double maxDistSq) {
        if (player.isRemoved() || player.isDead()) {
            return false;
        }
        GameMode mode = player.gameMode();
        if (mode == GameMode.CREATIVE || mode == GameMode.SPECTATOR) {
            return false;
        }
        return player.world() == world() && distanceSqTo(player) <= maxDistSq;
    }

    public final ServerPlayer target() {
        return target;
    }

    public final double distanceSqTo(ServerPlayer player) {
        Location self = location();
        Location other = player.location();
        double dx = self.x() - other.x();
        double dy = self.y() - other.y();
        double dz = self.z() - other.z();
        return dx * dx + dy * dy + dz * dz;
    }

    public final boolean hasLineOfSightTo(ServerPlayer player) {
        Location self = location();
        Location other = player.location();
        return BlockView.hasLineOfSight(serverWorld(),
                self.x(), self.y() + 1.2, self.z(),
                other.x(), other.y() + 1.5, other.z());
    }

    public final void setMoveSpeed(double speed) {
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

    public final void setVelocity(double x, double y, double z) {
        this.velocityX = x;
        this.velocityY = y;
        this.velocityZ = z;
    }

    public final boolean onGround() {
        return this.onGround;
    }

    public final void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public final Navigation navigation() {
        return navigation;
    }

    public final void lookAt(double x, double y, double z) {
        Location self = location();
        double dx = x - self.x();
        double dy = y - (self.y() + 1.2);
        double dz = z - self.z();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        if (horizontal > 1.0E-4 || Math.abs(dy) > 1.0E-4) {
            this.yaw = (float) Math.toDegrees(Math.atan2(-dx, dz));
            this.pitch = (float) Math.toDegrees(-Math.atan2(dy, horizontal));
        }
    }

    public final void lookAt(ServerPlayer player) {
        Location other = player.location();
        lookAt(other.x(), other.y() + 1.5, other.z());
    }

    private void applyPhysics() {
        Location current = location();
        double x = current.x();
        double y = current.y();
        double z = current.z();

        navigation.tick(x, z);

        double inputX = 0.0;
        double inputZ = 0.0;
        var waypoint = navigation.currentWaypoint();
        if (waypoint != null && moveSpeed > 0.0) {
            double dx = waypoint.x() + 0.5 - x;
            double dz = waypoint.z() + 0.5 - z;
            double length = Math.sqrt(dx * dx + dz * dz);
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

        double newX = x + velocityX;
        boolean blockedX = velocityX != 0.0 && isBoxBlocked(newX, y, z);
        if (blockedX) {
            newX = x;
        }

        double newZ = z + velocityZ;
        boolean blockedZ = velocityZ != 0.0 && isBoxBlocked(newX, y, newZ);
        if (blockedZ) {
            newZ = z;
        }

        if ((blockedX || blockedZ) && onGround && velocityY <= 0.0
                && !isBoxBlocked(x, y + 1.0, z)) {
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

        if (newX != x || newY != y || newZ != z
                || yaw != current.yaw() || pitch != current.pitch()) {
            setLocation(new Location(newX, newY, newZ, yaw, pitch));
        }
    }

    protected double height() {
        return 1.7;
    }

    private boolean isBoxBlocked(double x, double y, double z) {
        int minBlockY = (int) Math.floor(y);
        int maxBlockY = (int) Math.floor(y + height() - 0.01);
        ServerWorld world = serverWorld();
        for (int blockY = minBlockY; blockY <= maxBlockY; blockY++) {
            if (!BlockView.isPassable(world, (int) Math.floor(x - HALF_WIDTH), blockY, (int) Math.floor(z - HALF_WIDTH))
                    || !BlockView.isPassable(world, (int) Math.floor(x + HALF_WIDTH), blockY, (int) Math.floor(z - HALF_WIDTH))
                    || !BlockView.isPassable(world, (int) Math.floor(x - HALF_WIDTH), blockY, (int) Math.floor(z + HALF_WIDTH))
                    || !BlockView.isPassable(world, (int) Math.floor(x + HALF_WIDTH), blockY, (int) Math.floor(z + HALF_WIDTH))) {
                return true;
            }
        }
        return false;
    }

    private void syncToClients() {
        Location current = location();
        double dx = current.x() - sentX;
        double dy = current.y() - sentY;
        double dz = current.z() - sentZ;
        boolean moved = Math.abs(dx) + Math.abs(dy) + Math.abs(dz) > 1.0 / 4096.0;
        boolean rotated = Math.abs(yaw - sentYaw) > 1.0f || Math.abs(pitch - sentPitch) > 1.0f;
        ticksSinceSync++;

        boolean needsAbsoluteSync = ticksSinceSync >= POSITION_SYNC_INTERVAL
                || Math.abs(dx) > MAX_RELATIVE_DELTA
                || Math.abs(dy) > MAX_RELATIVE_DELTA
                || Math.abs(dz) > MAX_RELATIVE_DELTA;

        if (needsAbsoluteSync && (moved || rotated || ticksSinceSync >= POSITION_SYNC_INTERVAL)) {
            server().broadcast(new ClientboundEntityPositionSyncPacket(entityId(),
                    current.x(), current.y(), current.z(),
                    velocityX, velocityY, velocityZ,
                    yaw, pitch, onGround));
            sentX = current.x();
            sentY = current.y();
            sentZ = current.z();
            sentYaw = yaw;
            sentPitch = pitch;
            ticksSinceSync = 0;
        } else if (moved) {
            short qx = (short) Math.round(dx * 4096.0);
            short qy = (short) Math.round(dy * 4096.0);
            short qz = (short) Math.round(dz * 4096.0);
            if (rotated) {
                server().broadcast(new ClientboundMoveEntityPosRotPacket(entityId(),
                        qx, qy, qz, yaw, pitch, onGround));
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
