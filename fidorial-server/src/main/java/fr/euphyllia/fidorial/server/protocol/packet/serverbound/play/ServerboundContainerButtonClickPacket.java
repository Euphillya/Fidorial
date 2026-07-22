package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Used when clicking on window buttons. Until 1.14, this was only used by enchantment tables.</p>
 *
 * <p><b>Packet ID:</b> Play = 17 (0x11)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Click_Container_Button">Click Container Button</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Window ID</td><td>VarInt</td><td>The ID of the window sent by Open Screen .</td></tr>
 *     <tr><td>1</td><td>Button ID</td><td>VarInt</td><td>Meaning depends on window type; see below.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundContainerButtonClickPacket(int windowId, int buttonId) implements ServerboundPacket {

    public static ServerboundContainerButtonClickPacket read(PacketBuffer buf) {
        int windowId = 0;
        windowId = buf.readVarInt();
        int buttonId = 0;
        buttonId = buf.readVarInt();
        return new ServerboundContainerButtonClickPacket(windowId, buttonId);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
