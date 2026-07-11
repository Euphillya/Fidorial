package fr.euphyllia.fidorial.server.protocol.packet.clientbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.LoginClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundHelloPacket(String serverId, byte[] publicKey, byte[] verifyToken,
                                     boolean shouldAuthenticate) implements ClientboundPacket {

    @Override
    public String name() {
        return LoginClientboundPackets.HELLO;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeString(serverId)
                .writeByteArray(publicKey)
                .writeByteArray(verifyToken)
                .writeBoolean(shouldAuthenticate);
    }
}
