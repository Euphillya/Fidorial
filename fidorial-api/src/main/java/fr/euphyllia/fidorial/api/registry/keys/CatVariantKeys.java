// GENERATED CODE - DO NOT EDIT.
// Regenerate: python3 tool/registry-generator/generate.py <project-root>
package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.CatVariant;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:cat_variant} registry.
 */
public final class CatVariantKeys {

    public static final TypedKey<CatVariant> ALL_BLACK = create("all_black");
    public static final TypedKey<CatVariant> BLACK = create("black");
    public static final TypedKey<CatVariant> BRITISH_SHORTHAIR = create("british_shorthair");
    public static final TypedKey<CatVariant> CALICO = create("calico");
    public static final TypedKey<CatVariant> JELLIE = create("jellie");
    public static final TypedKey<CatVariant> PERSIAN = create("persian");
    public static final TypedKey<CatVariant> RAGDOLL = create("ragdoll");
    public static final TypedKey<CatVariant> RED = create("red");
    public static final TypedKey<CatVariant> SIAMESE = create("siamese");
    public static final TypedKey<CatVariant> TABBY = create("tabby");
    public static final TypedKey<CatVariant> WHITE = create("white");
    private CatVariantKeys() {
    }

    private static TypedKey<CatVariant> create(String value) {
        return TypedKey.create(RegistryKey.CAT_VARIANT, Key.minecraft(value));
    }
}
