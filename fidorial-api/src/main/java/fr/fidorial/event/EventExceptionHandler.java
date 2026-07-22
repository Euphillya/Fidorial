package fr.fidorial.event;

@FunctionalInterface
public interface EventExceptionHandler {
    void handle(Throwable throwable, Class<?> eventClass, Subscription subscription);
}
