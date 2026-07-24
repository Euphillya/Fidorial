package fr.fidorial.event;

import java.time.Duration;

/**
 * Allows an event to bound how long each asynchronous subscriber may hold dispatch.
 *
 * @since 0.1.0
 */
public interface AsyncEventTimeout {
    /**
     * Gets the maximum time an asynchronous subscriber may take before dispatch skips it.
     *
     * @return the async subscriber timeout
     * @since 0.1.0
     */
    Duration asyncHandlerTimeout();

    /**
     * Gets the time after which a warning is logged for a still-running asynchronous subscriber.
     *
     * @return the async subscriber warning timeout, or {@link Duration#ZERO} to disable warnings
     * @since 0.1.0
     */
    default Duration asyncHandlerWarningTimeout() {
        return asyncHandlerTimeout().dividedBy(2);
    }
}
