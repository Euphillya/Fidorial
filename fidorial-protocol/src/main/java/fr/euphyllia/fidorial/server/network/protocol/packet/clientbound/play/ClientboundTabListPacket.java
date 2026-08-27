package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

// https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Tab_List_Header_And_Footer
public record ClientboundTabListPacket(Component header, Component footer) implements ClientboundPacket {

    @Override
    public Key name() {
        return PlayClientboundPackets.TAB_LIST;
    }

    @Override
    public void write(final PacketBuffer buf) {
        buf.writeComponent(header).writeComponent(footer);
    }
}
