package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.LoginClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;


public record ClientboundLoginCompressionPacket(int threshold) implements ClientboundPacket {

    @Override
    public String name() {
        return LoginClientboundPackets.LOGIN_COMPRESSION;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(threshold);
    }
}
