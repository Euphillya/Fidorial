package fr.fidorial.testing;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A sequence of steps executed one tick at a time.
 * Built with {@link Builder} obtained from {@link ScenarioTestHelper#sequence()}.
 */
public final class ScenarioTestSequence {

    private record Step(Runnable action, boolean retryable) {
    }

    private final Deque<Step> steps;

    private ScenarioTestSequence(final Deque<Step> steps) {
        this.steps = steps;
    }

    boolean tick() {
        while (!steps.isEmpty()) {
            final Step step = steps.peekFirst();
            try {
                step.action().run();
            } catch (final ScenarioAssertionException e) {
                if (step.retryable()) {
                    return false; // retry next tick
                }
                throw e;
            }
            steps.pollFirst();
        }
        return true;
    }

    /**
     * Gathers steps for a {@link ScenarioTestSequence}.
     * <p>
     * Steps only start ticking once {@link #build()} is called.
     */
    public static final class Builder {

        private final ScenarioTestInfo info;
        private final Deque<Step> steps = new ArrayDeque<>();
        private boolean built;

        Builder(final ScenarioTestInfo info) {
            this.info = info;
        }

        /**
         * Runs the {@code action} once, then moves to the next step.
         */
        public Builder execute(final Runnable action) {
            checkNotBuilt();
            steps.addLast(new Step(action, false));
            return this;
        }

        /**
         * Retries the {@code assertion} every tick until it stops throwing {@link ScenarioAssertionException}.
         */
        public Builder waitUntil(final Runnable assertion) {
            checkNotBuilt();
            steps.addLast(new Step(assertion, true));
            return this;
        }

        /**
         * Builds and returns the {@link ScenarioTestSequence}.
         */
        public ScenarioTestSequence build() {
            checkNotBuilt();
            built = true;
            final ScenarioTestSequence sequence = new ScenarioTestSequence(steps);
            info.attachSequence(sequence);
            return sequence;
        }

        private void checkNotBuilt() {
            if (built) {
                throw new IllegalStateException("Sequence already built");
            }
        }
    }
}
