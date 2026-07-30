package fr.euphyllia.fidorial.server.registry.entity;

import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.fidorial.entity.EntityType;
import fr.fidorial.registry.Registry;
import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class EntityTypeRegistry implements Registry<EntityType> {

    @Override
    public RegistryKey<EntityType> registryKey() {
        return RegistryKey.ENTITY_TYPE;
    }

    @Override
    public EntityType get(final TypedKey<EntityType> key) {
        return EntityTypes.get(key.key());
    }

    @Override
    public Optional<EntityType> find(final TypedKey<EntityType> key) {
        return Optional.ofNullable(EntityTypes.get(key.key()));
    }

    @Override
    public TypedKey<EntityType> key(final EntityType value) {
        return TypedKey.create(registryKey(), value.key());
    }

    @Override
    public Collection<EntityType> values() {
        return StreamSupport.stream(EntityTypes.values().spliterator(), false).toList();
    }

    @Override
    public Stream<EntityType> stream() {
        return StreamSupport.stream(EntityTypes.values().spliterator(), false);
    }

    public int networkId(final EntityType type) {
        return EntityTypes.networkId(type);
    }

    public boolean hasNetworkId(final EntityType type) {
        return EntityTypes.hasNetworkId(type);
    }
}
