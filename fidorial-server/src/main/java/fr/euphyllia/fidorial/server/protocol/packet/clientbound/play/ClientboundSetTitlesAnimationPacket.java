package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p><b>Packet ID:</b> Play = 115 (0x73)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Title_Animation_Times">Set Title Animation Times</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Fade In</td><td>Int</td><td>Ticks to spend fading in.</td></tr>
 *     <tr><td>1</td><td>Stay</td><td>Int</td><td>Ticks to keep the title displayed.</td></tr>
 *     <tr><td>2</td><td>Fade Out</td><td>Int</td><td>Ticks to spend fading out, not when to start fading out.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetTitlesAnimationPacket(int fadeIn, int stay, int fadeOut) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_TITLES_ANIMATION;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeInt(fadeIn);
        buf.writeInt(stay);
        buf.writeInt(fadeOut);
    }
}
