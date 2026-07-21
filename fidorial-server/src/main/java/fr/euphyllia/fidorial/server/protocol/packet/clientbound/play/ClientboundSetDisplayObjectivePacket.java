package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>This is sent to the client when it should display a scoreboard.</p>
 *
 * <p><b>Packet ID:</b> Play = 98 (0x62)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Display_Objective">Display Objective</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Position</td><td>VarInt</td><td>The position of the scoreboard. 0: list, 1: sidebar, 2: below name, 3 - 18: team-specific sidebar, indexed as 3 + team color.</td></tr>
 *     <tr><td>1</td><td>Score Name</td><td>String (32767)</td><td>The unique name for the scoreboard to be displayed.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetDisplayObjectivePacket(int position, String scoreName) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_DISPLAY_OBJECTIVE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(position);
        buf.writeString(scoreName);
    }
}
