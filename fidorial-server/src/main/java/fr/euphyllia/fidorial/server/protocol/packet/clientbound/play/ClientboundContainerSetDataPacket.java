package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>This packet is used to inform the client that part of a GUI window should be updated. The meaning of the Property field depends on the type of the window. The following table shows the known combinations of window type and property, and how the value is to be interpreted. For an enchanting table, the following numerical IDs are used:</p>
 *
 * <p><b>Packet ID:</b> Play = 19 (0x13)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Container_Property">Set Container Property</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Window ID</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Property</td><td>Short</td><td>The property to be updated, see below.</td></tr>
 *     <tr><td>2</td><td>Value</td><td>Short</td><td>The new value for the property, see below.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundContainerSetDataPacket(int windowId, short property,
                                                short value) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.CONTAINER_SET_DATA;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(windowId);
        buf.writeShort(property);
        buf.writeShort(value);
    }
}
