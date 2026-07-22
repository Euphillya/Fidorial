package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import net.kyori.adventure.text.Component;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p><b>Packet ID:</b> Play = 114 (0x72)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Title_Text">Set Title Text</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Title Text</td><td>Text Component</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetTitleTextPacket(Component titleText) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_TITLE_TEXT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeComponent(titleText);
    }
}
