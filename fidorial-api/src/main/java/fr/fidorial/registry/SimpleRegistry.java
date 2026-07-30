package fr.fidorial.registry;

import net.kyori.adventure.key.Key;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public final class SimpleRegistry<T> implements Registry<T> {

    private final RegistryKey<T> registryKey;
    private final Map<Key, T> byKey;
    private final List<T> values;

    private SimpleRegistry(final RegistryKey<T> registryKey, final Map<Key, T> byKey) {
        this.registryKey = registryKey;
        this.byKey = Map.copyOf(byKey);
        this.values = List.copyOf(byKey.values());
    }

    public static <T> SimpleRegistry<T> of(
            final RegistryKey<T> registryKey,
            final Collection<TypedKey<T>> keys,
            final Function<Key, T> resolver
    ) {
        final Map<Key, T> byKey = new LinkedHashMap<>();
        for (final TypedKey<T> typedKey : keys) {
            byKey.put(typedKey.key(), resolver.apply(typedKey.key()));
        }
        return new SimpleRegistry<>(registryKey, byKey);
    }

    @Override public RegistryKey<T> registryKey() {
        return registryKey;
    }

    @Override public T get(final TypedKey<T> key) {
        return byKey.get(key.key());
    }

    @Override public Optional<T> find(final TypedKey<T> key) {
        return Optional.ofNullable(byKey.get(key.key()));
    }

    @Override
    public TypedKey<T> key(final T value) {
        throw new UnsupportedOperationException("Unsupported for registry: " + registryKey);
    }

    @Override public Collection<T> values() { return values; }

    @Override public Stream<T> stream() { return values.stream(); }
}
