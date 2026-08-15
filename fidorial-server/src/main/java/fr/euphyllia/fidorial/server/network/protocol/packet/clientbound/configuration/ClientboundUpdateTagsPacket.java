package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.registry.Registry;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import fr.euphyllia.fidorial.server.registry.biome.FidorialBiomeRegistry;
import net.kyori.adventure.key.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record ClientboundUpdateTagsPacket(RegistryHolder dynamic, FidorialBiomeRegistry biomes)
        implements ClientboundPacket {

    @Override
    public Key name() {
        return ConfigurationClientboundPackets.UPDATE_TAGS;
    }

    @Override
    public void write(final PacketBuffer buf) {
        buf.writeVarInt(dynamic.size() + 1);

        for (final Registry reg : dynamic.all()) {
            final boolean isBiome = reg.name().equals(FidorialBiomeRegistry.REGISTRY_NAME);
            final List<Key> entries = reg.entries();

            buf.writeKey(reg.name());
            buf.writeVarInt(reg.tags().size());

            for (final Map.Entry<Key, List<Key>> tag : reg.tags().entrySet()) {
                final List<Integer> ids = new ArrayList<>(tag.getValue().size());

                for (final Key entry : tag.getValue()) {
                    final int id = isBiome ? biomes.networkId(entry) : entries.indexOf(entry);
                    if (id >= 0) {
                        ids.add(id);
                    }
                }

                buf.writeKey(tag.getKey());
                buf.writeVarInt(ids.size());
                for (final int id : ids) {
                    buf.writeVarInt(id);
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
