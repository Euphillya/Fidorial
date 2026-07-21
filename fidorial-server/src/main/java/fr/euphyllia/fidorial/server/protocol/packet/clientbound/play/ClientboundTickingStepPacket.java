package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Advances the client processing by the specified number of ticks. Has no effect unless client ticking is frozen.</p>
 *
 * <p><b>Packet ID:</b> Play = 128 (0x80)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Step_Tick">Step Tick</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Tick steps</td><td>VarInt</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundTickingStepPacket(int tickSteps) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.TICKING_STEP;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(tickSteps);
    }
}
