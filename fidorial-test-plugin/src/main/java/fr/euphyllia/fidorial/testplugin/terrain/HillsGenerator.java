package fr.euphyllia.fidorial.testplugin.terrain;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.world.generation.GeneratedChunk;
import fr.euphyllia.fidorial.api.world.generation.WorldGenerator;

public final class HillsGenerator implements WorldGenerator {

    private static final Key BEDROCK = Key.minecraft("bedrock");
    private static final Key STONE = Key.minecraft("stone");
    private static final Key DIRT = Key.minecraft("dirt");
    private static final Key GRASS_BLOCK = Key.minecraft("grass_block");
    private static final Key SAND = Key.minecraft("sand");
    private static final Key WATER = Key.minecraft("water");

    private static final Key BIOME_PLAINS = Key.minecraft("plains");
    private static final Key BIOME_BEACH = Key.minecraft("beach");
    private static final Key BIOME_RIVER = Key.minecraft("river");

    /**
     * Hauteur de base du terrain.
     */
    private final int baseHeight;
    /**
     * Amplitude maximale des collines au-dessus/en-dessous de la base.
     */
    private final int amplitude;
    /**
     * Niveau de la mer : tout creux sous ce niveau est rempli d'eau.
     */
    private final int seaLevel;

    private final PerlinNoise heightNoise;
    private final PerlinNoise detailNoise;

    public HillsGenerator(long seed, int baseHeight, int amplitude, int seaLevel) {
        this.baseHeight = baseHeight;
        this.amplitude = amplitude;
        this.seaLevel = seaLevel;
        this.heightNoise = new PerlinNoise(seed);
        this.detailNoise = new PerlinNoise(seed * 31 + 7);
    }

    @Override
    public void generate(GeneratedChunk chunk) {
        int minY = chunk.minY();
        int baseX = chunk.chunkX() << 4;
        int baseZ = chunk.chunkZ() << 4;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = baseX + x;
                int worldZ = baseZ + z;
                int surface = surfaceHeight(worldX, worldZ);
                boolean underWater = surface < seaLevel;

                // socle
                chunk.setBlock(x, minY, z, BEDROCK);

                // pierre jusqu'a 4 blocs sous la surface
                for (int y = minY + 1; y <= surface - 4; y++) {
                    chunk.setBlock(x, y, z, STONE);
                }

                // couche superficielle : sable pres de l'eau, sinon terre + herbe
                if (underWater || surface <= seaLevel + 1) {
                    for (int y = Math.max(minY + 1, surface - 3); y <= surface; y++) {
                        chunk.setBlock(x, y, z, SAND);
                    }
                } else {
                    for (int y = Math.max(minY + 1, surface - 3); y < surface; y++) {
                        chunk.setBlock(x, y, z, DIRT);
                    }
                    chunk.setBlock(x, surface, z, GRASS_BLOCK);
                }

                // eau jusqu'au niveau de la mer
                for (int y = surface + 1; y <= seaLevel; y++) {
                    chunk.setBlock(x, y, z, WATER);
                }

                // biome : resolution 4x4x4, donc un seul appel par cellule
                // (coin de cellule uniquement, pour eviter 16 ecritures redondantes)
                if ((x & 3) == 0 && (z & 3) == 0) {
                    Key biome = underWater ? BIOME_RIVER
                            : surface <= seaLevel + 2 ? BIOME_BEACH
                              : BIOME_PLAINS;
                    for (int y = minY; y < minY + chunk.height(); y += 4) {
                        chunk.setBiome(x, y, z, biome);
                    }
                }
            }
        }
    }

    /**
     * Hauteur de surface pour une colonne monde donnee.
     */
    private int surfaceHeight(int worldX, int worldZ) {
        double hills = heightNoise.fbm(worldX * 0.004, worldZ * 0.004, 4);
        double detail = detailNoise.fbm(worldX * 0.02, worldZ * 0.02, 2);
        return baseHeight + (int) Math.round(hills * amplitude + detail * 4);
    }
}
