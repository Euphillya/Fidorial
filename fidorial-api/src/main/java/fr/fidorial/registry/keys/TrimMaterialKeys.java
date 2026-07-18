package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.TrimMaterial;
import net.kyori.adventure.key.KeyPattern;

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

    private static TypedKey<TrimMaterial> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.TRIM_MATERIAL, value);
    }
}
