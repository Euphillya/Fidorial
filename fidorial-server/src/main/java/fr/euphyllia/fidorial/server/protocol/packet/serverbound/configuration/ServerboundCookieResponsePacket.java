package fr.euphyllia.fidorial.server.protocol.packet.serverbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p>Response to a Cookie Request from the server. The vanilla server only accepts responses of up to 5 kiB in size.</p>
 *
 * <p><b>Packet ID:</b> Login = 4 (0x04), Configuration = 1 (0x01), Play = 21 (0x15)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Cookie_Response">Cookie Response</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Key</td><td>Identifier</td><td>The identifier of the cookie.</td></tr>
 *     <tr><td>1</td><td>Payload</td><td>Prefixed Optional Prefixed Array (5120) of Byte</td><td>The data of the cookie.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundCookieResponsePacket(String key, Object payload) implements ServerboundPacket {

    public static ServerboundCookieResponsePacket read(PacketBuffer buf) {
        String key = null;
        key = buf.readIdentifier();
        Object payload = null; // TODO: read payload (Prefixed Optional Prefixed Array of Byte)
        return new ServerboundCookieResponsePacket(key, payload);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
