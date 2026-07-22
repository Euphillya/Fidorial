package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Time is based on ticks, where 20 ticks happen every second. There are 24000 ticks in a day, making Minecraft days exactly 20 minutes long. The time of day is based on the timestamp modulo 24000. 0 is sunrise, 6000 is noon, 12000 is sunset, and 18000 is midnight. The default SMP server increments the time by 20 every second.</p>
 *
 * <p><b>Packet ID:</b> Play = 113 (0x71)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Update_Time">Update Time</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>World Age</td><td>Long</td><td>In ticks; not changed by server commands.</td></tr>
 *     <tr><td>1</td><td>Clocks</td><td>Clock ID</td><td>Prefixed Array</td></tr>
 *     <tr><td>2</td><td>Time</td><td>VarLong</td><td>Current time of the clock, in ticks.</td></tr>
 *     <tr><td>3</td><td>Fractional time</td><td>Float</td><td>Fractional part of the time in ticks, normally a nonnegative number less than 1.</td></tr>
 *     <tr><td>4</td><td>Rate</td><td>Float</td><td>Rate at which the client should automatically advance the time of the clock, in clock ticks per client tick.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundSetTimePacket(long worldAge, Object clocks, Object time, float fractionalTime,
                                       float rate) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.SET_TIME;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeLong(worldAge);
        // TODO: write clocks (Clock ID)
        // TODO: write time (VarLong)
        buf.writeFloat(fractionalTime);
        buf.writeFloat(rate);
    }
}
