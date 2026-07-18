package fr.fidorial.world.block.data;

import fr.fidorial.world.BlockFace;
import fr.fidorial.world.block.BlockData;

import java.util.Locale;

public interface Directional extends BlockData {

    default BlockFace getFacing() {
        return BlockFace.valueOf(get("facing").toUpperCase(Locale.ROOT));
    }

    default Directional setFacing(BlockFace facing) {
        return (Directional) with("facing", facing.name().toLowerCase(Locale.ROOT));
    }
}
