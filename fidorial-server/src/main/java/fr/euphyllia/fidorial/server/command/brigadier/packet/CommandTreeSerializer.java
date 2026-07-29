package fr.euphyllia.fidorial.server.command.brigadier.packet;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistry;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.NetworkArgumentIds;
import fr.euphyllia.fidorial.server.command.brigadier.packet.util.PermissionlessCommandSource;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.registry.data.ArgumentTypeIds;
import fr.fidorial.command.CommandSource;
import net.kyori.adventure.key.Key;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public final class CommandTreeSerializer {

    private static final byte TYPE_ROOT = 0;
    private static final byte TYPE_LITERAL = 1;
    private static final byte TYPE_ARGUMENT = 2;

    private static final byte FLAG_EXECUTABLE = 4;
    private static final byte FLAG_REDIRECT = 8;
    private static final byte FLAG_CUSTOM_SUGGESTIONS = 16;
    private static final byte FLAG_RESTRICTED = 32;

    private static final CommandSource NO_PERMISSION_SOURCE = PermissionlessCommandSource.instance();

    private CommandTreeSerializer() {
    }

    public static void write(final PacketBuffer buf, final RootCommandNode<CommandSource> root) {
        final List<CommandNode<CommandSource>> nodes = enumerate(root);

        final Map<CommandNode<CommandSource>, Integer> ids = new IdentityHashMap<>();

        for (int i = 0; i < nodes.size(); i++) {
            ids.put(nodes.get(i), i);
        }

        buf.writeVarInt(nodes.size());

        for (final CommandNode<CommandSource> node : nodes) {
            writeNode(buf, node, ids);
        }

        buf.writeVarInt(ids.get(root));
    }

    private static List<CommandNode<CommandSource>> enumerate(final RootCommandNode<CommandSource> root) {
        final List<CommandNode<CommandSource>> nodes = new ArrayList<>();

        final Map<CommandNode<CommandSource>, Integer> ids = new IdentityHashMap<>();

        final Queue<CommandNode<CommandSource>> queue = new ArrayDeque<>();

        queue.add(root);

        while (!queue.isEmpty()) {
            final CommandNode<CommandSource> node = queue.poll();

            if (ids.containsKey(node)) {
                continue;
            }

            ids.put(node, nodes.size());
            nodes.add(node);

            queue.addAll(node.getChildren());

            if (node.getRedirect() != null) {
                queue.add(node.getRedirect());
            }
        }

        return nodes;
    }

    private static void writeNode(
            final PacketBuffer buf,
            final CommandNode<CommandSource> node,
            final Map<CommandNode<CommandSource>, Integer> ids
    ) {

        int flags = 0;

        if (node instanceof LiteralCommandNode<?>) {
            flags |= TYPE_LITERAL;
        } else if (node instanceof ArgumentCommandNode<?, ?>) {
            flags |= TYPE_ARGUMENT;
        }

        if (node.getCommand() != null) {
            flags |= FLAG_EXECUTABLE;
        }

        if (node.getRedirect() != null) {
            flags |= FLAG_REDIRECT;
        }

        if (isRestricted(node)) {
            flags |= FLAG_RESTRICTED;
        }

        if (node instanceof final ArgumentCommandNode<?, ?> argument && argument.getCustomSuggestions() != null) {
            flags |= FLAG_CUSTOM_SUGGESTIONS;
        }

        buf.writeByte(flags);

        final int[] children = node.getChildren().stream()
                .filter(ids::containsKey)
                .mapToInt(ids::get)
                .toArray();

        buf.writeVarIntArray(children);

        if ((flags & FLAG_REDIRECT) != 0) {
            buf.writeVarInt(ids.get(node.getRedirect()));
        }

        switch (node) {
            case final LiteralCommandNode<?> literal -> buf.writeString(literal.getLiteral());
            case final ArgumentCommandNode<?, ?> argument -> {
                buf.writeString(argument.getName());

                writeArgumentType(buf, argument.getType());

                if (argument.getCustomSuggestions() != null) {
                    buf.writeKey(Key.key("minecraft", "ask_server"));
                }
            }

            default -> {
                // root has no payload
            }
        }
    }

    public static RootCommandNode<CommandSource> filter(final RootCommandNode<CommandSource> root, final CommandSource source) {
        final Map<CommandNode<CommandSource>, CommandNode<CommandSource>> converted = new IdentityHashMap<>();

        final RootCommandNode<CommandSource> result = new RootCommandNode<>();
        converted.put(root, result);

        fillUsableCommands(root, result, source, converted);

        return result;
    }

    private static void fillUsableCommands(
            final CommandNode<CommandSource> from,
            final CommandNode<CommandSource> to,
            final CommandSource source,
            final Map<CommandNode<CommandSource>, CommandNode<CommandSource>> converted
    ) {
        for (final CommandNode<CommandSource> child : from.getChildren()) {
            if (!child.canUse(source)) {
                continue;
            }

            final var builder = child.createBuilder();

            if (child.getRedirect() != null) {
                builder.redirect(converted.get(child.getRedirect()));
            }

            final CommandNode<CommandSource> copy = builder.build();

            converted.put(child, copy);
            to.addChild(copy);

            fillUsableCommands(child, copy, source, converted);
        }
    }

    private static void writeArgumentType(final PacketBuffer buf, final ArgumentType<?> argument) {
        if (argument instanceof fr.fidorial.command.argument.ForceServerSuggestions) {
            writeAliasedAsString(buf, argument, ArgumentTypeRegistry.registrar(argument));
            return;
        }

        writeArgumentTypeCaptured(buf, argument, ArgumentTypeRegistry.registrar(argument));
    }

    private static <A extends ArgumentType<?>, S extends ArgumentTypeRegistrar.Spec<A>> void writeAliasedAsString(
            final PacketBuffer buf,
            final A argument,
            final ArgumentTypeRegistrar<A, S> registrar
    ) {
        buf.writeVarInt(ArgumentTypeIds.STRING_ARGUMENT_ID);

        final S spec = registrar.access(argument);
        registrar.serialize(spec, buf);
    }

    private static <A extends ArgumentType<?>, S extends ArgumentTypeRegistrar.Spec<A>> void writeArgumentTypeCaptured(
            final PacketBuffer buf,
            final A argument,
            final ArgumentTypeRegistrar<A, S> registrar
    ) {
        final int id = NetworkArgumentIds.getId(registrar);
        buf.writeVarInt(id);

        final S spec = registrar.access(argument);
        registrar.serialize(spec, buf);
    }

    private static boolean isRestricted(final CommandNode<CommandSource> node) {
        return !node.getRequirement().test(NO_PERMISSION_SOURCE);
    }
}
