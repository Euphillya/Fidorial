package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.LoginClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import net.kyori.adventure.key.Key;

public record ClientboundHelloPacket(String serverId, byte[] publicKey, byte[] verifyToken,
                                     boolean shouldAuthenticate) implements ClientboundPacket {

    @Override
    public Key name() {
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
