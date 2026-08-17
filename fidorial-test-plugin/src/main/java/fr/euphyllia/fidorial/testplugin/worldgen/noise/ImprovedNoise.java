package fr.euphyllia.fidorial.testplugin.worldgen.noise;

import java.util.Random;

public final class ImprovedNoise {

    private final int[] permutation = new int[512];
    private final double originX;
    private final double originY;
    private final double originZ;

    public ImprovedNoise(final long seed) {
        final Random random = new Random(seed);
        this.originX = random.nextDouble() * 256.0;
        this.originY = random.nextDouble() * 256.0;
        this.originZ = random.nextDouble() * 256.0;

        final int[] table = new int[256];
        for (int i = 0; i < 256; i++) {
            table[i] = i;
        }
        for (int i = 0; i < 255; i++) {
            final int j = i + random.nextInt(256 - i);
            final int swap = table[i];
            table[i] = table[j];
            table[j] = swap;
        }
        for (int i = 0; i < 512; i++) {
            permutation[i] = table[i & 255];
        }
    }

    private static double fade(final double t) {
        return t * t * t * (t * (t * 6.0 - 15.0) + 10.0);
    }

    private static double lerp(final double t, final double a, final double b) {
        return a + t * (b - a);
    }

    private static double gradient(final int hash, final double x, final double y, final double z) {
        final int h = hash & 15;
        final double u = h < 8 ? x : y;
        final double v = h < 4 ? y : (h == 12 || h == 14 ? x : z);
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    private static int fastFloor(final double value) {
        final int i = (int) value;
        return value < i ? i - 1 : i;
    }

    public double noise(final double sampleX, final double sampleY, final double sampleZ) {
        final double x = sampleX + originX;
        final double y = sampleY + originY;
        final double z = sampleZ + originZ;

        final int floorX = fastFloor(x);
        final int floorY = fastFloor(y);
        final int floorZ = fastFloor(z);

        final int xi = floorX & 255;
        final int yi = floorY & 255;
        final int zi = floorZ & 255;

        final double xf = x - floorX;
        final double yf = y - floorY;
        final double zf = z - floorZ;

        final double u = fade(xf);
        final double v = fade(yf);
        final double w = fade(zf);

        final int a = permutation[xi] + yi;
        final int aa = permutation[a & 255] + zi;
        final int ab = permutation[(a + 1) & 255] + zi;
        final int b = permutation[(xi + 1) & 255] + yi;
        final int ba = permutation[b & 255] + zi;
        final int bb = permutation[(b + 1) & 255] + zi;

        final double x1 = lerp(
                u,
                gradient(permutation[aa & 255], xf, yf, zf),
                gradient(permutation[ba & 255], xf - 1.0, yf, zf));
        final double x2 = lerp(
                u,
                gradient(permutation[ab & 255], xf, yf - 1.0, zf),
                gradient(permutation[bb & 255], xf - 1.0, yf - 1.0, zf));
        final double y1 = lerp(v, x1, x2);

        final double x3 = lerp(
                u,
                gradient(permutation[(aa + 1) & 255], xf, yf, zf - 1.0),
                gradient(permutation[(ba + 1) & 255], xf - 1.0, yf, zf - 1.0));
        final double x4 = lerp(
                u,
                gradient(permutation[(ab + 1) & 255], xf, yf - 1.0, zf - 1.0),
                gradient(permutation[(bb + 1) & 255], xf - 1.0, yf - 1.0, zf - 1.0));
        final double y2 = lerp(v, x3, x4);

        return lerp(w, y1, y2);
    }
}
