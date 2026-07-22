package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p><b>Packet ID:</b> Play = 55 (0x37)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Program_Command_Block_Minecart">Program Command Block Minecart</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Entity ID</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Command</td><td>String (32767)</td><td>&nbsp;</td></tr>
 *     <tr><td>2</td><td>Track Output</td><td>Boolean</td><td>If false, the output of the previous command will not be stored within the command block.</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundSetCommandMinecartPacket(int entityId, String command,
                                                  boolean trackOutput) implements ServerboundPacket {

    public static ServerboundSetCommandMinecartPacket read(PacketBuffer buf) {
        int entityId = 0;
        entityId = buf.readVarInt();
        String command = null;
        command = buf.readString(32767);
        boolean trackOutput = false;
        trackOutput = buf.readBoolean();
        return new ServerboundSetCommandMinecartPacket(entityId, command, trackOutput);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
