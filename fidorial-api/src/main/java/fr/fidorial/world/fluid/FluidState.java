package fr.fidorial.world.fluid;

import org.jspecify.annotations.Nullable;

public record FluidState(@Nullable FluidType type, int level, boolean falling) {

    private static final FluidState EMPTY = new FluidState(null, 0, false);

    public static FluidState empty() {
        return EMPTY;
    }

    public static FluidState source(FluidType type) {
        return new FluidState(type, 0, false);
    }

    public static FluidState flowing(FluidType type, int level) {
        return new FluidState(type, level, false);
    }

    public static FluidState fallingFluid(FluidType type) {
        return new FluidState(type, 0, true);
    }

    public boolean isEmpty() {
        return type == null;
    }

    public boolean isSource() {
        return type != null && level == 0 && !falling;
    }

    public int effectiveLevel() {
        return (falling || level <= 0) ? 0 : level;
    }
}
