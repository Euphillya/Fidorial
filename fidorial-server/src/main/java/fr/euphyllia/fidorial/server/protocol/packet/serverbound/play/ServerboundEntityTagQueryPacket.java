package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Used when F3 + I is pressed while looking at an entity.</p>
 *
 * <p><b>Packet ID:</b> Play = 25 (0x19)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Query_Entity_Tag">Query Entity Tag</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Transaction ID</td><td>VarInt</td><td>An incremental ID so that the client can verify that the response matches.</td></tr>
 *     <tr><td>1</td><td>Entity ID</td><td>VarInt</td><td>The ID of the entity to query.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundEntityTagQueryPacket(int transactionId, int entityId) implements ServerboundPacket {

    public static ServerboundEntityTagQueryPacket read(PacketBuffer buf) {
        int transactionId = 0;
        transactionId = buf.readVarInt();
        int entityId = 0;
        entityId = buf.readVarInt();
        return new ServerboundEntityTagQueryPacket(transactionId, entityId);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
