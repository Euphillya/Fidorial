package fr.euphyllia.fidorial.testplugin.worldgen.noise;

import java.util.Random;

public final class Seeds {

    private Seeds() {
        throw new UnsupportedOperationException("Seeds cannot be instantiated.");
    }

    public static long derive(final long seed, final String salt) {
        long hash = seed ^ 0x5DEECE66DL;
        for (int i = 0; i < salt.length(); i++) {
            hash = hash * 6364136223846793005L + (salt.charAt(i) + 1442695040888963407L);
        }
        return mix(hash);
    }

    public static long forChunk(final long seed, final int chunkX, final int chunkZ, final String salt) {
        long hash = derive(seed, salt);
        hash += chunkX * 341873128712L;
        hash += chunkZ * 132897987541L;
        return mix(hash);
    }

    public static Random randomForChunk(
            final long seed, final int chunkX, final int chunkZ, final String salt) {
        return new Random(forChunk(seed, chunkX, chunkZ, salt));
    }

    public static double hash01(final long saltedSeed, final int x, final int y, final int z) {
        long hash = saltedSeed;
        hash = hash * 6364136223846793005L + x * 0x9E3779B97F4A7C15L;
        hash = hash * 6364136223846793005L + y * 0xC2B2AE3D27D4EB4FL;
        hash = hash * 6364136223846793005L + z * 0x165667B19E3779F9L;
        return (mix(hash) >>> 11) * 0x1.0p-53;
    }

    public static long mix(final long input) {
        long value = input;
        value ^= value >>> 33;
        value *= 0xFF51AFD7ED558CCDL;
        value ^= value >>> 33;
        value *= 0xC4CEB9FE1A85EC53L;
        value ^= value >>> 33;
        return value;
    }
}
