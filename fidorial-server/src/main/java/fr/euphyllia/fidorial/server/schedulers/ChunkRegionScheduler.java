package fr.euphyllia.fidorial.server.schedulers;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public final class ChunkRegionScheduler {

    private final LongOpenHashSet locked = new LongOpenHashSet();
    private final Long2ObjectOpenHashMap<@Nullable List<PendingTask>> waitingOn = new Long2ObjectOpenHashMap<>();
    private final Executor executor;

    public ChunkRegionScheduler(final Executor executor) {
        this.executor = executor;
    }

    public synchronized void submit(final LongSet keys, final Runnable task) {
        final LongOpenHashSet blockers = overlapping(keys);
        final PendingTask pending = new PendingTask(keys, task, blockers.size());

        if (blockers.isEmpty()) {
            lockAndRun(pending);
            return;
        }

        for (final long k : blockers) {
            waitingOn.computeIfAbsent(k, _ -> new ArrayList<>()).add(pending);
        }
    }

    private void lockAndRun(final PendingTask task) {
        locked.addAll(task.keys);
        executor.execute(() -> {
            try {
                task.task.run();
            } finally {
                release(task.keys);
            }
        });
    }

    private synchronized void release(final LongSet keys) {
        locked.removeAll(keys);
        for (final long k : keys) {
            final List<PendingTask> list = waitingOn.remove(k);
            if (list == null) {
                continue;
            }
            for (final PendingTask p : list) {
                if (--p.remainingBlockers == 0) {
                    lockAndRun(p);
                }
            }
        }
    }

    private LongOpenHashSet overlapping(final LongSet keys) {
        final LongOpenHashSet result = new LongOpenHashSet();
        for (final long k : keys) {
            if (locked.contains(k)) {
                result.add(k);
            }
        }
        return result;
    }

    private static final class PendingTask {
        final LongSet keys;
        final Runnable task;
        int remainingBlockers;

        PendingTask(final LongSet keys, final Runnable task, final int remainingBlockers) {
            this.keys = keys;
            this.task = task;
            this.remainingBlockers = remainingBlockers;
        }
    }
}
