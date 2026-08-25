package fr.fidorial.world.generation;

@FunctionalInterface
public interface WorldGenerator {
    void generate(GeneratedChunk chunk);

    default int minY() {
        return -64;
    }

    default int height() {
        return 384;
    }

    default GenerationDescriptor describeForSave() {
        return GenerationDescriptor.unknown();
    }
}
