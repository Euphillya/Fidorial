package fr.euphyllia.fidorial.api.event;

public interface Subscription extends AutoCloseable {

    boolean isActive();

    void unsubscribe();

    @Override
    default void close() {
        unsubscribe();
    }
}
