package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Sent when recipe is first seen in recipe book. Replaces Recipe Book Data, type 0.</p>
 *
 * <p><b>Packet ID:</b> Play = 47 (0x2F)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Seen_Recipe">Set Seen Recipe</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Recipe ID</td><td>VarInt</td><td>ID of recipe previously defined in Recipe Book Add.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundRecipeBookSeenRecipePacket(int recipeId) implements ServerboundPacket {

    public static ServerboundRecipeBookSeenRecipePacket read(PacketBuffer buf) {
        int recipeId = 0;
        recipeId = buf.readVarInt();
        return new ServerboundRecipeBookSeenRecipePacket(recipeId);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
