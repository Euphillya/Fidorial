package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Sent by the server when an item in a slot (in a window) is added/removed. If Window ID is 0, the hotbar and offhand slots (slots 36 through 45) may be updated even when a different container window is open. (The vanilla server does not appear to utilize this special case.) Updates are also restricted to those slots when the player is looking at a creative inventory tab other than the survival inventory. (The vanilla server does not handle this restriction in any way, leading to MC-242392 .) When a container window is open, the server never sends updates targeting Window ID 0—all of the window types include slots for the player inventory. The client must automatically apply changes targeting the inventory portion of a container window to the main inventory; the server does not resend them for ID 0 when the window is closed. However, since the armor and offhand slots are only present on ID 0, updates to those slots occurring while a window is open must be deferred by the server until the window's closure.</p>
 *
 * <p><b>Packet ID:</b> Play = 20 (0x14)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Container_Slot">Set Container Slot</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Window ID</td><td>VarInt</td><td>The window that is being updated. 0 for player inventory. The client ignores any packets targeting a Window ID other than the current one; see below for exceptions.</td></tr>
 *     <tr><td>1</td><td>State ID</td><td>VarInt</td><td>A server-managed sequence number used to avoid desynchronization; see #Click Container .</td></tr>
 *     <tr><td>2</td><td>Slot</td><td>Short</td><td>The slot that should be updated.</td></tr>
 *     <tr><td>3</td><td>Slot Data</td><td>Slot</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundContainerSetSlotPacket(int windowId, int stateId, short slot,
                                                Object slotData) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.CONTAINER_SET_SLOT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(windowId);
        buf.writeVarInt(stateId);
        buf.writeShort(slot);
        // TODO: write slotData (Slot)
    }
}
