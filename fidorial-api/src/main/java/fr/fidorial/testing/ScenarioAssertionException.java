package fr.fidorial.testing;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
final class ScenarioAssertionException extends RuntimeException {

    private final int tick;

    ScenarioAssertionException(final String message, final int tick) {
        super(message);
        this.tick = tick;
    }

    int tick() {
        return tick;
    }
}
