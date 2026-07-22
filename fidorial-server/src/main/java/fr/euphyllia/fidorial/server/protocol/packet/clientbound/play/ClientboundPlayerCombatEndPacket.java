package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Unused by the vanilla client.</p>
 * <p>This data was once used for twitch.tv metadata circa 1.8.</p>
 *
 * <p><b>Packet ID:</b> Play = 66 (0x42)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#End_Combat">End Combat</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Duration</td><td>VarInt</td><td>Length of the combat in ticks.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundPlayerCombatEndPacket(int duration) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.PLAYER_COMBAT_END;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(duration);
    }
}
