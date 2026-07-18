package fr.fidorial.world.block.data.type;

import fr.fidorial.world.BlockFace;
import fr.fidorial.world.block.data.Bisected;
import fr.fidorial.world.block.data.Directional;
import fr.fidorial.world.block.data.Openable;
import fr.fidorial.world.block.data.Powerable;

import java.util.Locale;

public interface Door extends Directional, Bisected, Openable, Powerable {

    @Override
    default Door setFacing(BlockFace facing) {
        return (Door) Directional.super.setFacing(facing);
    }

    @Override
    default Door setHalf(Half half) {
        return (Door) Bisected.super.setHalf(half);
    }

    @Override
    default Door setOpen(boolean open) {
        return (Door) Openable.super.setOpen(open);
    }

    @Override
    default Door setPowered(boolean powered) {
        return (Door) Powerable.super.setPowered(powered);
    }

    default Hinge getHinge() {
        return Hinge.valueOf(get("hinge").toUpperCase(Locale.ROOT));
    }

    default Door setHinge(Hinge hinge) {
        return (Door) with("hinge", hinge.name().toLowerCase(Locale.ROOT));
    }

    enum Hinge {LEFT, RIGHT}
}
