package fr.fidorial.event;

import java.util.function.Consumer;

public interface EventBus {

    default <E extends Event> Subscription subscribe(Class<E> type, Consumer<E> listener) {
        return subscribe(type, EventPriority.NORMAL, listener);
    }

    <E extends Event> Subscription subscribe(Class<E> type, EventPriority priority, Consumer<E> listener);


    <E extends Event> E post(E event);

    void unsubscribeAll(Object owner);
}
