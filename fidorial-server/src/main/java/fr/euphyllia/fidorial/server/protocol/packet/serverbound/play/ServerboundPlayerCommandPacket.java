package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p>Sent by the client to indicate that it has performed certain actions: sprinting, exiting a bed, jumping with a horse, and opening a horse's inventory while riding it. Action ID can be one of the following values: Leave bed is only sent when the “Leave Bed” button is clicked on the sleep GUI, not when waking up in the morning. Open vehicle inventory is only sent when pressing the inventory key (default: E) while on a horse or chest boat — all other methods of opening such an inventory (involving right-clicking or shift-right-clicking it) do not use this packet.</p>
 *
 * <p><b>Packet ID:</b> Play = 42 (0x2A)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Player_Command">Player Command</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Entity ID</td><td>VarInt</td><td>Player ID (ignored by the vanilla server)</td></tr>
 *     <tr><td>1</td><td>Action ID</td><td>VarInt Enum</td><td>The ID of the action, see below.</td></tr>
 *     <tr><td>2</td><td>Jump Boost</td><td>VarInt</td><td>Only used by the “start jump with horse” action, in which case it ranges from 0 to 100. In all other cases it is 0.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundPlayerCommandPacket(int entityId, Object actionId,
                                             int jumpBoost) implements ServerboundPacket {

    public static ServerboundPlayerCommandPacket read(PacketBuffer buf) {
        int entityId = 0;
        entityId = buf.readVarInt();
        Object actionId = null; // TODO: read actionId (VarInt Enum)
        int jumpBoost = 0;
        jumpBoost = buf.readVarInt();
        return new ServerboundPlayerCommandPacket(entityId, actionId, jumpBoost);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
