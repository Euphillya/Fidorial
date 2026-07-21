package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.fidorial.world.BlockPos;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p><b>Packet ID:</b> Play = 40 (0x28)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Game_Test_Highlight_Position">Game Test Highlight Position</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Absolute Location</td><td>Position</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Relative Location</td><td>Position</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundGameTestHighlightPosPacket(BlockPos absoluteLocation,
                                                    BlockPos relativeLocation) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.GAME_TEST_HIGHLIGHT_POS;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writePosition(absoluteLocation.x(), absoluteLocation.y(), absoluteLocation.z());
        buf.writePosition(relativeLocation.x(), relativeLocation.y(), relativeLocation.z());
    }
}
