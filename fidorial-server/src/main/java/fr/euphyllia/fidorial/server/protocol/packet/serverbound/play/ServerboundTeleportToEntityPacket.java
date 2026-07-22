package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

import java.util.UUID;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Teleports the player to the given entity.</p>
 * <p>The player must be in spectator mode. The vanilla client only uses this to teleport to players, but it appears to accept any type of entity.</p>
 * <p>The entity does not need to be in the same dimension as the player; if necessary, the player will be respawned in the right world.</p>
 * <p>If the given entity cannot be found (or isn't loaded), this packet will be ignored.</p>
 *
 * <p><b>Packet ID:</b> Play = 64 (0x40)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Teleport_To_Entity">Teleport To Entity</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Target Player</td><td>UUID</td><td>UUID of the player to teleport to (can also be an entity UUID).</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundTeleportToEntityPacket(UUID targetPlayer) implements ServerboundPacket {

    public static ServerboundTeleportToEntityPacket read(PacketBuffer buf) {
        UUID targetPlayer = null;
        targetPlayer = buf.readUuid();
        return new ServerboundTeleportToEntityPacket(targetPlayer);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
