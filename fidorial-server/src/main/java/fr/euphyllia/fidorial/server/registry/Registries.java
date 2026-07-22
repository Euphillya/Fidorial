package fr.euphyllia.fidorial.server.registry;

import fr.euphyllia.fidorial.server.registry.entity.EntityTypeRegistry;
import fr.fidorial.registry.Registry;
import fr.fidorial.registry.RegistryKey;

import java.util.HashMap;
import java.util.Map;

public final class Registries {

    private final RegistryHolder dynamic;
    private final RegistryHolder frozen;
    private final Map<RegistryKey<?>, Registry<?>> typedRegistries;

    private Registries(
            RegistryHolder dynamic,
            RegistryHolder frozen,
            Map<RegistryKey<?>, Registry<?>> typedRegistries
    ) {
        this.dynamic = dynamic;
        this.frozen = frozen;
        this.typedRegistries = Map.copyOf(typedRegistries);
    }

    public static Registries load() {
        RegistryDataLoader data = RegistryDataLoader.load();

        Map<RegistryKey<?>, Registry<?>> registries = new HashMap<>();

        EntityTypeRegistry entityTypes = new EntityTypeRegistry();

        registries.put(RegistryKey.ENTITY_TYPE, entityTypes);

        return new Registries(RegistryHolder.of(data.dynamic()), RegistryHolder.of(data.frozen()), registries);
    }

    public RegistryHolder dynamic() {
        return dynamic;
    }

    public RegistryHolder frozen() {
        return frozen;
    }

    @SuppressWarnings("unchecked")
    public <T> Registry<T> registry(RegistryKey<T> key) {
        Registry<T> registry = (Registry<T>) typedRegistries.get(key);

        if (registry == null) {
            throw new IllegalArgumentException("Unknown registry: " + key);
        }

        return registry;
    }
}
