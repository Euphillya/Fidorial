package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Contains a list of key-value text entries that are included in any crash or disconnection report generated during connection to the server.</p>
 *
 * <p><b>Packet ID:</b> Configuration = 15 (0x0F), Play = 136 (0x88)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Custom_Report_Details">Custom Report Details</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Details</td><td>Title</td><td>Prefixed Array (32)</td></tr>
 *     <tr><td>1</td><td>Description</td><td>String (4096)</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundCustomReportDetailsPacket(Object details, String description) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.CUSTOM_REPORT_DETAILS;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write details (Title)
        buf.writeString(description);
    }
}
