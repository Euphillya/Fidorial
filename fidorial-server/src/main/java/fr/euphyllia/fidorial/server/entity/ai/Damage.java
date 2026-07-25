package fr.euphyllia.fidorial.server.entity.ai;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.AbstractEntity;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundHurtAnimationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSetEntityMotionPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSetHealthPacket;
import fr.fidorial.entity.GameMode;
import fr.fidorial.world.Location;

public class Damage {

    public static final int INVULNERABILITY_TICKS = 10;

    public static final double BASE_KNOCKBACK = 0.4;

    private static final double KNOCKBACK_UP = 0.4;

    private Damage() {
    }

    public static boolean hurt(final ServerPlayer target, final float amount,
                               final AbstractEntity source, final double knockback) {
        if (target.isRemoved() || target.isDead() || amount <= 0f) {
            return false;
        }
        final GameMode mode = target.gameMode();
        if (mode == GameMode.CREATIVE || mode == GameMode.SPECTATOR) {
            return false;
        }

        target.setHealth(target.health() - amount);
        target.connection().send(new ClientboundSetHealthPacket(target.health(), 20, 5f));

        final Location from = source.location();
        final Location to = target.location();
        final double dx = to.x() - from.x();
        final double dz = to.z() - from.z();
        final double length = Math.sqrt(dx * dx + dz * dz);

        if (knockback > 0.0 && length > 1.0E-4) {
            target.connection().send(new ClientboundSetEntityMotionPacket(target.entityId(),
                    dx / length * knockback, KNOCKBACK_UP, dz / length * knockback));
        }

        final float hurtYaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
        FidorialServer.getInstance()
                .broadcastNear(target.world(), to.x(), to.y(), to.z(),
                        new ClientboundHurtAnimationPacket(target.entityId(), hurtYaw));
        return true;
    }
}
