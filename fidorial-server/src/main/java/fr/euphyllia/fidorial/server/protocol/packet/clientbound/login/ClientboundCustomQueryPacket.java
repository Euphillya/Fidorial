package fr.euphyllia.fidorial.server.protocol.packet.clientbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.LoginClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundCustomQueryPacket(int transactionId, String channel, byte[] payload)
        implements ClientboundPacket {

    @Override
    public String name() {
        return LoginClientboundPackets.CUSTOM_QUERY;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(transactionId)
                .writeIdentifier(channel)
                .writeRawBytes(payload);
    }
}
