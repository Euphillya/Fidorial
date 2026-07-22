package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import net.kyori.adventure.text.Component;

/**
 * <p>Updates the status of the currently open Test Instance Block screen, if any.</p>
 *
 * <p><b>Packet ID:</b> Play = 126 (0x7E)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Test_Instance_Block_Status">Test Instance Block Status</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Status</td><td>Text Component</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Has Size</td><td>Boolean</td><td>&nbsp;</td></tr>
 *     <tr><td>2</td><td>Size X</td><td>Optional Double</td><td>Only present if Has Size is true.</td></tr>
 *     <tr><td>3</td><td>Size Y</td><td>Optional Double</td><td>Only present if Has Size is true.</td></tr>
 *     <tr><td>4</td><td>Size Z</td><td>Optional Double</td><td>Only present if Has Size is true.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundTestInstanceBlockStatusPacket(Component status, boolean hasSize, Object sizeX, Object sizeY,
                                                       Object sizeZ) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.TEST_INSTANCE_BLOCK_STATUS;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeComponent(status);
        buf.writeBoolean(hasSize);
        // TODO: write sizeX (Optional Double)
        // TODO: write sizeY (Optional Double)
        // TODO: write sizeZ (Optional Double)
    }
}
