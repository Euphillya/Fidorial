package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Removes a message from the client's chat. This only works for messages with signatures; system messages cannot be deleted with this packet.</p>
 *
 * <p><b>Packet ID:</b> Play = 31 (0x1F)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Delete_Message">Delete Message</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Message ID</td><td>VarInt</td><td>The message ID + 1, used for validating message signature. The next field is present only when value of this field is equal to 0.</td></tr>
 *     <tr><td>1</td><td>Signature</td><td>Optional Byte Array (256)</td><td>The previous message's signature. Always 256 bytes and not length-prefixed.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundDeleteChatPacket(int messageId, Object signature) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.DELETE_CHAT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(messageId);
        // TODO: write signature (Optional Byte Array)
    }
}
