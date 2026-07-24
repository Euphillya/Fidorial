package fr.fidorial.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method as an event subscriber.
 *
 * @since 0.1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {
    /**
     * Gets the priority used when dispatching events to this subscriber.
     *
     * @return the subscriber priority
     * @since 0.1.0
     */
    EventPriority priority() default EventPriority.NORMAL;
}
