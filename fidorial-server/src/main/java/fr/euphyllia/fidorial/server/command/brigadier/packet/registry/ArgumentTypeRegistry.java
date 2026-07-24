package fr.euphyllia.fidorial.server.command.brigadier.packet.registry;

import com.mojang.brigadier.arguments.ArgumentType;
import fr.euphyllia.fidorial.server.command.brigadier.argument.WrappedArgumentType;

import java.util.HashMap;
import java.util.Map;

public final class ArgumentTypeRegistry {

    private static final Map<Class<?>, ArgumentTypeRegistrar<?, ?>> REGISTRARS = new HashMap<>();

    private ArgumentTypeRegistry() {
    }

    public static <A extends ArgumentType<?>, S extends ArgumentTypeRegistrar.Spec<A>> void register(
            Class<? extends A> type,
            ArgumentTypeRegistrar<A, S> registrar
    ) {
        REGISTRARS.put(type, registrar);
    }

    @SuppressWarnings("unchecked")
    public static <A extends ArgumentType<?>> ArgumentTypeRegistrar<A, ?> registrar(A argument) {
        argument = unwrap(argument);

        ArgumentTypeRegistrar<?, ?> registrar = REGISTRARS.get(argument.getClass());

        if (registrar == null) {
            throw new IllegalArgumentException(
                    "Unknown argument type: " + argument.getClass().getName());
        }

        return (ArgumentTypeRegistrar<A, ?>) registrar;
    }

    @SuppressWarnings("unchecked")
    public static <A extends ArgumentType<?>> A unwrap(A argument) {
        while (argument instanceof WrappedArgumentType<?> wrapper) {
            argument = (A) wrapper.internalArgumentType();
        }
        return argument;
    }
}
