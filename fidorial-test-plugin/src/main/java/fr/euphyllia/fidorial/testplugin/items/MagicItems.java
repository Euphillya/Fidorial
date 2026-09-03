package fr.euphyllia.fidorial.testplugin.items;

import fr.fidorial.attribute.Attribute;
import fr.fidorial.attribute.AttributeModifier;
import fr.fidorial.inventory.EquipmentSlotGroup;
import fr.fidorial.item.ItemDefinition;
import fr.fidorial.item.ItemRegistry;
import fr.fidorial.item.component.AttackRange;
import fr.fidorial.item.component.ItemAttributeModifiers;
import fr.fidorial.registry.keys.ItemKeys;
import net.kyori.adventure.key.Key;
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
}
