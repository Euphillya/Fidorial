package fr.euphyllia.fidorial.testplugin.worldgen.climate;

public record ClimatePoint(
        double temperature,
        double humidity,
        double continentalness,
        double erosion,
        double weirdness,
        double peaksValleys) {

    public int temperatureLevel() {
        if (temperature < -0.45) return 0;
        if (temperature < -0.15) return 1;
        if (temperature < 0.2) return 2;
        if (temperature < 0.55) return 3;
        return 4;
    }

    public int humidityLevel() {
        if (humidity < -0.35) return 0;
        if (humidity < -0.1) return 1;
        if (humidity < 0.1) return 2;
        if (humidity < 0.3) return 3;
        return 4;
    }

    public int erosionLevel() {
        if (erosion < -0.78) return 0;
        if (erosion < -0.375) return 1;
        if (erosion < -0.2225) return 2;
        if (erosion < 0.05) return 3;
        if (erosion < 0.45) return 4;
        if (erosion < 0.55) return 5;
        return 6;
    }

    public int continentLevel() {
        if (continentalness < -1.05) return 0;
        if (continentalness < -0.455) return 1;
        if (continentalness < -0.19) return 2;
        if (continentalness < -0.11) return 3;
        if (continentalness < 0.03) return 4;
        if (continentalness < 0.3) return 5;
        return 6;
    }

    public int peaksValleysLevel() {
        if (peaksValleys < -0.85) return 0;
        if (peaksValleys < -0.2) return 1;
        if (peaksValleys < 0.2) return 2;
        if (peaksValleys < 0.7) return 3;
        return 4;
    }

    public boolean isOceanic() {
        return continentLevel() <= 2;
    }
}
