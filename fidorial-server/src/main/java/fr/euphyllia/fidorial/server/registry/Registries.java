package fr.euphyllia.fidorial.server.registry;

import fr.euphyllia.fidorial.server.registry.entity.EntityTypeRegistry;
import fr.euphyllia.fidorial.server.registry.sound.SoundEventRegistry;
import fr.fidorial.registry.Registry;
import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.keys.SoundEventKeys;

import java.util.HashMap;
import java.util.Map;

public final class Registries {

    private final RegistryHolder dynamic;
    private final RegistryHolder frozen;
    private final Map<RegistryKey<?>, Registry<?>> typedRegistries;

    private Registries(
            final RegistryHolder dynamic,
            final RegistryHolder frozen,
            final Map<RegistryKey<?>, Registry<?>> typedRegistries
    ) {
        this.dynamic = dynamic;
        this.frozen = frozen;
        this.typedRegistries = Map.copyOf(typedRegistries);
    }

    public static Registries load() {
        final RegistryDataLoader data = RegistryDataLoader.load();

        final Map<RegistryKey<?>, Registry<?>> registries = new HashMap<>();

        final EntityTypeRegistry entityTypes = new EntityTypeRegistry();
        final SoundEventRegistry soundEvents = new SoundEventRegistry();

        SoundEventKeys.values()
                .forEach(soundEvents::register);

        registries.put(RegistryKey.ENTITY_TYPE, entityTypes);
        registries.put(RegistryKey.SOUND_EVENT, soundEvents);

        return new Registries(RegistryHolder.of(data.dynamic()), RegistryHolder.of(data.frozen()), registries);
    }

    public RegistryHolder dynamic() {
        return dynamic;
    }

    public RegistryHolder frozen() {
        return frozen;
    }

    @SuppressWarnings("unchecked")
    public <T> Registry<T> registry(final RegistryKey<T> key) {
        final Registry<T> registry = (Registry<T>) typedRegistries.get(key);

        if (registry == null) {
            throw new IllegalArgumentException("Unknown registry: " + key);
        }

        return registry;
    }
}
