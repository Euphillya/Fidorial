package fr.euphyllia.fidorial.testplugin.items;

import fr.fidorial.item.ItemDefinition;
import fr.fidorial.item.ItemRegistry;
import fr.fidorial.registry.keys.ItemKeys;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

public class MagicItems {

    public static final Key MAGIC_SWORD = Key.key("fidorialtest", "epee_magique");

    public static final Key NETHERITE_SWORD = ItemKeys.NETHERITE_SWORD.key();

    private static final int SWORD_DURABILITY = 500;

    private MagicItems() {
    }

    public static void registerMagicSword(final ItemRegistry items, final Object owner, final ComponentLogger logger) {
        final ItemDefinition sword = ItemDefinition.builder(MAGIC_SWORD, NETHERITE_SWORD)
                .maxStackSize(1)
                .maxDamage(SWORD_DURABILITY)
                .itemName(Component.text("Epee magique", NamedTextColor.LIGHT_PURPLE))
                .lore(Component.text("First line"), Component.text("Second line"))
                .glint(true)
                .build();

        items.register(sword, owner);
        logger.info("[TestPlugin] Item {} enregistre, rendu comme {}", MAGIC_SWORD, NETHERITE_SWORD.asString());
    }
}
