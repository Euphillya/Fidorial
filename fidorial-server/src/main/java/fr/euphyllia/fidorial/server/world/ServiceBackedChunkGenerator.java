package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.fidorial.service.ServiceRegistry;
import fr.fidorial.world.generation.GenerationDescriptor;
import fr.fidorial.world.generation.WorldGenerator;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

public class ServiceBackedChunkGenerator implements ChunkGenerator {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(ServiceBackedChunkGenerator.class);
    private static final Key DEFAULT_BIOME = Key.key("plains");

    private final ServiceRegistry services;
    private final ChunkGenerator fallback;
    private final int minY;
    private final int height;

    public ServiceBackedChunkGenerator(final ServiceRegistry services, final ChunkGenerator fallback, final int minY, final int height) {
        this.services = services;
        this.fallback = fallback;
        this.minY = minY;
        this.height = height;
    }

    @Override
    public ChunkColumn generate(final int chunkX, final int chunkZ) {
        final WorldGenerator custom = services.find(WorldGenerator.class).orElse(null);
        if (custom == null) {
            return fallback.generate(chunkX, chunkZ);
        }

        final PluginGeneratedChunk chunk = new PluginGeneratedChunk(chunkX, chunkZ, minY, height, DEFAULT_BIOME);
        try {
            custom.generate(chunk);
            return chunk.column();
        } catch (final Exception e) {
            LOGGER.error(
                    "The World Generator {} failed; reverting to the default generator.",
                    custom.getClass().getName(),
                    e);
            return fallback.generate(chunkX, chunkZ);
        }
    }

    @Override
    public ChunkGeneratorConfig describeForSave() {
        final WorldGenerator custom = services.find(WorldGenerator.class).orElse(null);
        if (custom == null) {
            return fallback.describeForSave();
        }

        return switch (custom.describeForSave()) {
            case GenerationDescriptor.Flat(final Key floorBlock, final int floorThickness, final Key biome) ->
                    new ChunkGeneratorConfig.Flat(
                            BlockState.of(floorBlock), floorThickness, biome);
            case GenerationDescriptor.Noise(final Key settings, final Key biomeSourcePreset) ->
                    new ChunkGeneratorConfig.Noise(settings, biomeSourcePreset);
            case GenerationDescriptor.Unknown _ -> fallback.describeForSave();
        };
    }
}
