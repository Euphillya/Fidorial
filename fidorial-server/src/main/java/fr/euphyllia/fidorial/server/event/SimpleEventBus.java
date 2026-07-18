package fr.euphyllia.fidorial.server.event;

import fr.euphyllia.fidorial.api.event.Event;
import fr.euphyllia.fidorial.api.event.EventBus;
import fr.euphyllia.fidorial.api.event.EventPriority;
import fr.euphyllia.fidorial.api.event.Subscription;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public final class SimpleEventBus implements EventBus {

    private static final ComponentLogger LOGGER = getLogger(SimpleEventBus.class);

    private final Map<Class<?>, List<Registration<?>>> byType = new ConcurrentHashMap<>();
    private final Map<Class<?>, List<Registration<?>>> resolved = new ConcurrentHashMap<>();

    private final ThreadLocal<Object> owner = new ThreadLocal<>();

    @Override
    public <E extends Event> Subscription subscribe(Class<E> type, EventPriority priority, Consumer<E> listener) {
        Registration<E> registration = new Registration<>(type, priority, listener, owner.get());
        byType.computeIfAbsent(type, t -> new CopyOnWriteArrayList<>()).add(registration);
        resolved.clear();
        return registration;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends Event> E post(E event) {
        for (Registration<?> registration : resolve(event.getClass())) {
            if (!registration.active) {
                continue;
            }
            try {
                ((Registration<E>) registration).listener.accept(event);
            } catch (Throwable t) {
                LOGGER.error("Listener en erreur sur {} (proprietaire : {})",
                        event.getClass().getSimpleName(), registration.owner, t);
            }
        }
        return event;
    }

    @Override
    public void unsubscribeAll(Object owner) {
        for (List<Registration<?>> registrations : byType.values()) {
            registrations.removeIf(r -> {
                boolean match = r.owner == owner;
                if (match) {
                    r.active = false;
                }
                return match;
            });
        }
        resolved.clear();
    }

    public void withOwner(Object pluginOwner, Runnable action) {
        Object previous = owner.get();
        owner.set(pluginOwner);
        try {
            action.run();
        } finally {
            if (previous == null) {
                owner.remove();
            } else {
                owner.set(previous);
            }
        }
    }

    private List<Registration<?>> resolve(Class<?> eventType) {
        return resolved.computeIfAbsent(eventType, type -> {
            List<Registration<?>> out = new ArrayList<>();
            collect(type, out);
            out.sort(Comparator.comparing(r -> r.priority));
            return List.copyOf(out);
        });
    }

    private void collect(Class<?> type, List<Registration<?>> out) {
        if (type == null || !Event.class.isAssignableFrom(type)) {
            return;
        }
        List<Registration<?>> direct = byType.get(type);
        if (direct != null) {
            out.addAll(direct);
        }
        collect(type.getSuperclass(), out);
        for (Class<?> itf : type.getInterfaces()) {
            collect(itf, out);
        }
    }

    private final class Registration<E extends Event> implements Subscription {

        private final Class<E> type;
        private final EventPriority priority;
        private final Consumer<E> listener;
        private final Object owner;
        private volatile boolean active = true;

        Registration(Class<E> type, EventPriority priority, Consumer<E> listener, Object owner) {
            this.type = type;
            this.priority = priority;
            this.listener = listener;
            this.owner = owner;
        }

        @Override
        public boolean isActive() {
            return active;
        }

        @Override
        public void unsubscribe() {
            if (!active) {
                return;
            }
            active = false;
            List<Registration<?>> registrations = byType.get(type);
            if (registrations != null) {
                registrations.remove(this);
            }
            resolved.clear();
        }
    }
}
