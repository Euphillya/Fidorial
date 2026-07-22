package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Requests a cookie that was previously stored.</p>
 *
 * <p><b>Packet ID:</b> Login = 5 (0x05), Configuration = 0 (0x00), Play = 21 (0x15)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Cookie_Request">Cookie Request</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Key</td><td>Identifier</td><td>The identifier of the cookie.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundCookieRequestPacket(String key) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.COOKIE_REQUEST;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeIdentifier(key);
    }
}
