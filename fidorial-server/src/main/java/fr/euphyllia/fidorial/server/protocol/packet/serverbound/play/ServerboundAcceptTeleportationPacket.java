package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

/**
 * <p>Sent by client as confirmation of Synchronize Player Position .</p>
 *
 * <p><b>Packet ID:</b> Play = 0 (0x00)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Confirm_Teleportation">Confirm Teleportation</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Teleport ID</td><td>VarInt</td><td>The ID given by the Synchronize Player Position packet.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundAcceptTeleportationPacket(int teleportId) implements ServerboundPacket {

    public static ServerboundAcceptTeleportationPacket read(PacketBuffer buf) {
        return new ServerboundAcceptTeleportationPacket(buf.readVarInt());
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handleAcceptTeleportation(this);
    }
}
