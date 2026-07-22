package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p><b>Packet ID:</b> Play = 90 (0x5A)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Border_Size">Set Border Size</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Diameter</td><td>Double</td><td>Length of a single side of the world border, in meters.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetBorderSizePacket(double diameter) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_BORDER_SIZE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeDouble(diameter);
    }
}
