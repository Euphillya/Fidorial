package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.fidorial.service.ServiceRegistry;
import fr.fidorial.world.generation.WorldGenerator;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public class ServiceBackedChunkGenerator implements ChunkGenerator {

    private static final ComponentLogger LOGGER = getLogger(ServiceBackedChunkGenerator.class);
    private static final String DEFAULT_BIOME = "minecraft:plains";

    private final ServiceRegistry services;
    private final ChunkGenerator fallback;
    private final int minY;
    private final int height;

    public ServiceBackedChunkGenerator(ServiceRegistry services, ChunkGenerator fallback, int minY, int height) {
        this.services = services;
        this.fallback = fallback;
        this.minY = minY;
        this.height = height;
    }

    @Override
    public ChunkColumn generate(int chunkX, int chunkZ) {
        WorldGenerator custom = services.find(WorldGenerator.class).orElse(null);
        if (custom == null) {
            return fallback.generate(chunkX, chunkZ);
        }

        PluginGeneratedChunk chunk = new PluginGeneratedChunk(chunkX, chunkZ, minY, height, DEFAULT_BIOME);
        try {
            custom.generate(chunk);
            return chunk.column();
        } catch (Exception e) {
            LOGGER.error(
                    "Le WorldGenerator {} a echoue, retour au generateur par defaut",
                    custom.getClass().getName(),
                    e);
            return fallback.generate(chunkX, chunkZ);
        }
    }
}
