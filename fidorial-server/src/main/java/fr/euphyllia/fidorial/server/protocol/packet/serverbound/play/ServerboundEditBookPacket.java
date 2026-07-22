package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p><b>Packet ID:</b> Play = 24 (0x18)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Edit_Book">Edit Book</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Slot</td><td>VarInt</td><td>The hotbar slot where the written book is located</td></tr>
 *     <tr><td>1</td><td>Entries</td><td>Prefixed Array (100) of String (1024)</td><td>Text from each page. Maximum string length is 1024 chars.</td></tr>
 *     <tr><td>2</td><td>Title</td><td>Prefixed Optional String (32)</td><td>Title of book. Present if book is being signed, not present if book is being edited.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundEditBookPacket(int slot, Object entries, Object title) implements ServerboundPacket {

    public static ServerboundEditBookPacket read(PacketBuffer buf) {
        int slot = 0;
        slot = buf.readVarInt();
        Object entries = null; // TODO: read entries (Prefixed Array)
        Object title = null; // TODO: read title (Prefixed Optional String)
        return new ServerboundEditBookPacket(slot, entries, title);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
