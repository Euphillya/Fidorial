package fr.euphyllia.fidorial.server.event;

import com.google.common.base.Preconditions;
import fr.fidorial.event.AsyncEventHandler;
import fr.fidorial.event.EventBus;
import fr.fidorial.event.EventHandler;
import fr.fidorial.event.EventPriority;
import fr.fidorial.event.Subscribe;
import fr.fidorial.event.Subscription;
import fr.fidorial.plugin.Plugin;
import org.jspecify.annotations.Nullable;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class SimpleEventBus implements EventBus {
    private static final int PRIORITY_COUNT = EventPriority.values().length;

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
    public List<Subscription> registerSubscribers(final Object instance, final Plugin plugin) {
        final List<Subscription> subscriptions = new ArrayList<>();
        for (final Method method : instance.getClass().getDeclaredMethods()) {
            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            final Subscribe subscribe = method.getAnnotation(Subscribe.class);
            if (subscribe == null) {
                continue;
            }
            subscriptions.add(registerAnnotatedMethod(instance, plugin, false, method, subscribe.priority()));
        }
        return List.copyOf(subscriptions);
    }

    @Override
    public List<Subscription> registerSubscribers(final Class<?> clazz, final Plugin plugin) {
        final List<Subscription> subscriptions = new ArrayList<>();
        for (final Method method : clazz.getDeclaredMethods()) {
            final Subscribe subscribe = method.getAnnotation(Subscribe.class);
            if (subscribe == null) {
                continue;
            }
            subscriptions.add(registerAnnotatedMethod(clazz, plugin, true, method, subscribe.priority()));
        }
        return List.copyOf(subscriptions);
    }

    @Override
    public void unsubscribeAll(final Plugin plugin) {
        removeAll(registration -> registration.owner == plugin);
    }

    @Override
    public void unsubscribeAll(final Class<?> clazz) {
        removeAll(registration -> registration.target == clazz);
    }

    @Override
    public void unsubscribeAll(final Object instance) {
        removeAll(registration -> registration.target == instance);
    }

    private void removeAll(final Predicate<Registration<?>> predicate) {
        boolean changed = false;
        for (final Map.Entry<Class<?>, DirectSubscribers> entry : directSubscribers.entrySet()) {
            DirectSubscribers current;
            DirectSubscribers updated;
            do {
                current = entry.getValue();
                updated = current.without(predicate);
                if (updated == current) {
                    break;
                }
            } while (!directSubscribers.replace(entry.getKey(), current, updated));
            if (updated != current) {
                changed = true;
            }
        }
        if (changed) {
            resolvedChains.clear();
        }
    }

    private Subscription registerAnnotatedMethod(
            final Object receiverOwner,
            final Plugin registrationOwner,
            final boolean staticOnly,
            final Method method,
            final EventPriority priority) {
        if (method.getParameterCount() != 1) {
            throw invalidSubscriber(method, "must have exactly one parameter");
        }
        if (!Modifier.isStatic(method.getModifiers()) && staticOnly) {
            throw invalidSubscriber(method, "instance method cannot be registered from Class<?>");
        }
        final Class<?> eventClass = method.getParameterTypes()[0];
        final boolean async = validateReturnType(method, eventClass);
        final MethodHandle handle = methodHandle(method);
        final Object receiver = Modifier.isStatic(method.getModifiers()) ? null : receiverOwner;
        if (async) {
            final AsyncEventHandler<Object> handler = event -> invokeAsync(handle, receiver, event);
            return addRegistration(eventClass, registrationOwner, priority, true, handler, receiverOwner);
        }
        final EventHandler<Object> handler = event -> invokeSync(handle, receiver, event);
        return addRegistration(eventClass, registrationOwner, priority, false, handler, receiverOwner);
    }

    private static boolean validateReturnType(final Method method, final Class<?> eventClass) {
        final Class<?> returnType = method.getReturnType();
        if (returnType == Void.TYPE) {
            return false;
        }
        if (!CompletionStage.class.isAssignableFrom(returnType)) {
            throw invalidSubscriber(method, "must return void or CompletionStage");
        }
        final Type genericReturnType = method.getGenericReturnType();
        if (!(genericReturnType instanceof final ParameterizedType parameterizedType)) {
            throw invalidSubscriber(method, "async return type must declare the event generic");
        }
        final Type actualType = parameterizedType.getActualTypeArguments()[0];
        if (actualType != eventClass) {
            throw invalidSubscriber(method, "async return generic must match the event parameter");
        }
        return true;
    }

    private static IllegalArgumentException invalidSubscriber(final Method method, final String reason) {
        return new IllegalArgumentException("Invalid @Subscribe method " + method + ": " + reason);
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
        final Registration registration = new Registration(this, eventClass, priority, async, handler, plugin, target);
        directSubscribers.compute(eventClass, (ignored, current) -> {
            final DirectSubscribers subscribers = current == null ? DirectSubscribers.EMPTY : current;
            return subscribers.with(registration);
        });
        resolvedChains.clear();
        return registration;
    }

    private void remove(final Registration<?> registration) {
        directSubscribers.computeIfPresent(registration.eventClass, (ignored, current) -> current.without(registration));
        resolvedChains.clear();
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
                throwable.printStackTrace(System.err); // todo: handle exceptions properly
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
                    Preconditions.checkState(nextStage != null, "async event handler returned null");
                    return nextStage.handle((next, throwable) -> {
                        if (throwable != null) {
                            throwable.printStackTrace(System.err); // todo: handle exceptions properly
                            return current;
                        }
                        return next;
                    });
                } catch (final Throwable throwable) {
                    throwable.printStackTrace(System.err); // todo: handle exceptions properly
                    return CompletableFuture.completedFuture(current);
                }
            });
        }
        return stage;
    }

    private static void defaultExceptionHandler(
            final Throwable throwable, final Class<?> eventClass, @Nullable final Subscription subscription) {
        final Thread currentThread = Thread.currentThread();
        currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, throwable);
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

    private static final class Registration<E> implements Subscription {

        private final SimpleEventBus bus;
        private final Class<E> eventClass;
        private final EventPriority priority;
        private final boolean async;
        private final Object handler;
        private final Plugin owner;
        private final Object target;
        private final AtomicBoolean active = new AtomicBoolean(true);

        private Registration(
                final SimpleEventBus bus,
                final Class<E> eventClass,
                final EventPriority priority,
                final boolean async,
                final Object handler,
                final Plugin owner,
                final Object target) {
            this.bus = bus;
            this.eventClass = eventClass;
            this.priority = priority;
            this.async = async;
            this.handler = handler;
            this.owner = owner;
            this.target = target;
        }

        @Override
        public boolean isActive() {
            return active.get();
        }

        @Override
        public void unsubscribe() {
            if (deactivate()) {
                bus.remove(this);
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
                    + ", owner=" + owner
                    + ", target=" + target
                    + '}';
        }
    }
}
