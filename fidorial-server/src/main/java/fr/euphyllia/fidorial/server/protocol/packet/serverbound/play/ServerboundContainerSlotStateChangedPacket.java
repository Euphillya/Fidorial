package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>This packet is sent by the client when toggling the state of a Crafter.</p>
 *
 * <p><b>Packet ID:</b> Play = 20 (0x14)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Change_Container_Slot_State">Change Container Slot State</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Slot ID</td><td>VarInt</td><td>This is the ID of the slot that was changed.</td></tr>
 *     <tr><td>1</td><td>Window ID</td><td>VarInt</td><td>This is the ID of the window that was changed.</td></tr>
 *     <tr><td>2</td><td>State</td><td>Boolean</td><td>The new state of the slot. True for enabled, false for disabled.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundContainerSlotStateChangedPacket(int slotId, int windowId,
                                                         boolean state) implements ServerboundPacket {

    public static ServerboundContainerSlotStateChangedPacket read(PacketBuffer buf) {
        int slotId = 0;
        slotId = buf.readVarInt();
        int windowId = 0;
        windowId = buf.readVarInt();
        boolean state = false;
        state = buf.readBoolean();
        return new ServerboundContainerSlotStateChangedPacket(slotId, windowId, state);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
