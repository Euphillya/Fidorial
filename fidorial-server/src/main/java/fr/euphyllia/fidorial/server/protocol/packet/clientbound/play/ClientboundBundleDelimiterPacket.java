package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>The delimiter for a bundle of packets. When received, the client should store every subsequent packet it receives and wait until another delimiter is received. Once that happens, the client is guaranteed to process every packet in the bundle on the same tick, and the client should stop storing packets. As of 1.20.6, the vanilla server only uses this to ensure Spawn Entity and associated packets used to configure the entity happen on the same tick. Each entity gets a separate bundle. The vanilla client doesn't allow more than 4096 packets in the same bundle.</p>
 *
 * <p><b>Packet ID:</b> Play = 0 (0x00)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Bundle_Delimiter">Bundle Delimiter</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>no fields</td><td>&nbsp;</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundBundleDelimiterPacket(Object noFields) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.BUNDLE_DELIMITER;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write noFields ()
    }
}
