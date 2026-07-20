package fr.euphyllia.fidorial.server.command.brigadier.packet;

import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.tree.*;
import fr.euphyllia.fidorial.server.command.brigadier.argument.*;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.EntityArgumentType;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.UuidArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.generic.TimeArgument;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistry;
import fr.euphyllia.fidorial.server.command.brigadier.argument.location.Vec3Argument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.player.GameModeArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.player.PlayerProfileArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.resource.ResourceKeyArgument;
import fr.euphyllia.fidorial.server.command.brigadier.packet.util.PermissionlessCommandSource;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.fidorial.command.CommandSource;

import java.util.*;

public final class CommandTreeSerializer {

    private static final byte TYPE_ROOT = 0;
    private static final byte TYPE_LITERAL = 1;
    private static final byte TYPE_ARGUMENT = 2;

    private static final byte FLAG_EXECUTABLE = 4;
    private static final byte FLAG_REDIRECT = 8;
    private static final byte FLAG_CUSTOM_SUGGESTIONS = 16;
    private static final byte FLAG_RESTRICTED = 32;

    private static final CommandSource NO_PERMISSION_SOURCE = PermissionlessCommandSource.instance();

    private record SerializerEntry(
            Class<?> type,
            ArgumentSerializer serializer
    ) {}

    private static final List<SerializerEntry> SERIALIZERS = List.of(
            new SerializerEntry(StringArgumentType.class, CommandTreeSerializer::writeStringArgument),
            new SerializerEntry(BoolArgumentType.class, CommandTreeSerializer::writeBoolArgument),
            new SerializerEntry(IntegerArgumentType.class, CommandTreeSerializer::writeIntegerArgument),
            new SerializerEntry(EntityArgumentType.class, CommandTreeSerializer::writeEntityArgument),
            new SerializerEntry(PlayerProfileArgument.class, CommandTreeSerializer::writePlayerProfileArgument),
            new SerializerEntry(Vec3Argument.class, CommandTreeSerializer::writeVec3Argument),
            new SerializerEntry(GameModeArgument.class, CommandTreeSerializer::writeGameModeArgument),
            new SerializerEntry(TimeArgument.class, CommandTreeSerializer::writeTimeArgument),
            new SerializerEntry(ResourceKeyArgument.class, CommandTreeSerializer::writeResourceKeyArgument),
            new SerializerEntry(UuidArgument.class, CommandTreeSerializer::writeUuidArgument)
    );

    private CommandTreeSerializer() {}


    public static void write(
            PacketBuffer buf,
            RootCommandNode<CommandSource> root
    ) {
        List<CommandNode<CommandSource>> nodes = enumerate(root);

        Map<CommandNode<CommandSource>, Integer> ids =
                new IdentityHashMap<>();

        for (int i = 0; i < nodes.size(); i++) {
            ids.put(nodes.get(i), i);
        }

        buf.writeVarInt(nodes.size());


        for (CommandNode<CommandSource> node : nodes) {
            writeNode(buf, node, ids);
        }


        buf.writeVarInt(ids.get(root));
    }


    private static List<CommandNode<CommandSource>> enumerate(
            RootCommandNode<CommandSource> root
    ) {
        List<CommandNode<CommandSource>> nodes = new ArrayList<>();

        Map<CommandNode<CommandSource>, Integer> ids =
                new IdentityHashMap<>();

        Queue<CommandNode<CommandSource>> queue =
                new ArrayDeque<>();

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
        }
        else if (node instanceof ArgumentCommandNode<?, ?>) {
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


        if (node instanceof ArgumentCommandNode<?, ?> argument
                && argument.getCustomSuggestions() != null) {
            flags |= FLAG_CUSTOM_SUGGESTIONS;
        }

        buf.writeByte(flags);

        int[] children = node.getChildren()
                .stream()
                .filter(ids::containsKey)
                .mapToInt(ids::get)
                .toArray();


        buf.writeVarIntArray(children);


        if ((flags & FLAG_REDIRECT) != 0) {
            buf.writeVarInt(
                    ids.get(node.getRedirect())
            );
        }

        switch (node) {
            case LiteralCommandNode<?> literal -> buf.writeString(literal.getLiteral());
            case ArgumentCommandNode<?, ?> argument -> {
                buf.writeString(argument.getName());

                writeArgumentType(
                        buf,
                        argument.getType()
                );

                if (argument.getCustomSuggestions() != null) {
                    buf.writeIdentifier(
                            "minecraft:ask_server"
                    );
                }
            }


            default -> {
                // root has no payload
            }
        }
    }

    public static RootCommandNode<CommandSource> filter(
            RootCommandNode<CommandSource> root,
            CommandSource source
    ) {
        Map<CommandNode<CommandSource>, CommandNode<CommandSource>> converted =
                new IdentityHashMap<>();

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
                builder.redirect(
                        converted.get(child.getRedirect())
                );
            }

            CommandNode<CommandSource> copy = builder.build();

            converted.put(child, copy);
            to.addChild(copy);

            fillUsableCommands(child, copy, source, converted);
        }
    }

    private static ArgumentType<?> unwrapArgumentType(ArgumentType<?> type) {
        while (type instanceof ArgumentProviderImpl.NativeWrapperArgumentType<?, ?> wrapper) {
            type = wrapper.vanillaArgumentType();
        }

        return type;
    }

    private static void writeArgumentType(
            PacketBuffer buf,
            ArgumentType<?> argumentType
    ) {
        argumentType = unwrapArgumentType(argumentType);

        for (SerializerEntry entry : SERIALIZERS) {
            if (entry.type().isInstance(argumentType)) {
                entry.serializer().write(buf, argumentType);
                return;
            }
        }

        throw new IllegalArgumentException(
                "Unsupported argument: " + argumentType
        );
    }

    private static void writeStringArgument(
            PacketBuffer buf,
            ArgumentType<?> rawArgument
    ) {
        StringArgumentType argument = (StringArgumentType) rawArgument;
        buf.writeVarInt(
                ArgumentTypeRegistry.STRING
        );

        buf.writeVarInt(
                switch (argument.getType()) {
                    case SINGLE_WORD -> 0;
                    case QUOTABLE_PHRASE -> 1;
                    case GREEDY_PHRASE -> 2;
                }
        );
    }

    private static void writeIntegerArgument(
            PacketBuffer buf,
            ArgumentType<?> rawArgument
    ) {
        IntegerArgumentType argument = (IntegerArgumentType) rawArgument;

        buf.writeVarInt(ArgumentTypeRegistry.INTEGER);

        int flags = 0;

        if (argument.getMinimum() != Integer.MIN_VALUE) {
            flags |= 1;
        }

        if (argument.getMaximum() != Integer.MAX_VALUE) {
            flags |= 2;
        }

        buf.writeByte(flags);

        if ((flags & 1) != 0) {
            buf.writeInt(argument.getMinimum());
        }

        if ((flags & 2) != 0) {
            buf.writeInt(argument.getMaximum());
        }
    }

    private static void writeBoolArgument(
            PacketBuffer buf,
            ArgumentType<?> rawArgument_not_yet_implemented
    ) {
        buf.writeVarInt(
                ArgumentTypeRegistry.BOOL
        );
    }

    private static void writeEntityArgument(
            PacketBuffer buf,
            ArgumentType<?> rawArgument
    ) {
        EntityArgumentType argument = (EntityArgumentType) rawArgument;
        buf.writeVarInt(
                ArgumentTypeRegistry.ENTITY
        );

        int flags = 0;

        if (argument.single()) {
            flags |= 1;
        }

        if (argument.playersOnly()) {
            flags |= 2;
        }

        buf.writeByte(flags);
    }

    private static void writePlayerProfileArgument(
            PacketBuffer buf,
            ArgumentType<?> rawArgument
    ) {
        buf.writeVarInt(
                ArgumentTypeRegistry.PLAYER_PROFILE
        );
    }

    private static void writeVec3Argument(
            PacketBuffer buf,
            ArgumentType<?> rawArgument
    ) {
        Vec3Argument argument =
                (Vec3Argument) rawArgument;

        buf.writeVarInt(
                ArgumentTypeRegistry.VEC3
        );
    }

    private static void writeGameModeArgument(
            PacketBuffer buf,
            ArgumentType<?> rawArgument
    ) {
        buf.writeVarInt(
                ArgumentTypeRegistry.GAME_MODE
        );
    }

    private static void writeTimeArgument(
            PacketBuffer buf,
            ArgumentType<?> rawArgument
    ) {
        TimeArgument argument =
                (TimeArgument) rawArgument;

        buf.writeVarInt(
                ArgumentTypeRegistry.TIME
        );

        buf.writeInt(
                argument.minimum()
        );
    }

    private static void writeResourceKeyArgument(
            PacketBuffer buf,
            ArgumentType<?> rawArgument
    ) {
        ResourceKeyArgument<?> argument =
                (ResourceKeyArgument<?>) rawArgument;

        buf.writeVarInt(
                ArgumentTypeRegistry.RESOURCE_KEY
        );

        buf.writeIdentifier(argument.registryKey().key().asString());
    }

    private static void writeUuidArgument(
            PacketBuffer buf,
            ArgumentType<?> rawArgument
    ) {
        buf.writeVarInt(
                ArgumentTypeRegistry.UUID
        );
    }

    private static boolean isRestricted(CommandNode<CommandSource> node) {
        return !node.getRequirement().test(NO_PERMISSION_SOURCE);
    }
}
