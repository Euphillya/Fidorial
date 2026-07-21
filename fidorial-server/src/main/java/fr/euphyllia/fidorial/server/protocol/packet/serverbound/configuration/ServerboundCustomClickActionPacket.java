package fr.euphyllia.fidorial.server.protocol.packet.serverbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p>Sent when the client clicks a Text Component with the minecraft:custom click action. This is meant as an alternative to running a command, but will not have any effect on vanilla servers.</p>
 *
 * <p><b>Packet ID:</b> Configuration = 8 (0x08), Play = 68 (0x44)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Custom_Click_Action">Custom Click Action</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>ID</td><td>Identifier</td><td>The identifier for the click action.</td></tr>
 *     <tr><td>1</td><td>Payload</td><td>NBT</td><td>The data to send with the click action. May be a TAG_END (0).</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundCustomClickActionPacket(String id, Object payload) implements ServerboundPacket {

    public static ServerboundCustomClickActionPacket read(PacketBuffer buf) {
        String id = null;
        id = buf.readIdentifier();
        Object payload = null; // TODO: read payload (NBT)
        return new ServerboundCustomClickActionPacket(id, payload);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
