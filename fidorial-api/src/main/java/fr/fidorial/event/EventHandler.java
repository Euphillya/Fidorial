package fr.fidorial.event;

/**
 * Handles an event synchronously.
 *
 * @param <E> the event type
 * @since 0.1.0
 */
@FunctionalInterface
public interface EventHandler<E> {
    /**
     * Handles an event.
     *
     * @param event the event to handle
     * @throws Throwable if handling fails
     * @since 0.1.0
     */
    void handle(E event) throws Throwable;
}
