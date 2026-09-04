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
import fr.fidorial.item.component.BannerPattern;
import fr.fidorial.item.component.BannerPatterns;
import fr.fidorial.item.component.Bees;
import fr.fidorial.item.component.DyeColor;
import fr.fidorial.item.component.ItemAttributeModifiers;
import fr.fidorial.item.component.ItemLore;
import io.netty.handler.codec.DecoderException;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
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
    private static final Key BANNER_PATTERN_REGISTRY = Key.key("banner_pattern");
    private static final int MAX_BANNER_LAYERS = 1_024;
    private static final int INLINE_HOLDER = 0;
    private static final int MAX_TRANSLATION_KEY_LENGTH = 32_767;

    private static final int MAX_ATTRIBUTE_MODIFIERS = 256;

    private static final int MAX_HIVE_OCCUPANTS = 1_024;
    private static final int MAX_ENTITY_NBT_BYTES = 2_097_152;

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
        register(DataComponentTypes.BANNER_PATTERNS, bannerPatternsCodec());
        register(DataComponentTypes.BASE_COLOR, dyeColorCodec());
        register(DataComponentTypes.BEES, beesCodec());
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

    private static Codec<BannerPatterns> bannerPatternsCodec() {
        return new Codec<>() {
            @Override
            public void write(final PacketBuffer buf,
                              final RegistryHolder frozen,
                              final BannerPatterns value) {
                final List<BannerPatterns.Layer> writable = new ArrayList<>(value.size());
                for (final BannerPatterns.Layer layer : value.layers()) {
                    if (layer.pattern() instanceof BannerPattern.Reference(final Key pattern)
                            && frozen.networkId(BANNER_PATTERN_REGISTRY, pattern) < 0) {
                        LOGGER.warn("Dropping banner layer: pattern {} is not in the registry",
                                pattern.asString());
                        continue;
                    }
                    writable.add(layer);
                }

                buf.writeVarInt(writable.size());

                for (final BannerPatterns.Layer layer : writable) {
                    writeBannerPattern(buf, frozen, layer.pattern());
                    buf.writeVarInt(layer.color().networkId());
                }
            }

            @Override
            public BannerPatterns read(final PacketBuffer buf, final RegistryHolder frozen) {
                final int size = readBoundedLength(buf, MAX_BANNER_LAYERS, "banner layers");

                final List<BannerPatterns.Layer> layers = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    final BannerPattern pattern = readBannerPattern(buf, frozen);

                    layers.add(new BannerPatterns.Layer(pattern, readDyeColor(buf)));
                }

                return BannerPatterns.of(layers);
            }
        };
    }

    private static void writeBannerPattern(final PacketBuffer buf,
                                           final RegistryHolder frozen,
                                           final BannerPattern pattern) {

        switch (pattern) {
            case final BannerPattern.Reference reference ->
                    buf.writeVarInt(frozen.networkId(BANNER_PATTERN_REGISTRY, reference.pattern()) + 1);
            case final BannerPattern.Inline inline -> {
                buf.writeVarInt(INLINE_HOLDER);
                buf.writeKey(inline.assetId());
                buf.writeString(inline.translationKey());
            }
        }
    }

    private static Codec<DyeColor> dyeColorCodec() {
        return new Codec<>() {
            @Override
            public void write(final PacketBuffer buf, final RegistryHolder frozen, final DyeColor value) {
                buf.writeVarInt(value.networkId());
            }

            @Override
            public DyeColor read(final PacketBuffer buf, final RegistryHolder frozen) {
                return readDyeColor(buf);
            }
        };
    }

    private static DyeColor readDyeColor(final PacketBuffer buf) {
        final int colorId = buf.readVarInt();

        final DyeColor color = DyeColor.byNetworkId(colorId);
        if (color == null) {
            throw new DecoderException("Unknown dye colour network id: " + colorId);
        }

        return color;
    }

    private static BannerPattern readBannerPattern(final PacketBuffer buf, final RegistryHolder frozen) {
        final int holderId = buf.readVarInt();

        if (holderId == INLINE_HOLDER) {
            return BannerPattern.inline(buf.readKey(), buf.readString(MAX_TRANSLATION_KEY_LENGTH));
        }

        final Key pattern = entryOf(frozen, BANNER_PATTERN_REGISTRY, holderId - 1);
        if (pattern == null) {
            throw new DecoderException("Unknown banner pattern network id: " + (holderId - 1));
        }

        return BannerPattern.reference(pattern);
    }

    private static Codec<Bees> beesCodec() {
        return new Codec<>() {
            @Override
            public void write(final PacketBuffer buf, final RegistryHolder frozen, final Bees value) {
                buf.writeVarInt(value.size());

                for (final Bees.Occupant occupant : value.occupants()) {
                    buf.writeSizedNbt(occupant.entityData());
                    buf.writeVarInt(occupant.ticksInHive());
                    buf.writeVarInt(occupant.minTicksInHive());
                }
            }

            @Override
            public Bees read(final PacketBuffer buf, final RegistryHolder frozen) {
                final int size = readBoundedLength(buf, MAX_HIVE_OCCUPANTS, "hive occupants");

                final List<Bees.Occupant> occupants = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    final BinaryTag entityData = buf.readSizedNbt(MAX_ENTITY_NBT_BYTES);

                    if (!(entityData instanceof final CompoundBinaryTag compound)) {
                        throw new DecoderException("Hive occupant " + i + " is not a compound tag");
                    }
                    final int ticksInHive = buf.readVarInt();
                    final int minTicksInHive = buf.readVarInt();

                    try {
                        occupants.add(new Bees.Occupant(compound, minTicksInHive, ticksInHive));
                    } catch (final IllegalArgumentException e) {
                        throw new DecoderException("Implausible hive occupant: " + e.getMessage(), e);
                    }
                }

                return Bees.of(occupants);
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
