package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 *
 * <p>Sent as a response to Client Status (id 1). Will only send the changed values if previously requested. Categories (defined in the minecraft:stat_type registry). Blocks, Items, and Entities use block (not block state), item, and entity ids. Custom uses IDs in the minecraft:custom_stat registry: Units:</p>
 *
 * <p><b>Packet ID:</b> Play = 3 (0x03)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Award_Statistics">Award Statistics</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Statistics</td><td>Category ID</td><td>Prefixed Array</td></tr>
 *     <tr><td>1</td><td>Statistic ID</td><td>VarInt</td><td>See below.</td></tr>
 *     <tr><td>2</td><td>Value</td><td>VarInt</td><td>The amount to set it to.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundAwardStatsPacket(Object statistics, int statisticId, int value) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.AWARD_STATS;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write statistics (Category ID)
        buf.writeVarInt(statisticId);
        buf.writeVarInt(value);
    }
}
