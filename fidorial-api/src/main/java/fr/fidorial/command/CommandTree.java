package fr.fidorial.command;

import com.google.common.base.Preconditions;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

public final class CommandTree {

    private CommandTree() {
    }

    /**
     * Creates a new {@link LiteralArgumentBuilder} of the required name.
     *
     * @param name the literal name
     * @return a new literal argument builder
     */
    public static LiteralArgumentBuilder<CommandSource> literal(final String name) {
        Preconditions.checkNotNull(name, "name");
        Preconditions.checkArgument(name.indexOf(' ') == -1, "the argument name cannot contain spaces");

        return LiteralArgumentBuilder.literal(name);
    }

    /**
     * Creates a new {@link RequiredArgumentBuilder} of the required name and type.
     *
     * @param name the argument name
     * @param argumentType the argument type required
     * @param <T> the argument type
     * @return a new required argument builder
     */
    public static <T> RequiredArgumentBuilder<CommandSource, T> argument(
            final String name,
            final ArgumentType<T> argumentType
    ) {
        Preconditions.checkNotNull(name, "name");
        Preconditions.checkNotNull(argumentType, "argumentType");

        return RequiredArgumentBuilder.argument(name, argumentType);
    }
}
