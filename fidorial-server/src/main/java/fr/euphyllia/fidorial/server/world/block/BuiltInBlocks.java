package fr.euphyllia.fidorial.server.world.block;

import fr.fidorial.world.block.BlockRegistry;
import net.kyori.adventure.key.Key;

public final class BuiltInBlocks {

    private BuiltInBlocks() {
    }

    public static void registerAll(final BlockRegistry registry) {
        registry.register(SimpleBlock.transparent(Key.key("air"), 0));
        registry.register(SimpleBlock.opaque(Key.key("cobblestone"), 14));
        registry.register(SimpleBlock.opaque(Key.key("obsidian"), 3369));

        registry.register(FluidBlock.WATER);
        registry.register(FluidBlock.LAVA);

        registry.register(EnderChestBlock.INSTANCE);
    }
}
