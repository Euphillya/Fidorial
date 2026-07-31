package fr.euphyllia.fidorial.server.registry;

import net.kyori.adventure.key.Key;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Function;

/**
 * Registry marker types don't have real implementations.
 * So instead we hand out dummy proxy objects that just carry a key around
 * and pretend to be the right type. Good enough since nothing ever calls methods on them.
 */
final class KeyStubs {

    private KeyStubs() {
        throw new UnsupportedOperationException("KeyStubs cannot be instantiated.");
    }

    static <T> Function<Key, T> resolver(final Class<T> type) {
        return key -> type.cast(Proxy.newProxyInstance(
                type.getClassLoader(),
                new Class<?>[] { type },
                new StubHandler(type, key)
        ));
    }

    private record StubHandler(Class<?> type, Key key) implements InvocationHandler {
        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) {
            return switch (method.getName()) {
                case "key" -> key;
                case "toString" -> type.getSimpleName() + "[" + key + "]";
                case "hashCode" -> key.hashCode();
                case "equals" -> args.length == 1 && proxy == args[0];
                default -> throw new UnsupportedOperationException(
                        "Stub for " + type.getSimpleName() + " does not implement " + method);
            };
        }
    }
}
