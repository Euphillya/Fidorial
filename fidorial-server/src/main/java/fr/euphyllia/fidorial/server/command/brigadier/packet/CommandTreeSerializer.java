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
import fr.fidorial.command.CommandSource;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import static fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistry.unwrap;

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

    public static void write(PacketBuffer buf, RootCommandNode<CommandSource> root) {
        List<CommandNode<CommandSource>> nodes = enumerate(root);

        Map<CommandNode<CommandSource>, Integer> ids = new IdentityHashMap<>();

        for (int i = 0; i < nodes.size(); i++) {
            ids.put(nodes.get(i), i);
        }

        buf.writeVarInt(nodes.size());

        for (CommandNode<CommandSource> node : nodes) {
            writeNode(buf, node, ids);
        }

        buf.writeVarInt(ids.get(root));
    }

    private static List<CommandNode<CommandSource>> enumerate(RootCommandNode<CommandSource> root) {
        List<CommandNode<CommandSource>> nodes = new ArrayList<>();

        Map<CommandNode<CommandSource>, Integer> ids = new IdentityHashMap<>();

        Queue<CommandNode<CommandSource>> queue = new ArrayDeque<>();

        queue.add(root);

        while (!queue.isEmpty()) {
            CommandNode<CommandSource> node = queue.poll();

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
            PacketBuffer buf,
            CommandNode<CommandSource> node,
            Map<CommandNode<CommandSource>, Integer> ids
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

        if (node instanceof ArgumentCommandNode<?, ?> argument && argument.getCustomSuggestions() != null) {
            flags |= FLAG_CUSTOM_SUGGESTIONS;
        }

        buf.writeByte(flags);

        int[] children = node.getChildren().stream()
                .filter(ids::containsKey)
                .mapToInt(ids::get)
                .toArray();

        buf.writeVarIntArray(children);

        if ((flags & FLAG_REDIRECT) != 0) {
            buf.writeVarInt(ids.get(node.getRedirect()));
        }

        System.out.printf("NODE %s flags=%02x children=%s%n", node.getName(), flags, Arrays.toString(children));
        switch (node) {
            case LiteralCommandNode<?> literal -> buf.writeString(literal.getLiteral());
            case ArgumentCommandNode<?, ?> argument -> {
                buf.writeString(argument.getName());

                writeArgumentType(buf, argument.getType());

                if (argument.getCustomSuggestions() != null) {
                    buf.writeIdentifier("minecraft:ask_server");
                }
            }

            default -> {
                // root has no payload
            }
        }
    }

    public static RootCommandNode<CommandSource> filter(RootCommandNode<CommandSource> root, CommandSource source) {
        Map<CommandNode<CommandSource>, CommandNode<CommandSource>> converted = new IdentityHashMap<>();

        RootCommandNode<CommandSource> result = new RootCommandNode<>();
        converted.put(root, result);

        fillUsableCommands(root, result, source, converted);

        return result;
    }

    private static void fillUsableCommands(
            CommandNode<CommandSource> from,
            CommandNode<CommandSource> to,
            CommandSource source,
            Map<CommandNode<CommandSource>, CommandNode<CommandSource>> converted
    ) {
        for (CommandNode<CommandSource> child : from.getChildren()) {
            if (!child.canUse(source)) {
                continue;
            }

            var builder = child.createBuilder();

            if (child.getRedirect() != null) {
                builder.redirect(converted.get(child.getRedirect()));
            }

            CommandNode<CommandSource> copy = builder.build();

            converted.put(child, copy);
            to.addChild(copy);

            fillUsableCommands(child, copy, source, converted);
        }
    }

    private static void writeArgumentType(PacketBuffer buf, ArgumentType<?> argument) {
        int start = buf.nettyBuf().writerIndex();

        ArgumentType<?> vanilla = unwrap(argument);

        ArgumentTypeRegistrar registrar = ArgumentTypeRegistry.registrar(vanilla);

        int id = NetworkArgumentIds.getId(registrar);

        buf.writeVarInt(id);

        registrar.serialize(registrar.access(vanilla), buf);

        int end = buf.nettyBuf().writerIndex();

        System.out.println(vanilla.getClass().getSimpleName() + " wrote " + (end - start) + " bytes");

        for (int i = start; i < end; i++) {
            System.out.printf("%02x ", buf.nettyBuf().getByte(i));
        }
        System.out.println();
    }

    private static boolean isRestricted(CommandNode<CommandSource> node) {
        return !node.getRequirement().test(NO_PERMISSION_SOURCE);
    }
}
