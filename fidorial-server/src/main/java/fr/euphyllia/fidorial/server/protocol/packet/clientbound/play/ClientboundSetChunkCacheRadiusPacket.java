package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Sent by the integrated singleplayer server when changing render distance.</p>
 * <p>This packet is sent by the server when the client reappears in the overworld after leaving the end.</p>
 *
 * <p><b>Packet ID:</b> Play = 95 (0x5F)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Render_Distance">Set Render Distance</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>View Distance</td><td>VarInt</td><td>Render distance (2-32).</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetChunkCacheRadiusPacket(int viewDistance) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_CHUNK_CACHE_RADIUS;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(viewDistance);
    }
}
