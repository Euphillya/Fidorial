package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Stores some arbitrary data on the client, which persists between server transfers. The vanilla client only accepts cookies of up to 5 kiB in size.</p>
 *
 * <p><b>Packet ID:</b> Configuration = 10 (0x0A), Play = 120 (0x78)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Store_Cookie">Store Cookie</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Key</td><td>Identifier</td><td>The identifier of the cookie.</td></tr>
 *     <tr><td>1</td><td>Payload</td><td>Prefixed Array (5120) of Byte</td><td>The data of the cookie.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundStoreCookiePacket(String key, Object payload) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.STORE_COOKIE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeIdentifier(key);
        // TODO: write payload (Prefixed Array of Byte)
    }
}
