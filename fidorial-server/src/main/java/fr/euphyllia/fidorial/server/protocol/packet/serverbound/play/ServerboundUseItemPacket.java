package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p>Sent when pressing the Use Item key (default: right click) with an item in hand. The player's rotation is permanently updated according to the Yaw and Pitch fields before performing the action, unless there is no item in the specified hand.</p>
 *
 * <p><b>Packet ID:</b> Play = 67 (0x43)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Use_Item">Use Item</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Hand</td><td>VarInt Enum</td><td>Hand used for the animation. 0: main hand, 1: off hand.</td></tr>
 *     <tr><td>1</td><td>Sequence</td><td>VarInt</td><td>Block change sequence number (see #Acknowledge Block Change ).</td></tr>
 *     <tr><td>2</td><td>Yaw</td><td>Float</td><td>Player head rotation around the Y-Axis.</td></tr>
 *     <tr><td>3</td><td>Pitch</td><td>Float</td><td>Player head rotation around the X-Axis.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundUseItemPacket(Object hand, int sequence, float yaw, float pitch) implements ServerboundPacket {

    public static ServerboundUseItemPacket read(PacketBuffer buf) {
        Object hand = null; // TODO: read hand (VarInt Enum)
        int sequence = 0;
        sequence = buf.readVarInt();
        float yaw = 0f;
        yaw = buf.readFloat();
        float pitch = 0f;
        pitch = buf.readFloat();
        return new ServerboundUseItemPacket(hand, sequence, yaw, pitch);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
