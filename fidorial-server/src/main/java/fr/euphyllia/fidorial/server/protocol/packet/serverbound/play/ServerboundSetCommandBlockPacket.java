package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.fidorial.world.BlockPos;

/**
 * <p><b>Packet ID:</b> Play = 54 (0x36)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Program_Command_Block">Program Command Block</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Location</td><td>Position</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Command</td><td>String (32767)</td><td>&nbsp;</td></tr>
 *     <tr><td>2</td><td>Mode</td><td>VarInt Enum</td><td>0: chain, 1: repeating, 2: impulse.</td></tr>
 *     <tr><td>3</td><td>Flags</td><td>Byte</td><td>0x01: Track Output (if false, the output of the previous command will not be stored within the command block); 0x02: Is conditional; 0x04: Automatic.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundSetCommandBlockPacket(BlockPos location, String command, Object mode,
                                               byte flags) implements ServerboundPacket {

    public static ServerboundSetCommandBlockPacket read(PacketBuffer buf) {
        BlockPos location = null;
        location = buf.readPosition();
        String command = null;
        command = buf.readString(32767);
        Object mode = null; // TODO: read mode (VarInt Enum)
        byte flags = (byte) 0;
        flags = buf.readByte();
        return new ServerboundSetCommandBlockPacket(location, command, mode, flags);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
