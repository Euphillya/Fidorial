package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p>Replaces Recipe Book Data, type 1.</p>
 *
 * <p><b>Packet ID:</b> Play = 46 (0x2E)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Change_Recipe_Book_Settings">Change Recipe Book Settings</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Book ID</td><td>VarInt Enum</td><td>0: crafting, 1: furnace, 2: blast furnace, 3: smoker.</td></tr>
 *     <tr><td>1</td><td>Book Open</td><td>Boolean</td><td>&nbsp;</td></tr>
 *     <tr><td>2</td><td>Filter Active</td><td>Boolean</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundRecipeBookChangeSettingsPacket(Object bookId, boolean bookOpen,
                                                        boolean filterActive) implements ServerboundPacket {

    public static ServerboundRecipeBookChangeSettingsPacket read(PacketBuffer buf) {
        Object bookId = null; // TODO: read bookId (VarInt Enum)
        boolean bookOpen = false;
        bookOpen = buf.readBoolean();
        boolean filterActive = false;
        filterActive = buf.readBoolean();
        return new ServerboundRecipeBookChangeSettingsPacket(bookId, bookOpen, filterActive);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
