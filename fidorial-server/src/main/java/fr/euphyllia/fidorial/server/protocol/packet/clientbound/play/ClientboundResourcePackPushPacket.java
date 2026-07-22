package fr.euphyllia.fidorial.server.protocol.packet.clientbound.play;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;

import java.util.UUID;

/**
 * <p><b>Packet ID:</b> Configuration = 9 (0x09), Play = 81 (0x51)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Add_Resource_Pack">Add Resource Pack</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>UUID</td><td>UUID</td><td>The unique identifier of the resource pack.</td></tr>
 *     <tr><td>1</td><td>URL</td><td>String (32767)</td><td>The URL to the resource pack.</td></tr>
 *     <tr><td>2</td><td>Hash</td><td>String (40)</td><td>A 40 character hexadecimal, case-insensitive SHA-1 hash of the resource pack file. If it's not a 40-character hexadecimal string, the client will not use it for hash verification and likely waste bandwidth.</td></tr>
 *     <tr><td>3</td><td>Forced</td><td>Boolean</td><td>The vanilla client will be forced to use the resource pack from the server. If they decline, they will be kicked from the server.</td></tr>
 *     <tr><td>4</td><td>Prompt Message</td><td>Prefixed Optional Text Component</td><td>This is shown in the prompt making the client accept or decline the resource pack (only if present).</td></tr>
 *   </tbody>
 * </table>
 */
public record ClientboundResourcePackPushPacket(UUID uuid, String url, String hash, boolean forced,
                                                Object promptMessage) implements ClientboundPacket {

    @Override
    public String name() {
        return PlayClientboundPackets.RESOURCE_PACK_PUSH;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeUuid(uuid);
        buf.writeString(url);
        buf.writeString(hash);
        buf.writeBoolean(forced);
        // TODO: write promptMessage (Prefixed Optional Text Component)
    }
}
