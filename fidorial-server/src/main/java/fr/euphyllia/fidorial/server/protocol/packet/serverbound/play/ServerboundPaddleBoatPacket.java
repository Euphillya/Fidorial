package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * Automatically generated from the wiki — verify the serialisation.
 *
 * <p>Used to visually update whether boat paddles are turning.</p>
 * <p>The server will update the Boat entity metadata to match the values here. Right paddle turning is set to true when the left button or forward button is held, left paddle turning is set to true when the right button or forward button is held.</p>
 *
 * <p><b>Packet ID:</b> Play = 35 (0x23)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Paddle_Boat">Paddle Boat</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Left paddle turning</td><td>Boolean</td><td>&nbsp;</td></tr>
 *     <tr><td>1</td><td>Right paddle turning</td><td>Boolean</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundPaddleBoatPacket(boolean leftPaddleTurning,
                                          boolean rightPaddleTurning) implements ServerboundPacket {

    public static ServerboundPaddleBoatPacket read(PacketBuffer buf) {
        boolean leftPaddleTurning = false;
        leftPaddleTurning = buf.readBoolean();
        boolean rightPaddleTurning = false;
        rightPaddleTurning = buf.readBoolean();
        return new ServerboundPaddleBoatPacket(leftPaddleTurning, rightPaddleTurning);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
