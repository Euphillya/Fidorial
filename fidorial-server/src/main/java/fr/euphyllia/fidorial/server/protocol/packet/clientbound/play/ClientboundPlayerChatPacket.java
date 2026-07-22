package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import net.kyori.adventure.text.Component;

import java.util.UUID;

/**
 * <p>Sends the client a chat message from a player. Currently, a lot is unknown about this packet, blank descriptions are for those that are unknown Filter Types: The filter type mask should NOT be specified unless partially filtered is selected</p>
 *
 * <p><b>Packet ID:</b> Play = 65 (0x41)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Player_Chat_Message">Player Chat Message</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Header</td><td>Global Index</td><td>VarInt</td></tr>
 *     <tr><td>1</td><td>Sender</td><td>UUID</td><td>Used by the vanilla client for the disableChat launch option. Setting both longs to 0 will always display the message regardless of the setting.</td></tr>
 *     <tr><td>2</td><td>Index</td><td>VarInt</td><td>&nbsp;</td></tr>
 *     <tr><td>3</td><td>Message Signature bytes</td><td>Prefixed Optional Byte Array (256)</td><td>Cryptography, the signature consists of the Sender UUID, Session UUID from the Player Session packet, Index, Salt, Timestamp in epoch seconds, the length of the original chat content, the original content itself, the length of Previous Messages, and all of the Previous message signatures. These values are hashed with SHA-256 and signed using the RSA cryptosystem. Modifying any of these values in the packet will cause this signature to fail. This buffer is always 256 bytes long and it is not length-prefixed.</td></tr>
 *     <tr><td>4</td><td>Body</td><td>Message</td><td>String (256)</td></tr>
 *     <tr><td>5</td><td>Timestamp</td><td>Long</td><td>Represents the time the message was signed as milliseconds since the epoch , used to check if the message was received within 2 minutes of it being sent.</td></tr>
 *     <tr><td>6</td><td>Salt</td><td>Long</td><td>Cryptography, used for validating the message signature.</td></tr>
 *     <tr><td>7</td><td>Prefixed Array (20)</td><td>Message ID</td><td>VarInt</td></tr>
 *     <tr><td>8</td><td>Signature</td><td>Optional Byte Array (256)</td><td>The previous message's signature. Contains the same type of data as Message Signature bytes (256 bytes) above. Not length-prefixed.</td></tr>
 *     <tr><td>9</td><td>Other</td><td>Unsigned Content</td><td>Prefixed Optional Text Component</td></tr>
 *     <tr><td>10</td><td>Filter Type</td><td>VarInt Enum</td><td>If the message has been filtered</td></tr>
 *     <tr><td>11</td><td>Filter Type Bits</td><td>Optional BitSet</td><td>Only present if the Filter Type is Partially Filtered. Specifies the indices at which characters in the original message string should be replaced with the # symbol (i.e., filtered) by the vanilla client</td></tr>
 *     <tr><td>12</td><td>Chat Formatting</td><td>Chat Type</td><td>ID or Chat Type</td></tr>
 *     <tr><td>13</td><td>Sender Name</td><td>Text Component</td><td>The name of the one sending the message, usually the sender's display name. This is used as the sender parameter when formatting the message on the client.</td></tr>
 *     <tr><td>14</td><td>Target Name</td><td>Prefixed Optional Text Component</td><td>The name of the one receiving the message, usually the receiver's display name. This is used as the target parameter when formatting the message on the client.</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundPlayerChatPacket(Object header, UUID sender, int index, Object messageSignatureBytes,
                                          Object body, long timestamp, long salt, Object prefixedArray20,
                                          Object signature, Object other, Object filterType, Object filterTypeBits,
                                          Object chatFormatting, Component senderName,
                                          Object targetName) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.PLAYER_CHAT;
    }

    @Override
    public void write(PacketBuffer buf) {
        // TODO: write header (Global Index)
        buf.writeUuid(sender);
        buf.writeVarInt(index);
        // TODO: write messageSignatureBytes (Prefixed Optional Byte Array)
        // TODO: write body (Message)
        buf.writeLong(timestamp);
        buf.writeLong(salt);
        // TODO: write prefixedArray20 (Message ID)
        // TODO: write signature (Optional Byte Array)
        // TODO: write other (Unsigned Content)
        // TODO: write filterType (VarInt Enum)
        // TODO: write filterTypeBits (Optional BitSet)
        // TODO: write chatFormatting (Chat Type)
        buf.writeComponent(senderName);
        // TODO: write targetName (Prefixed Optional Text Component)
    }
}
