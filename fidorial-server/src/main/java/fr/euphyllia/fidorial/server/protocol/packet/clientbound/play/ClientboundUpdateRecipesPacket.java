package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p><b>Packet ID:</b> Play = 133 (0x85)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Update_Recipes">Update Recipes</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Property Sets</td><td>Property Set ID</td><td>Prefixed Array</td></tr>
 *     <tr><td>1</td><td>Items</td><td>Prefixed Array of VarInt</td><td>IDs in the minecraft:item registry.</td></tr>
 *     <tr><td>2</td><td>Stonecutter Recipes</td><td>Ingredients</td><td>Prefixed Array</td></tr>
 *     <tr><td>3</td><td>Slot Display</td><td>Slot Display</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundUpdateRecipesPacket(Object propertySets, Object items, Object stonecutterRecipes,
                                             Object slotDisplay) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.UPDATE_RECIPES;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write propertySets (Property Set ID)
        // TODO: write items (Prefixed Array of VarInt)
        // TODO: write stonecutterRecipes (Ingredients)
        // TODO: write slotDisplay (Slot Display)
    }
}
