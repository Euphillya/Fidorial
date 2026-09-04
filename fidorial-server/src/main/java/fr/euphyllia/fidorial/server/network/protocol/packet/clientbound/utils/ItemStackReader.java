package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.utils;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.registry.Registry;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import fr.fidorial.item.DataComponentMap;
import fr.fidorial.item.DataComponentType;
import fr.fidorial.item.DataComponentTypes;
import fr.fidorial.item.ItemStack;
import io.netty.handler.codec.DecoderException;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;


public class ItemStackReader {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(ItemStackReader.class);

    private static final Key ITEM_REGISTRY = Key.key("item");
    private static final Key DATA_COMPONENT_TYPE_REGISTRY = Key.key("data_component_type");

    private static final int MAX_COMPONENTS = 256;

    private ItemStackReader() {
        throw new UnsupportedOperationException("ItemStackReader cannot be instantiated.");
    }

    public static ItemStack readSlot(final PacketBuffer buf, final RegistryHolder frozen) {

        final int count = buf.readVarInt();
        if (count <= 0) {
            return ItemStack.EMPTY;
        }

        final int itemId = buf.readVarInt();
        final Key item = entryOf(frozen, ITEM_REGISTRY, itemId);
        if (item == null) {
            throw new DecoderException("Unknown item network id: " + itemId);
        }

        final int addedCount = boundedCount(buf.readVarInt(), "components to add");
        final int removedCount = boundedCount(buf.readVarInt(), "components to remove");

        final DataComponentMap.Builder components = DataComponentMap.builder();

        for (int i = 0; i < addedCount; i++) {
            readComponent(buf, frozen, components);
        }

        for (int i = 0; i < removedCount; i++) {
            final Key key = entryOf(frozen, DATA_COMPONENT_TYPE_REGISTRY, buf.readVarInt());
            if (key != null) {
                final DataComponentType<?> type = DataComponentTypes.byKey(key);
                if (type != null) {
                    components.remove(type);
                }
            }
        }

        final DataComponentMap built = components.build();

        return new ItemStack(resolve(item, built.get(DataComponentTypes.ITEM_MODEL)), count, built);
    }

    private static Key resolve(final Key item, @Nullable final Key itemModel) {
        if (itemModel == null) {
            return item;
        }
        return FidorialServer.getInstance().items().resolve(item, itemModel);
    }

    private static void readComponent(final PacketBuffer buf,
                                      final RegistryHolder frozen,
                                      final DataComponentMap.Builder components) {

        final int typeId = buf.readVarInt();
        final int length = buf.readVarInt();
        if (length < 0 || length > buf.readableBytes()) {
            throw new DecoderException("Implausible payload length " + length
                    + " for data component network id " + typeId);
        }

        final PacketBuffer payload = new PacketBuffer(buf.nettyBuf().readSlice(length));
        final Key key = entryOf(frozen, DATA_COMPONENT_TYPE_REGISTRY, typeId);

        if (key == null) {
            LOGGER.debug("Skipping unknown data component network id {} ({} bytes)", typeId, length);
            return;
        }

        final DataComponentType<?> type = DataComponentTypes.byKey(key);
        if (type == null || !DataComponentNetworkCodecs.hasCodec(type)) {
            LOGGER.debug("Skipping component {}: not modelled here ({} bytes)", key.asString(), length);
            return;
        }

        try {
            readInto(payload, frozen, components, type);
        } catch (final RuntimeException e) {
            LOGGER.warn("Dropping component {}: {}", key.asString(), e.getMessage());
            return;
        }

        if (payload.readableBytes() != 0) {
            LOGGER.warn("Component {} left {} of its {} bytes unread; the wire format here is wrong",
                    key.asString(), payload.readableBytes(), length);
        }
    }

    private static <T> void readInto(final PacketBuffer buf,
                                     final RegistryHolder frozen,
                                     final DataComponentMap.Builder components,
                                     final DataComponentType<T> type) {

        final DataComponentNetworkCodecs.Codec<T> codec = DataComponentNetworkCodecs.codec(type);
        if (codec == null) {
            throw new DecoderException("Data component " + type
                    + " has no wire codec; the rest of this slot cannot be read");
        }

        components.set(type, codec.read(buf, frozen));
    }

    private static int boundedCount(final int count, final String what) {
        if (count < 0 || count > MAX_COMPONENTS) {
            throw new DecoderException("Implausible " + what + " count: " + count);
        }
        return count;
    }

    private static @Nullable Key entryOf(final RegistryHolder frozen, final Key registryKey, final int networkId) {
        final Registry registry = frozen.get(registryKey);
        if (registry == null || networkId < 0 || networkId >= registry.entries().size()) {
            return null;
        }
        return registry.entries().get(networkId);
    }
}
