package fr.fidorial.world.block;

import java.util.ArrayList;
import java.util.List;


public final class BlockProperties {

    public static final BlockProperty FACING =
            of("facing", "north", "east", "south", "west", "up", "down");

    public static final BlockProperty HORIZONTAL_FACING =
            of("facing", "north", "south", "west", "east");

    public static final BlockProperty HOPPER_FACING =
            of("facing", "down", "north", "south", "west", "east");

    public static final BlockProperty AXIS = of("axis", "x", "y", "z");

    public static final BlockProperty HORIZONTAL_AXIS = of("axis", "x", "z");

    public static final BlockProperty WATERLOGGED = bool("waterlogged");
    public static final BlockProperty POWERED = bool("powered");
    public static final BlockProperty LIT = bool("lit");
    public static final BlockProperty OPEN = bool("open");
    public static final BlockProperty SNOWY = bool("snowy");

    public static final BlockProperty HALF = of("half", "top", "bottom");

    public static final BlockProperty DOUBLE_BLOCK_HALF = of("half", "upper", "lower");

    public static final BlockProperty ROTATION = integer("rotation", 0, 15);

    public static final BlockProperty FLUID_LEVEL = integer("level", 0, 15);

    private BlockProperties() {
    }

    public static BlockProperty bool(final String name) {
        return new BlockProperty(name, List.of("true", "false"));
    }

    public static BlockProperty integer(final String name, final int min, final int max) {
        if (min > max) {
            throw new IllegalArgumentException("min > max for the '" + name + "' property");
        }
        final List<String> values = new ArrayList<>(max - min + 1);
        for (int value = min; value <= max; value++) {
            values.add(Integer.toString(value));
        }
        return new BlockProperty(name, List.copyOf(values));
    }

    public static BlockProperty of(final String name, final String... values) {
        return new BlockProperty(name, List.of(values));
    }
}
