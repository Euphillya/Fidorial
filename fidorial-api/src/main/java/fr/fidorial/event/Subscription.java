package fr.fidorial.event;

public interface Subscription extends AutoCloseable {

    boolean isActive();

    void unsubscribe();

    @Override
    default void close() {
        unsubscribe();
    }
}
