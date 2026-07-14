package fr.euphyllia.fidorial.api.service;

import java.util.Optional;

public interface ServiceRegistry {

    <T> void register(Class<T> service, T implementation, Object owner, ServicePriority priority);

    default <T> void register(Class<T> service, T implementation, Object owner) {
        register(service, implementation, owner, ServicePriority.NORMAL);
    }

    <T> T get(Class<T> service);

    <T> Optional<T> find(Class<T> service);

    void unregisterAll(Object owner);
}
