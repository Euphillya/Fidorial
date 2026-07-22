package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>This packet is used exclusively for opening the horse GUI. Open Screen is used for all other GUIs.</p>
 * <p>The client will not open the inventory if the Entity ID does not point to a horse-like animal.</p>
 *
 * <p><b>Packet ID:</b> Play = 41 (0x29)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Open_Horse_Screen">Open Horse Screen</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Window ID</td><td>VarInt</td><td>Same as the field of Open Screen .</td></tr>
 *     <tr><td>1</td><td>Inventory columns count</td><td>VarInt</td><td>How many columns of horse inventory slots exist in the GUI, 3 slots per column.</td></tr>
 *     <tr><td>2</td><td>Entity ID</td><td>Int</td><td>The "owner" entity of the GUI. The client should close the GUI if the owner entity dies or is cleared.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundMountScreenOpenPacket(int windowId, int inventoryColumnsCount,
                                               int entityId) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.MOUNT_SCREEN_OPEN;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(windowId);
        buf.writeVarInt(inventoryColumnsCount);
        buf.writeInt(entityId);
    }
}
