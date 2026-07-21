package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Applies a cooldown period to all items with the given type.</p>
 * <p>Used by the vanilla server with enderpearls.</p>
 * <p>This packet should be sent when the cooldown starts and also when the cooldown ends (to compensate for lag), although the client will end the cooldown automatically. Can be applied to any item, note that interactions still get sent to the server with the item, but the client does not play the animation nor attempt to predict results (i.e, block placing).</p>
 *
 * <p><b>Packet ID:</b> Play = 22 (0x16)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Cooldown">Set Cooldown</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Cooldown Group</td><td>Identifier</td><td>Identifier of the item (minecraft:stone) or the cooldown group ("use_cooldown" item component)</td></tr>
 *     <tr><td>1</td><td>Cooldown Ticks</td><td>VarInt</td><td>Number of ticks to apply a cooldown for, or 0 to clear the cooldown.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundCooldownPacket(String cooldownGroup, int cooldownTicks) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.COOLDOWN;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeIdentifier(cooldownGroup);
        buf.writeVarInt(cooldownTicks);
    }
}
