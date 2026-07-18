package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.CatVariant;
import net.kyori.adventure.key.KeyPattern;

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

    private static TypedKey<CatVariant> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.CAT_VARIANT, value);
    }
}
