package fr.fidorial.event;

/**
 * Determines when a subscriber runs during event dispatch.
 * <p>
 * Lower priorities are handled first, the higher the priority, the later it will be handled.
 * {@link #MONITOR} is reserved for observing the final event state.
 *
 * @since 0.1.0
 */
public enum EventPriority {
    /**
     * Runs first.
     * <p>
     * Use this for foundational changes that other subscribers should see,
     * such as making an early cancellation decision.
     *
     * @since 0.1.0
     */
    LOWEST,

    /**
     * Runs before normal subscribers.
     *
     * @since 0.1.0
     */
    LOW,

    /**
     * Runs at the default priority.
     * <p>
     * Use this for ordinary event handling when the handler does not need to
     * run especially early or late.
     *
     * @since 0.1.0
     */
    NORMAL,

    /**
     * Runs after normal subscribers.
     *
     * @since 0.1.0
     */
    HIGH,

    /**
     * Runs after high-priority subscribers.
     * <p>
     * Use this for final mutable changes before the event is observed as complete.
     *
     * @since 0.1.0
     */
    HIGHEST,

    /**
     * Runs as the very last priority.
     * <p>
     * Use this for logging, metrics, auditing, or other read-only observers
     * that need the final event state.
     * <p>
     * Mutating an event at this point has undefined behavior and must be avoided.
     *
     * @since 0.1.0
     */
    MONITOR
}
