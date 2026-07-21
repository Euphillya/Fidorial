package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.fidorial.world.BlockPos;

/**
 * <p>Tries to perform an action the Test Instance Block at the given position.</p>
 *
 * <p><b>Packet ID:</b> Play = 65 (0x41)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Test_Instance_Block_Action">Test Instance Block Action</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Position</td><td>Position</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Action</td><td>VarInt Enum</td><td>0: init, 1: query, 2: set, 3: reset, 4: save, 5: export, 6: run.</td></tr>
 *     <tr><td>2</td><td>Test</td><td>Prefixed Optional Identifier</td><td>ID in the minecraft:test_instance registry.</td></tr>
 *     <tr><td>3</td><td>Size X</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>4</td><td>Size Y</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>5</td><td>Size Z</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>6</td><td>Rotation</td><td>VarInt Enum</td><td>0: none, 1: clockwise 90°, 2: clockwise 180°, 3: counter-clockwise 90°.</td></tr>
 *     <tr><td>7</td><td>Ignore Entities</td><td>Boolean</td><td>&nbsp;</td></tr>
 *     <tr><td>8</td><td>Status</td><td>VarInt Enum</td><td>0: cleared, 1: running, 2: finished.</td></tr>
 *     <tr><td>9</td><td>Error Message</td><td>Prefixed Optional Text Component</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundTestInstanceBlockActionPacket(BlockPos position, Object action, Object test, int sizeX,
                                                       int sizeY, int sizeZ, Object rotation, boolean ignoreEntities,
                                                       Object status,
                                                       Object errorMessage) implements ServerboundPacket {

    public static ServerboundTestInstanceBlockActionPacket read(PacketBuffer buf) {
        BlockPos position = null;
        position = buf.readPosition();
        Object action = null; // TODO: read action (VarInt Enum)
        Object test = null; // TODO: read test (Prefixed Optional Identifier)
        int sizeX = 0;
        sizeX = buf.readVarInt();
        int sizeY = 0;
        sizeY = buf.readVarInt();
        int sizeZ = 0;
        sizeZ = buf.readVarInt();
        Object rotation = null; // TODO: read rotation (VarInt Enum)
        boolean ignoreEntities = false;
        ignoreEntities = buf.readBoolean();
        Object status = null; // TODO: read status (VarInt Enum)
        Object errorMessage = null; // TODO: read errorMessage (Prefixed Optional Text Component)
        return new ServerboundTestInstanceBlockActionPacket(position, action, test, sizeX, sizeY, sizeZ, rotation, ignoreEntities, status, errorMessage);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
