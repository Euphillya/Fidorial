package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.TrimMaterial;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:trim_material} registry.
 */
public final class TrimMaterialKeys {

    public static final TypedKey<TrimMaterial> AMETHYST = create("amethyst");
    public static final TypedKey<TrimMaterial> COPPER = create("copper");
    public static final TypedKey<TrimMaterial> DIAMOND = create("diamond");
    public static final TypedKey<TrimMaterial> EMERALD = create("emerald");
    public static final TypedKey<TrimMaterial> GOLD = create("gold");
    public static final TypedKey<TrimMaterial> IRON = create("iron");
    public static final TypedKey<TrimMaterial> LAPIS = create("lapis");
    public static final TypedKey<TrimMaterial> NETHERITE = create("netherite");
    public static final TypedKey<TrimMaterial> QUARTZ = create("quartz");
    public static final TypedKey<TrimMaterial> REDSTONE = create("redstone");
    public static final TypedKey<TrimMaterial> RESIN = create("resin");

    private TrimMaterialKeys() {
    }

    private static TypedKey<TrimMaterial> create(String value) {
        return TypedKey.create(RegistryKey.TRIM_MATERIAL, Key.minecraft(value));
    }
}
