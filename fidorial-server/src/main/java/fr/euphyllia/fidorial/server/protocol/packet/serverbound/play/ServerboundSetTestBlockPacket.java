package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.fidorial.world.BlockPos;

/**
 * <p>Updates the value of the Test Block at the given position.</p>
 *
 * <p><b>Packet ID:</b> Play = 60 (0x3C)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Test_Block">Set Test Block</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Position</td><td>Position</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Mode</td><td>VarInt Enum</td><td>0: start, 1: log, 2: fail, 3: accept</td></tr>
 *     <tr><td>2</td><td>Message</td><td>String</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundSetTestBlockPacket(BlockPos position, Object mode,
                                            String message) implements ServerboundPacket {

    public static ServerboundSetTestBlockPacket read(PacketBuffer buf) {
        BlockPos position = null;
        position = buf.readPosition();
        Object mode = null; // TODO: read mode (VarInt Enum)
        String message = null;
        message = buf.readString(32767);
        return new ServerboundSetTestBlockPacket(position, mode, message);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
