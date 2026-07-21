package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>This is sent to the client when it should remove a scoreboard item.</p>
 *
 * <p><b>Packet ID:</b> Play = 79 (0x4F)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Reset_Score">Reset Score</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Entity Name</td><td>String (32767)</td><td>The entity whose score this is. For players, this is their username; for other entities, it is their UUID.</td></tr>
 *     <tr><td>1</td><td>Objective Name</td><td>Prefixed Optional String (32767)</td><td>The name of the objective the score belongs to.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundResetScorePacket(String entityName, Object objectiveName) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.RESET_SCORE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeString(entityName);
        // TODO: write objectiveName (Prefixed Optional String)
    }
}
