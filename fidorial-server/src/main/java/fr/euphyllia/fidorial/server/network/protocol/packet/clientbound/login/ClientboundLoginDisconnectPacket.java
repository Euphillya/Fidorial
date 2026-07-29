package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.LoginClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import net.kyori.adventure.text.Component;

public record ClientboundLoginDisconnectPacket(Component reason) implements ClientboundPacket {

    public static ClientboundLoginDisconnectPacket ofComponent(Component reason) {
        return new ClientboundLoginDisconnectPacket(reason);
    }

    @Override
    public String name() {
        return LoginClientboundPackets.LOGIN_DISCONNECT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeComponent(reason);
    }
}
