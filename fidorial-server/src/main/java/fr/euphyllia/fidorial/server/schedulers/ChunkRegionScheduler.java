package fr.euphyllia.fidorial.server.schedulers;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public final class ChunkRegionScheduler {

    private final LongOpenHashSet locked = new LongOpenHashSet();
    private final Long2ObjectOpenHashMap<List<PendingTask>> waitingOn = new Long2ObjectOpenHashMap<>();
    private final Executor executor;

    public ChunkRegionScheduler(final Executor executor) {
        this.executor = executor;
    }

    public synchronized void submit(final LongSet keys, final Runnable task) {
        final PendingTask pending = new PendingTask(new LongOpenHashSet(keys), task);
        final LongOpenHashSet blockers = registerAndCollectBlockers(pending);
        if (blockers.isEmpty()) {
            lockAndRun(pending);
        } else {
            pending.remainingBlockers = blockers.size();
        }
    }

    private LongOpenHashSet registerAndCollectBlockers(final PendingTask pending) {
        final LongOpenHashSet blockers = new LongOpenHashSet();
        final LongIterator it = pending.keys.iterator();
        while (it.hasNext()) {
            final long k = it.nextLong();
            if (locked.contains(k)) {
                blockers.add(k);
                waitingOn.computeIfAbsent(k, _ -> new ArrayList<>()).add(pending);
            }
        }
        return blockers;
    }

    private void lockAndRun(final PendingTask pending) {
        locked.addAll(pending.keys);
        executor.execute(() -> {
            try {
                pending.task.run();
            } finally {
                release(pending);
            }
        });
    }

    private synchronized void release(final PendingTask finished) {
        locked.removeAll(finished.keys);

        List<PendingTask> ready = null;
        final LongIterator it = finished.keys.iterator();
        while (it.hasNext()) {
            final long k = it.nextLong();
            final List<PendingTask> waiters = waitingOn.remove(k);
            if (waiters == null) continue;
            for (final PendingTask p : waiters) {
                if (--p.remainingBlockers == 0) {
                    if (ready == null) ready = new ArrayList<>();
                    ready.add(p);
                }
            }
        }
        if (ready == null) return;

        for (final PendingTask p : ready) {
            tryStart(p);
        }
    }

    private void tryStart(final PendingTask p) {
        if (!isBlocked(p.keys)) {
            lockAndRun(p);
            return;
        }
        final LongOpenHashSet blockers = registerAndCollectBlockers(p);
        p.remainingBlockers = blockers.size();
    }

    private boolean isBlocked(final LongOpenHashSet keys) {
        final LongIterator it = keys.iterator();
        while (it.hasNext()) {
            if (locked.contains(it.nextLong())) return true;
        }
        return false;
    }

    private static final class PendingTask {
        final LongOpenHashSet keys;
        final Runnable task;
        int remainingBlockers;

        PendingTask(final LongOpenHashSet keys, final Runnable task) {
            this.keys = keys;
            this.task = task;
        }
    }
}
