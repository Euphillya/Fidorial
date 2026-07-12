package fr.euphyllia.fidorial.api.registry;

import java.util.Objects;

public record TypedKey<T>(RegistryKey<T> registry, Key key) {

    public TypedKey {
        Objects.requireNonNull(registry, "registry");
        Objects.requireNonNull(key, "key");
    }

    public static <T> TypedKey<T> create(RegistryKey<T> registry, Key key) {
        return new TypedKey<>(registry, key);
    }

    public static <T> TypedKey<T> create(RegistryKey<T> registry, String key) {
        return new TypedKey<>(registry, Key.parse(key));
    }

    @Override
    public String toString() {
        return "TypedKey[" + registry.key() + " / " + key + "]";
    }
}
