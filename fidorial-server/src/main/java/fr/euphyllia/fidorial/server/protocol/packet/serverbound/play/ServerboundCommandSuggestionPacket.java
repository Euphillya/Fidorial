package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Sent when the client needs to tab-complete a minecraft:ask_server suggestion type.</p>
 *
 * <p><b>Packet ID:</b> Play = 15 (0x0F)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Command_Suggestions_Request">Command Suggestions Request</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Transaction Id</td><td>VarInt</td><td>The ID of the transaction that the server will send back to the client in the response of this packet. Client generates this and increments it each time it sends another tab completion that doesn't get a response.</td></tr>
 *     <tr><td>1</td><td>Text</td><td>String (32500)</td><td>All the text behind the cursor including the / (e.g. to the left of the cursor in left-to-right languages like English).</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundCommandSuggestionPacket(int transactionId, String text) implements ServerboundPacket {

    public static ServerboundCommandSuggestionPacket read(PacketBuffer buf) {
        int transactionId = 0;
        transactionId = buf.readVarInt();
        String text = null;
        text = buf.readString(32767);
        return new ServerboundCommandSuggestionPacket(transactionId, text);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
