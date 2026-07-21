package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;
import fr.fidorial.world.BlockPos;

/**
 * <p>Upon placing a block, this packet is sent once. The Cursor Position X/Y/Z fields (also known as in-block coordinates) are calculated using raytracing. The unit corresponds to sixteen pixels in the default resource pack. For example, let's say a slab is being placed against the south face of a full block. The Cursor Position X will be higher if the player was pointing near the right (east) edge of the face, lower if pointing near the left. The Cursor Position Y will be used to determine whether it will appear as a bottom slab (values 0.0–0.5) or as a top slab (values 0.5-1.0). The Cursor Position Z should be 1.0 since the player was looking at the southernmost part of the block. Inside block is true when a player's head (specifically eyes) are inside of a block's collision. In 1.13 and later versions, collision is rather complicated and individual blocks can have multiple collision boxes. For instance, a ring of vines has a non-colliding hole in the middle. This value is only true when the player is directly in the box. In practice, though, this value is only used by scaffolding to place in front of the player when sneaking inside of it (other blocks will place behind when you intersect with them -- try with glass for instance).</p>
 *
 * <p><b>Packet ID:</b> Play = 66 (0x42)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Use_Item_On">Use Item On</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Hand</td><td>VarInt Enum</td><td>The hand from which the block is placed; 0: main hand, 1: off hand.</td></tr>
 *     <tr><td>1</td><td>Location</td><td>Position</td><td>Block position.</td></tr>
 *     <tr><td>2</td><td>Face</td><td>VarInt Enum</td><td>The face on which the block is placed (as documented at Player Action ).</td></tr>
 *     <tr><td>3</td><td>Cursor Position X</td><td>Float</td><td>The position of the crosshair on the block, from 0 to 1 increasing from west to east.</td></tr>
 *     <tr><td>4</td><td>Cursor Position Y</td><td>Float</td><td>The position of the crosshair on the block, from 0 to 1 increasing from bottom to top.</td></tr>
 *     <tr><td>5</td><td>Cursor Position Z</td><td>Float</td><td>The position of the crosshair on the block, from 0 to 1 increasing from north to south.</td></tr>
 *     <tr><td>6</td><td>Inside block</td><td>Boolean</td><td>True when the player's head is inside of a block.</td></tr>
 *     <tr><td>7</td><td>World Border Hit</td><td>Boolean</td><td>Seems to always be false, even when interacting with blocks around or outside the world border, or while the player is outside the border.</td></tr>
 *     <tr><td>8</td><td>Sequence</td><td>VarInt</td><td>Block change sequence number (see #Acknowledge Block Change ).</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundUseItemOnPacket(int hand, BlockPos target, int face,
                                         float cursorX, float cursorY, float cursorZ,
                                         boolean insideBlock, int sequence)
        implements ServerboundPacket {

    public static ServerboundUseItemOnPacket read(PacketBuffer buf) {
        int hand = buf.readVarInt();
        BlockPos target = buf.readPosition();
        int face = buf.readVarInt();
        float cursorX = buf.readFloat();
        float cursorY = buf.readFloat();
        float cursorZ = buf.readFloat();
        boolean insideBlock = buf.readBoolean();

        if (buf.readableBytes() > 0) {
            buf.readBoolean();
        }
        int sequence = buf.readableBytes() > 0 ? buf.readVarInt() : 0;
        return new ServerboundUseItemOnPacket(hand, target, face,
                cursorX, cursorY, cursorZ, insideBlock, sequence);
    }

    @Override
    public void handle(PacketListener listener) {
        ((PlayPacketListener) listener).handleUseItemOn(this);
    }
}
