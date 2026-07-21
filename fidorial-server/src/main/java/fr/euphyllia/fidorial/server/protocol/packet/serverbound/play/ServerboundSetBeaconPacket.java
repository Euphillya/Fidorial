package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p>Changes the effect of the current beacon.</p>
 *
 * <p><b>Packet ID:</b> Play = 52 (0x34)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Beacon_Effect">Set Beacon Effect</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Primary Effect</td><td>Prefixed Optional VarInt</td><td>A Potion ID .</td></tr>
 *     <tr><td>1</td><td>Secondary Effect</td><td>Prefixed Optional VarInt</td><td>A Potion ID .</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundSetBeaconPacket(Object primaryEffect, Object secondaryEffect) implements ServerboundPacket {

    public static ServerboundSetBeaconPacket read(PacketBuffer buf) {
        Object primaryEffect = null; // TODO: read primaryEffect (Prefixed Optional VarInt)
        Object secondaryEffect = null; // TODO: read secondaryEffect (Prefixed Optional VarInt)
        return new ServerboundSetBeaconPacket(primaryEffect, secondaryEffect);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
