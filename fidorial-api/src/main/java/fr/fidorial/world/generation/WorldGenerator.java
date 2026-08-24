package fr.fidorial.world.generation;

@FunctionalInterface
public interface WorldGenerator {
    void generate(GeneratedChunk chunk);

    default GenerationDescriptor describeForSave() {
        return GenerationDescriptor.unknown();
    }
}
