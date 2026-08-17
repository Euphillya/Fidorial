package fr.euphyllia.fidorial.testplugin.worldgen.noise;

public final class OctaveNoise {

    private final ImprovedNoise[] octaves;
    private final double normalisation;

    public OctaveNoise(final long seed, final int octaveCount) {
        if (octaveCount < 1) {
            throw new IllegalArgumentException("octaveCount must be >= 1");
        }
        this.octaves = new ImprovedNoise[octaveCount];
        double amplitudeSum = 0.0;
        double amplitude = 1.0;
        for (int i = 0; i < octaveCount; i++) {
            this.octaves[i] = new ImprovedNoise(Seeds.derive(seed, "octave" + i));
            amplitudeSum += amplitude;
            amplitude *= 0.5;
        }
        this.normalisation = 1.0 / amplitudeSum;
    }

    public double sample(final double x, final double y, final double z) {
        double total = 0.0;
        double amplitude = 1.0;
        double frequency = 1.0;
        for (final ImprovedNoise octave : octaves) {
            total += octave.noise(x * frequency, y * frequency, z * frequency) * amplitude;
            frequency *= 2.0;
            amplitude *= 0.5;
        }
        return total * normalisation;
    }

    public double sample2d(final double x, final double z) {
        return sample(x, 0.0, z);
    }
}
