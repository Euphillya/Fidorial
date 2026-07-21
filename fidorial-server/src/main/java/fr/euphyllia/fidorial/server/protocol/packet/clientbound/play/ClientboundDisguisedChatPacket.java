package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import net.kyori.adventure.text.Component;

/**
 * <p>Sends the client a chat message, but without any message signing information. The vanilla server uses this packet when the console is communicating with players through commands, such as /say , /tell , /me , among others.</p>
 *
 * <p><b>Packet ID:</b> Play = 33 (0x21)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Disguised_Chat_Message">Disguised Chat Message</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Message</td><td>Text Component</td><td>This is used as the content parameter when formatting the message on the client.</td></tr>
 *     <tr><td>1</td><td>Chat Type</td><td>ID or Chat Type</td><td>Either the type of chat in the minecraft:chat_type registry, defined by the Registry Data packet, or an inline definition.</td></tr>
 *     <tr><td>2</td><td>Sender Name</td><td>Text Component</td><td>The name of the one sending the message, usually the sender's display name. This is used as the sender parameter when formatting the message on the client.</td></tr>
 *     <tr><td>3</td><td>Target Name</td><td>Prefixed Optional Text Component</td><td>The name of the one receiving the message, usually the receiver's display name. This is used as the target parameter when formatting the message on the client.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundDisguisedChatPacket(Component message, Object chatType, Component senderName,
                                             Object targetName) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.DISGUISED_CHAT;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeComponent(message);
        // TODO: write chatType (ID or Chat Type)
        buf.writeComponent(senderName);
        // TODO: write targetName (Prefixed Optional Text Component)
    }
}
