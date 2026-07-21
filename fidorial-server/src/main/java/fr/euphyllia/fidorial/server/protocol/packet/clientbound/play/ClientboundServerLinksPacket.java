package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>This packet contains a list of links that the vanilla client will display in the menu available from the pause menu. Link labels can be built-in or custom (i.e., any text).</p>
 *
 * <p><b>Packet ID:</b> Configuration = 16 (0x10), Play = 137 (0x89)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Server_Links">Server Links</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Links</td><td>Label</td><td>Prefixed Array</td></tr>
 *     <tr><td>1</td><td>URL</td><td>String</td><td>Valid URL.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundServerLinksPacket(Object links, String url) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SERVER_LINKS;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write links (Label)
        buf.writeString(url);
    }
}
