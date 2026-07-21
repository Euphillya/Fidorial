package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

/**
 * <p>Updates a rectangular area on a map item. For icons, a direction of 0 is a vertical icon and increments by 22.5° (360/16). Types are based off of rows and columns in map_icons.png :</p>
 *
 * <p><b>Packet ID:</b> Play = 51 (0x33)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Map_Data">Map Data</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Map ID</td><td>VarInt</td><td>Map ID of the map being modified</td></tr>
 *     <tr><td>1</td><td>Scale</td><td>Byte</td><td>From 0 for a fully zoomed-in map (1 block per pixel) to 4 for a fully zoomed-out map (16 blocks per pixel)</td></tr>
 *     <tr><td>2</td><td>Locked</td><td>Boolean</td><td>True if the map has been locked in a cartography table</td></tr>
 *     <tr><td>3</td><td>Icons</td><td>Type</td><td>Prefixed Optional Prefixed Array</td></tr>
 *     <tr><td>4</td><td>X</td><td>Byte</td><td>Map coordinates: -128 for furthest left, +127 for furthest right</td></tr>
 *     <tr><td>5</td><td>Z</td><td>Byte</td><td>Map coordinates: -128 for highest, +127 for lowest</td></tr>
 *     <tr><td>6</td><td>Direction</td><td>Byte</td><td>0-15</td></tr>
 *     <tr><td>7</td><td>Display Name</td><td>Prefixed Optional Text Component</td><td>&nbsp;</td></tr>
 *     <tr><td>8</td><td>Color Patch</td><td>Columns</td><td>Unsigned Byte</td></tr>
 *     <tr><td>9</td><td>Rows</td><td>Optional Unsigned Byte</td><td>Only if Columns is more than 0; number of rows updated</td></tr>
 *     <tr><td>10</td><td>X</td><td>Optional Unsigned Byte</td><td>Only if Columns is more than 0; x offset of the westernmost column</td></tr>
 *     <tr><td>11</td><td>Z</td><td>Optional Unsigned Byte</td><td>Only if Columns is more than 0; z offset of the northernmost row</td></tr>
 *     <tr><td>12</td><td>Data</td><td>Optional Prefixed Array of Unsigned Byte</td><td>Only if Columns is more than 0; see Map item format</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundMapItemDataPacket(int mapId, byte scale, boolean locked, Object icons, byte x, byte z,
                                           byte direction, Object displayName, Object colorPatch, Object rows,
                                           Object x2, Object z2, Object data) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.MAP_ITEM_DATA;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(mapId);
        buf.writeByte(scale);
        buf.writeBoolean(locked);
        // TODO: write icons (Type)
        buf.writeByte(x);
        buf.writeByte(z);
        buf.writeByte(direction);
        // TODO: write displayName (Prefixed Optional Text Component)
        // TODO: write colorPatch (Columns)
        // TODO: write rows (Optional Unsigned Byte)
        // TODO: write x2 (Optional Unsigned Byte)
        // TODO: write z2 (Optional Unsigned Byte)
        // TODO: write data (Optional Prefixed Array of Unsigned Byte)
    }
}
