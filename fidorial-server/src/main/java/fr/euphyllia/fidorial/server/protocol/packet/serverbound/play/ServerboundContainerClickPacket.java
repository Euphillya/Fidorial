package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p>This packet is sent by the client when the player clicks on a slot in a window. See Java Edition protocol/Inventory for further information about how slots are indexed. After performing the action, the server compares the results to the slot change information included in the packet, as applied on top of the server's view of the container's state prior to the action. For any slots that do not match, it sends Set Container Slot packets containing the correct results. If State ID does not match the last ID sent by the server, it will instead send a full Set Container Content to resynchronize the client. When right-clicking on a stack of items, half the stack will be picked up and half left in the slot. If the stack is an odd number, the half left in the slot will be the smaller of the amounts. The distinct type of click performed by the client is determined by the combination of the Mode and Button fields. Starting from version 1.5, “painting mode” is available for use in inventory windows. It is done by picking up a stack of something (more than 1 item), then holding the mouse button (left, right, or middle) and dragging the held stack over empty (or same type in case of right button) slots. In that case client sends the following to the server after the mouse button release (omitting the first pickup packet, which is sent as usual): If any of the painting packets other than the “progress” ones are sent out of order (for example, a start, some slots, then another start; or a left-click in the middle) the painting status will be reset.</p>
 *
 * <p><b>Packet ID:</b> Play = 18 (0x12)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Click_Container">Click Container</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Window ID</td><td>VarInt</td><td>The ID of the window that was clicked. 0 for player inventory. The server ignores any packets targeting a Window ID other than the current one, including ignoring 0 when any other window is open.</td></tr>
 *     <tr><td>1</td><td>State ID</td><td>VarInt</td><td>The last received State ID from either a Set Container Slot or a Set Container Content packet.</td></tr>
 *     <tr><td>2</td><td>Slot</td><td>Short</td><td>The clicked slot number, see below.</td></tr>
 *     <tr><td>3</td><td>Button</td><td>Byte</td><td>The button used in the click, see below.</td></tr>
 *     <tr><td>4</td><td>Mode</td><td>VarInt Enum</td><td>Inventory operation mode, see below.</td></tr>
 *     <tr><td>5</td><td>Array of changed slots</td><td>Slot number</td><td>Prefixed Array (128)</td></tr>
 *     <tr><td>6</td><td>Slot data</td><td>Hashed Slot</td><td>New data for this slot, in the client's opinion; see below.</td></tr>
 *     <tr><td>7</td><td>Carried item</td><td>Hashed Slot</td><td>Item carried by the cursor.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundContainerClickPacket(int windowId, int stateId, short slot, byte button, Object mode,
                                              Object arrayOfChangedSlots, Object slotData,
                                              Object carriedItem) implements ServerboundPacket {

    public static ServerboundContainerClickPacket read(PacketBuffer buf) {
        int windowId = 0;
        windowId = buf.readVarInt();
        int stateId = 0;
        stateId = buf.readVarInt();
        short slot = (short) 0;
        slot = buf.readShort();
        byte button = (byte) 0;
        button = buf.readByte();
        Object mode = null; // TODO: read mode (VarInt Enum)
        Object arrayOfChangedSlots = null; // TODO: read arrayOfChangedSlots (Slot number)
        Object slotData = null; // TODO: read slotData (Hashed Slot)
        Object carriedItem = null; // TODO: read carriedItem (Hashed Slot)
        return new ServerboundContainerClickPacket(windowId, stateId, slot, button, mode, arrayOfChangedSlots, slotData, carriedItem);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
