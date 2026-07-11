package fr.euphyllia.fidorial.server.protocol.packet.clientbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.registry.Registry;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;

import java.util.List;
import java.util.Map;

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
