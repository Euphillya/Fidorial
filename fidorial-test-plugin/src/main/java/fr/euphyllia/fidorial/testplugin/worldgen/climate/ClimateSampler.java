package fr.euphyllia.fidorial.testplugin.worldgen.climate;

import fr.euphyllia.fidorial.testplugin.worldgen.noise.OctaveNoise;
import fr.euphyllia.fidorial.testplugin.worldgen.noise.Seeds;

public final class ClimateSampler {

    private static final double TEMPERATURE_SCALE = 1.0 / 4096.0;
    private static final double HUMIDITY_SCALE = 1.0 / 2048.0;
    private static final double CONTINENT_SCALE = 1.0 / 2560.0;
    private static final double EROSION_SCALE = 1.0 / 2048.0;
    private static final double WEIRDNESS_SCALE = 1.0 / 1024.0;
    private static final double SHIFT_SCALE = 1.0 / 512.0;

    private static final double TEMPERATURE_GAIN = 2.1;

    private static final double HUMIDITY_GAIN = 2.1;
    private static final double CONTINENT_GAIN = 2.4;
    private static final double EROSION_GAIN = 2.2;
    private static final double WEIRDNESS_GAIN = 1.9;

    private final OctaveNoise temperature;
    private final OctaveNoise humidity;
    private final OctaveNoise continentalness;
    private final OctaveNoise erosion;
    private final OctaveNoise weirdness;
    private final OctaveNoise shiftX;
    private final OctaveNoise shiftZ;

    public ClimateSampler(final long seed) {
        this.temperature = new OctaveNoise(Seeds.derive(seed, "climate/temperature"), 3);
        this.humidity = new OctaveNoise(Seeds.derive(seed, "climate/humidity"), 3);
        this.continentalness = new OctaveNoise(Seeds.derive(seed, "climate/continentalness"), 6);
        this.erosion = new OctaveNoise(Seeds.derive(seed, "climate/erosion"), 4);
        this.weirdness = new OctaveNoise(Seeds.derive(seed, "climate/weirdness"), 4);
        this.shiftX = new OctaveNoise(Seeds.derive(seed, "climate/shift_x"), 2);
        this.shiftZ = new OctaveNoise(Seeds.derive(seed, "climate/shift_z"), 2);
    }

    public static double peaksValleys(final double weirdness) {
        final double folded = 1.0 - Math.abs(3.0 * Math.abs(weirdness) - 2.0);
        return clamp(folded, -1.0, 1.0);
    }

    private static double shape(final double raw, final double gain) {
        return clamp(raw * gain, -1.0, 1.0);
    }

    private static double clamp(final double value, final double min, final double max) {
        return value < min ? min : Math.min(value, max);
    }

    public ClimatePoint sample(final int worldX, final int worldZ) {
        final double offsetX = shiftX.sample2d(worldX * SHIFT_SCALE, worldZ * SHIFT_SCALE) * 48.0;
        final double offsetZ = shiftZ.sample2d(worldX * SHIFT_SCALE, worldZ * SHIFT_SCALE) * 48.0;
        final double x = worldX + offsetX;
        final double z = worldZ + offsetZ;

        final double t = shape(temperature.sample2d(x * TEMPERATURE_SCALE, z * TEMPERATURE_SCALE), TEMPERATURE_GAIN);
        final double h = shape(humidity.sample2d(x * HUMIDITY_SCALE, z * HUMIDITY_SCALE), HUMIDITY_GAIN);
        final double c = clamp(
                continentalness.sample2d(x * CONTINENT_SCALE, z * CONTINENT_SCALE) * CONTINENT_GAIN, -1.2, 1.0);
        final double e = shape(erosion.sample2d(x * EROSION_SCALE, z * EROSION_SCALE), EROSION_GAIN);
        final double w = shape(weirdness.sample2d(x * WEIRDNESS_SCALE, z * WEIRDNESS_SCALE), WEIRDNESS_GAIN);

        return new ClimatePoint(t, h, c, e, w, peaksValleys(w));
    }
}
