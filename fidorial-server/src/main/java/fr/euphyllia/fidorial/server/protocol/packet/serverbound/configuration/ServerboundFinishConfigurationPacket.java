package fr.euphyllia.fidorial.server.protocol.packet.serverbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.ConfigurationPacketListener;

/**
 * <p>Sent by the client to notify the server that the configuration process has finished. It is sent in response to the server's Finish Configuration . This packet switches the connection state to play .</p>
 *
 * <p><b>Packet ID:</b> Configuration = 3 (0x03)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Acknowledge_Finish_Configuration">Acknowledge Finish Configuration</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>no fields</td><td>&nbsp;</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundFinishConfigurationPacket() implements ServerboundPacket {

    public static ServerboundFinishConfigurationPacket read(PacketBuffer buf) {
        return new ServerboundFinishConfigurationPacket();
    }

    @Override
    public void handle(PacketListener listener) {
        ((ConfigurationPacketListener) listener).handleFinishConfiguration(this);
    }
}
