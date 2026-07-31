package fr.euphyllia.fidorial.server.testing;

import fr.fidorial.testing.ScenarioTestInfo;
import fr.fidorial.testing.ScenarioTestInstance;
import fr.fidorial.world.World;

import java.util.ArrayList;
import java.util.List;

final class ScenarioTestRunner {

    private final List<ScenarioTestInfo> running = new ArrayList<>();
    private final List<ScenarioTestInfo> finished = new ArrayList<>();

    public void start(final List<ScenarioTestInstance> tests, final World world) {
        for (final ScenarioTestInstance test : tests) {
            running.add(new ScenarioTestInfo(test, world));
        }
    }

    public void tick(final int currentTick) {
        for (final ScenarioTestInfo info : List.copyOf(running)) {
            info.tick(currentTick);
            if (info.isDone()) {
                running.remove(info);
                finished.add(info);
            }
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
