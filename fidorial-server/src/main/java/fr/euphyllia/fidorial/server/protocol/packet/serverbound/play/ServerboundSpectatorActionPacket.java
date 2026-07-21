package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Sent when the player left-clicks in spectator mode.</p>
 *
 * <p><b>Packet ID:</b> Play = 62 (0x3E)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Spectator_Action">Spectator Action</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Entity ID</td><td>VarInt</td><td>If 0, the player was not targeting an entity. Otherwise the ID of the targeted entity plus 1.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundSpectatorActionPacket(int entityId) implements ServerboundPacket {

    public static ServerboundSpectatorActionPacket read(PacketBuffer buf) {
        int entityId = 0;
        entityId = buf.readVarInt();
        return new ServerboundSpectatorActionPacket(entityId);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
