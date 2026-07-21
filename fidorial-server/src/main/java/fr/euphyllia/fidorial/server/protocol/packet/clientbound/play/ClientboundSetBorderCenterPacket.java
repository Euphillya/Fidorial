package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p><b>Packet ID:</b> Play = 88 (0x58)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Border_Center">Set Border Center</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>X</td><td>Double</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Z</td><td>Double</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetBorderCenterPacket(double x, double z) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_BORDER_CENTER;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeDouble(x);
        buf.writeDouble(z);
    }
}
