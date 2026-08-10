package fr.euphyllia.fidorial.server.world.block;

import fr.fidorial.world.block.BlockBehaviour;
import fr.fidorial.world.block.BlockData;
import fr.fidorial.world.block.BlockType;
import net.kyori.adventure.key.Key;

public final class SimpleBlock implements BlockBehaviour {

    private final BlockType type;
    private final int opacity;
    private final int emission;

    private SimpleBlock(final Key key, final int stateId, final int opacity, final int emission) {
        this.type = BlockType.builder(key).firstStateId(stateId).build();
        this.opacity = opacity;
        this.emission = emission;
    }

    public static SimpleBlock opaque(final Key key, final int stateId) {
        return new SimpleBlock(key, stateId, 15, 0);
    }

    public static SimpleBlock transparent(final Key key, final int stateId) {
        return new SimpleBlock(key, stateId, 0, 0);
    }

    public static SimpleBlock of(final Key key, final int stateId, final int opacity, final int emission) {
        return new SimpleBlock(key, stateId, opacity, emission);
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
