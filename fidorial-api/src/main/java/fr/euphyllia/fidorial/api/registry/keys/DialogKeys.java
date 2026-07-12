// GENERATED CODE - DO NOT EDIT.
// Regenerate: python3 tool/registry-generator/generate.py <project-root>
package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.Dialog;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:dialog} registry.
 */
public final class DialogKeys {

    private DialogKeys() {
    }

    public static final TypedKey<Dialog> CUSTOM_OPTIONS = create("custom_options");
    public static final TypedKey<Dialog> QUICK_ACTIONS = create("quick_actions");
    public static final TypedKey<Dialog> SERVER_LINKS = create("server_links");

    private static TypedKey<Dialog> create(String value) {
        return TypedKey.create(RegistryKey.DIALOG, Key.minecraft(value));
    }
}
