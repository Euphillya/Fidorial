package fr.fidorial.event;

import fr.fidorial.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

/**
 * Dispatches events to registered subscribers.
 *
 * @since 0.1.0
 */
@ApiStatus.NonExtendable
public interface EventBus {
    /**
     * Fires an event synchronously.
     *
     * @param event the event to fire
     * @param <E>   the event type
     * @return the fired event
     * @since 0.1.0
     */
    <E> E fire(E event);

    /**
     * Fires an event synchronously if the event type has subscribers.
     * <p>
     * The event factory is only called when subscribers are present.
     *
     * @param eventClass   the event class
     * @param eventFactory supplies the event to fire
     * @param <E>          the event type
     * @return the fired event, or an empty optional when there are no subscribers
     * @since 0.1.0
     */
    <E> Optional<E> fire(Class<E> eventClass, Supplier<E> eventFactory);

    /**
     * Fires an event asynchronously.
     * <p>
     * Events implementing {@link AsyncEventTimeout} bound how long each
     * asynchronous subscriber may hold dispatch.
     *
     * @param event the event to fire
     * @param <E>   the event type
     * @return a stage completed with the fired event after asynchronous dispatch
     * @since 0.1.0
     */
    <E> CompletionStage<E> fireAsync(E event);

    /**
     * Fires an event asynchronously if the event type has subscribers.
     *
     * <p>The event factory is only called when subscribers are present.</p>
     * <p>
     * Events implementing {@link AsyncEventTimeout} bound how long each
     * asynchronous subscriber may hold dispatch.
     *
     * @param eventClass   the event class
     * @param eventFactory supplies the event to fire
     * @param <E>          the event type
     * @return a stage completed with the fired event, or {@code null} when there are no subscribers
     * @since 0.1.0
     */
    <E> CompletionStage<@Nullable E> fireAsync(Class<E> eventClass, Supplier<E> eventFactory);

    /**
     * Fires an event asynchronously without exposing its completion stage.
     * <p>
     * Events implementing {@link AsyncEventTimeout} bound how long each
     * asynchronous subscriber may hold dispatch.
     *
     * @param event the event to fire
     * @param <E>   the event type
     * @see #fireAsync(Object)
     * @since 0.1.0
     */
    <E> void fireAndForget(E event);

    /**
     * Fires an event asynchronously without exposing its completion stage if the
     * event type has subscribers.
     * <p>
     * The event factory is only called when subscribers are present.
     * <p>
     * Events implementing {@link AsyncEventTimeout} bound how long each
     * asynchronous subscriber may hold dispatch.
     *
     * @param eventClass   the event class
     * @param eventFactory supplies the event to fire
     * @param <E>          the event type
     * @see #fireAsync(Class, Supplier)
     * @since 0.1.0
     */
    <E> void fireAndForget(Class<E> eventClass, Supplier<E> eventFactory);

    /**
     * Checks whether an event type has subscribers.
     *
     * @param eventClass the event class
     * @return {@code true} if the event type has subscribers
     * @since 0.1.0
     */
    boolean hasSubscribers(Class<?> eventClass);

    /**
     * Subscribes a synchronous event handler with {@link EventPriority#NORMAL} priority.
     *
     * @param eventClass the event class
     * @param handler    the handler to call
     * @param plugin     the owning plugin
     * @param <E>        the event type
     * @return the created subscription
     * @since 0.1.0
     */
    default <E> Subscription subscribe(final Class<E> eventClass, final EventHandler<? super E> handler, final Plugin plugin) {
        return subscribe(eventClass, EventPriority.NORMAL, handler, plugin);
    }

    /**
     * Subscribes a synchronous event handler.
     *
     * @param eventClass the event class
     * @param priority   the event priority
     * @param handler    the handler to call
     * @param plugin     the owning plugin
     * @param <E>        the event type
     * @return the created subscription
     * @since 0.1.0
     */
    <E> Subscription subscribe(Class<E> eventClass, EventPriority priority, EventHandler<? super E> handler, Plugin plugin);

    /**
     * Subscribes an asynchronous event handler with {@link EventPriority#NORMAL} priority.
     *
     * @param eventClass the event class
     * @param handler    the handler to call
     * @param plugin     the owning plugin
     * @param <E>        the event type
     * @return the created subscription
     * @since 0.1.0
     */
    default <E> Subscription subscribeAsync(final Class<E> eventClass, final AsyncEventHandler<? super E> handler, final Plugin plugin) {
        return subscribeAsync(eventClass, EventPriority.NORMAL, handler, plugin);
    }

    /**
     * Subscribes an asynchronous event handler.
     *
     * @param eventClass the event class
     * @param priority   the event priority
     * @param handler    the handler to call
     * @param plugin     the owning plugin
     * @param <E>        the event type
     * @return the created subscription
     * @since 0.1.0
     */
    <E> Subscription subscribeAsync(Class<E> eventClass, EventPriority priority, AsyncEventHandler<? super E> handler, Plugin plugin);

    /**
     * Registers non-static {@link Subscribe} methods from an instance.
     *
     * @param instance the subscriber instance
     * @param plugin   the owning plugin
     * @return a subscription that manages all created subscriptions
     * @since 0.1.0
     */
    Subscription registerSubscribers(Object instance, Plugin plugin);

    /**
     * Registers static {@link Subscribe} methods from a class.
     *
     * @param clazz  the subscriber class
     * @param plugin the owning plugin
     * @return a subscription that manages all created subscriptions
     * @since 0.1.0
     */
    Subscription registerSubscribers(Class<?> clazz, Plugin plugin);

    /**
     * Unsubscribes all subscriptions owned by a plugin.
     *
     * @param plugin the plugin
     * @since 0.1.0
     */
    void unsubscribeAll(Plugin plugin);
}
