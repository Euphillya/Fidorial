package fr.fidorial.event;

import java.util.concurrent.CompletionStage;

@FunctionalInterface
public interface AsyncEventHandler<E> {
    CompletionStage<E> handle(E event) throws Throwable;
}
