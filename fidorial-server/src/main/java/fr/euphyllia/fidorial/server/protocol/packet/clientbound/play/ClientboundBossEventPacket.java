package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

import java.util.UUID;

/**
 * <p><b>Packet ID:</b> Play = 9 (0x09)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Boss_Bar">Boss Bar</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>UUID</td><td>UUID</td><td>Unique ID for this bar.</td></tr>
 *     <tr><td>1</td><td>Action</td><td>VarInt Enum</td><td>Determines the layout of the remaining packet.</td></tr>
 *     <tr><td>2</td><td>Action</td><td>Field Name</td><td>&nbsp;</td></tr>
 *     <tr><td>3</td><td>0: add</td><td>Title</td><td>Text Component</td></tr>
 *     <tr><td>4</td><td>Health</td><td>Float</td><td>From 0 to 1. Values greater than 1 do not crash a vanilla client, and start rendering part of a second health bar at around 1.5.</td></tr>
 *     <tr><td>5</td><td>Color</td><td>VarInt Enum</td><td>Color ID (see below).</td></tr>
 *     <tr><td>6</td><td>Division</td><td>VarInt Enum</td><td>Type of division (see below).</td></tr>
 *     <tr><td>7</td><td>Flags</td><td>Unsigned Byte</td><td>Bit mask. 0x01: should darken sky, 0x02: is dragon bar (used to play end music), 0x04: create fog (previously was also controlled by 0x02).</td></tr>
 *     <tr><td>8</td><td>1: remove</td><td>no fields</td><td>no fields</td></tr>
 *     <tr><td>9</td><td>2: update health</td><td>Health</td><td>Float</td></tr>
 *     <tr><td>10</td><td>3: update title</td><td>Title</td><td>Text Component</td></tr>
 *     <tr><td>11</td><td>4: update style</td><td>Color</td><td>VarInt Enum</td></tr>
 *     <tr><td>12</td><td>Dividers</td><td>VarInt Enum</td><td>as above</td></tr>
 *     <tr><td>13</td><td>5: update flags</td><td>Flags</td><td>Unsigned Byte</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundBossEventPacket(UUID uuid, Object action, Object action2, Object _0Add, float health,
                                         Object color, Object division, int flags, Object _1Remove,
                                         Object _2UpdateHealth, Object _3UpdateTitle, Object _4UpdateStyle,
                                         Object dividers, Object _5UpdateFlags) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.BOSS_EVENT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeUuid(uuid);
        // TODO: write action (VarInt Enum)
        // TODO: write action2 (Field Name)
        // TODO: write _0Add (Title)
        buf.writeFloat(health);
        // TODO: write color (VarInt Enum)
        // TODO: write division (VarInt Enum)
        buf.writeByte(flags);
        // TODO: write _1Remove (no fields)
        // TODO: write _2UpdateHealth (Health)
        // TODO: write _3UpdateTitle (Title)
        // TODO: write _4UpdateStyle (Color)
        // TODO: write dividers (VarInt Enum)
        // TODO: write _5UpdateFlags (Flags)
    }
}
