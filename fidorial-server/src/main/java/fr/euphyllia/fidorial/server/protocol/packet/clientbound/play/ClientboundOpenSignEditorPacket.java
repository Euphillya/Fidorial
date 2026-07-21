package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.fidorial.world.BlockPos;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Sent when the client has placed a sign and is allowed to send Update Sign .</p>
 * <p>There must already be a sign at the given location (which the client does not do automatically) - send a Block Update first.</p>
 *
 * <p><b>Packet ID:</b> Play = 60 (0x3C)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Open_Sign_Editor">Open Sign Editor</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Location</td><td>Position</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Is Front Text</td><td>Boolean</td><td>Whether the opened editor is for the front or on the back of the sign</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundOpenSignEditorPacket(BlockPos location, boolean isFrontText) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.OPEN_SIGN_EDITOR;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writePosition(location.x(), location.y(), location.z());
        buf.writeBoolean(isFrontText);
    }
}
