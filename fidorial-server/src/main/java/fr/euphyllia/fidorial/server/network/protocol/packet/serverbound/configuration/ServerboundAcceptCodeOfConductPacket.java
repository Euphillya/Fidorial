package fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.ConfigurationPacketListener;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;

/**
 * @see <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Accept_Code_of_Conduct">Accept Code of Conduct</a>
 */
public record ServerboundAcceptCodeOfConductPacket() implements ServerboundPacket {

    public static ServerboundAcceptCodeOfConductPacket read(final PacketBuffer buf) {
        return new ServerboundAcceptCodeOfConductPacket();
    }

    @Override
    public void handle(final PacketListener listener) {
        ((ConfigurationPacketListener) listener).handleAcceptCodeOfConduct(this);
    }
}
