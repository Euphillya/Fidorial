package fr.euphyllia.fidorial.server.world.block;

import fr.fidorial.world.block.BlockRegistry;

public final class BuiltInBlocks {

    private BuiltInBlocks() {
    }

    public static void registerAll(final BlockRegistry registry) {
        registry.register(SimpleBlock.transparent("minecraft:air", 0));
        registry.register(SimpleBlock.opaque("minecraft:cobblestone", 14));
        registry.register(SimpleBlock.opaque("minecraft:obsidian", 3369));

        registry.register(FluidBlock.WATER);
        registry.register(FluidBlock.LAVA);

        registry.register(EnderChestBlock.INSTANCE);
    }
}
