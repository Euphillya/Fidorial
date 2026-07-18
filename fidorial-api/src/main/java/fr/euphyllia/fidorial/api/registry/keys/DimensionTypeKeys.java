package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.DimensionType;
import net.kyori.adventure.key.KeyPattern;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:dimension_type} registry.
 */
public final class DimensionTypeKeys {

    public static final TypedKey<DimensionType> OVERWORLD = create("overworld");
    public static final TypedKey<DimensionType> OVERWORLD_CAVES = create("overworld_caves");
    public static final TypedKey<DimensionType> THE_END = create("the_end");
    public static final TypedKey<DimensionType> THE_NETHER = create("the_nether");

    private DimensionTypeKeys() {
    }

    private static TypedKey<DimensionType> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.DIMENSION_TYPE, value);
    }
}
