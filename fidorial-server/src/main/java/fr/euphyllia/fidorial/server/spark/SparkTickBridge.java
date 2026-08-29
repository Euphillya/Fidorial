/*
 * This file is part of spark.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.euphyllia.fidorial.server.spark;

import fr.euphyllia.fidorial.server.schedulers.RegionTickProfiler;
import me.lucko.spark.common.tick.AbstractTickHook;
import me.lucko.spark.common.tick.AbstractTickReporter;
import me.lucko.spark.common.tick.TickHook;
import me.lucko.spark.common.tick.TickReporter;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Gives spark the single global tick it expects on top of Fidorial's regionized scheduler.
 */
public final class SparkTickBridge implements RegionTickProfiler {

    private static final long TICK_INTERVAL_NS = TimeUnit.MILLISECONDS.toNanos(50L);
    private static final double NANOS_PER_MILLI = 1_000_000.0D;

    private static final ComponentLogger LOGGER = ComponentLogger.logger("spark");

    private final Hook hook = new Hook();
    private final Reporter reporter = new Reporter();

    private final Queue<Runnable> syncTasks = new ConcurrentLinkedQueue<>();

    /**
     * {@link System#nanoTime()} value at which the window currently open closes.
     */
    private final AtomicLong windowEnd = new AtomicLong(System.nanoTime() + TICK_INTERVAL_NS);

    /**
     * Longest region tick observed inside the window currently open, in nanoseconds.
     */
    private final AtomicLong worstTickNanos = new AtomicLong();

    @Override
    public void heartbeat() {
        final long now = System.nanoTime();
        final long deadline = this.windowEnd.get();
        if (now - deadline < 0L) {
            return;
        }

        // Line the next window up with the grid, unless we have drifted so far behind that the
        // grid is meaningless - after a long GC pause, say - in which case start a fresh one.
        long next = deadline + TICK_INTERVAL_NS;
        if (now - next >= 0L) {
            next = now + TICK_INTERVAL_NS;
        }

        if (!this.windowEnd.compareAndSet(deadline, next)) {
            // Another region closed this window first; it owns the callbacks below.
            return;
        }

        closeWindow();
    }

    /**
     * Publishes the window that just ended and opens the next one.
     *
     * <p>Only ever reached by the single caller that won the {@link #windowEnd} race, so the
     * non-atomic tick counter inside {@link AbstractTickHook} stays consistent.</p>
     */
    private void closeWindow() {
        this.reporter.tick(this.worstTickNanos.getAndSet(0L) / NANOS_PER_MILLI);
        this.hook.tick();
        drainSyncTasks();
    }

    @Override
    public void reportRegionTick(final double durationMillis) {
        this.worstTickNanos.accumulateAndGet((long) (durationMillis * NANOS_PER_MILLI), Math::max);
    }

    /**
     * Queues a task to run on a region worker at the next tick boundary.
     *
     * <p>Backs {@code SparkPlugin#executeSync(Runnable)}, which spark uses for work that has to
     * observe a consistent game state — {@code AsyncWorldInfoProvider} above all.</p>
     *
     * @param task the task to run
     */
    public void executeSync(final Runnable task) {
        this.syncTasks.add(task);
    }

    private void drainSyncTasks() {
        Runnable task;
        while ((task = this.syncTasks.poll()) != null) {
            try {
                task.run();
            } catch (final Throwable t) {
                LOGGER.error("An exception was thrown while executing a synchronous spark task", t);
            }
        }
    }

    public TickHook tickHook() {
        return this.hook;
    }

    public TickReporter tickReporter() {
        return this.reporter;
    }

    /**
     * Drops any queued task. Called when the profiler shuts down so nothing is left pointing at a
     * platform that no longer exists.
     */
    public void clear() {
        this.syncTasks.clear();
    }

    private static final class Hook extends AbstractTickHook {

        private volatile boolean open;

        @Override
        public void start() {
            this.open = true;
        }

        @Override
        public void close() {
            this.open = false;
        }

        void tick() {
            if (this.open) {
                onTick();
            }
        }
    }

    private static final class Reporter extends AbstractTickReporter {

        private volatile boolean open;

        @Override
        public void start() {
            this.open = true;
        }

        @Override
        public void close() {
            this.open = false;
        }

        void tick(final double durationMillis) {
            if (this.open) {
                onTick(durationMillis);
            }
        }
    }
}
