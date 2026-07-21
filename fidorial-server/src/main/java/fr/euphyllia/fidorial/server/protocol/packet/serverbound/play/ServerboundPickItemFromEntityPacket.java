package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Used for pick block functionality (middle click) on entities to retrieve items from the inventory in survival or creative mode or create them in creative mode.</p>
 * <p>See Controls#Pick Block for more information.</p>
 *
 * <p><b>Packet ID:</b> Play = 37 (0x25)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Pick_Item_From_Entity">Pick Item From Entity</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Entity ID</td><td>VarInt</td><td>The ID of the entity to pick.</td></tr>
 *     <tr><td>1</td><td>Include Data</td><td>Boolean</td><td>Unused by the vanilla server.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundPickItemFromEntityPacket(int entityId, boolean includeData) implements ServerboundPacket {

    public static ServerboundPickItemFromEntityPacket read(PacketBuffer buf) {
        int entityId = 0;
        entityId = buf.readVarInt();
        boolean includeData = false;
        includeData = buf.readBoolean();
        return new ServerboundPickItemFromEntityPacket(entityId, includeData);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
