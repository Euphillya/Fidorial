package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Sent by the server when the client should change experience levels.</p>
 *
 * <p><b>Packet ID:</b> Play = 103 (0x67)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Experience">Set Experience</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Experience bar</td><td>Float</td><td>Between 0 and 1.</td></tr>
 *     <tr><td>1</td><td>Level</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>2</td><td>Total Experience</td><td>VarInt</td><td>See Experience#Leveling up on the Minecraft Wiki for Total Experience to Level conversion.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetExperiencePacket(float experienceBar, int level,
                                             int totalExperience) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_EXPERIENCE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeFloat(experienceBar);
        buf.writeVarInt(level);
        buf.writeVarInt(totalExperience);
    }
}
