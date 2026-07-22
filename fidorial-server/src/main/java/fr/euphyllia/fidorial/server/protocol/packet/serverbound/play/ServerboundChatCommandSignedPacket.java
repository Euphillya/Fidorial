package fr.euphyllia.fidorial.server.protocol.packet.serverbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;

/**
 * <p><b>Packet ID:</b> Play = 8 (0x08)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Signed_Chat_Command">Signed Chat Command</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Command</td><td>String (32767)</td><td>The command typed by the client excluding the / .</td></tr>
 *     <tr><td>1</td><td>Timestamp</td><td>Long</td><td>The timestamp that the command was executed.</td></tr>
 *     <tr><td>2</td><td>Salt</td><td>Long</td><td>The salt for the following argument signatures.</td></tr>
 *     <tr><td>3</td><td>Array of argument signatures</td><td>Argument name</td><td>Prefixed Array (8)</td></tr>
 *     <tr><td>4</td><td>Signature</td><td>Byte Array (256)</td><td>The signature that verifies the argument. Always 256 bytes and is not length-prefixed.</td></tr>
 *     <tr><td>5</td><td>Message Count</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>6</td><td>Acknowledged</td><td>Fixed BitSet (20)</td><td>&nbsp;</td></tr>
 *     <tr><td>7</td><td>Checksum</td><td>Byte</td><td>&nbsp;</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundChatCommandSignedPacket(String command, long timestamp, long salt,
                                                 Object arrayOfArgumentSignatures, Object signature, int messageCount,
                                                 Object acknowledged, byte checksum) implements ServerboundPacket {

    public static ServerboundChatCommandSignedPacket read(PacketBuffer buf) {
        String command = null;
        command = buf.readString(32767);
        long timestamp = 0L;
        timestamp = buf.readLong();
        long salt = 0L;
        salt = buf.readLong();
        Object arrayOfArgumentSignatures = null; // TODO: read arrayOfArgumentSignatures (Argument name)
        Object signature = null; // TODO: read signature (Byte Array)
        int messageCount = 0;
        messageCount = buf.readVarInt();
        Object acknowledged = null; // TODO: read acknowledged (Fixed BitSet)
        byte checksum = (byte) 0;
        checksum = buf.readByte();
        return new ServerboundChatCommandSignedPacket(command, timestamp, salt, arrayOfArgumentSignatures, signature, messageCount, acknowledged, checksum);
    }

    @Override
    public void handle(PacketListener listener) {
        // TODO: route to the appropriate listener once the packet is implemented and registered.
    }
}
