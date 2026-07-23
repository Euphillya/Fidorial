package fr.fidorial.event;

import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a registered event subscription.
 *
 * @since 0.1.0
 */
@ApiStatus.NonExtendable
public interface Subscription {
    /**
     * Checks whether this subscription is still active.
     *
     * @return {@code true} if this subscription is active
     * @since 0.1.0
     */
    boolean isActive();

    /**
     * Unsubscribes this subscription.
     *
     * @since 0.1.0
     */
    void unsubscribe();
}
