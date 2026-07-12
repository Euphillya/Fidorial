// GENERATED CODE - DO NOT EDIT.
// Regenerate: python3 tool/registry-generator/generate.py <project-root>
package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.DimensionType;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:dimension_type} registry.
 */
public final class DimensionTypeKeys {

    private DimensionTypeKeys() {
    }

    public static final TypedKey<DimensionType> OVERWORLD = create("overworld");
    public static final TypedKey<DimensionType> OVERWORLD_CAVES = create("overworld_caves");
    public static final TypedKey<DimensionType> THE_END = create("the_end");
    public static final TypedKey<DimensionType> THE_NETHER = create("the_nether");

    private static TypedKey<DimensionType> create(String value) {
        return TypedKey.create(RegistryKey.DIMENSION_TYPE, Key.minecraft(value));
    }
}
