package fr.fidorial.event;

@FunctionalInterface
public interface EventHandler<E> {
    void handle(E event) throws Throwable;
}
