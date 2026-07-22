package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Unused by the vanilla server. Likely provided for custom servers to send chat message completions to clients.</p>
 *
 * <p><b>Packet ID:</b> Play = 23 (0x17)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Chat_Suggestions">Chat Suggestions</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Action</td><td>VarInt Enum</td><td>0: Add, 1: Remove, 2: Set</td></tr>
 *     <tr><td>1</td><td>Entries</td><td>Prefixed Array of String (32767)</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundCustomChatCompletionsPacket(Object action, Object entries) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.CUSTOM_CHAT_COMPLETIONS;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write action (VarInt Enum)
        // TODO: write entries (Prefixed Array of String)
    }
}
