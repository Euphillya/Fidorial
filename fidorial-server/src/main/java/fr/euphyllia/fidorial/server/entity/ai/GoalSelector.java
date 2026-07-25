package fr.euphyllia.fidorial.server.entity.ai;

import fr.fidorial.entity.ai.Goal;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GoalSelector {

    private final List<Goal> goals = new ArrayList<>();

    private @Nullable Goal active;

    public void add(Goal goal) {
        goals.add(goal);
        goals.sort(Comparator.comparingInt(Goal::priority));
    }

    public void tick() {
        if (active != null) {
            for (Goal goal : goals) {
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
            for (Goal goal : goals) {
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
