package fr.euphyllia.fidorial.server.protocol.packet.clientbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.LoginClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

public record ClientboundLoginDisconnectPacket(String reasonJson) implements ClientboundPacket {

    public static ClientboundLoginDisconnectPacket ofText(String text) {
        return new ClientboundLoginDisconnectPacket("{\"text\":\"" + text + "\"}");
    }

    @Override
    public String name() {
        return LoginClientboundPackets.LOGIN_DISCONNECT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeString(reasonJson);
    }
}
