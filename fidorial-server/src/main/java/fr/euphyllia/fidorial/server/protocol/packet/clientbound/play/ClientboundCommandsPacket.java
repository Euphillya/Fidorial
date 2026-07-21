package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.RootCommandNode;
import fr.euphyllia.fidorial.server.command.brigadier.packet.CommandTreeSerializer;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.fidorial.command.CommandSource;

public record ClientboundCommandsPacket(CommandDispatcher<CommandSource> dispatcher, CommandSource source)
        implements ClientboundPacket {
    @Override
    public String name() {
        return PlayClientboundPackets.COMMANDS;
    }

    @Override
    public void write(PacketBuffer buf) {
        RootCommandNode<CommandSource> filtered = CommandTreeSerializer.filter(dispatcher.getRoot(), source);
        CommandTreeSerializer.write(buf, filtered);
    }
}
