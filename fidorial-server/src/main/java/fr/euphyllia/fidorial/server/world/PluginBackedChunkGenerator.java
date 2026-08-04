package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.fidorial.world.generation.WorldGenerator;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

public final class PluginBackedChunkGenerator implements ChunkGenerator {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(PluginBackedChunkGenerator.class);
    private static final Key DEFAULT_BIOME = Key.key("plains");

    private final WorldGenerator generator;
    private final ChunkGenerator fallback;
    private final int minY;
    private final int height;

    public PluginBackedChunkGenerator(
            final WorldGenerator generator, final ChunkGenerator fallback, final int minY, final int height) {
        this.generator = generator;
        this.fallback = fallback;
        this.minY = minY;
        this.height = height;
    }

    @Override
    public ChunkColumn generate(final int chunkX, final int chunkZ) {
        final PluginGeneratedChunk chunk = new PluginGeneratedChunk(chunkX, chunkZ, minY, height, DEFAULT_BIOME);
        try {
            generator.generate(chunk);
            return chunk.column();
        } catch (final Exception e) {
            LOGGER.error(
                    "The World Generator {} failed for the chunk ({}, {}), returning to the fallback generator",
                    generator.getClass().getName(),
                    chunkX,
                    chunkZ,
                    e);
            return fallback.generate(chunkX, chunkZ);
        }
    }
}
