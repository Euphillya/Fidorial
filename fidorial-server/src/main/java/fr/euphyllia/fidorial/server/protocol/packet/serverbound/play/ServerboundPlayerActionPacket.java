package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;
import fr.fidorial.world.BlockPos;

/**
 * <p>Sent when the player mines a block. A vanilla server only accepts digging packets with coordinates within a 6-unit radius between the center of the block and the player's eyes. Status can be one of seven values: The Face field can be one of the following values, representing the face being hit:</p>
 *
 * <p><b>Packet ID:</b> Play = 41 (0x29)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Player_Action">Player Action</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Status</td><td>VarInt Enum</td><td>The action the player is taking against the block (see below).</td></tr>
 *     <tr><td>1</td><td>Location</td><td>Position</td><td>Block position.</td></tr>
 *     <tr><td>2</td><td>Face</td><td>Byte Enum</td><td>The face being hit (see below).</td></tr>
 *     <tr><td>3</td><td>Sequence</td><td>VarInt</td><td>Block change sequence number (see #Acknowledge Block Change ).</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundPlayerActionPacket(int status, BlockPos position, int face, int sequence)
        implements ServerboundPacket {

    public static final int START_DESTROY_BLOCK = 0;
    public static final int ABORT_DESTROY_BLOCK = 1;
    public static final int FINISH_DESTROY_BLOCK = 2;

    public static ServerboundPlayerActionPacket read(PacketBuffer buf) {
        int status = buf.readVarInt();
        BlockPos position = buf.readPosition();
        int face = buf.readUByte();
        int sequence = buf.readVarInt();
        return new ServerboundPlayerActionPacket(status, position, face, sequence);
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handlePlayerAction(this);
    }
}
