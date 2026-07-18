package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.WolfVariant;
import net.kyori.adventure.key.KeyPattern;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:wolf_variant} registry.
 */
public final class WolfVariantKeys {

    public static final TypedKey<WolfVariant> ASHEN = create("ashen");
    public static final TypedKey<WolfVariant> BLACK = create("black");
    public static final TypedKey<WolfVariant> CHESTNUT = create("chestnut");
    public static final TypedKey<WolfVariant> PALE = create("pale");
    public static final TypedKey<WolfVariant> RUSTY = create("rusty");
    public static final TypedKey<WolfVariant> SNOWY = create("snowy");
    public static final TypedKey<WolfVariant> SPOTTED = create("spotted");
    public static final TypedKey<WolfVariant> STRIPED = create("striped");
    public static final TypedKey<WolfVariant> WOODS = create("woods");

    private WolfVariantKeys() {
    }

    private static TypedKey<WolfVariant> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.WOLF_VARIANT, value);
    }
}
