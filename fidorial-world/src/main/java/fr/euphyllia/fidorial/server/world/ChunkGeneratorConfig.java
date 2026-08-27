package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import net.kyori.adventure.key.Key;

public sealed interface ChunkGeneratorConfig {

    record Flat(BlockState floor, int floorThickness, Key biome) implements ChunkGeneratorConfig {
    }

    record Debug() implements ChunkGeneratorConfig {
    }

    record Noise(Key settings, Key biomeSourcePreset) implements ChunkGeneratorConfig {
    }
}
