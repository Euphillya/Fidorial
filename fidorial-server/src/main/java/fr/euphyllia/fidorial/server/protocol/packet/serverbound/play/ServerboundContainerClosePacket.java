package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>This packet is sent by the client when closing a window. vanilla clients send a Close Window packet with Window ID 0 to close their inventory, even though there is never an Open Screen packet for the inventory.</p>
 *
 * <p><b>Packet ID:</b> Play = 19 (0x13)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Close_Container_2">Close Container</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Window ID</td><td>VarInt</td><td>This is the ID of the window that was closed. 0 for player inventory.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundContainerClosePacket(int windowId) implements ServerboundPacket {

    public static ServerboundContainerClosePacket read(PacketBuffer buf) {
        int windowId = 0;
        windowId = buf.readVarInt();
        return new ServerboundContainerClosePacket(windowId);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
