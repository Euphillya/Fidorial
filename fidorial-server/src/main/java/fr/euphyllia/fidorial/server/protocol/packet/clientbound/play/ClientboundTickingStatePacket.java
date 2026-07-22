package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Used to adjust the ticking rate of the client, and whether it's frozen.</p>
 *
 * <p><b>Packet ID:</b> Play = 127 (0x7F)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Ticking_State">Set Ticking State</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Tick rate</td><td>Float</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Is frozen</td><td>Boolean</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundTickingStatePacket(float tickRate, boolean isFrozen) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.TICKING_STATE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeFloat(tickRate);
        buf.writeBoolean(isFrozen);
    }
}
