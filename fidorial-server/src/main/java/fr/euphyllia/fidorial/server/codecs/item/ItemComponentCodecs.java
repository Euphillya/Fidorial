package fr.euphyllia.fidorial.server.codecs.item;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.euphyllia.fidorial.server.codecs.DispatchCodecs;
import fr.euphyllia.fidorial.server.codecs.adventure.ComponentCodecs;
import fr.euphyllia.fidorial.server.codecs.adventure.NbtCodecs;
import fr.fidorial.attribute.AttributeModifier;
import fr.fidorial.attribute.AttributeModifierDisplay;
import fr.fidorial.inventory.EquipmentSlotGroup;
import fr.fidorial.item.component.AttackRange;
import fr.fidorial.item.component.BannerPattern;
import fr.fidorial.item.component.BannerPatterns;
import fr.fidorial.item.component.Bees;
import fr.fidorial.item.component.DyeColor;
import fr.fidorial.item.component.ItemAttributeModifiers;

import java.util.List;
import java.util.Objects;

import static fr.euphyllia.fidorial.server.codecs.CommonCodecs.KEY_CODEC;

public final class ItemComponentCodecs {

    private static final Codec<Float> REACH_CODEC = Codec.floatRange(0.0F, AttackRange.MAX_REACH_LIMIT);

    public static final Codec<AttackRange> ATTACK_RANGE_CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    REACH_CODEC.optionalFieldOf("min_reach", AttackRange.DEFAULT_MIN_REACH)
                            .forGetter(AttackRange::minReach),
                    REACH_CODEC.optionalFieldOf("max_reach", AttackRange.DEFAULT_MAX_REACH)
                            .forGetter(AttackRange::maxReach),
                    REACH_CODEC.optionalFieldOf("min_creative_reach", AttackRange.DEFAULT_MIN_CREATIVE_REACH)
                            .forGetter(AttackRange::minCreativeReach),
                    REACH_CODEC.optionalFieldOf("max_creative_reach", AttackRange.DEFAULT_MAX_CREATIVE_REACH)
                            .forGetter(AttackRange::maxCreativeReach),
                    Codec.floatRange(0.0F, AttackRange.MAX_HITBOX_MARGIN)
                            .optionalFieldOf("hitbox_margin", AttackRange.DEFAULT_HITBOX_MARGIN)
                            .forGetter(AttackRange::hitboxMargin),
                    Codec.floatRange(0.0F, AttackRange.MAX_MOB_FACTOR)
                            .optionalFieldOf("mob_factor", AttackRange.DEFAULT_MOB_FACTOR)
                            .forGetter(AttackRange::mobFactor)
            ).apply(instance, AttackRange::new));

    private static final Codec<AttributeModifier.Operation> OPERATION_CODEC = Codec.STRING.comapFlatMap(
            name -> {
                for (final AttributeModifier.Operation operation : AttributeModifier.Operation.values()) {
                    if (operation.serializedName().equals(name)) {
                        return DataResult.success(operation);
                    }
                }
                return DataResult.error(() -> "Unknown attribute modifier operation: " + name);
            },
            AttributeModifier.Operation::serializedName);

    private static final Codec<EquipmentSlotGroup> SLOT_GROUP_CODEC = Codec.STRING.comapFlatMap(
            name -> {
                for (final EquipmentSlotGroup group : EquipmentSlotGroup.values()) {
                    if (group.serializedName().equals(name)) {
                        return DataResult.success(group);
                    }
                }
                return DataResult.error(() -> "Unknown equipment slot group: " + name);
            },
            EquipmentSlotGroup::serializedName);

    private static final MapCodec<AttributeModifierDisplay> DISPLAY_MAP_CODEC =
            DispatchCodecs.matcher("type", List.of(
                    DispatchCodecs.Variant.of(
                            AttributeModifierDisplay.Type.DEFAULT.serializedName(), null, true,
                            display -> display.type() == AttributeModifierDisplay.Type.DEFAULT,
                            MapCodec.unit(AttributeModifierDisplay.DEFAULT)),
                    DispatchCodecs.Variant.<AttributeModifierDisplay, AttributeModifierDisplay>of(
                            AttributeModifierDisplay.Type.HIDDEN.serializedName(), null, true,
                            display -> display.type() == AttributeModifierDisplay.Type.HIDDEN,
                            MapCodec.unit(AttributeModifierDisplay.HIDDEN)),
                    DispatchCodecs.Variant.of(
                            AttributeModifierDisplay.Type.OVERRIDE.serializedName(), null, true,
                            display -> display.type() == AttributeModifierDisplay.Type.OVERRIDE,
                            RecordCodecBuilder.mapCodec(instance -> instance.group(
                                    ComponentCodecs.COMPONENT_CODEC.fieldOf("value")
                                            .forGetter(display -> Objects.requireNonNull(display.value(), "value"))
                            ).apply(instance, AttributeModifierDisplay::override)))));

    public static final Codec<AttributeModifier> ATTRIBUTE_MODIFIER_CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    KEY_CODEC.fieldOf("type").forGetter(AttributeModifier::attribute),
                    KEY_CODEC.fieldOf("id").forGetter(AttributeModifier::id),
                    Codec.DOUBLE.fieldOf("amount").forGetter(AttributeModifier::amount),
                    OPERATION_CODEC.fieldOf("operation").forGetter(AttributeModifier::operation),
                    SLOT_GROUP_CODEC.optionalFieldOf("slot", EquipmentSlotGroup.ANY)
                            .forGetter(AttributeModifier::slot),
                    DISPLAY_MAP_CODEC.codec().optionalFieldOf("display", AttributeModifierDisplay.DEFAULT)
                            .forGetter(AttributeModifier::display)
            ).apply(instance, AttributeModifier::new));

    public static final Codec<ItemAttributeModifiers> ATTRIBUTE_MODIFIERS_CODEC = ATTRIBUTE_MODIFIER_CODEC
            .listOf()
            .xmap(ItemAttributeModifiers::of, ItemAttributeModifiers::modifiers);

    public static final Codec<DyeColor> DYE_COLOR_CODEC = Codec.STRING.comapFlatMap(
            name -> {
                final DyeColor color = DyeColor.byName(name);
                return color == null
                        ? DataResult.error(() -> "Unknown dye colour: " + name)
                        : DataResult.success(color);
            },
            DyeColor::serializedName);

    private static final Codec<BannerPattern.Inline> INLINE_BANNER_PATTERN_CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    KEY_CODEC.fieldOf("asset_id").forGetter(BannerPattern.Inline::assetId),
                    Codec.STRING.fieldOf("translation_key").forGetter(BannerPattern.Inline::translationKey)
            ).apply(instance, BannerPattern.Inline::new));

    private static final Codec<BannerPattern> BANNER_PATTERN_CODEC = Codec.either(
                    KEY_CODEC, INLINE_BANNER_PATTERN_CODEC)
            .xmap(
                    either -> either.map(BannerPattern::reference, pattern -> pattern),
                    pattern -> pattern instanceof final BannerPattern.Reference reference
                            ? Either.left(reference.pattern())
                            : Either.right((BannerPattern.Inline) pattern));

    private static final Codec<BannerPatterns.Layer> BANNER_LAYER_CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    BANNER_PATTERN_CODEC.fieldOf("pattern").forGetter(BannerPatterns.Layer::pattern),
                    DYE_COLOR_CODEC.fieldOf("color").forGetter(BannerPatterns.Layer::color)
            ).apply(instance, BannerPatterns.Layer::new));

    public static final Codec<BannerPatterns> BANNER_PATTERNS_CODEC = BANNER_LAYER_CODEC
            .listOf()
            .xmap(BannerPatterns::of, BannerPatterns::layers);

    private static final Codec<Bees.Occupant> HIVE_OCCUPANT_CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    NbtCodecs.COMPOUND_BINARY_TAG_CODEC.fieldOf("entity_data")
                            .forGetter(Bees.Occupant::entityData),
                    Codec.INT.fieldOf("min_ticks_in_hive").forGetter(Bees.Occupant::minTicksInHive),
                    Codec.INT.fieldOf("ticks_in_hive").forGetter(Bees.Occupant::ticksInHive)
            ).apply(instance, Bees.Occupant::new));

    public static final Codec<Bees> BEES_CODEC = HIVE_OCCUPANT_CODEC
            .listOf()
            .xmap(Bees::of, Bees::occupants);

    private ItemComponentCodecs() {
        throw new UnsupportedOperationException("ItemComponentCodecs cannot be instantiated.");
    }
}
