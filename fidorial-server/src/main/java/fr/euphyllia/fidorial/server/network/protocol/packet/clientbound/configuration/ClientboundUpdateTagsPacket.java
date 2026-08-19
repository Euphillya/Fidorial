package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.configuration;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.network.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.registry.Registry;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import fr.euphyllia.fidorial.server.registry.biome.FidorialBiomeRegistry;
import fr.euphyllia.fidorial.server.registry.dialog.FidorialDialogRegistry;
import net.kyori.adventure.key.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

public record ClientboundUpdateTagsPacket(
        RegistryHolder dynamic,
        FidorialBiomeRegistry biomes,
        FidorialDialogRegistry dialogs
) implements ClientboundPacket {

    @Override
    public Key name() {
        return ConfigurationClientboundPackets.UPDATE_TAGS;
    }

    @Override
    public void write(final PacketBuffer buf) {
        buf.writeVarInt(dynamic.size() + 1);

        for (final Registry reg : dynamic.all()) {
            final Map<Key, List<Key>> tags;
            final ToIntFunction<Key> networkId;

            if (reg.name().equals(FidorialBiomeRegistry.REGISTRY_NAME)) {
                tags = reg.tags();
                networkId = biomes::networkId;
            } else if (reg.name().equals(FidorialDialogRegistry.REGISTRY_NAME)) {
                tags = dialogs.networkTags();
                networkId = dialogs::networkId;
            } else {
                tags = reg.tags();
                final List<Key> entries = reg.entries();
                networkId = entries::indexOf;
            }

            buf.writeKey(reg.name());
            buf.writeVarInt(tags.size());

            for (final Map.Entry<Key, List<Key>> tag : tags.entrySet()) {
                final List<Integer> ids = new ArrayList<>(tag.getValue().size());

                for (final Key entry : tag.getValue()) {
                    final int id = networkId.applyAsInt(entry);
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
