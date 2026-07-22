package fr.euphyllia.fidorial.server.protocol.packet.serverbound.login;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.LoginPacketListener;

/**
 * <p><b>Packet ID:</b> Login = 2 (0x02)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Login_Plugin_Response">Login Plugin Response</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Message ID</td><td>VarInt</td><td>Should match ID from server.</td></tr>
 *     <tr><td>1</td><td>Data</td><td>Prefixed Optional Varies</td><td>Any data, depending on the channel. Only present if the client understood the request. Typically this would be a sequence of fields using standard data types, but some unofficial channels have unusual formats. There is no length prefix that applies to all channel types, but the format specific to the channel may or may not include one or more length prefixes (e.g. for strings).</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundCustomQueryAnswerPacket(int transactionId, boolean understood, byte[] payload)
        implements ServerboundPacket {

    public static ServerboundCustomQueryAnswerPacket read(PacketBuffer buf) {
        int transactionId = buf.readVarInt();
        boolean understood = buf.readBoolean();
        byte[] payload = understood ? buf.readRemainingBytes() : new byte[0];
        return new ServerboundCustomQueryAnswerPacket(transactionId, understood, payload);
    }

    @Override
    public void handle(PacketListener listener) {
        ((LoginPacketListener) listener).handleCustomQueryAnswer(this);
    }
}
