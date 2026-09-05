package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.utils;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import org.jspecify.annotations.Nullable;

public final class PositionData {

    private PositionData() {
    }

    public record Vec3D(double x, double y, double z) {
        public void writeTo(PacketBuffer buf) {
            buf.writeDouble(x);
            buf.writeDouble(y);
            buf.writeDouble(z);
        }
    }

    public record DeltaVec3D(short x, short y, short z) {

        private static final double SCALE = 4096.0;
        private static final long MIN = -32768L;
        private static final long MAX = 32767L;

        public static @Nullable DeltaVec3D between(Vec3D prev, Vec3D current) {
            long dx = encode(current.x()) - encode(prev.x());
            long dy = encode(current.y()) - encode(prev.y());
            long dz = encode(current.z()) - encode(prev.z());
            if (isTooBig(dx) || isTooBig(dy) || isTooBig(dz)) {
                return null;
            }
            return new DeltaVec3D((short) dx, (short) dy, (short) dz);
        }

        private static long encode(double v) {
            return Math.round(v * SCALE);
        }

        private static boolean isTooBig(long v) {
            return v < MIN || v > MAX;
        }

        public void writeTo(PacketBuffer buf) {
            buf.writeShort(x);
            buf.writeShort(y);
            buf.writeShort(z);
        }
    }

    public record VelocityVec3D(double x, double y, double z) {
        public void writeTo(PacketBuffer buf) {
            buf.writeLpVec3(x, y, z);
        }
    }

    public record FloatRotation(float yaw, float pitch) {
        public void writeTo(PacketBuffer buf) {
            buf.writeFloat(yaw);
            buf.writeFloat(pitch);
        }
    }

    public record AngleRotation(float yaw, float pitch) {
        public void writeTo(PacketBuffer buf) {
            buf.writeAngle(yaw);
            buf.writeAngle(pitch);
        }
    }

    public record PositionMoveRotationData(Vec3D position, Vec3D deltaMovement, FloatRotation rotation) {
        public void writeTo(PacketBuffer buf) {
            position.writeTo(buf);
            deltaMovement.writeTo(buf);
            rotation.writeTo(buf);
        }
    }

    public record LinearPositionPath(Vec3D position) {
        public void writeTo(PacketBuffer buf) {
            buf.writeVarInt(0); // position tag (0 = linear (absolute), 1 = stepped (interpolated multi-tick path))
            position.writeTo(buf);
        }
    }
}
