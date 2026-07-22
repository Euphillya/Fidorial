package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p><b>Packet ID:</b> Play = 74 (0x4A)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Recipe_Book_Add">Recipe Book Add</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Recipes</td><td>Recipe ID</td><td>Prefixed Array</td></tr>
 *     <tr><td>1</td><td>Display</td><td>Recipe Display</td><td>&nbsp;</td></tr>
 *     <tr><td>2</td><td>Group ID</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>3</td><td>Category ID</td><td>VarInt</td><td>ID in the minecraft:recipe_book_category registry.</td></tr>
 *     <tr><td>4</td><td>Ingredients</td><td>Prefixed Optional Prefixed Array of ID Set</td><td>IDs in the minecraft:item registry, or an inline definition.</td></tr>
 *     <tr><td>5</td><td>Flags</td><td>Byte</td><td>0x01: show notification; 0x02: highlight as new</td></tr>
 *     <tr><td>6</td><td>Replace</td><td>Boolean</td><td>Replace or Add to known recipes</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundRecipeBookAddPacket(Object recipes, Object display, int groupId, int categoryId,
                                             Object ingredients, byte flags,
                                             boolean replace) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.RECIPE_BOOK_ADD;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write recipes (Recipe ID)
        // TODO: write display (Recipe Display)
        buf.writeVarInt(groupId);
        buf.writeVarInt(categoryId);
        // TODO: write ingredients (Prefixed Optional Prefixed Array of ID Set)
        buf.writeByte(flags);
        buf.writeBoolean(replace);
    }
}
