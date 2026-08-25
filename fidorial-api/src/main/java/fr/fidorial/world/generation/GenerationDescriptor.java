package fr.fidorial.world.generation;

import net.kyori.adventure.key.Key;

public sealed interface GenerationDescriptor {

    static GenerationDescriptor unknown() {
        return Unknown.INSTANCE;
    }

    static GenerationDescriptor flat(final Key floorBlock, final int floorThickness, final Key biome) {
        return new Flat(floorBlock, floorThickness, biome);
    }

    static GenerationDescriptor noise(final Key settings, final Key biomeSourcePreset) {
        return new Noise(settings, biomeSourcePreset);
    }

    record Flat(Key floorBlock, int floorThickness, Key biome) implements GenerationDescriptor {
    }

    record Noise(Key settings, Key biomeSourcePreset) implements GenerationDescriptor {
    }

    record Unknown() implements GenerationDescriptor {
        static final Unknown INSTANCE = new Unknown();
    }
}
