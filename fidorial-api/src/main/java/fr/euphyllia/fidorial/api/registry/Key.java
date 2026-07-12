package fr.euphyllia.fidorial.api.registry;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * NamespacedKey Bukkit
 *
 * @param namespace
 * @param value
 */
public record Key(String namespace, String value) {

    public static final String MINECRAFT = "minecraft";

    private static final Pattern NAMESPACE = Pattern.compile("[a-z0-9_.-]+");
    private static final Pattern VALUE = Pattern.compile("[a-z0-9_.\\-/]+");

    public Key {
        Objects.requireNonNull(namespace, "namespace");
        Objects.requireNonNull(value, "value");
        if (!NAMESPACE.matcher(namespace).matches()) {
            throw new IllegalArgumentException("Invalid namespace: " + namespace);
        }
        if (!VALUE.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid key value: " + value);
        }
    }

    public static Key minecraft(String value) {
        return new Key(MINECRAFT, value);
    }

    public static Key parse(String key) {
        Objects.requireNonNull(key, "key");
        int colon = key.indexOf(':');
        if (colon < 0) {
            return minecraft(key);
        }
        return new Key(key.substring(0, colon), key.substring(colon + 1));
    }

    public String asString() {
        return namespace + ":" + value;
    }

    @Override
    public String toString() {
        return asString();
    }
}
