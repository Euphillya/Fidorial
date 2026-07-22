package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Within flags:</p>
 *
 * <p><b>Packet ID:</b> Play = 132 (0x84)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Entity_Effect">Entity Effect</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Entity ID</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Effect ID</td><td>VarInt</td><td>See this table .</td></tr>
 *     <tr><td>2</td><td>Amplifier</td><td>VarInt</td><td>Vanilla client displays effect level as Amplifier + 1.</td></tr>
 *     <tr><td>3</td><td>Duration</td><td>VarInt</td><td>Duration in ticks. (-1 for infinite)</td></tr>
 *     <tr><td>4</td><td>Flags</td><td>Byte</td><td>Bit field, see below.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundUpdateMobEffectPacket(int entityId, int effectId, int amplifier, int duration,
                                               byte flags) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.UPDATE_MOB_EFFECT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(entityId);
        buf.writeVarInt(effectId);
        buf.writeVarInt(amplifier);
        buf.writeVarInt(duration);
        buf.writeByte(flags);
    }
}
