package fr.euphyllia.fidorial.server.event;

import com.google.common.base.Preconditions;
import fr.fidorial.event.AsyncEventHandler;
import fr.fidorial.event.AsyncEventTimeout;
import fr.fidorial.event.EventBus;
import fr.fidorial.event.EventHandler;
import fr.fidorial.event.EventPriority;
import fr.fidorial.event.Subscribe;
import fr.fidorial.event.Subscription;
import fr.fidorial.plugin.Plugin;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class SimpleEventBus implements EventBus {
    private static final int PRIORITY_COUNT = EventPriority.values().length;
    private static final ComponentLogger LOGGER = ComponentLogger.logger(SimpleEventBus.class); // todo: use plugin logger
    private static final long MIN_TIMEOUT_MILLIS = 1L;
    private static final ScheduledThreadPoolExecutor ASYNC_TIMEOUT_SCHEDULER = asyncTimeoutScheduler();

    private final Map<Class<?>, DirectSubscribers> directSubscribers = new ConcurrentHashMap<>();
    private final Map<Class<?>, DispatchChain> resolvedChains = new ConcurrentHashMap<>();

    @Override
    public <E> E fire(final E event) {
        final DispatchChain chain = resolve(event.getClass());
        dispatchSync(event, chain.mutableSync);
        dispatchSync(event, chain.monitorSync);
        return event;
    }

    @Override
    public <E> Optional<E> fire(final Class<E> eventClass, final Supplier<E> eventFactory) {
        return hasSubscribers(eventClass)
                ? Optional.of(fire(eventFactory.get()))
                : Optional.empty();
    }

    @Override
    public <E> CompletionStage<E> fireAsync(final E event) {
        final DispatchChain chain = resolve(event.getClass());
        dispatchSync(event, chain.mutableSync);
        final CompletionStage<E> stage = dispatchAsync(event, chain.mutableAsync);
        if (chain.monitorSync.length == 0 && chain.monitorAsync.length == 0) {
            return stage;
        }
        return stage.thenCompose(result -> {
            dispatchSync(result, chain.monitorSync);
            return dispatchAsync(result, chain.monitorAsync);
        });
    }

    @Override
    public <E> CompletionStage<@Nullable E> fireAsync(final Class<E> eventClass, final Supplier<E> eventFactory) {
        return hasSubscribers(eventClass)
                ? fireAsync(eventFactory.get())
                : CompletableFuture.completedFuture(null);
    }

    @Override
    public <E> void fireAndForget(final E event) {
        final DispatchChain chain = resolve(event.getClass());
        dispatchSync(event, chain.mutableSync);
        dispatchAsync(event, chain.mutableAsync).thenCompose(result -> {
            dispatchSync(result, chain.monitorSync);
            return dispatchAsync(result, chain.monitorAsync);
        });
    }

    @Override
    public <E> void fireAndForget(final Class<E> eventClass, final Supplier<E> eventFactory) {
        if (hasSubscribers(eventClass)) fireAndForget(eventFactory.get());
    }

    @Override
    public boolean hasSubscribers(final Class<?> eventClass) {
        return resolve(eventClass).hasSubscribers;
    }

    @Override
    public <E> Subscription subscribe(
            final Class<E> eventClass,
            final EventPriority priority,
            final EventHandler<? super E> handler,
            final Plugin plugin) {
        return addRegistration(eventClass, plugin, priority, false, handler, handler);
    }

    @Override
    public <E> Subscription subscribeAsync(
            final Class<E> eventClass,
            final EventPriority priority,
            final AsyncEventHandler<? super E> handler,
            final Plugin plugin) {
        return addRegistration(eventClass, plugin, priority, true, handler, handler);
    }

    @Override
    public Subscription registerSubscribers(final Object instance, final Plugin plugin) {
        final List<Subscription> subscriptions = new ArrayList<>();
        for (final Method method : instance.getClass().getDeclaredMethods()) {
            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            final Subscribe subscribe = method.getAnnotation(Subscribe.class);
            if (subscribe == null) {
                continue;
            }
            final Subscription subscription = registerAnnotatedMethod(instance, plugin, method, subscribe.priority());
            if (subscription != null) subscriptions.add(subscription);
        }
        if (subscriptions.isEmpty()) {
            noSubscribersRegistered(plugin, instance.getClass());
            return CompositeSubscription.EMPTY;
        }
        return new CompositeSubscription(subscriptions);
    }

    @Override
    public Subscription registerSubscribers(final Class<?> clazz, final Plugin plugin) {
        final List<Subscription> subscriptions = new ArrayList<>();
        for (final Method method : clazz.getDeclaredMethods()) {
            final Subscribe subscribe = method.getAnnotation(Subscribe.class);
            if (subscribe == null) {
                continue;
            }
            if (!Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            final Subscription subscription = registerAnnotatedMethod(clazz, plugin, method, subscribe.priority());
            if (subscription != null) subscriptions.add(subscription);
        }
        if (subscriptions.isEmpty()) {
            noSubscribersRegistered(plugin, clazz);
            return CompositeSubscription.EMPTY;
        }
        return new CompositeSubscription(subscriptions);
    }

    @Override
    public void unsubscribeAll(final Plugin plugin) {
        for (final Class<?> eventClass : directSubscribers.keySet()) {
            directSubscribers.computeIfPresent(eventClass, (ignored, current) -> {
                return current.without(registration -> registration.plugin == plugin);
            });
        }
        resolvedChains.clear();
    }

    private @Nullable Subscription registerAnnotatedMethod(
            final Object instance,
            final Plugin plugin,
            final Method method,
            final EventPriority priority) {
        if (method.getParameterCount() != 1) {
            invalidSubscriber(plugin, method, "must have exactly one parameter");
            return null;
        }
        final Class<?> eventClass = method.getParameterTypes()[0];
        final Boolean async = validateReturnType(plugin, method, eventClass);
        if (async == null) return null;
        final MethodHandle handle = methodHandle(method);
        final Object receiver = Modifier.isStatic(method.getModifiers()) ? null : instance;
        if (async) {
            final AsyncEventHandler<Object> handler = event -> invokeAsync(handle, receiver, event);
            return addRegistration(eventClass, plugin, priority, true, handler, instance);
        }
        final EventHandler<Object> handler = event -> invokeSync(handle, receiver, event);
        return addRegistration(eventClass, plugin, priority, false, handler, instance);
    }

    private static @Nullable Boolean validateReturnType(final Plugin plugin, final Method method, final Class<?> eventClass) {
        final Class<?> returnType = method.getReturnType();
        if (returnType == Void.TYPE) {
            return false;
        }
        if (!CompletionStage.class.isAssignableFrom(returnType)) {
            invalidSubscriber(plugin, method, "must return void or CompletionStage");
            return null;
        }
        final Type genericReturnType = method.getGenericReturnType();
        if (!(genericReturnType instanceof final ParameterizedType parameterizedType)) {
            invalidSubscriber(plugin, method, "async return type must declare the event generic");
            return null;
        }
        final Type actualType = parameterizedType.getActualTypeArguments()[0];
        if (actualType != eventClass) {
            invalidSubscriber(plugin, method, "async return generic must match the event parameter");
            return null;
        }
        return true;
    }

    private static void invalidSubscriber(final Plugin plugin, final Method method, final String reason) {
        LOGGER.error("Invalid @Subscribe method {}", method, new IllegalStateException(reason));
    }

    private static void noSubscribersRegistered(final Plugin plugin, final Class<?> clazz) {
        LOGGER.warn("No @Subscribe methods registered from {}", clazz.getName(), new Exception());
    }

    private static MethodHandle methodHandle(final Method method) {
        try {
            method.setAccessible(true);
            return MethodHandles.lookup().unreflect(method);
        } catch (final IllegalAccessException e) {
            throw new IllegalArgumentException("Cannot access @Subscribe method " + method, e);
        }
    }

    private static void invokeSync(final MethodHandle handle, @Nullable final Object receiver, final Object event) throws Throwable {
        if (receiver == null) {
            handle.invoke(event);
        } else {
            handle.invoke(receiver, event);
        }
    }

    @SuppressWarnings("unchecked")
    private static CompletionStage<Object> invokeAsync(final MethodHandle handle, @Nullable final Object receiver, final Object event)
            throws Throwable {
        final Object result = receiver == null ? handle.invoke(event) : handle.invoke(receiver, event);
        Preconditions.checkState(result != null, "async @Subscribe method returned null");
        return (CompletionStage<Object>) result;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <E> Subscription addRegistration(
            final Class<E> eventClass,
            final Plugin plugin,
            final EventPriority priority,
            final boolean async,
            final Object handler,
            final Object target
    ) {
        final Registration registration = new Registration(eventClass, priority, async, handler, plugin, target);
        directSubscribers.compute(eventClass, (ignored, current) -> {
            final DirectSubscribers subscribers = current == null ? DirectSubscribers.EMPTY : current;
            return subscribers.with(registration);
        });
        resolvedChains.clear();
        return registration;
    }

    private DispatchChain resolve(final Class<?> eventClass) {
        return resolvedChains.computeIfAbsent(eventClass, this::buildChain);
    }

    private DispatchChain buildChain(final Class<?> eventClass) {
        final List<Class<?>> ancestry = ancestry(eventClass);
        final List<Registration<?>> mutableSync = new ArrayList<>();
        final List<Registration<?>> mutableAsync = new ArrayList<>();
        final List<Registration<?>> monitorSync = new ArrayList<>();
        final List<Registration<?>> monitorAsync = new ArrayList<>();
        final Set<Registration<?>> seen = Collections.newSetFromMap(new IdentityHashMap<>());

        for (final EventPriority priority : EventPriority.values()) {
            for (final Class<?> type : ancestry) {
                final DirectSubscribers direct = directSubscribers.get(type);
                if (direct == null) {
                    continue;
                }
                for (final Registration<?> registration : direct.byPriority[priority.ordinal()]) {
                    if (!seen.add(registration)) {
                        continue;
                    }
                    if (priority == EventPriority.MONITOR) {
                        if (registration.async) {
                            monitorAsync.add(registration);
                        } else {
                            monitorSync.add(registration);
                        }
                    } else if (registration.async) {
                        mutableAsync.add(registration);
                    } else {
                        mutableSync.add(registration);
                    }
                }
            }
        }
        return new DispatchChain(
                mutableSync.toArray(Registration[]::new),
                mutableAsync.toArray(Registration[]::new),
                monitorSync.toArray(Registration[]::new),
                monitorAsync.toArray(Registration[]::new));
    }

    private static List<Class<?>> ancestry(final Class<?> eventClass) {
        final LinkedHashSet<Class<?>> types = new LinkedHashSet<>();
        collectAncestry(eventClass, types);
        return List.copyOf(types);
    }

    private static void collectAncestry(@Nullable final Class<?> type, final LinkedHashSet<Class<?>> types) {
        if (type == null) {
            return;
        }
        collectAncestry(type.getSuperclass(), types);
        for (final Class<?> interfaceType : type.getInterfaces()) {
            collectAncestry(interfaceType, types);
        }
        types.add(type);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void dispatchSync(final Object event, final Registration<?>[] registrations) {
        for (final Registration<?> registration : registrations) {
            try {
                ((EventHandler) registration.handler).handle(event);
            } catch (final Throwable throwable) {
                LOGGER.error("Failed to handle event {}", event.getClass().getName(), throwable);
            }
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes", "ConstantValue"})
    private <E> CompletionStage<E> dispatchAsync(final E event, final Registration<?>[] registrations) {
        CompletionStage<E> stage = CompletableFuture.completedFuture(event);
        for (final Registration registration : registrations) {
            stage = stage.thenCompose(current -> {
                try {
                    final CompletionStage<E> nextStage = ((AsyncEventHandler) registration.handler).handle(current);
                    Preconditions.checkState(nextStage != null, "Async event handler returned null");
                    return guardAsyncHandler(event, current, registration, nextStage);
                } catch (final Throwable throwable) {
                    LOGGER.error("Failed to handle event {} async", event.getClass().getName(), throwable);
                    return CompletableFuture.completedFuture(current);
                }
            });
        }
        return stage;
    }

    private <E> CompletionStage<E> guardAsyncHandler(
            final E event,
            final E current,
            final Registration<?> registration,
            final CompletionStage<E> nextStage) {
        final AsyncHandlerTimeout timeout = asyncHandlerTimeout(event);

        final CompletableFuture<E> guarded = new CompletableFuture<>();
        final AtomicBoolean completed = new AtomicBoolean();
        final ScheduledFuture<?> warningTask = !timeout.warning().isZero()
                ? ASYNC_TIMEOUT_SCHEDULER.schedule(() -> {
            if (!completed.get()) LOGGER.warn(
                    "Async event handler {} is still handling {} after {} ms",
                    registration,
                    event.getClass().getName(),
                    timeout.warning().toMillis()
            );
        }, timeout.warning().toMillis(), TimeUnit.MILLISECONDS) : null;

        final ScheduledFuture<?> timeoutTask = ASYNC_TIMEOUT_SCHEDULER.schedule(() -> {
            if (!completed.compareAndSet(false, true)) return;
            LOGGER.error(
                    "Async event handler {} timed out handling {} after {} ms; skipping",
                    registration,
                    event.getClass().getName(),
                    timeout.timeout().toMillis()
            );
            guarded.complete(current);
            if (warningTask != null) warningTask.cancel(false);
        }, timeout.timeout().toMillis(), TimeUnit.MILLISECONDS);

        nextStage.whenComplete((next, throwable) -> {
            if (!completed.compareAndSet(false, true)) return;
            guarded.complete(handleAsyncResult(event, current, next, throwable));
            if (warningTask != null) warningTask.cancel(false);
            timeoutTask.cancel(false);
        });
        return guarded;
    }

    private static <E> E handleAsyncResult(
            final E event,
            final E current,
            @Nullable final E next,
            @Nullable final Throwable throwable) {
        if (throwable != null) {
            LOGGER.error("Failed to handle event {} async", event.getClass().getName(), throwable);
            return current;
        }
        if (next == null) {
            final Exception exception = new IllegalStateException("Async event handler completed with null");
            LOGGER.error("Failed to handle event {} async", event.getClass().getName(), exception);
            return current;
        }
        return next;
    }

    private static AsyncHandlerTimeout asyncHandlerTimeout(final Object event) {
        AsyncEventTimeout configured = event instanceof final AsyncEventTimeout t ? t : () -> Duration.ofSeconds(1);
        final Duration timeout = requirePositiveDuration(configured.asyncHandlerTimeout(), "async handler timeout");
        final Duration warning = requireNonNegativeDuration(
                configured.asyncHandlerWarningTimeout(),
                "async handler warning timeout"
        );
        if (warning.compareTo(timeout) >= 0) {
            return new AsyncHandlerTimeout(Duration.ZERO, timeout);
        }
        return new AsyncHandlerTimeout(warning, timeout);
    }

    private static Duration requirePositiveDuration(final Duration duration, final String name) {
        Preconditions.checkArgument(!duration.isNegative() && !duration.isZero(), "%s must be positive", name);
        return atLeastOneMillisecond(duration);
    }

    private static Duration requireNonNegativeDuration(final Duration duration, final String name) {
        Preconditions.checkArgument(!duration.isNegative(), "%s must not be negative", name);
        return duration.isZero() ? Duration.ZERO : atLeastOneMillisecond(duration);
    }

    private static Duration atLeastOneMillisecond(final Duration duration) {
        return duration.compareTo(Duration.ofMillis(MIN_TIMEOUT_MILLIS)) < 0
                ? Duration.ofMillis(MIN_TIMEOUT_MILLIS)
                : duration;
    }

    private static ScheduledThreadPoolExecutor asyncTimeoutScheduler() {
        final ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1, task -> {
            final Thread thread = new Thread(task, "fidorial-event-timeouts");
            thread.setDaemon(true);
            return thread;
        });
        scheduler.setRemoveOnCancelPolicy(true);
        return scheduler;
    }

    private static final class DispatchChain {

        private final Registration<?>[] mutableSync;
        private final Registration<?>[] mutableAsync;
        private final Registration<?>[] monitorSync;
        private final Registration<?>[] monitorAsync;
        private final boolean hasSubscribers;

        private DispatchChain(
                final Registration<?>[] mutableSync,
                final Registration<?>[] mutableAsync,
                final Registration<?>[] monitorSync,
                final Registration<?>[] monitorAsync) {
            this.mutableSync = mutableSync;
            this.mutableAsync = mutableAsync;
            this.monitorSync = monitorSync;
            this.monitorAsync = monitorAsync;
            this.hasSubscribers = mutableSync.length != 0
                    || mutableAsync.length != 0
                    || monitorSync.length != 0
                    || monitorAsync.length != 0;
        }
    }

    private record AsyncHandlerTimeout(Duration warning, Duration timeout) {
    }

    private record DirectSubscribers(Registration<?>[][] byPriority) {
        private static final DirectSubscribers EMPTY = new DirectSubscribers(emptyPriorities());

        private DirectSubscribers with(final Registration<?> registration) {
            final Registration<?>[][] copy = copyPriorities(byPriority);
            final int index = registration.priority.ordinal();
            final Registration<?>[] current = copy[index];
            final Registration<?>[] next = Arrays.copyOf(current, current.length + 1);
            next[current.length] = registration;
            copy[index] = next;
            return new DirectSubscribers(copy);
        }

        private DirectSubscribers without(final Registration<?> registration) {
            final Registration<?>[][] copy = copyPriorities(byPriority);
            final int index = registration.priority.ordinal();
            final Registration<?>[] current = copy[index];
            for (int i = 0; i < current.length; i++) {
                if (current[i] == registration) {
                    final Registration<?>[] next = new Registration<?>[current.length - 1];
                    System.arraycopy(current, 0, next, 0, i);
                    System.arraycopy(current, i + 1, next, i, current.length - i - 1);
                    copy[index] = next;
                    return new DirectSubscribers(copy);
                }
            }
            return this;
        }

        private DirectSubscribers without(final Predicate<Registration<?>> predicate) {
            final Registration<?>[][] copy = copyPriorities(byPriority);
            boolean changed = false;
            for (int priority = 0; priority < copy.length; priority++) {
                final Registration<?>[] current = copy[priority];
                int retained = 0;
                for (final Registration<?> registration : current) {
                    if (predicate.test(registration)) {
                        registration.deactivate();
                        changed = true;
                    } else {
                        retained++;
                    }
                }
                if (retained != current.length) {
                    final Registration<?>[] next = new Registration<?>[retained];
                    int index = 0;
                    for (final Registration<?> registration : current) {
                        if (!predicate.test(registration)) {
                            next[index++] = registration;
                        }
                    }
                    copy[priority] = next;
                }
            }
            return changed ? new DirectSubscribers(copy) : this;
        }

        private static Registration<?>[][] emptyPriorities() {
            final Registration<?>[][] priorities = new Registration<?>[PRIORITY_COUNT][];
            Arrays.fill(priorities, new Registration<?>[0]);
            return priorities;
        }

        private static Registration<?>[][] copyPriorities(final Registration<?>[][] source) {
            final Registration<?>[][] copy = new Registration<?>[source.length][];
            System.arraycopy(source, 0, copy, 0, source.length);
            return copy;
        }
    }

    private record CompositeSubscription(List<Subscription> subscriptions) implements Subscription {
        private static final CompositeSubscription EMPTY = new CompositeSubscription(List.of());

        @Override
        public boolean isActive() {
            for (final Subscription subscription : subscriptions) {
                if (subscription.isActive()) return true;
            }
            return false;
        }

        @Override
        public void unsubscribe() {
            subscriptions.forEach(Subscription::unsubscribe);
        }
    }

    private final class Registration<E> implements Subscription {
        private final Class<E> eventClass;
        private final EventPriority priority;
        private final boolean async;
        private final Object handler;
        private final Plugin plugin;
        private final AtomicBoolean active = new AtomicBoolean(true);

        private Registration(
                final Class<E> eventClass,
                final EventPriority priority,
                final boolean async,
                final Object handler,
                final Plugin plugin,
                final Object target) {
            this.eventClass = eventClass;
            this.priority = priority;
            this.async = async;
            this.handler = handler;
            this.plugin = plugin;
        }

        @Override
        public boolean isActive() {
            return active.get();
        }

        @Override
        public void unsubscribe() {
            if (deactivate()) {
                directSubscribers.computeIfPresent(eventClass, (ignored, current) -> current.without(this));
                resolvedChains.clear();
            }
        }

        private boolean deactivate() {
            return active.compareAndSet(true, false);
        }

        @Override
        public String toString() {
            return "Registration{"
                    + "eventClass=" + eventClass.getName()
                    + ", priority=" + priority
                    + ", async=" + async
                    + ", plugin=" + plugin
                    + '}';
        }
    }
}
