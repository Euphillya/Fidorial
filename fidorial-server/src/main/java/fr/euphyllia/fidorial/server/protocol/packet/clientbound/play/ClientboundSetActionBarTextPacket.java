package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import net.kyori.adventure.text.Component;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Displays a message above the hotbar. Equivalent to System Chat Message with Overlay set to true, except that chat message blocking isn't performed. Used by the vanilla server only to implement the /title command.</p>
 *
 * <p><b>Packet ID:</b> Play = 87 (0x57)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Action_Bar_Text">Set Action Bar Text</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Action bar text</td><td>Text Component</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetActionBarTextPacket(Component actionBarText) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_ACTION_BAR_TEXT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeComponent(actionBarText);
    }
}
