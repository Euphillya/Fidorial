package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.DimensionType;
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
