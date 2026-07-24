package fr.fidorial.plugin;

import fr.fidorial.event.AsyncEventHandler;
import fr.fidorial.event.EventBus;
import fr.fidorial.event.EventHandler;
import fr.fidorial.event.EventPriority;
import fr.fidorial.event.Subscribe;
import fr.fidorial.event.Subscription;
import org.jetbrains.annotations.ApiStatus;

/**
 * Plugin-scoped access to the server {@link EventBus}.
 * <p>
 * Subscriptions created through this facade are owned by the plugin that
 * received it, so callers do not need to pass a {@link Plugin} argument.
 *
 * @see EventBus
 * @since 0.1.0
 */
@ApiStatus.NonExtendable
public interface PluginEventBus {
    /**
     * Subscribes a synchronous event handler with {@link EventPriority#NORMAL} priority.
     *
     * @param eventClass the event class
     * @param handler    the handler to call
     * @param <E>        the event type
     * @return the created subscription
     * @see EventBus#subscribe(Class, EventHandler, Plugin)
     * @since 0.1.0
     */
    default <E> Subscription subscribe(final Class<E> eventClass, final EventHandler<? super E> handler) {
        return subscribe(eventClass, EventPriority.NORMAL, handler);
    }

    /**
     * Subscribes a synchronous event handler.
     *
     * @param eventClass the event class
     * @param priority   the event priority
     * @param handler    the handler to call
     * @param <E>        the event type
     * @return the created subscription
     * @see EventBus#subscribe(Class, EventPriority, EventHandler, Plugin)
     * @since 0.1.0
     */
    <E> Subscription subscribe(Class<E> eventClass, EventPriority priority, EventHandler<? super E> handler);

    /**
     * Subscribes an asynchronous event handler with {@link EventPriority#NORMAL} priority.
     *
     * @param eventClass the event class
     * @param handler    the handler to call
     * @param <E>        the event type
     * @return the created subscription
     * @see EventBus#subscribeAsync(Class, AsyncEventHandler, Plugin)
     * @since 0.1.0
     */
    default <E> Subscription subscribeAsync(final Class<E> eventClass, final AsyncEventHandler<? super E> handler) {
        return subscribeAsync(eventClass, EventPriority.NORMAL, handler);
    }

    /**
     * Subscribes an asynchronous event handler.
     *
     * @param eventClass the event class
     * @param priority   the event priority
     * @param handler    the handler to call
     * @param <E>        the event type
     * @return the created subscription
     * @see EventBus#subscribeAsync(Class, EventPriority, AsyncEventHandler, Plugin)
     * @since 0.1.0
     */
    <E> Subscription subscribeAsync(Class<E> eventClass, EventPriority priority, AsyncEventHandler<? super E> handler);

    /**
     * Registers non-static {@link Subscribe} methods from an instance.
     *
     * @param instance the subscriber instance
     * @return a subscription that manages all created subscriptions
     * @see EventBus#registerSubscribers(Object, Plugin)
     * @since 0.1.0
     */
    Subscription registerSubscribers(Object instance);

    /**
     * Registers static {@link Subscribe} methods from a class.
     *
     * @param clazz the subscriber class
     * @return a subscription that manages all created subscriptions
     * @see EventBus#registerSubscribers(Class, Plugin)
     * @since 0.1.0
     */
    Subscription registerSubscribers(Class<?> clazz);

    /**
     * Unsubscribes all subscriptions owned by this plugin.
     *
     * @see EventBus#unsubscribeAll(Plugin)
     * @since 0.1.0
     */
    void unsubscribeAll();
}
