package fr.euphyllia.fidorial.api.entity.ai;

public interface Goal {

    int priority();

    boolean canStart();

    boolean shouldContinue();

    default void start() {
    }

    default void stop() {
    }

    default void tick() {
    }
}
