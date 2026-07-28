package fr.fidorial.world.block;

import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

public interface BlockBehaviour {

    BlockType type();

    default Key key() {
        return type().key();
    }

    default @Nullable BlockData placementState(final BlockPlaceContext context) {
        return type().defaultData();
    }

    default int lightEmission(final BlockData data) {
        return 0;
    }

    default int lightOpacity(final BlockData data) {
        return 15;
    }
}
