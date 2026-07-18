package fr.fidorial.world;

public record Location(double x, double y, double z, float yaw, float pitch) {

    public ChunkPos chunk() {
        return ChunkPos.fromBlock((int) Math.floor(x), (int) Math.floor(z));
    }
}
