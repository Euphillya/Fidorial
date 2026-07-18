package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.Dialog;
import net.kyori.adventure.key.KeyPattern;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:dialog} registry.
 */
public final class DialogKeys {

    public static final TypedKey<Dialog> CUSTOM_OPTIONS = create("custom_options");
    public static final TypedKey<Dialog> QUICK_ACTIONS = create("quick_actions");
    public static final TypedKey<Dialog> SERVER_LINKS = create("server_links");

    private DialogKeys() {
    }

    private static TypedKey<Dialog> create(@KeyPattern String value) {
        return TypedKey.create(RegistryKey.DIALOG, value);
    }
}
