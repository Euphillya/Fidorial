package fr.euphyllia.fidorial.server.registry.entity;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.fidorial.entity.EntityType;
import fr.fidorial.registry.Registry;
import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public final class EntityTypeRegistry implements Registry<EntityType> {

    private final Map<TypedKey<EntityType>, EntityType> entries =
            new ConcurrentHashMap<>();

    private final Map<TypedKey<EntityType>, Integer> networkIds =
            new ConcurrentHashMap<>();


    public EntityTypeRegistry() {
        for (EntityType type : EntityTypes.values()) {
            register(
                    type,
                    EntityTypes.networkId(type)
            );
        }
    }


    @Override
    public RegistryKey<EntityType> registryKey() {
        return RegistryKey.ENTITY_TYPE;
    }


    @Override
    public EntityType get(TypedKey<EntityType> key) {
        return entries.get(key);
    }


    @Override
    public Optional<EntityType> find(TypedKey<EntityType> key) {
        return Optional.ofNullable(entries.get(key));
    }


    @Override
    public TypedKey<EntityType> key(EntityType value) {
        return TypedKey.create(
                registryKey(),
                value.key()
        );
    }


    @Override
    public Collection<EntityType> values() {
        return entries.values();
    }


    @Override
    public Stream<EntityType> stream() {
        return entries.values().stream();
    }


    public EntityType register(EntityType type) {
        return register(type, -1);
    }


    public EntityType register(
            EntityType type,
            int networkId
    ) {
        TypedKey<EntityType> key = key(type);

        if (entries.putIfAbsent(key, type) != null) {
            throw new IllegalStateException(
                    "Duplicate entity type: " + type.key()
            );
        }

        if (networkId >= 0) {
            networkIds.put(key, networkId);
        }

        return type;
    }


    public int networkId(EntityType type) {
        Integer id = networkIds.get(key(type));

        if (id == null) {
            throw new IllegalStateException(
                    "Missing network id for " + type.key()
            );
        }

        return id;
    }

    public boolean hasNetworkId(EntityType type) {
        return networkIds.containsKey(key(type));
    }
}
