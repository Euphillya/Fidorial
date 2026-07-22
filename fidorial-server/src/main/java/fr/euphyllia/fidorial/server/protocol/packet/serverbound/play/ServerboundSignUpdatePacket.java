package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.fidorial.world.BlockPos;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>This message is sent from the client to the server when the “Done” button is pushed after placing a sign. The server only accepts this packet after Open Sign Editor , otherwise this packet is silently ignored.</p>
 *
 * <p><b>Packet ID:</b> Play = 61 (0x3D)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Update_Sign">Update Sign</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Location</td><td>Position</td><td>Block Coordinates.</td></tr>
 *     <tr><td>1</td><td>Is Front Text</td><td>Boolean</td><td>Whether the updated text is in front or on the back of the sign</td></tr>
 *     <tr><td>2</td><td>Line 1</td><td>String (384)</td><td>First line of text in the sign.</td></tr>
 *     <tr><td>3</td><td>Line 2</td><td>String (384)</td><td>Second line of text in the sign.</td></tr>
 *     <tr><td>4</td><td>Line 3</td><td>String (384)</td><td>Third line of text in the sign.</td></tr>
 *     <tr><td>5</td><td>Line 4</td><td>String (384)</td><td>Fourth line of text in the sign.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundSignUpdatePacket(BlockPos location, boolean isFrontText, String line1, String line2,
                                          String line3, String line4) implements ServerboundPacket {

    public static ServerboundSignUpdatePacket read(PacketBuffer buf) {
        BlockPos location = null;
        location = buf.readPosition();
        boolean isFrontText = false;
        isFrontText = buf.readBoolean();
        String line1 = null;
        line1 = buf.readString(32767);
        String line2 = null;
        line2 = buf.readString(32767);
        String line3 = null;
        line3 = buf.readString(32767);
        String line4 = null;
        line4 = buf.readString(32767);
        return new ServerboundSignUpdatePacket(location, isFrontText, line1, line2, line3, line4);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
