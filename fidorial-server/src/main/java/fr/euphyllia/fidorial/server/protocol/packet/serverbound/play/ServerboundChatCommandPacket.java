package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

/**
 * <p><b>Packet ID:</b> Play = 7 (0x07)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Chat_Command">Chat Command</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Command</td><td>String (32767)</td><td>The command typed by the client excluding the / .</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundChatCommandPacket(String command) implements ServerboundPacket {

    private static final int MAX_LENGTH = 32767;

    public static ServerboundChatCommandPacket read(PacketBuffer buf) {
        return new ServerboundChatCommandPacket(buf.readString(MAX_LENGTH));
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handleChatCommand(this);
    }
}
