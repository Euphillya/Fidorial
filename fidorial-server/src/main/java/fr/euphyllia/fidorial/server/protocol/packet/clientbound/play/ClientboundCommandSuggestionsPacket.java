package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>The server responds with a list of auto-completions of the last word sent to it. In the case of regular chat, this is a player username. Command names and parameters are also supported. The client sorts these alphabetically before listing them.</p>
 *
 * <p><b>Packet ID:</b> Play = 15 (0x0F)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Command_Suggestions_Response">Command Suggestions Response</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>ID</td><td>VarInt</td><td>Transaction ID.</td></tr>
 *     <tr><td>1</td><td>Start</td><td>VarInt</td><td>Start of the text to replace.</td></tr>
 *     <tr><td>2</td><td>Length</td><td>VarInt</td><td>Length of the text to replace.</td></tr>
 *     <tr><td>3</td><td>Matches</td><td>Match</td><td>Prefixed Array</td></tr>
 *     <tr><td>4</td><td>Tooltip</td><td>Prefixed Optional Text Component</td><td>Tooltip to display.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundCommandSuggestionsPacket(int id, int start, int length, Object matches,
                                                  Object tooltip) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.COMMAND_SUGGESTIONS;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(id);
        buf.writeVarInt(start);
        buf.writeVarInt(length);
        // TODO: write matches (Match)
        // TODO: write tooltip (Prefixed Optional Text Component)
    }
}
