package fr.fidorial.event;

import java.util.concurrent.CompletionStage;

/**
 * Handles an event asynchronously.
 *
 * @param <E> the event type
 * @since 0.1.0
 */
@FunctionalInterface
public interface AsyncEventHandler<E> {
    /**
     * Handles an event and returns the stage representing asynchronous work.
     *
     * @param event the event to handle
     * @return a stage completed with the handled event
     * @throws Throwable if handling fails
     * @since 0.1.0
     */
    CompletionStage<E> handle(E event) throws Throwable;
}
