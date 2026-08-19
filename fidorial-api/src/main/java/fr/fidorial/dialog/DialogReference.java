package fr.fidorial.dialog;

import net.kyori.adventure.key.Key;

import java.util.Objects;

/**
 * A pointer to a dialog held by the {@linkplain DialogRegistry dialog registry}.
 *
 * @param key the key the dialog is registered under
 * @see Dialog#reference(Key)
 * @since 0.1.0
 */
public record DialogReference(Key key) implements Dialog {

    /**
     * @param key the key the dialog is registered under
     * @since 0.1.0
     */
    public DialogReference {
        Objects.requireNonNull(key, "key");
    }
}
