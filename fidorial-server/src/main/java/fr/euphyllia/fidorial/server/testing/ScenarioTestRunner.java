package fr.euphyllia.fidorial.server.testing;

import fr.fidorial.testing.ScenarioTestInfo;
import fr.fidorial.testing.ScenarioTestInstance;
import fr.fidorial.world.World;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

final class ScenarioTestRunner {

    private final List<ScenarioTestInfo> running = new ArrayList<>();
    private final List<ScenarioTestInfo> finished = new ArrayList<>();
    private final AtomicBoolean doneFired = new AtomicBoolean();
    private volatile @Nullable Runnable onComplete;

    public void start(final List<ScenarioTestInstance> tests, final World world) {
        for (final ScenarioTestInstance test : tests) {
            running.add(new ScenarioTestInfo(test, world));
        }
    }

    public void onComplete(final Runnable action) {
        this.onComplete = action;
    }

    public void tick(final int currentTick) {
        for (final ScenarioTestInfo info : List.copyOf(running)) {
            info.tick(currentTick);
            if (info.isDone()) {
                running.remove(info);
                finished.add(info);
            }
        }
        if (running.isEmpty() && doneFired.compareAndSet(false, true)) {
            final Runnable action = onComplete;
            if (action != null) action.run();
        }
    }

    public boolean isDone() {
        return running.isEmpty();
    }

    public List<ScenarioTestInfo> finished() {
        return finished;
    }

    public int failedRequiredCount() {
        return (int) finished.stream()
                .filter(i -> i.state() == ScenarioTestInfo.State.FAILED && i.instance().required())
                .count();
    }
}
