package fr.euphyllia.fidorial.server.entity.ai;

import fr.fidorial.entity.ai.Goal;
import fr.fidorial.entity.ai.Goals;
import org.jspecify.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

public class GoalSelector implements Goals {

    private final List<Goal> goals = new CopyOnWriteArrayList<>();

    private @Nullable Goal active;

    public void add(final Goal goal) {
        goals.add(goal);
        goals.sort(Comparator.comparingInt(Goal::priority));
    }

    @Override
    public boolean remove(final Goal goal) {
        if (active == goal) {
            stopAll();
        }
        return goals.remove(goal);
    }

    @Override
    public boolean removeIf(final Predicate<Goal> filter) {
        if (active != null && filter.test(active)) {
            stopAll();
        }
        return goals.removeIf(filter);
    }

    @Override
    public void clear() {
        stopAll();
        goals.clear();
    }

    @Override
    public List<Goal> all() {
        return List.copyOf(goals);
    }

    public void tick() {
        if (active != null) {
            for (final Goal goal : goals) {
                if (goal.priority() >= active.priority()) {
                    break;
                }
                if (goal.canStart()) {
                    active.stop();
                    active = goal;
                    goal.start();
                    break;
                }
            }
        }

        if (active != null && !active.shouldContinue()) {
            active.stop();
            active = null;
        }

        if (active == null) {
            for (final Goal goal : goals) {
                if (goal.canStart()) {
                    active = goal;
                    goal.start();
                    break;
                }
            }
        }
        if (active != null) {
            active.tick();
        }
    }

    public @Nullable Goal active() {
        return active;
    }

    public void stopAll() {
        if (active != null) {
            active.stop();
            active = null;
        }
    }
}
