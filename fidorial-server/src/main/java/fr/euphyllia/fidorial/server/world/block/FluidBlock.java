package fr.euphyllia.fidorial.server.world.block;

import fr.fidorial.world.block.BlockBehaviour;
import fr.fidorial.world.block.BlockData;
import fr.fidorial.world.block.BlockProperties;
import fr.fidorial.world.block.BlockType;
import net.kyori.adventure.key.Key;

public final class FluidBlock implements BlockBehaviour {

    public static final FluidBlock WATER = new FluidBlock(Key.key("water"), 86, 1, 0);

    public static final FluidBlock LAVA = new FluidBlock(Key.key("lava"), 102, 15, 15);

    private final BlockType type;
    private final int opacity;
    private final int emission;

    private FluidBlock(final Key key, final int firstStateId, final int opacity, final int emission) {
        this.type = BlockType.builder(key)
                .property(BlockProperties.FLUID_LEVEL)
                .firstStateId(firstStateId)
                .defaultValue("level", "0")
                .build();
        this.opacity = opacity;
        this.emission = emission;
    }

    @Override
    public BlockType type() {
        return type;
    }

    @Override
    public int lightOpacity(final BlockData data) {
        return opacity;
    }

    @Override
    public int lightEmission(final BlockData data) {
        return emission;
    }
}
