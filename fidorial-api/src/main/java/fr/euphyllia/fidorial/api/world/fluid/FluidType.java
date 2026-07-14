package fr.euphyllia.fidorial.api.world.fluid;

import fr.euphyllia.fidorial.api.registry.Key;

public enum FluidType {

    WATER(Key.minecraft("water"), 5, 1, 7, true),
    LAVA(Key.minecraft("lava"), 30, 2, 6, false);

    private final Key blockKey;
    private final int tickDelay;
    private final int dropOff;
    private final int maxSpreadLevel;
    private final boolean canFormSources;

    FluidType(Key blockKey, int tickDelay, int dropOff, int maxSpreadLevel, boolean canFormSources) {
        this.blockKey = blockKey;
        this.tickDelay = tickDelay;
        this.dropOff = dropOff;
        this.maxSpreadLevel = maxSpreadLevel;
        this.canFormSources = canFormSources;
    }

    public static FluidType byBlockKey(String key) {
        for (FluidType type : values()) {
            if (type.blockKey.asString().equals(key)) {
                return type;
            }
        }
        return null;
    }

    public Key blockKey() {
        return blockKey;
    }

    public int tickDelay() {
        return tickDelay;
    }

    public int dropOff() {
        return dropOff;
    }

    public int maxSpreadLevel() {
        return maxSpreadLevel;
    }

    public boolean canFormSources() {
        return canFormSources;
    }
}
