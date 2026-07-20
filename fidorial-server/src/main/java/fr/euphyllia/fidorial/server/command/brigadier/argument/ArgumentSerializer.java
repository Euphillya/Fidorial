package fr.euphyllia.fidorial.server.command.brigadier.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fr.euphyllia.fidorial.server.network.PacketBuffer;

@FunctionalInterface
public interface ArgumentSerializer {
    void write(PacketBuffer buf, ArgumentType<?> argument);
}
