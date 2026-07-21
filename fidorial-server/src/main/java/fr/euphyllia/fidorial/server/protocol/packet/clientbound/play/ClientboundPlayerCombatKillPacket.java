package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import net.kyori.adventure.text.Component;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Used to send a respawn screen.</p>
 *
 * <p><b>Packet ID:</b> Play = 68 (0x44)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Combat_Death">Combat Death</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Player ID</td><td>VarInt</td><td>Entity ID of the player that died (should match the client's entity ID).</td></tr>
 *     <tr><td>1</td><td>Message</td><td>Text Component</td><td>The death message.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundPlayerCombatKillPacket(int playerId, Component message) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.PLAYER_COMBAT_KILL;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(playerId);
        buf.writeComponent(message);
    }
}
