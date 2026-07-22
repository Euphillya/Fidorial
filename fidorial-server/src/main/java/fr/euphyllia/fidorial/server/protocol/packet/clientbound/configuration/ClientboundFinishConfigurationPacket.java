package fr.euphyllia.fidorial.server.protocol.packet.clientbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Sent by the server to notify the client that the configuration process has finished. The client validates the registry and tag data received from the server, and answers with Acknowledge Finish Configuration whenever it is ready to continue. This packet switches the connection state to play .</p>
 *
 * <p><b>Packet ID:</b> Configuration = 3 (0x03)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Finish_Configuration">Finish Configuration</a></p>
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
public record ClientboundFinishConfigurationPacket() implements ClientboundPacket {

    @Override
    public String name() {
        return ConfigurationClientboundPackets.FINISH_CONFIGURATION;
    }

    @Override
    public void write(PacketBuffer buf) {
        // aucun champ
    }
}
