package fr.euphyllia.fidorial.server.protocol.packet.serverbound.common;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.ConfigurationPacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;


public record ServerboundClientInformationPacket(String language, int displayedSkinParts) implements ServerboundPacket {

    public static ServerboundClientInformationPacket read(PacketBuffer buf) {
        String language = buf.readString(16);
        buf.readByte();
        buf.readVarInt();
        buf.readBoolean();
        int skinParts = buf.readUByte();
        return new ServerboundClientInformationPacket(language, skinParts);
    }

    @Override
    public void handle(PacketListener listener) {
        if (listener instanceof PlayPacketListener play) {
            play.handleClientInformation(this);
        } else if (listener instanceof ConfigurationPacketListener config) {
            config.handleClientInformation(this);
        }
    }
}
