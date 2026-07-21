package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p>Requests for the server to update our game mode. Has no effect on vanilla servers if the client doesn't have the required permissions.</p>
 *
 * <p><b>Packet ID:</b> Play = 5 (0x05)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Change_Game_Mode">Change Game Mode</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Game mode</td><td>VarInt Enum</td><td>0: survival, 1: creative, 2: adventure, 3: spectator.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundChangeGameModePacket(Object gameMode) implements ServerboundPacket {

    public static ServerboundChangeGameModePacket read(PacketBuffer buf) {
        Object gameMode = null; // TODO: read gameMode (VarInt Enum)
        return new ServerboundChangeGameModePacket(gameMode);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
