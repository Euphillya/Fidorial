package fr.euphyllia.fidorial.server.protocol.packet.serverbound.handshake;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.HandshakePacketListener;

public record ServerboundIntentionPacket(int protocolVersion, String hostname, int port, int nextState)
        implements ServerboundPacket {

    public static ServerboundIntentionPacket read(PacketBuffer buf) {
        int protocolVersion = buf.readVarInt();
        String hostname = buf.readString(255);
        int port = buf.readUShort();
        int nextState = buf.readVarInt();
        return new ServerboundIntentionPacket(protocolVersion, hostname, port, nextState);
    }

    @Override
    public void handle(PacketListener listener) {
        ((HandshakePacketListener) listener).handleIntention(this);
    }
}
