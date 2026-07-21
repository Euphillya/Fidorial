package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>This packet is sent from the client to the server when the client attacks another entity (a player, minecart, etc). A vanilla server only accepts this packet if the entity being attacked is visible without obstruction and within a 4-unit radius of the player's position.</p>
 *
 * <p><b>Packet ID:</b> Play = 1 (0x01)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Attack">Attack</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Entity ID</td><td>VarInt</td><td>The ID of the attacked entity. Note the special case of the ender dragon described on the Interact packet.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundAttackPacket(int entityId) implements ServerboundPacket {

    public static ServerboundAttackPacket read(PacketBuffer buf) {
        int entityId = 0;
        entityId = buf.readVarInt();
        return new ServerboundAttackPacket(entityId);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
