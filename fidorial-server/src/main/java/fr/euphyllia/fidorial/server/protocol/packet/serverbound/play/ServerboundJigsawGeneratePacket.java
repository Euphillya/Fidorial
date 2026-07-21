package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.fidorial.world.BlockPos;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Sent when Generate is pressed on the Jigsaw Block interface.</p>
 *
 * <p><b>Packet ID:</b> Play = 27 (0x1B)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Jigsaw_Generate">Jigsaw Generate</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Location</td><td>Position</td><td>Block entity location.</td></tr>
 *     <tr><td>1</td><td>Levels</td><td>VarInt</td><td>Value of the levels slider/max depth to generate.</td></tr>
 *     <tr><td>2</td><td>Keep Jigsaws</td><td>Boolean</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundJigsawGeneratePacket(BlockPos location, int levels,
                                              boolean keepJigsaws) implements ServerboundPacket {

    public static ServerboundJigsawGeneratePacket read(PacketBuffer buf) {
        BlockPos location = null;
        location = buf.readPosition();
        int levels = 0;
        levels = buf.readVarInt();
        boolean keepJigsaws = false;
        keepJigsaws = buf.readBoolean();
        return new ServerboundJigsawGeneratePacket(location, levels, keepJigsaws);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
