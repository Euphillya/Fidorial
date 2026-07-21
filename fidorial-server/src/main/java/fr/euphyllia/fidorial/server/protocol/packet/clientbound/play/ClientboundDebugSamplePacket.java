package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Sample data that is sent periodically after the client has subscribed with Debug Sample Subscription . The vanilla server only sends debug samples to players who are server operators. Types:</p>
 *
 * <p><b>Packet ID:</b> Play = 30 (0x1E)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Debug_Sample">Debug Sample</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Sample</td><td>Prefixed Array of Long</td><td>Array of type-dependent samples.</td></tr>
 *     <tr><td>1</td><td>Sample Type</td><td>VarInt Enum</td><td>See below.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundDebugSamplePacket(Object sample, Object sampleType) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.DEBUG_SAMPLE;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write sample (Prefixed Array of Long)
        // TODO: write sampleType (VarInt Enum)
    }
}
