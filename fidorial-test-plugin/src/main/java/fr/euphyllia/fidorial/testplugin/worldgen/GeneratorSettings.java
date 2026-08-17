package fr.euphyllia.fidorial.testplugin.worldgen;

public record GeneratorSettings(long seed, int seaLevel, boolean caves, boolean ores, boolean decoration) {

    public static GeneratorSettings defaults(final long seed) {
        return new GeneratorSettings(seed, 63, true, true, true);
    }

    public GeneratorSettings withSeed(final long newSeed) {
        return new GeneratorSettings(newSeed, seaLevel, caves, ores, decoration);
    }
}
