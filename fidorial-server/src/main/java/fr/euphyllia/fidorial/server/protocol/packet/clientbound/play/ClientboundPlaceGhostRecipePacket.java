package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Response to the serverbound packet ( Place Recipe ), with the same recipe ID. Appears to be used to notify the UI.</p>
 *
 * <p><b>Packet ID:</b> Play = 63 (0x3F)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Place_Ghost_Recipe">Place Ghost Recipe</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Window ID</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Recipe Display</td><td>Recipe Display</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundPlaceGhostRecipePacket(int windowId, Object recipeDisplay) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.PLACE_GHOST_RECIPE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(windowId);
        // TODO: write recipeDisplay (Recipe Display)
    }
}
