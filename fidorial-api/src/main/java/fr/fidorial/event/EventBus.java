package fr.fidorial.event;

import fr.fidorial.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

@ApiStatus.NonExtendable
public interface EventBus {
    <E> E fire(E event);

    <E> Optional<E> fire(Class<E> eventClass, Supplier<E> eventFactory);

    <E> CompletionStage<E> fireAsync(E event);

    <E> CompletionStage<@Nullable E> fireAsync(Class<E> eventClass, Supplier<E> eventFactory);

    <E> void fireAndForget(E event);

    <E> void fireAndForget(Class<E> eventClass, Supplier<E> eventFactory);

    boolean hasSubscribers(Class<?> eventClass);

    default <E> Subscription subscribe(final Class<E> eventClass, final EventHandler<? super E> handler, final Plugin plugin) {
        return subscribe(eventClass, EventPriority.NORMAL, handler, plugin);
    }

    <E> Subscription subscribe(Class<E> eventClass, EventPriority priority, EventHandler<? super E> handler, Plugin plugin);

    default <E> Subscription subscribeAsync(final Class<E> eventClass, final AsyncEventHandler<? super E> handler, final Plugin plugin) {
        return subscribeAsync(eventClass, EventPriority.NORMAL, handler, plugin);
    }

    <E> Subscription subscribeAsync(Class<E> eventClass, EventPriority priority, AsyncEventHandler<? super E> handler, Plugin plugin);

    List<Subscription> registerSubscribers(Object instance, Plugin plugin);

    List<Subscription> registerSubscribers(Class<?> clazz, Plugin plugin);

    void unsubscribeAll(Class<?> clazz);

    void unsubscribeAll(Object instance);

    void unsubscribeAll(Plugin plugin);
}
