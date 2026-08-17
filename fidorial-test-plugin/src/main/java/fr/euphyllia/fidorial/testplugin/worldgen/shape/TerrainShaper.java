package fr.euphyllia.fidorial.testplugin.worldgen.shape;

import fr.euphyllia.fidorial.testplugin.worldgen.climate.ClimatePoint;

public final class TerrainShaper {

    private static final Spline CONTINENT_HEIGHT = Spline.of(
            new double[]{-1.2, -1.05, -0.455, -0.19, -0.11, 0.03, 0.3, 0.6, 1.0},
            new double[]{30.0, 32.0, 38.0, 50.0, 62.0, 68.0, 76.0, 92.0, 112.0});

    private static final Spline PV_HEIGHT = Spline.of(
            new double[]{-1.0, -0.85, -0.6, -0.2, 0.2, 0.7, 1.0},
            new double[]{-26.0, -16.0, -8.0, 0.0, 14.0, 52.0, 94.0});

    private static final Spline EROSION_RELIEF = Spline.of(
            new double[]{-1.0, -0.78, -0.375, -0.2225, 0.05, 0.45, 0.55, 1.0},
            new double[]{1.0, 0.92, 0.70, 0.52, 0.30, 0.14, 0.46, 0.08});

    private static final Spline INLAND_FACTOR = Spline.of(
            new double[]{-1.2, -0.455, -0.19, -0.11, 0.03, 0.3, 1.0},
            new double[]{0.12, 0.18, 0.30, 0.45, 0.70, 1.0, 1.0});

    private final int seaLevel;

    public TerrainShaper(final int seaLevel) {
        this.seaLevel = seaLevel;
    }

    public TerrainShape shape(final ClimatePoint point) {
        final double continentalness = point.continentalness();
        final double relief = EROSION_RELIEF.apply(point.erosion());
        final double inland = INLAND_FACTOR.apply(continentalness);

        double height = CONTINENT_HEIGHT.apply(continentalness) + PV_HEIGHT.apply(point.peaksValleys()) * relief * inland;

        if (continentalness < -1.05) {
            final double emergence = Math.min(1.0, (-1.05 - continentalness) / 0.1);
            final double island = seaLevel + 7.0 + PV_HEIGHT.apply(point.peaksValleys()) * 0.2;
            height += (island - height) * emergence;
        }

        final double pv = point.peaksValleys();
        if (pv < -0.75 && continentalness > -0.19) {
            final double strength = Math.min(1.0, (-0.75 - pv) / 0.25);
            final double riverBed = seaLevel - 5.0;
            height += (riverBed - height) * strength * 0.85;
        }

        final double peakiness = Math.clamp((pv + 1.0) / 1.5, 0.0, 1.0);
        double verticalScale = 2.2 + 26.0 * relief * inland * (0.35 + 0.65 * peakiness);
        verticalScale = Math.max(1.6, verticalScale);

        double caveTop = height - 6.0;
        if (height < seaLevel + 3.0) {
            caveTop = Math.min(caveTop, height - 13.0);
        }

        return new TerrainShape(height, verticalScale, caveTop);
    }
}
