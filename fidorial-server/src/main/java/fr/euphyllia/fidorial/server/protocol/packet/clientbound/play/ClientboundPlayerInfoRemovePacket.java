package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Sent by the server to remove players from the client's player list.</p>
 *
 * <p><b>Packet ID:</b> Play = 69 (0x45)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Player_Info_Remove">Player Info Remove</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>UUIDs</td><td>Prefixed Array of UUID</td><td>UUIDs of players to remove.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundPlayerInfoRemovePacket(Object uuids) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.PLAYER_INFO_REMOVE;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write uuids (Prefixed Array of UUID)
    }
}
