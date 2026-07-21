package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p><b>Packet ID:</b> Configuration = 8 (0x08), Play = 80 (0x50)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Remove_Resource_Pack">Remove Resource Pack</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>UUID</td><td>Prefixed Optional UUID</td><td>The UUID of the resource pack to be removed. If not present, every resource pack will be removed.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundResourcePackPopPacket(Object uuid) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.RESOURCE_PACK_POP;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write uuid (Prefixed Optional UUID)
    }
}
