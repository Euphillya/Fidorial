package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>This packet is sent when a player clicks a recipe in the crafting book that is craftable (white border).</p>
 *
 * <p><b>Packet ID:</b> Play = 39 (0x27)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Place_Recipe">Place Recipe</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Window ID</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Recipe ID</td><td>VarInt</td><td>ID of recipe previously defined in Recipe Book Add .</td></tr>
 *     <tr><td>2</td><td>Make all</td><td>Boolean</td><td>Affects the amount of items processed; true if shift is down when clicked.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundPlaceRecipePacket(int windowId, int recipeId, boolean makeAll) implements ServerboundPacket {

    public static ServerboundPlaceRecipePacket read(PacketBuffer buf) {
        int windowId = 0;
        windowId = buf.readVarInt();
        int recipeId = 0;
        recipeId = buf.readVarInt();
        boolean makeAll = false;
        makeAll = buf.readBoolean();
        return new ServerboundPlaceRecipePacket(windowId, recipeId, makeAll);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
