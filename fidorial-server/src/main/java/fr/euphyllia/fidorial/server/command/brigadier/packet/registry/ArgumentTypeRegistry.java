package fr.euphyllia.fidorial.server.command.brigadier.packet.registry;

import com.mojang.brigadier.arguments.ArgumentType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class ArgumentTypeRegistry {

    private static final Map<Class<?>, ArgumentTypeRegistrar<?, ?>> REGISTRARS = new HashMap<>();

    private ArgumentTypeRegistry() {
    }

    public static <A extends ArgumentType<?>, S extends ArgumentTypeRegistrar.Spec<A>> void register(
            final ArgumentTypeRegistrar<A, S> registrar
    ) {
        final Class<?> type = resolveArgumentTypeClass(registrar);
        REGISTRARS.put(type, registrar);
    }

    private static Class<?> resolveArgumentTypeClass(final ArgumentTypeRegistrar<?, ?> registrar) {

        for (final Type genericInterface : registrar.getClass().getGenericInterfaces()) {

            if (genericInterface instanceof final ParameterizedType parameterized
                    && parameterized.getRawType() == ArgumentTypeRegistrar.class) {

                return rawClassOf(parameterized.getActualTypeArguments()[0], registrar);
            }
        }

        throw new IllegalStateException(
                "Could not resolve argument type for registrar: " + registrar.getClass().getName()
                        + " — it must directly implement ArgumentTypeRegistrar<T, ...>");
    }

    private static Class<?> rawClassOf(final Type type, final ArgumentTypeRegistrar<?, ?> registrar) {

        if (type instanceof final Class<?> clazz) {
            return clazz;
        }

        if (type instanceof final ParameterizedType parameterized && parameterized.getRawType() instanceof final Class<?> clazz) {
            return clazz;
        }

        throw new IllegalStateException(
                "Cannot resolve a concrete argument type for registrar: " + registrar.getClass().getName()
                        + " — its ArgumentTypeRegistrar type parameter is not concrete (found: " + type + "). "
                        + "Generic registrars (e.g. Info<T>) must register with an explicit class.");
    }

    public static boolean hasRegistrar(final ArgumentType<?> argument) {
        return REGISTRARS.containsKey(argument.getClass());
    }

    @SuppressWarnings("unchecked")
    public static <A extends ArgumentType<?>> ArgumentTypeRegistrar<A, ?> registrar(final A argument) {
        final ArgumentTypeRegistrar<?, ?> registrar = REGISTRARS.get(argument.getClass());

        if (registrar == null) {
            throw new IllegalArgumentException(
                    "Unknown argument type: " + argument.getClass().getName());
        }

        return (ArgumentTypeRegistrar<A, ?>) registrar;
    }
}
