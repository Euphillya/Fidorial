package fr.fidorial.registry;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

import java.util.Objects;

public record TypedKey<T>(RegistryKey<T> registry, Key key) {

    public TypedKey {
        Objects.requireNonNull(registry, "registry");
        Objects.requireNonNull(key, "key");
    }

    public static <T> TypedKey<T> create(RegistryKey<T> registry, Key key) {
        return new TypedKey<>(registry, key);
    }

    public static <T> TypedKey<T> create(RegistryKey<T> registry, @KeyPattern String key) {
        return new TypedKey<>(registry, Key.key(key));
    }

    @Override
    public String toString() {
        return "TypedKey[" + registry.key() + " / " + key + "]";
    }
}
