package fr.euphyllia.fidorial.testplugin.items;

import fr.fidorial.attribute.Attribute;
import fr.fidorial.attribute.AttributeModifier;
import fr.fidorial.inventory.EquipmentSlotGroup;
import fr.fidorial.item.DataComponentTypes;
import fr.fidorial.item.ItemDefinition;
import fr.fidorial.item.ItemRegistry;
import fr.fidorial.item.component.AttackRange;
import fr.fidorial.item.component.BannerPatterns;
import fr.fidorial.item.component.Bees;
import fr.fidorial.item.component.DyeColor;
import fr.fidorial.item.component.ItemAttributeModifiers;
import fr.fidorial.registry.keys.BannerPatternKeys;
import fr.fidorial.registry.keys.ItemKeys;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

public class MagicItems {

    public static final Key MAGIC_SWORD = Key.key("fidorialtest", "epee_magique");

    public static final Key NETHERITE_SWORD = ItemKeys.NETHERITE_SWORD.key();

    private static final int SWORD_DURABILITY = 500;

    private static final float SWORD_REACH = 4.5F;
    private static final float SWORD_HITBOX_MARGIN = 0.125F;

    private static final double SWORD_ATTACK_DAMAGE = 12.0D;
    private static final double SWORD_ATTACK_SPEED = -2.4D;
    private static final double SWORD_MOVEMENT_BONUS = 0.1D;

    public static final Key MAGIC_BANNER = Key.key("fidorialtest", "banniere_magique");
    public static final Key BLACK_BANNER = ItemKeys.BLACK_BANNER.key();

    private static final int SHIELD_DURABILITY = 336;
    public static final Key MAGIC_SHIELD = Key.key("fidorialtest", "bouclier_magique");
    public static final Key SHIELD = ItemKeys.SHIELD.key();

    public static final Key MAGIC_HIVE = Key.key("fidorialtest", "ruche_magique");
    public static final Key BEE_NEST = ItemKeys.BEE_NEST.key();
    private static final int HIVE_MIN_TICKS = 60;

    private MagicItems() {
    }

    public static void registerMagicSword(final ItemRegistry items, final Object owner, final ComponentLogger logger) {
        final ItemDefinition sword = ItemDefinition.builder(MAGIC_SWORD, NETHERITE_SWORD)
                .maxStackSize(1)
                .maxDamage(SWORD_DURABILITY)
                .edit(components -> components
                        .itemName(Component.text("Magic Sword", NamedTextColor.LIGHT_PURPLE))
                        .lore(Component.text("First line"), Component.text("Second line"))
                        .attackRange(AttackRange.builder()
                                .maxReach(SWORD_REACH)
                                .hitboxMargin(SWORD_HITBOX_MARGIN)
                                .build()
                        )
                        .attributeModifiers(ItemAttributeModifiers.of(
                                AttributeModifier.of(
                                        Attribute.ATTACK_DAMAGE,
                                        Key.key("fidorialtest", "sword_damage"),
                                        SWORD_ATTACK_DAMAGE,
                                        AttributeModifier.Operation.ADD_VALUE,
                                        EquipmentSlotGroup.MAIN_HAND
                                ),
                                AttributeModifier.of(
                                        Attribute.ATTACK_SPEED,
                                        Key.key("fidorialtest", "sword_speed"),
                                        SWORD_ATTACK_SPEED,
                                        AttributeModifier.Operation.ADD_VALUE,
                                        EquipmentSlotGroup.MAIN_HAND
                                ),
                                AttributeModifier.of(
                                        Attribute.MOVEMENT_SPEED,
                                        Key.key("fidorialtest", "sword_movement"),
                                        SWORD_MOVEMENT_BONUS,
                                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                                        EquipmentSlotGroup.MAIN_HAND
                                )
                        ))
                        .glint(true))
                .build();

        items.register(sword, owner);
        logger.info("[TestPlugin] Item {} registered, rendered as {}", MAGIC_SWORD, NETHERITE_SWORD.asString());
    }

    public static void registerMagicBanner(final ItemRegistry items, final Object owner, final ComponentLogger logger) {
        final BannerPatterns patterns = BannerPatterns.EMPTY
                .plus(BannerPatternKeys.TRIANGLE_TOP.key(), DyeColor.RED)
                .plus(BannerPatternKeys.CROSS.key(), DyeColor.WHITE);

        final ItemDefinition banner = ItemDefinition.builder(MAGIC_BANNER, BLACK_BANNER)
                .maxStackSize(16)
                .edit(components -> components
                        .itemName(Component.text("Magic Banner", NamedTextColor.LIGHT_PURPLE))
                        .set(DataComponentTypes.BANNER_PATTERNS, patterns))
                .build();

        items.register(banner, owner);
        logger.info("[TestPlugin] Item {} registered, rendered as {}", MAGIC_BANNER, BLACK_BANNER.asString());
    }

    public static void registerMagicShield(final ItemRegistry items, final Object owner, final ComponentLogger logger) {
        final ItemDefinition shield = ItemDefinition.builder(MAGIC_SHIELD, SHIELD)
                .maxStackSize(1)
                .maxDamage(SHIELD_DURABILITY)
                .edit(components -> components
                        .set(DataComponentTypes.BASE_COLOR, DyeColor.LIME)
                        .set(DataComponentTypes.BANNER_PATTERNS, BannerPatterns.EMPTY
                                .plus(BannerPatternKeys.STRIPE_DOWNRIGHT.key(), DyeColor.PURPLE)))
                .build();

        items.register(shield, owner);
        logger.info("[TestPlugin] Item {} registered, rendered as {}", MAGIC_SHIELD, SHIELD.asString());
    }

    public static void registerMagicHive(final ItemRegistry items, final Object owner, final ComponentLogger logger) {
        final Bees bees = Bees.EMPTY
                .plus(Bees.Occupant.of(CompoundBinaryTag.builder()
                        .putString("id", "minecraft:bee")
                        .putString("CustomName", "\"Maya\"")
                        .build(), HIVE_MIN_TICKS))
                .plus(Bees.Occupant.of(CompoundBinaryTag.builder()
                        .putString("id", "minecraft:bee")
                        .build(), HIVE_MIN_TICKS));

        final ItemDefinition hive = ItemDefinition.builder(MAGIC_HIVE, BEE_NEST)
                .maxStackSize(1)
                .edit(components -> components
                        .itemName(Component.text("Magic Hive", NamedTextColor.LIGHT_PURPLE))
                        .set(DataComponentTypes.BEES, bees))
                .build();

        items.register(hive, owner);
        logger.info("[TestPlugin] Item {} registered, rendered as {}", MAGIC_HIVE, BEE_NEST.asString());
    }
}
