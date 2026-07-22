package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import net.kyori.adventure.text.Component;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>This packet may be used by custom servers to display additional information above/below the tab list. It is never sent by the vanilla server.</p>
 *
 * <p><b>Packet ID:</b> Play = 122 (0x7A)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Tab_List_Header_And_Footer">Set Tab List Header And Footer</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Header</td><td>Text Component</td><td>To remove the header, send an empty text component: {"text":""} .</td></tr>
 *     <tr><td>1</td><td>Footer</td><td>Text Component</td><td>To remove the footer, send an empty text component: {"text":""} .</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundTabListPacket(Component header, Component footer) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.TAB_LIST;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeComponent(header);
        buf.writeComponent(footer);
    }
}
