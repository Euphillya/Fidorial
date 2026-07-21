package fr.euphyllia.fidorial.server.protocol.packet.serverbound.common;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.ConfigurationPacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;

/**
 * <p>Sent when the player connects, or when settings are changed. Displayed Skin Parts flags: The most significant bit (bit 7, 0x80) appears to be unused.</p>
 *
 * <p><b>Packet ID:</b> Configuration = 0 (0x00), Play = 14 (0x0E)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Client_Information">Client Information</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Locale</td><td>String (16)</td><td>e.g. en_GB .</td></tr>
 *     <tr><td>1</td><td>View Distance</td><td>Byte</td><td>Client-side render distance, in chunks.</td></tr>
 *     <tr><td>2</td><td>Chat Mode</td><td>VarInt Enum</td><td>0: enabled, 1: commands only, 2: hidden.  See Java Edition protocol/Chat#Client chat mode for more information.</td></tr>
 *     <tr><td>3</td><td>Chat Colors</td><td>Boolean</td><td>“Colors” multiplayer setting. The vanilla server stores this value but does nothing with it (see MC-64867 ). Some third-party servers disable all coloring in chat and system messages when it is false.</td></tr>
 *     <tr><td>4</td><td>Displayed Skin Parts</td><td>Unsigned Byte</td><td>Bit mask, see below.</td></tr>
 *     <tr><td>5</td><td>Main Hand</td><td>VarInt Enum</td><td>0: Left, 1: Right.</td></tr>
 *     <tr><td>6</td><td>Enable text filtering</td><td>Boolean</td><td>Enables filtering of text on signs and written book titles. The vanilla client sets this according to the profanityFilterPreferences.profanityFilterOn account attribute indicated by the Mojang API endpoint for player attributes . In offline mode, it is always false.</td></tr>
 *     <tr><td>7</td><td>Allow server listings</td><td>Boolean</td><td>Servers usually list online players; this option should let you not show up in that list.</td></tr>
 *     <tr><td>8</td><td>Particle Status</td><td>VarInt Enum</td><td>0: all, 1: decreased, 2: minimal</td></tr>
 *   </tbody>
 * </table>
 */
public record ServerboundClientInformationPacket(String language, int displayedSkinParts) implements ServerboundPacket {

    public static ServerboundClientInformationPacket read(PacketBuffer buf) {
        String language = buf.readString(16);
        buf.readByte();
        buf.readVarInt();
        buf.readBoolean();
        int skinParts = buf.readUByte();
        return new ServerboundClientInformationPacket(language, skinParts);
    }

    @Override
    public void handle(PacketListener listener) {
        if (listener instanceof PlayPacketListener play) {
            play.handleClientInformation(this);
        } else if (listener instanceof ConfigurationPacketListener config) {
            config.handleClientInformation(this);
        }
    }
}
