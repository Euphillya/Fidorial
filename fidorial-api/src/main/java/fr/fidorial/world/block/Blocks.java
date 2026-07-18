package fr.fidorial.world.block;

import net.kyori.adventure.key.Key;

public final class Blocks {

    private static volatile BlockRegistry registry;

    private Blocks() {
    }

    public static void bootstrap(BlockRegistry blockRegistry) {
        if (registry != null) {
            throw new IllegalStateException("Block registry already bootstrapped");
        }
        registry = blockRegistry;
    }

    public static BlockRegistry registry() {
        BlockRegistry current = registry;
        if (current == null) {
            throw new IllegalStateException("Block registry not bootstrapped yet");
        }
        return current;
    }

    public static BlockType type(String key) {
        return registry().type(key).orElse(null);
    }

    public static BlockType type(Key key) {
        return registry().type(key).orElse(null);
    }


    public static BlockData data(String input) {
        return registry().parse(input);
    }
}
