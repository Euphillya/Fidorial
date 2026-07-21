package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import net.kyori.adventure.text.Component;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>This is sent to the client when it should open an inventory, such as a chest, workbench, furnace, or other container. Resending this packet with the already existing window ID, will update the window title and window type without closing the window. This message is not sent to clients opening their own inventory, nor do clients inform the server in any way when doing so. From the server's perspective, the inventory is always "open" whenever no other windows are. For horses, use Open Horse Screen .</p>
 *
 * <p><b>Packet ID:</b> Play = 59 (0x3B)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Open_Screen">Open Screen</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Window ID</td><td>VarInt</td><td>An identifier for the window to be displayed. The vanilla server implementation is a counter, starting at 1. There can only be one window at a time; this is only used to ignore outdated packets targeting already-closed windows. Note also that the Window ID field in most other packets is only a single byte, and indeed, the vanilla server wraps around after 100.</td></tr>
 *     <tr><td>1</td><td>Window Type</td><td>VarInt</td><td>The window type to use for display. Contained in the minecraft:menu registry; see Java Edition protocol/Inventory for the different values.</td></tr>
 *     <tr><td>2</td><td>Window Title</td><td>Text Component</td><td>The title of the window.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundOpenScreenPacket(int windowId, int windowType,
                                          Component windowTitle) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.OPEN_SCREEN;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(windowId);
        buf.writeVarInt(windowType);
        buf.writeComponent(windowTitle);
    }
}
