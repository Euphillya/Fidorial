package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Adds, removes, or updates an entry that will be tracked on the player locator bar.</p>
 *
 * <p><b>Packet ID:</b> Play = 138 (0x8A)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Waypoint">Waypoint</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Operation</td><td>VarInt Enum</td><td>0: track, 1: untrack, 2: update.</td></tr>
 *     <tr><td>1</td><td>Identifier</td><td>Either Uuid or String</td><td>Something that uniquely identifies this specific waypoint.</td></tr>
 *     <tr><td>2</td><td>Icon style</td><td>Identifier</td><td>Path to the waypoint style JSON: assets/&lt;namespace&gt;/waypoint_style/&lt;value&gt;.json.</td></tr>
 *     <tr><td>3</td><td>Color</td><td>&nbsp;</td><td>&nbsp;</td></tr>
 *     <tr><td>4</td><td>Red</td><td>Prefixed Optional</td><td>Unsigned Byte</td></tr>
 *     <tr><td>5</td><td>Green</td><td>Unsigned Byte</td><td>&nbsp;</td></tr>
 *     <tr><td>6</td><td>Blue</td><td>Unsigned Byte</td><td>&nbsp;</td></tr>
 *     <tr><td>7</td><td>Waypoint type</td><td>VarInt Enum</td><td>Defines how the following field is read.</td></tr>
 *     <tr><td>8</td><td>Waypoint data</td><td>Varies</td><td>Type Field Name Field Data Notes 0: Empty no fields 1: Vec3i X VarInt The position that the waypoint will point to. Y VarInt Z VarInt 2: Chunk X VarInt The chunk coordinates that the waypoint will point to. Z VarInt 3: Azimuth Angle Float The angle that will be pointed to, in radians.</td></tr>
 *     <tr><td>9</td><td>Type</td><td>Field Name</td><td>Field Data</td></tr>
 *     <tr><td>10</td><td>0: Empty</td><td>no fields</td><td>&nbsp;</td></tr>
 *     <tr><td>11</td><td>1: Vec3i</td><td>X</td><td>VarInt</td></tr>
 *     <tr><td>12</td><td>Y</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>13</td><td>Z</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>14</td><td>2: Chunk</td><td>X</td><td>VarInt</td></tr>
 *     <tr><td>15</td><td>Z</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>16</td><td>3: Azimuth</td><td>Angle</td><td>Float</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundWaypointPacket(Object operation, Object identifier, String iconStyle, Object color, Object red,
                                        int green, int blue, Object waypointType, Object waypointData, Object type,
                                        Object _0Empty, Object _1Vec3i, int y, int z, Object _2Chunk, int z2,
                                        Object _3Azimuth) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.WAYPOINT;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write operation (VarInt Enum)
        // TODO: write identifier (Either Uuid or String)
        buf.writeIdentifier(iconStyle);
        // TODO: write color ()
        // TODO: write red (Prefixed Optional)
        buf.writeByte(green);
        buf.writeByte(blue);
        // TODO: write waypointType (VarInt Enum)
        // TODO: write waypointData (Varies)
        // TODO: write type (Field Name)
        // TODO: write _0Empty (no fields)
        // TODO: write _1Vec3i (X)
        buf.writeVarInt(y);
        buf.writeVarInt(z);
        // TODO: write _2Chunk (X)
        buf.writeVarInt(z2);
        // TODO: write _3Azimuth (Angle)
    }
}
