package fr.euphyllia.fidorial.server.plugin;

import fr.fidorial.event.AsyncEventHandler;
import fr.fidorial.event.EventBus;
import fr.fidorial.event.EventHandler;
import fr.fidorial.event.EventPriority;
import fr.fidorial.event.Subscription;
import fr.fidorial.plugin.Plugin;
import fr.fidorial.plugin.PluginEventBus;

final class SimplePluginEventBus implements PluginEventBus {
    private final EventBus events;
    private final Plugin plugin;

    SimplePluginEventBus(final EventBus events, final Plugin plugin) {
        this.events = events;
        this.plugin = plugin;
    }

    @Override
    public <E> Subscription subscribe(final Class<E> eventClass, final EventPriority priority, final EventHandler<? super E> handler) {
        return events.subscribe(eventClass, priority, handler, plugin);
    }

    @Override
    public <E> Subscription subscribeAsync(final Class<E> eventClass, final EventPriority priority, final AsyncEventHandler<? super E> handler) {
        return events.subscribeAsync(eventClass, priority, handler, plugin);
    }

    @Override
    public Subscription registerSubscribers(final Object instance) {
        return events.registerSubscribers(instance, plugin);
    }

    @Override
    public Subscription registerSubscribers(final Class<?> clazz) {
        return events.registerSubscribers(clazz, plugin);
    }

    @Override
    public void unsubscribeAll() {
        events.unsubscribeAll(plugin);
    }
}
