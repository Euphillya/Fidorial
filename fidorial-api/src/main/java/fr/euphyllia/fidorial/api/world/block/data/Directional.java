package fr.euphyllia.fidorial.api.world.block.data;

import fr.euphyllia.fidorial.api.world.BlockFace;
import fr.euphyllia.fidorial.api.world.block.BlockData;

import java.util.Locale;

public interface Directional extends BlockData {

    default BlockFace getFacing() {
        return BlockFace.valueOf(get("facing").toUpperCase(Locale.ROOT));
    }

    default Directional setFacing(BlockFace facing) {
        return (Directional) with("facing", facing.name().toLowerCase(Locale.ROOT));
    }
}
