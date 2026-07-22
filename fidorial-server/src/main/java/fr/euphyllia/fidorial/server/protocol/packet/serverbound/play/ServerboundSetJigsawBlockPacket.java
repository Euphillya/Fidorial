package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.fidorial.world.BlockPos;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Sent when Done is pressed on the Jigsaw Block interface.</p>
 *
 * <p><b>Packet ID:</b> Play = 58 (0x3A)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Program_Jigsaw_Block">Program Jigsaw Block</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Location</td><td>Position</td><td>Block entity location</td></tr>
 *     <tr><td>1</td><td>Name</td><td>Identifier</td><td>&nbsp;</td></tr>
 *     <tr><td>2</td><td>Target</td><td>Identifier</td><td>&nbsp;</td></tr>
 *     <tr><td>3</td><td>Pool</td><td>Identifier</td><td>&nbsp;</td></tr>
 *     <tr><td>4</td><td>Final state</td><td>String (32767)</td><td>"Turns into" on the GUI, final_state in NBT.</td></tr>
 *     <tr><td>5</td><td>Joint type</td><td>String (32767)</td><td>rollable if the attached piece can be rotated, else aligned .</td></tr>
 *     <tr><td>6</td><td>Selection priority</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>7</td><td>Placement priority</td><td>VarInt</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundSetJigsawBlockPacket(BlockPos location, String name, String target, String pool,
                                              String finalState, String jointType, int selectionPriority,
                                              int placementPriority) implements ServerboundPacket {

    public static ServerboundSetJigsawBlockPacket read(PacketBuffer buf) {
        BlockPos location = null;
        location = buf.readPosition();
        String name = null;
        name = buf.readIdentifier();
        String target = null;
        target = buf.readIdentifier();
        String pool = null;
        pool = buf.readIdentifier();
        String finalState = null;
        finalState = buf.readString(32767);
        String jointType = null;
        jointType = buf.readString(32767);
        int selectionPriority = 0;
        selectionPriority = buf.readVarInt();
        int placementPriority = 0;
        placementPriority = buf.readVarInt();
        return new ServerboundSetJigsawBlockPacket(location, name, target, pool, finalState, jointType, selectionPriority, placementPriority);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
