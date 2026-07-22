package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Sets the entity that the player renders from. This is normally used when the player left-clicks an entity while in spectator mode. The player's camera will move with the entity and look where it is looking. The entity is often another player, but can be any type of entity.</p>
 * <p>The player is unable to move this entity (move packets will act as if they are coming from the other entity). If the given entity is not loaded by the player, this packet is ignored.</p>
 * <p>To return control to the player, send this packet with their entity ID. The vanilla server resets this (sends it back to the default entity) whenever the spectated entity is killed or the player sneaks, but only if they were spectating an entity. It also sends this packet whenever the player switches out of spectator mode (even if they weren't spectating an entity). The vanilla client also loads certain shaders for given entities:</p>
 *
 * <p><b>Packet ID:</b> Play = 93 (0x5D)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Camera">Set Camera</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Camera ID</td><td>VarInt</td><td>ID of the entity to set the client's camera to.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetCameraPacket(int cameraId) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_CAMERA;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(cameraId);
    }
}
