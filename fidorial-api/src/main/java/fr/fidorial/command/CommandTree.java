package fr.fidorial.command;

import com.google.common.base.Preconditions;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;

import java.util.List;

public record CommandTree(LiteralCommandNode<CommandSource> node, List<String> aliases) {

    /**
     * Constructs a {@link CommandTree} from the node returned by
     * the given builder.
     *
     * @param builder the command builder
     */
    public CommandTree(final LiteralArgumentBuilder<CommandSource> builder) {
        this(Preconditions.checkNotNull(builder, "builder").build(), List.of(builder.getLiteral()));
    }

    /**
     * Constructs a {@link CommandTree} from the given command node.
     *
     * @param node the command node
     */
    public CommandTree(final LiteralCommandNode<CommandSource> node) {
        this(Preconditions.checkNotNull(node, "node"), List.of(node.getName()));
    }

    /**
     * Returns the literal node for this command.
     *
     * @return the command node
     */
    @Override
    public LiteralCommandNode<CommandSource> node() {
        return node;
    }

    /**
     * Creates a new LiteralArgumentBuilder of the required name.
     *
     * @param name the literal name.
     * @return a new LiteralArgumentBuilder.
     */
    public static LiteralArgumentBuilder<CommandSource> literal(final String name) {
        Preconditions.checkNotNull(name, "name");
        Preconditions.checkArgument(name.indexOf(' ') == -1, "the argument name cannot contain spaces");
        return LiteralArgumentBuilder.literal(name);
    }

    /**
     * Creates a new RequiredArgumentBuilder of the required name and type.
     *
     * @param name         the argument name
     * @param argumentType the argument type required
     * @param <T>          the ArgumentType required type
     * @return a new RequiredArgumentBuilder
     */
    public static <T> RequiredArgumentBuilder<CommandSource, T> argument(
            final String name,
            final ArgumentType<T> argumentType
    ) {
        Preconditions.checkNotNull(name, "name");
        Preconditions.checkNotNull(argumentType, "argument type");

        return RequiredArgumentBuilder.argument(name, argumentType);
    }
}
