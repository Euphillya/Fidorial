package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.fidorial.world.BlockPos;

/**
 * <p>Possible actions: The vanilla client uses update data to indicate no special action should be taken (i.e. the done button).</p>
 *
 * <p><b>Packet ID:</b> Play = 59 (0x3B)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Program_Structure_Block">Program Structure Block</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Location</td><td>Position</td><td>Block entity location.</td></tr>
 *     <tr><td>1</td><td>Action</td><td>VarInt Enum</td><td>An additional action to perform beyond simply saving the given data; see below.</td></tr>
 *     <tr><td>2</td><td>Mode</td><td>VarInt Enum</td><td>One of SAVE (0), LOAD (1), CORNER (2), DATA (3).</td></tr>
 *     <tr><td>3</td><td>Name</td><td>String (32767)</td><td>&nbsp;</td></tr>
 *     <tr><td>4</td><td>Offset X</td><td>Byte</td><td>Between -48 and 48.</td></tr>
 *     <tr><td>5</td><td>Offset Y</td><td>Byte</td><td>Between -48 and 48.</td></tr>
 *     <tr><td>6</td><td>Offset Z</td><td>Byte</td><td>Between -48 and 48.</td></tr>
 *     <tr><td>7</td><td>Size X</td><td>Byte</td><td>Between 0 and 48.</td></tr>
 *     <tr><td>8</td><td>Size Y</td><td>Byte</td><td>Between 0 and 48.</td></tr>
 *     <tr><td>9</td><td>Size Z</td><td>Byte</td><td>Between 0 and 48.</td></tr>
 *     <tr><td>10</td><td>Mirror</td><td>VarInt Enum</td><td>One of NONE (0), LEFT_RIGHT (1), FRONT_BACK (2).</td></tr>
 *     <tr><td>11</td><td>Rotation</td><td>VarInt Enum</td><td>One of NONE (0), CLOCKWISE_90 (1), CLOCKWISE_180 (2), COUNTERCLOCKWISE_90 (3).</td></tr>
 *     <tr><td>12</td><td>Metadata</td><td>String (128)</td><td>&nbsp;</td></tr>
 *     <tr><td>13</td><td>Integrity</td><td>Float</td><td>Between 0 and 1.</td></tr>
 *     <tr><td>14</td><td>Seed</td><td>VarLong</td><td>&nbsp;</td></tr>
 *     <tr><td>15</td><td>Flags</td><td>Byte</td><td>0x01: Ignore entities; 0x02: Show air; 0x04: Show bounding box; 0x08: Strict placement.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundSetStructureBlockPacket(BlockPos location, Object action, Object mode, String name,
                                                 byte offsetX, byte offsetY, byte offsetZ, byte sizeX, byte sizeY,
                                                 byte sizeZ, Object mirror, Object rotation, String metadata,
                                                 float integrity, Object seed,
                                                 byte flags) implements ServerboundPacket {

    public static ServerboundSetStructureBlockPacket read(PacketBuffer buf) {
        BlockPos location = null;
        location = buf.readPosition();
        Object action = null; // TODO: read action (VarInt Enum)
        Object mode = null; // TODO: read mode (VarInt Enum)
        String name = null;
        name = buf.readString(32767);
        byte offsetX = (byte) 0;
        offsetX = buf.readByte();
        byte offsetY = (byte) 0;
        offsetY = buf.readByte();
        byte offsetZ = (byte) 0;
        offsetZ = buf.readByte();
        byte sizeX = (byte) 0;
        sizeX = buf.readByte();
        byte sizeY = (byte) 0;
        sizeY = buf.readByte();
        byte sizeZ = (byte) 0;
        sizeZ = buf.readByte();
        Object mirror = null; // TODO: read mirror (VarInt Enum)
        Object rotation = null; // TODO: read rotation (VarInt Enum)
        String metadata = null;
        metadata = buf.readString(32767);
        float integrity = 0f;
        integrity = buf.readFloat();
        Object seed = null; // TODO: read seed (VarLong)
        byte flags = (byte) 0;
        flags = buf.readByte();
        return new ServerboundSetStructureBlockPacket(location, action, mode, name, offsetX, offsetY, offsetZ, sizeX, sizeY, sizeZ, mirror, rotation, metadata, integrity, seed, flags);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
