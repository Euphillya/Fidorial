package fr.euphyllia.fidorial.server.protocol.packet.clientbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.registry.Registry;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;

import java.util.List;
import java.util.Map;

/**
 * <p>Each tag looks like: See Tag for more information, including a list of vanilla tags. Due to MC-249007 , any tags that were defined in built-in registries in previous game sessions of a client process will be present by default in new sessions, unless overridden by the server. This occurs on a per-tag basis, so defining a specific tag in a registry will not cause any other tags in that registry to be overridden. If the server sends Finish Configuration without sending any Registry Data packets during the configuration phase, previously specified tags in synchronized registries will also be retained unless respecified on a per-tag basis. The server must have already sent registries during a previous configuration phase. Note that it is also possible to update tags in play state , so entering configuration is not necessary to do this. If any Registry Data packets are sent during reconfiguration, all previous tags in all synchronized registries are forgotten, as are the registries themselves. When used in play state, this packet always only replaces the tags mentioned in the packet. Other tags, including ones belonging to the registries mentioned in the packet, are unaffected.</p>
 *
 * <p><b>Packet ID:</b> Configuration = 13 (0x0D), Play = 134 (0x86)</p>
 * <p><b>Source:</b> <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Update_Tags">Update Tags</a></p>
 *
 * <h4>Packet structure</h4>
 * <table>
 *   <caption>Fields, in write/read order</caption>
 *   <thead>
 *     <tr><th>#</th><th>Field</th><th>Type</th><th>Description</th></tr>
 *   </thead>
 *   <tbody>
 *     <tr><td>0</td><td>Tagged Registries</td><td>Registry</td><td>Prefixed Array</td></tr>
 *     <tr><td>1</td><td>Tags</td><td>Prefixed Array of Tag</td><td>Array of tags defined for the registry, each containing a sub-array of entries that have the tag (see below).</td></tr>
 *   </tbody>
 * </table>
 */
public final class ClientboundUpdateTagsPacket implements ClientboundPacket {

    private final List<Registry> withTags;

    public ClientboundUpdateTagsPacket(RegistryHolder dynamic) {
        this.withTags = dynamic.all().stream()
                .filter(Registry::hasTags)
                .filter(r -> !r.name().contains("enchantment"))
                .toList();
    }

    @Override
    public String name() {
        return ConfigurationClientboundPackets.UPDATE_TAGS;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(withTags.size() + 1);

        for (Registry reg : withTags) {
            List<String> entries = reg.entries();
            buf.writeIdentifier(reg.name());
            buf.writeVarInt(reg.tags().size());
            for (Map.Entry<String, List<String>> tag : reg.tags().entrySet()) {
                buf.writeIdentifier(tag.getKey());
                buf.writeVarInt(tag.getValue().size());
                for (String entry : tag.getValue()) {
                    buf.writeVarInt(entries.indexOf(entry));
                }
            }
        }

        buf.writeIdentifier("minecraft:block");
        buf.writeVarInt(3);
        buf.writeIdentifier("minecraft:infiniburn_overworld");
        buf.writeVarInt(2).writeVarInt(285).writeVarInt(671);
        buf.writeIdentifier("minecraft:infiniburn_nether");
        buf.writeVarInt(2).writeVarInt(285).writeVarInt(671);
        buf.writeIdentifier("minecraft:infiniburn_end");
        buf.writeVarInt(3).writeVarInt(285).writeVarInt(671).writeVarInt(34);
    }
}
