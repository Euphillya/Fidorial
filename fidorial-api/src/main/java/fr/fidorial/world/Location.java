package fr.fidorial.world;

public record Location(double x, double y, double z, float yaw, float pitch) {

    public ChunkPos chunk() {
        return ChunkPos.fromBlock((int) Math.floor(x), (int) Math.floor(z));
    }

    public double distanceSquared(Location other) {
        double dx = x() - other.x();
        double dy = y() - other.y();
        double dz = z() - other.z();
        return dx * dx + dy * dy + dz * dz;
    }
}
