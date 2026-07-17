package fr.euphyllia.fidorial.api.registry;

import net.kyori.adventure.key.KeyPattern;
import org.jspecify.annotations.NonNull;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.OptionalInt;

import static java.util.Objects.requireNonNull;

/**
 * NamespacedKey Bukkit
 * <p>
 * Based off of <a href="https://github.com/PaperMC/adventure/blob/main/5/key/src/main/java/net/kyori/adventure/key/KeyImpl.java">Adventure KeyImpl</a>
 * Licensed under the MIT license.
 *
 * @param namespace
 * @param value
 */
public record Key(String namespace, String value) implements net.kyori.adventure.key.Key {
    public Key {
        Key.checkError("namespace", namespace, namespace, value, net.kyori.adventure.key.Key.checkNamespace(namespace), KeyPattern.NAMESPACE_PATTERN);
        Key.checkError("value", value, namespace, value, net.kyori.adventure.key.Key.checkValue(value), KeyPattern.VALUE_PATTERN);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    static void checkError(final String name, final String checkPart, final String namespace, final String value, final OptionalInt index, final String pattern) {
        requireNonNull(checkPart, name);
        if (index.isPresent()) {
            final int indexValue = index.getAsInt();
            if (indexValue == -1) {
                throw new RuntimeException(String.format(
                        "'%s' is not a valid value for %s",
                        checkPart,
                        name
                ));
            } else {
                final char character = checkPart.charAt(indexValue);
                throw new RuntimeException(String.format(
                        "Non " + pattern + " character in %s of Key[%s] at index %d ('%s', bytes: %s)",
                        name,
                        asString(namespace, value),
                        indexValue,
                        character,
                        Arrays.toString(String.valueOf(character).getBytes(StandardCharsets.UTF_8))
                ));
            }
        }
    }

    static boolean allowedInNamespace(final char character) {
        return character == '_' || character == '-' || (character >= 'a' && character <= 'z') || (character >= '0' && character <= '9') || character == '.';
    }

    static boolean allowedInValue(final char character) {
        return character == '_' || character == '-' || (character >= 'a' && character <= 'z') || (character >= '0' && character <= '9') || character == '.' || character == '/';
    }

    public static Key parse(final String key) {
        Objects.requireNonNull(key, "key");
        int colon = key.indexOf(':');
        if (colon < 0) {
            return minecraft(key);
        }
        return new Key(key.substring(0, colon), key.substring(colon + 1));
    }

    public static Key key(final String namespace, final String value) {
        return new Key(namespace, value);
    }

    public static Key minecraft(final String value) {
        return new Key(MINECRAFT_NAMESPACE, value);
    }

    @Override
    public @NonNull String asString() {
        return asString(this.namespace, this.value);
    }

    private static String asString(final String namespace, final String value) {
        return namespace + DEFAULT_SEPARATOR + value;
    }

    @Override
    public String toString() {
        return this.asString();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (!(other instanceof Key(String thatNamespace, String thatValue))) return false;
        return Objects.equals(this.namespace, thatNamespace) && Objects.equals(this.value, thatValue);
    }

    @Override
    public int compareTo(final net.kyori.adventure.key.@NonNull Key that) {
        return net.kyori.adventure.key.Key.super.compareTo(that);
    }
}
