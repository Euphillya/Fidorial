package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Sent by the server to indicate that the client should switch advancement tab. Sent either when the client switches tab in the GUI or when an advancement is made in another tab. The Identifier must be one of the following if no custom data pack is loaded: If no or an invalid identifier is sent, the client will switch to the first tab in the GUI.</p>
 *
 * <p><b>Packet ID:</b> Play = 85 (0x55)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Select_Advancements_Tab">Select Advancements Tab</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Identifier</td><td>Prefixed Optional Identifier</td><td>See below.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSelectAdvancementsTabPacket(Object identifier) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SELECT_ADVANCEMENTS_TAB;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write identifier (Prefixed Optional Identifier)
    }
}
