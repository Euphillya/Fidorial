package fr.euphyllia.fidorial.api.world.generation;

@FunctionalInterface
public interface WorldGenerator {
    void generate(GeneratedChunk chunk);
}
