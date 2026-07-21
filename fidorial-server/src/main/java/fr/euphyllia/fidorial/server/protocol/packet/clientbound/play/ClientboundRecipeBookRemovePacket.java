package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p><b>Packet ID:</b> Play = 75 (0x4B)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Recipe_Book_Remove">Recipe Book Remove</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Recipes</td><td>Prefixed Array of VarInt</td><td>IDs of recipes to remove.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundRecipeBookRemovePacket(Object recipes) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.RECIPE_BOOK_REMOVE;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write recipes (Prefixed Array of VarInt)
    }
}
