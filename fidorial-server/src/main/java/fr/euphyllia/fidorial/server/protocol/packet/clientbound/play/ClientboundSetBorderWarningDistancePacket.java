package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p><b>Packet ID:</b> Play = 92 (0x5C)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Border_Warning_Distance">Set Border Warning Distance</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Warning Blocks</td><td>VarInt</td><td>In meters.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetBorderWarningDistancePacket(int warningBlocks) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_BORDER_WARNING_DISTANCE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(warningBlocks);
    }
}
