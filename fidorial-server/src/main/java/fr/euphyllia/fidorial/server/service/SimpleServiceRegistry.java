package fr.euphyllia.fidorial.server.service;

import fr.euphyllia.fidorial.api.service.ServicePriority;
import fr.euphyllia.fidorial.api.service.ServiceRegistry;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public final class SimpleServiceRegistry implements ServiceRegistry {

    private static final ComponentLogger LOGGER = getLogger(SimpleServiceRegistry.class);

    private final Map<Class<?>, List<Provider<?>>> providers = new ConcurrentHashMap<>();

    @Override
    public <T> void register(Class<T> service, T implementation, Object owner, ServicePriority priority) {
        if (!service.isInstance(implementation)) {
            throw new IllegalArgumentException(implementation.getClass().getName()
                    + " n'implemente pas " + service.getName());
        }
        List<Provider<?>> list = providers.computeIfAbsent(service, s -> new ArrayList<>());
        synchronized (list) {
            list.add(new Provider<>(implementation, owner, priority));
            list.sort(Comparator.comparing((Provider<?> p) -> p.priority).reversed());
        }
        LOGGER.debug("Service {} fourni par {} (priorite {})",
                service.getSimpleName(), implementation.getClass().getName(), priority);
    }

    @Override
    public <T> T get(Class<T> service) {
        return find(service).orElseThrow(() -> new IllegalStateException(
                "Aucune implementation enregistree pour " + service.getName()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> find(Class<T> service) {
        List<Provider<?>> list = providers.get(service);
        if (list == null) {
            return Optional.empty();
        }
        synchronized (list) {
            return list.isEmpty() ? Optional.empty() : Optional.of((T) list.getFirst().implementation);
        }
    }

    @Override
    public void unregisterAll(Object owner) {
        for (List<Provider<?>> list : providers.values()) {
            synchronized (list) {
                list.removeIf(p -> p.owner == owner);
            }
        }
    }

    private record Provider<T>(T implementation, Object owner, ServicePriority priority) {
    }
}
