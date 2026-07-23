package fr.fidorial.plugin;

import fr.fidorial.event.AsyncEventHandler;
import fr.fidorial.event.EventHandler;
import fr.fidorial.event.EventPriority;
import fr.fidorial.event.Subscription;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface PluginEventBus {
    default <E> Subscription subscribe(final Class<E> eventClass, final EventHandler<? super E> handler) {
        return subscribe(eventClass, EventPriority.NORMAL, handler);
    }

    <E> Subscription subscribe(Class<E> eventClass, EventPriority priority, EventHandler<? super E> handler);

    default <E> Subscription subscribeAsync(final Class<E> eventClass, final AsyncEventHandler<? super E> handler) {
        return subscribeAsync(eventClass, EventPriority.NORMAL, handler);
    }

    <E> Subscription subscribeAsync(Class<E> eventClass, EventPriority priority, AsyncEventHandler<? super E> handler);

    Subscription registerSubscribers(Object instance);

    Subscription registerSubscribers(Class<?> clazz);

    void unsubscribeAll();
}
