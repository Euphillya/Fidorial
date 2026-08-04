package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.registry.Registry;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import net.kyori.adventure.key.Key;

import java.util.List;
import java.util.Map;

public final class ClientboundUpdateTagsPacket implements ClientboundPacket {

    private final List<Registry> withTags;

    public ClientboundUpdateTagsPacket(RegistryHolder dynamic) {
        this.withTags = dynamic.all().stream()
                .filter(Registry::hasTags)
                .filter(r -> !r.name().value().contains("enchantment"))
                .toList();
    }

    @Override
    public Key name() {
        return ConfigurationClientboundPackets.UPDATE_TAGS;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeVarInt(withTags.size() + 1);

        for (Registry reg : withTags) {
            List<Key> entries = reg.entries();
            buf.writeKey(reg.name());
            buf.writeVarInt(reg.tags().size());
            for (Map.Entry<Key, List<Key>> tag : reg.tags().entrySet()) {
                buf.writeKey(tag.getKey());
                buf.writeVarInt(tag.getValue().size());
                for (Key entry : tag.getValue()) {
                    buf.writeVarInt(entries.indexOf(entry));
                }
            }
        }

        buf.writeKey(Key.key("block"));
        buf.writeVarInt(3);
        buf.writeKey(Key.key("infiniburn_overworld"));
        buf.writeVarInt(2).writeVarInt(285).writeVarInt(671);
        buf.writeKey(Key.key("infiniburn_nether"));
        buf.writeVarInt(2).writeVarInt(285).writeVarInt(671);
        buf.writeKey(Key.key("infiniburn_end"));
        buf.writeVarInt(3).writeVarInt(285).writeVarInt(671).writeVarInt(34);
    }
}
