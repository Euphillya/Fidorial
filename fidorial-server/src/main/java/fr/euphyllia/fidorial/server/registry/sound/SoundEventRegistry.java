package fr.euphyllia.fidorial.server.registry.sound;

import fr.fidorial.registry.Registry;
import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.SoundEvent;
import net.kyori.adventure.key.Key;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public final class SoundEventRegistry implements Registry<SoundEvent> {

    private static final SoundEvent EMPTY = new SoundEvent() {};

    private final Map<Key, TypedKey<SoundEvent>> entries = new HashMap<>();

    @Override
    public RegistryKey<SoundEvent> registryKey() {
        return RegistryKey.SOUND_EVENT;
    }

    @Override
    public SoundEvent get(final TypedKey<SoundEvent> key) {
        if (!entries.containsKey(key.key())) {
            throw new IllegalArgumentException("Unknown sound event: " + key);
        }

        return EMPTY;
    }

    @Override
    public Optional<SoundEvent> find(final TypedKey<SoundEvent> key) {
        return entries.containsKey(key.key())
                ? Optional.of(EMPTY)
                : Optional.empty();
    }

    @Override
    public TypedKey<SoundEvent> key(final SoundEvent value) {
        throw new UnsupportedOperationException("SoundEvent has no unique value mapping");
    }

    @Override
    public Collection<SoundEvent> values() {
        return Collections.nCopies(entries.size(), EMPTY);
    }

    @Override
    public Stream<SoundEvent> stream() {
        return values().stream();
    }

    public void register(final TypedKey<SoundEvent> key) {
        entries.put(key.key(), key);
    }
}
