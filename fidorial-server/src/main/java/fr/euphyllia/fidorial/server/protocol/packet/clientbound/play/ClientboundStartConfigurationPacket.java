package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Sent during gameplay in order to redo the configuration process. The client must respond with Acknowledge Configuration for the process to start. This packet switches the connection state to configuration .</p>
 *
 * <p><b>Packet ID:</b> Play = 118 (0x76)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Start_Configuration">Start Configuration</a></p>
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
public record ClientboundStartConfigurationPacket(Object noFields) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.START_CONFIGURATION;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write noFields ()
    }
}
