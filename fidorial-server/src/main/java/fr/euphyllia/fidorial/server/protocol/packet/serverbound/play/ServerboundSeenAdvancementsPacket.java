package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p><b>Packet ID:</b> Play = 50 (0x32)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Seen_Advancements">Seen Advancements</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Action</td><td>VarInt Enum</td><td>0: Opened tab, 1: Closed screen.</td></tr>
 *     <tr><td>1</td><td>Tab ID</td><td>Optional Identifier</td><td>Only present if action is Opened tab.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundSeenAdvancementsPacket(Object action, Object tabId) implements ServerboundPacket {

    public static ServerboundSeenAdvancementsPacket read(PacketBuffer buf) {
        Object action = null; // TODO: read action (VarInt Enum)
        Object tabId = null; // TODO: read tabId (Optional Identifier)
        return new ServerboundSeenAdvancementsPacket(action, tabId);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
