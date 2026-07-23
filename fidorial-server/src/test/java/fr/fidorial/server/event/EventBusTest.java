package fr.fidorial.server.event;

import fr.euphyllia.fidorial.server.event.SimpleEventBus;
import fr.fidorial.event.EventBus;
import fr.fidorial.event.EventPriority;
import fr.fidorial.event.Subscribe;
import fr.fidorial.event.Subscription;
import fr.fidorial.plugin.Plugin;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class EventBusTest {
    private static final Plugin OWNER = new TestPluginOwner();

    @Test
    void skipsDeferredSupplierWithoutSubscribers() {
        final EventBus bus = new SimpleEventBus();
        final AtomicInteger calls = new AtomicInteger();

        final Optional<MutableEvent> result = bus.fire(MutableEvent.class, () -> {
            calls.incrementAndGet();
            return new MutableEvent();
        });

        assertTrue(result.isEmpty());
        assertEquals(0, calls.get());
        assertFalse(bus.hasSubscribers(MutableEvent.class));
    }

    @Test
    void callsDeferredSupplierForParentSubscriber() {
        final EventBus bus = new SimpleEventBus();
        final AtomicInteger calls = new AtomicInteger();
        bus.subscribe(ParentEvent.class, event -> event.value++, OWNER);

        final Optional<ChildEvent> result = bus.fire(ChildEvent.class, () -> {
            calls.incrementAndGet();
            return new ChildEvent();
        });

        assertNotNull(result);
        assertEquals(1, calls.get());
        assertEquals(1, result.orElseThrow().value);
        assertTrue(bus.hasSubscribers(ChildEvent.class));
    }

    @Test
    void ordersParentSubscribersBeforeChildSubscribers() {
        final EventBus bus = new SimpleEventBus();
        final List<String> calls = new ArrayList<>();
        bus.subscribe(ChildEvent.class, EventPriority.NORMAL, event -> calls.add("child"), OWNER);
        bus.subscribe(MarkerEvent.class, EventPriority.NORMAL, event -> calls.add("marker"), OWNER);
        bus.subscribe(ParentEvent.class, EventPriority.NORMAL, event -> calls.add("parent"), OWNER);
        bus.subscribe(RootEvent.class, EventPriority.NORMAL, event -> calls.add("root"), OWNER);

        bus.fire(new ChildEvent());

        assertEquals(List.of("root", "marker", "parent", "child"), calls);
    }

    @Test
    void deduplicatesSubscribersAcrossParentPaths() {
        final EventBus bus = new SimpleEventBus();
        final AtomicInteger calls = new AtomicInteger();
        bus.subscribe(MarkerEvent.class, event -> calls.incrementAndGet(), OWNER);

        bus.fire(new MultiPathEvent());

        assertEquals(1, calls.get());
    }

    @Test
    void fireRunsSyncSubscribersOnly() {
        final EventBus bus = new SimpleEventBus();
        final MutableEvent event = new MutableEvent();
        bus.subscribe(MutableEvent.class, current -> current.value++, OWNER);
        bus.subscribeAsync(MutableEvent.class, current -> {
            current.value += 100;
            return CompletableFuture.completedFuture(current);
        }, OWNER);

        final MutableEvent result = bus.fire(event);

        assertSame(event, result);
        assertEquals(1, event.value);
    }

    @Test
    void fireAsyncWaitsBeforeMonitorPhase() {
        final EventBus bus = new SimpleEventBus();
        final List<String> calls = new CopyOnWriteArrayList<>();
        final CompletableFuture<MutableEvent> asyncGate = new CompletableFuture<>();
        final MutableEvent event = new MutableEvent();
        bus.subscribe(MutableEvent.class, current -> {
            current.value++;
            calls.add("sync");
        }, OWNER);
        bus.subscribe(MutableEvent.class, EventPriority.MONITOR, current -> {
            calls.add("monitor-" + current.value);
            current.value++;
        }, OWNER);
        bus.subscribeAsync(MutableEvent.class, current -> {
            calls.add("async-start");
            return asyncGate.thenApply(ignored -> {
                current.value++;
                calls.add("async-done");
                return current;
            });
        }, OWNER);

        final CompletionStage<MutableEvent> stage = bus.fireAsync(event);
        assertEquals(List.of("sync", "async-start"), calls);

        asyncGate.complete(event);
        final MutableEvent result = stage.toCompletableFuture().join();

        assertSame(event, result);
        assertEquals(List.of("sync", "async-start", "async-done", "monitor-2"), calls);
        assertEquals(3, event.value);
    }

    @Test
    void fireAndForgetRunsMonitorAfterAsync() throws Exception {
        final EventBus bus = new SimpleEventBus();
        final CountDownLatch monitorRan = new CountDownLatch(1);
        final CompletableFuture<MutableEvent> asyncGate = new CompletableFuture<>();
        final MutableEvent event = new MutableEvent();
        bus.subscribeAsync(MutableEvent.class, current -> asyncGate.thenApply(ignored -> {
            current.value = 7;
            return current;
        }), OWNER);
        bus.subscribe(MutableEvent.class, EventPriority.MONITOR, current -> {
            assertEquals(7, current.value);
            monitorRan.countDown();
        }, OWNER);

        bus.fireAndForget(event);
        assertEquals(0, event.value);
        asyncGate.complete(event);

        assertTrue(monitorRan.await(1, TimeUnit.SECONDS));
    }

    @Test
    void registersAnnotatedMethods() {
        final EventBus bus = new SimpleEventBus();
        final AnnotatedSubscribers instance = new AnnotatedSubscribers();

        final Subscription instanceSubscriptions = bus.registerSubscribers(instance, OWNER);
        final Subscription staticSubscriptions = bus.registerSubscribers(AnnotatedStaticSubscribers.class, OWNER);
        final MutableEvent event = bus.fireAsync(new MutableEvent()).toCompletableFuture().join();

        assertTrue(instanceSubscriptions.isActive());
        assertTrue(staticSubscriptions.isActive());
        assertEquals(111, event.value);
    }

    @Test
    void compositeUnsubscribeRemovesInstanceRegistrations() {
        final EventBus bus = new SimpleEventBus();
        final AnnotatedSubscribers first = new AnnotatedSubscribers();
        final AnnotatedSubscribers second = new AnnotatedSubscribers();
        final Subscription firstSubscriptions = bus.registerSubscribers(first, OWNER);
        final Subscription secondSubscriptions = bus.registerSubscribers(second, OWNER);

        assertEquals(22, bus.fireAsync(new MutableEvent()).toCompletableFuture().join().value);

        firstSubscriptions.unsubscribe();

        assertEquals(11, bus.fireAsync(new MutableEvent()).toCompletableFuture().join().value);

        secondSubscriptions.unsubscribe();

        assertFalse(bus.hasSubscribers(MutableEvent.class));
        assertFalse(firstSubscriptions.isActive());
        assertFalse(secondSubscriptions.isActive());
    }

    @Test
    void unsubscribeRemovesClassRegistrations() {
        final EventBus bus = new SimpleEventBus();
        final Subscription staticSubscriptions = bus.registerSubscribers(AnnotatedStaticSubscribers.class, OWNER);
        bus.registerSubscribers(OtherAnnotatedStaticSubscribers.class, OWNER);

        assertEquals(300, bus.fire(new MutableEvent()).value);

        staticSubscriptions.unsubscribe();

        assertEquals(200, bus.fire(new MutableEvent()).value);
        assertFalse(staticSubscriptions.isActive());
    }

    @Test
    void ignoresInvalidAnnotatedMethods() {
        final EventBus bus = new SimpleEventBus();

        assertFalse(bus.registerSubscribers(InvalidNoParameters.class, OWNER).isActive());
        assertFalse(bus.registerSubscribers(InvalidReturn.class, OWNER).isActive());
        assertFalse(bus.registerSubscribers(InvalidAsyncGeneric.class, OWNER).isActive());
    }

    @Test
    void invalidAnnotatedMethodsDoNotBlockValidMethods() {
        final EventBus bus = new SimpleEventBus();

        final Subscription subscriptions = bus.registerSubscribers(MixedValidAndInvalidSubscribers.class, OWNER);

        assertTrue(subscriptions.isActive());
        assertEquals(1, bus.fire(new MutableEvent()).value);
    }

    @Test
    void classRegistrationCollectsOnlyStaticMethods() {
        final EventBus bus = new SimpleEventBus();

        final Subscription subscriptions = bus.registerSubscribers(MixedSubscribersForClassRegistration.class, OWNER);

        assertTrue(subscriptions.isActive());
        assertEquals(1, bus.fire(new MutableEvent()).value);
    }

    @Test
    void classRegistrationIgnoresOnlyInstanceMethods() {
        final EventBus bus = new SimpleEventBus();

        final Subscription subscriptions = bus.registerSubscribers(InstanceOnlySubscribersForClassRegistration.class, OWNER);

        assertFalse(subscriptions.isActive());
        assertFalse(bus.hasSubscribers(MutableEvent.class));
    }

    @Test
    void instanceRegistrationIgnoresEmptyInstances() {
        final EventBus bus = new SimpleEventBus();

        final Subscription subscriptions = bus.registerSubscribers(new NoSubscribers(), OWNER);

        assertFalse(subscriptions.isActive());
    }

    @Test
    void dispatchUsesStableSnapshot() {
        final EventBus bus = new SimpleEventBus();
        final List<String> calls = new ArrayList<>();
        final Subscription[] second = new Subscription[1];
        bus.subscribe(MutableEvent.class, event -> {
            calls.add("first");
            second[0].unsubscribe();
        }, OWNER);
        second[0] = bus.subscribe(MutableEvent.class, event -> calls.add("second"), OWNER);

        bus.fire(new MutableEvent());
        bus.fire(new MutableEvent());

        assertEquals(List.of("first", "second", "first"), calls);
    }

    @Test
    void handlesConcurrentSubscriptionChanges() {
        final EventBus bus = new SimpleEventBus();
        final AtomicInteger calls = new AtomicInteger();
        final var executor = Executors.newFixedThreadPool(4);
        try {
            final List<CompletableFuture<Void>> tasks = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                tasks.add(CompletableFuture.runAsync(() -> {
                    for (int j = 0; j < 1_000; j++) {
                        final Subscription subscription = bus.subscribe(MutableEvent.class, event -> calls.incrementAndGet(), OWNER);
                        bus.fire(new MutableEvent());
                        subscription.unsubscribe();
                    }
                }, executor));
            }
            CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new)).join();
        } finally {
            executor.shutdownNow();
        }
    }

    private static class RootEvent {
        int value;
    }

    private static final class TestPluginOwner implements Plugin {
    }

    private interface MarkerEvent {
    }

    private static class ParentEvent extends RootEvent implements MarkerEvent {
    }

    private static class ChildEvent extends ParentEvent {
    }

    private interface SecondMarkerEvent extends MarkerEvent {
    }

    private static final class MultiPathEvent extends ParentEvent implements SecondMarkerEvent {
    }

    private static final class MutableEvent {
        private int value;
    }

    private static final class OtherEvent {
    }

    private static final class AnnotatedSubscribers {
        @Subscribe(priority = EventPriority.LOW)
        private static void staticSubscriber(final MutableEvent event) {
            event.value += 100;
        }

        @Subscribe
        private void instanceSubscriber(final MutableEvent event) {
            event.value += 10;
        }

        @Subscribe
        private CompletionStage<MutableEvent> asyncSubscriber(final MutableEvent event) {
            event.value += 1;
            return CompletableFuture.completedFuture(event);
        }
    }

    private static final class AnnotatedStaticSubscribers {
        @Subscribe(priority = EventPriority.LOW)
        private static void staticSubscriber(final MutableEvent event) {
            event.value += 100;
        }
    }

    private static final class OtherAnnotatedStaticSubscribers {
        @Subscribe(priority = EventPriority.LOW)
        private static void staticSubscriber(final MutableEvent event) {
            event.value += 200;
        }
    }

    private static final class InvalidNoParameters {
        @Subscribe
        private static void invalid() {
        }
    }

    private static final class InvalidReturn {
        @Subscribe
        private static String invalid(final MutableEvent event) {
            return "";
        }
    }

    private static final class InvalidAsyncGeneric {
        @Subscribe
        private static CompletionStage<OtherEvent> invalid(final MutableEvent event) {
            return CompletableFuture.completedFuture(new OtherEvent());
        }
    }

    private static final class MixedValidAndInvalidSubscribers {
        @Subscribe
        private static void invalid() {
        }

        @Subscribe
        private static void valid(final MutableEvent event) {
            event.value++;
        }
    }

    private static final class MixedSubscribersForClassRegistration {
        @Subscribe
        private void invalid(final MutableEvent event) {
        }

        @Subscribe
        private static void valid(final MutableEvent event) {
            event.value++;
        }
    }

    private static final class InstanceOnlySubscribersForClassRegistration {
        @Subscribe
        private void invalid(final MutableEvent event) {
        }
    }

    private static final class NoSubscribers {
    }
}
