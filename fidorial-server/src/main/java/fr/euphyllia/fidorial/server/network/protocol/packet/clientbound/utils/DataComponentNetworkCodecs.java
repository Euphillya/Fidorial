package fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.utils;

import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.euphyllia.fidorial.server.registry.Registry;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import fr.fidorial.attribute.AttributeModifier;
import fr.fidorial.attribute.AttributeModifierDisplay;
import fr.fidorial.inventory.EquipmentSlotGroup;
import fr.fidorial.item.DataComponentType;
import fr.fidorial.item.DataComponentTypes;
import fr.fidorial.item.component.AttackRange;
import fr.fidorial.item.component.ItemAttributeModifiers;
import fr.fidorial.item.component.ItemLore;
import io.netty.handler.codec.DecoderException;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class DataComponentNetworkCodecs {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(DataComponentNetworkCodecs.class);

    private static final int MAX_COMPONENT_TEXT_LENGTH = 262_144;

    private static final Key ATTRIBUTE_REGISTRY = Key.key("attribute");

    private static final int MAX_ATTRIBUTE_MODIFIERS = 256;

    public interface Codec<T> {

        void write(PacketBuffer buf, RegistryHolder frozen, T value);

        T read(PacketBuffer buf, RegistryHolder frozen);
    }

    private static final Map<DataComponentType<?>, Codec<?>> CODECS = new LinkedHashMap<>();

    static {

        register(DataComponentTypes.MAX_STACK_SIZE, varIntCodec());
        register(DataComponentTypes.MAX_DAMAGE, varIntCodec());
        register(DataComponentTypes.DAMAGE, varIntCodec());

        register(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, booleanCodec());
        register(DataComponentTypes.ITEM_MODEL, identifierCodec());
        register(DataComponentTypes.CUSTOM_NAME, textCodec());
        register(DataComponentTypes.ITEM_NAME, textCodec());
        register(DataComponentTypes.LORE, loreCodec());

        register(DataComponentTypes.ATTACK_RANGE, attackRangeCodec());
        register(DataComponentTypes.ATTRIBUTE_MODIFIERS, attributeModifiersCodec());

    }

    private DataComponentNetworkCodecs() {
        throw new UnsupportedOperationException("DataComponentNetworkCodecs cannot be instantiated.");
    }

    private static <T> void register(final DataComponentType<T> type, final Codec<T> codec) {
        CODECS.put(type, codec);
    }

    @SuppressWarnings("unchecked")
    public static <T> @Nullable Codec<T> codec(final DataComponentType<T> type) {
        return (Codec<T>) CODECS.get(Objects.requireNonNull(type, "type"));
    }

    public static boolean hasCodec(final DataComponentType<?> type) {
        return CODECS.containsKey(Objects.requireNonNull(type, "type"));
    }

    public static <T> void writeErased(final PacketBuffer buf,
                                       final RegistryHolder frozen,
                                       final DataComponentType<T> type,
                                       final Object value) {

        final Codec<T> codec = codec(type);
        if (codec == null) {
            throw new IllegalStateException("No wire codec for component " + type);
        }
        codec.write(buf, frozen, (T) type.valueType().cast(value));
    }

    private static Codec<Integer> varIntCodec() {
        return new Codec<>() {
            @Override
            public void write(final PacketBuffer buf, final RegistryHolder frozen, final Integer value) {
                buf.writeVarInt(value);
            }

            @Override
            public Integer read(final PacketBuffer buf, final RegistryHolder frozen) {
                return buf.readVarInt();
            }
        };
    }

    private static Codec<Boolean> booleanCodec() {
        return new Codec<>() {
            @Override
            public void write(final PacketBuffer buf, final RegistryHolder frozen, final Boolean value) {
                buf.writeBoolean(value);
            }

            @Override
            public Boolean read(final PacketBuffer buf, final RegistryHolder frozen) {
                return buf.readBoolean();
            }
        };
    }

    private static Codec<Key> identifierCodec() {
        return new Codec<>() {
            @Override
            public void write(final PacketBuffer buf, final RegistryHolder frozen, final Key value) {
                buf.writeKey(value);
            }

            @Override
            public Key read(final PacketBuffer buf, final RegistryHolder frozen) {
                return buf.readKey();
            }
        };
    }

    private static Codec<Component> textCodec() {
        return new Codec<>() {
            @Override
            public void write(final PacketBuffer buf, final RegistryHolder frozen, final Component value) {
                buf.writeComponent(value);
            }

            @Override
            public Component read(final PacketBuffer buf, final RegistryHolder frozen) {
                return buf.readComponent(MAX_COMPONENT_TEXT_LENGTH);
            }
        };
    }

    private static Codec<ItemLore> loreCodec() {
        return new Codec<>() {
            @Override
            public void write(final PacketBuffer buf, final RegistryHolder frozen, final ItemLore value) {
                buf.writeVarInt(value.size());
                for (final Component line : value.lines()) {
                    buf.writeComponent(line);
                }
            }

            @Override
            public ItemLore read(final PacketBuffer buf, final RegistryHolder frozen) {
                final int size = readBoundedLength(buf, ItemLore.MAX_LINES, "lore");

                final List<Component> lines = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    lines.add(buf.readComponent(MAX_COMPONENT_TEXT_LENGTH));
                }

                return new ItemLore(lines);
            }
        };
    }

    private static Codec<AttackRange> attackRangeCodec() {
        return new Codec<>() {
            @Override
            public void write(final PacketBuffer buf, final RegistryHolder frozen, final AttackRange value) {
                buf.writeFloat(value.minReach());
                buf.writeFloat(value.maxReach());
                buf.writeFloat(value.minCreativeReach());
                buf.writeFloat(value.maxCreativeReach());
                buf.writeFloat(value.hitboxMargin());
                buf.writeFloat(value.mobFactor());
            }

            @Override
            public AttackRange read(final PacketBuffer buf, final RegistryHolder frozen) {
                final float minReach = buf.readFloat();
                final float maxReach = buf.readFloat();
                final float minCreativeReach = buf.readFloat();
                final float maxCreativeReach = buf.readFloat();
                final float hitboxMargin = buf.readFloat();
                final float mobFactor = buf.readFloat();

                try {
                    return new AttackRange(
                            minReach, maxReach, minCreativeReach, maxCreativeReach, hitboxMargin, mobFactor);
                } catch (final IllegalArgumentException e) {
                    throw new DecoderException("Implausible attack range: " + e.getMessage(), e);
                }
            }
        };
    }

    private static Codec<ItemAttributeModifiers> attributeModifiersCodec() {
        return new Codec<>() {
            @Override
            public void write(final PacketBuffer buf,
                              final RegistryHolder frozen,
                              final ItemAttributeModifiers value) {

                final List<AttributeModifier> writable = new ArrayList<>(value.size());
                for (final AttributeModifier modifier : value.modifiers()) {
                    if (frozen.networkId(ATTRIBUTE_REGISTRY, modifier.attribute()) < 0) {
                        LOGGER.warn("Dropping modifier {}: attribute {} is not in the registry",
                                modifier.id().asString(), modifier.attribute().asString());
                        continue;
                    }
                    writable.add(modifier);
                }

                buf.writeVarInt(writable.size());

                for (final AttributeModifier modifier : writable) {
                    buf.writeVarInt(frozen.networkId(ATTRIBUTE_REGISTRY, modifier.attribute()));
                    buf.writeKey(modifier.id());
                    buf.writeDouble(modifier.amount());
                    buf.writeVarInt(modifier.operation().networkId());
                    buf.writeVarInt(modifier.slot().networkId());
                    writeDisplay(buf, modifier.display());
                }
            }

            @Override
            public ItemAttributeModifiers read(final PacketBuffer buf, final RegistryHolder frozen) {
                final int size = readBoundedLength(buf, MAX_ATTRIBUTE_MODIFIERS, "attribute modifiers");

                final List<AttributeModifier> modifiers = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    final int attributeId = buf.readVarInt();
                    final Key attribute = entryOf(frozen, ATTRIBUTE_REGISTRY, attributeId);
                    if (attribute == null) {
                        throw new DecoderException("Unknown attribute network id: " + attributeId);
                    }

                    final Key id = buf.readKey();
                    final double amount = buf.readDouble();
                    final AttributeModifier.Operation operation = operationOf(buf.readVarInt());
                    final EquipmentSlotGroup slot = slotGroupOf(buf.readVarInt());
                    final AttributeModifierDisplay display = readDisplay(buf);

                    modifiers.add(new AttributeModifier(attribute, id, amount, operation, slot, display));
                }

                return ItemAttributeModifiers.of(modifiers);
            }
        };
    }

    private static void writeDisplay(final PacketBuffer buf, final AttributeModifierDisplay display) {
        buf.writeVarInt(display.type().networkId());

        if (display.type() == AttributeModifierDisplay.Type.OVERRIDE) {
            buf.writeComponent(Objects.requireNonNull(display.value(), "value"));
        }
    }

    private static AttributeModifierDisplay readDisplay(final PacketBuffer buf) {
        final int typeId = buf.readVarInt();

        final AttributeModifierDisplay.Type type = AttributeModifierDisplay.Type.byNetworkId(typeId);
        if (type == null) {
            throw new DecoderException("Unknown attribute modifier display type: " + typeId);
        }

        return switch (type) {
            case DEFAULT -> AttributeModifierDisplay.DEFAULT;
            case HIDDEN -> AttributeModifierDisplay.HIDDEN;
            case OVERRIDE -> AttributeModifierDisplay.override(buf.readComponent(MAX_COMPONENT_TEXT_LENGTH));
        };
    }

    private static int readBoundedLength(final PacketBuffer buf, final int max, final String what) {
        final int size = buf.readVarInt();
        if (size < 0 || size > max) {
            throw new DecoderException("Implausible " + what + " length: " + size);
        }
        return size;
    }

    private static @Nullable Key entryOf(final RegistryHolder frozen, final Key registryKey, final int networkId) {
        final Registry registry = frozen.get(registryKey);
        if (registry == null || networkId < 0 || networkId >= registry.entries().size()) {
            return null;
        }
        return registry.entries().get(networkId);
    }

    private static AttributeModifier.Operation operationOf(final int networkId) {
        for (final AttributeModifier.Operation operation : AttributeModifier.Operation.values()) {
            if (operation.networkId() == networkId) {
                return operation;
            }
        }
        return AttributeModifier.Operation.ADD_VALUE;
    }

    private static EquipmentSlotGroup slotGroupOf(final int networkId) {
        for (final EquipmentSlotGroup group : EquipmentSlotGroup.values()) {
            if (group.networkId() == networkId) {
                return group;
            }
        }
        return EquipmentSlotGroup.ANY;
    }
}
