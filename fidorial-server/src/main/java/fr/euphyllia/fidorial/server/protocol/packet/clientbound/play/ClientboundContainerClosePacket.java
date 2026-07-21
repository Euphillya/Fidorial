package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>This packet is sent from the server to the client when a window is forcibly closed, such as when a chest is destroyed while it's open. The vanilla client disregards the provided window ID and closes any active window.</p>
 *
 * <p><b>Packet ID:</b> Play = 17 (0x11)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Close_Container">Close Container</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Window ID</td><td>VarInt</td><td>This is the ID of the window that was closed. 0 for inventory.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundContainerClosePacket(int windowId) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.CONTAINER_CLOSE;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(windowId);
    }
}
