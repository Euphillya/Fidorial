package fr.euphyllia.fidorial.testplugin.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.euphyllia.fidorial.testplugin.TestPlugin;
import fr.euphyllia.fidorial.testplugin.items.MagicItems;
import fr.fidorial.command.CommandSender;
import fr.fidorial.command.CommandSource;
import fr.fidorial.entity.Player;
import fr.fidorial.item.ItemDefaults;
import fr.fidorial.item.ItemStack;
import fr.fidorial.registry.keys.ItemKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import static fr.fidorial.command.Commands.literal;

public final class ItemCommand {
    private static TestPlugin plugin;

    public ItemCommand(final TestPlugin plugin) {
        ItemCommand.plugin = plugin;
    }

    public LiteralCommandNode<CommandSource> create() {
        return literal("item")
                .then(literal("give").executes(ItemCommand::give))
                .then(literal("info").executes(ItemCommand::info))
                .then(literal("damage").executes(ItemCommand::damage))
                .then(literal("edit").executes(ItemCommand::edit))
                .build();
    }

    private static int give(final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();
        if (!(sender instanceof final Player player)) {
            plugin.msg(sender, "<red>[TestPlugin] Run this command in-game.</red>");
            return Command.SINGLE_SUCCESS;
        }

        final ItemStack sword = ItemStack.builder(ItemKeys.NETHERITE_SWORD.key())
                .edit(components -> components
                        .customName(Component.text("Fidorial's Fang", NamedTextColor.LIGHT_PURPLE)
                                .decoration(TextDecoration.ITALIC, false))
                        .itemName(Component.text("Legendary sword"))
                        .glint(true)
                        .maxDamage(250)
                        .damage(80))
                .build();

        final ItemStack magicBanner = plugin.server().items().create(MagicItems.MAGIC_BANNER);

        player.inventory().set(player.selectedSlot(), magicBanner);
        player.updateInventory();

        plugin.msg(player, "[TestPlugin] You receive " + magicBanner.translationKey());
        return Command.SINGLE_SUCCESS;
    }

    private static int info(final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();
        if (!(sender instanceof final Player player)) {
            plugin.msg(sender, "<red>[TestPlugin] Run this command in-game.</red>");
            return Command.SINGLE_SUCCESS;
        }

        final ItemStack held = player.inventory().get(player.selectedSlot());

        if (held.isEmpty()) {
            plugin.msg(player, "[TestPlugin] You aren't holding anything..");
            return Command.SINGLE_SUCCESS;
        }

        player.sendMessage(Component.text("[TestPlugin] Item in hand: ").append(held.displayName()));
        plugin.msg(player, "[TestPlugin] id=" + held.id()
                + " count=" + held.count()
                + " translationKey=" + held.translationKey());
        final int maxDamage = ItemDefaults.maxDamage(held.id(), held);
        plugin.msg(player, "[TestPlugin] damageable=" + (maxDamage > 0)
                + " damage=" + held.damage()
                + "/" + maxDamage
                + " remaining=" + Math.max(0, maxDamage - held.damage())
                + " willBreak=" + (maxDamage > 0 && held.damage() >= maxDamage - 1));
        plugin.msg(player, "[TestPlugin] maxStackSize=" + ItemDefaults.maxStackSize(held.id(), held)
                + " hasCustomName=" + held.hasCustomName());
        plugin.msg(player, "[TestPlugin] List of attributes: " + held.attributeModifiers().size());

        for (final var entry : held.attributeModifiers().modifiers()) {
            plugin.msg(player, "[TestPlugin]   " + entry.attribute() + " -> " + entry.amount() + " (" + entry.operation() + ") in " + entry.slot());
        }

        plugin.msg(player, "[TestPlugin] hasLore=" + held.hasLore()
                + " lignes=" + held.lore().size());

        int line = 0;
        for (final Component loreLine : held.loreLines()) {
            player.sendMessage(Component.text("[TestPlugin]   lore[" + line++ + "] ").append(loreLine));
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int edit(final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();
        if (!(sender instanceof final Player player)) {
            plugin.msg(sender, "<red>[TestPlugin] Run this command in-game.</red>");
            return Command.SINGLE_SUCCESS;
        }

        final int slot = player.selectedSlot();
        final ItemStack held = player.inventory().get(slot);

        if (held.isEmpty()) {
            plugin.msg(player, "[TestPlugin] You aren't holding anything..");
            return Command.SINGLE_SUCCESS;
        }

        final ItemStack edited = held.edit(components -> components
                .customName(Component.text("Renamed via edit", NamedTextColor.GOLD)
                        .decoration(TextDecoration.ITALIC, false))
                .addLore(Component.text("Line added on " + System.currentTimeMillis()))
                .glint(true));

        player.inventory().set(slot, edited);
        player.updateInventory();

        plugin.msg(player, "[TestPlugin] held unchanged ? " + (held.lore().size() != edited.lore().size())
                + " lines before=" + held.lore().size()
                + " after=" + edited.lore().size());

        return Command.SINGLE_SUCCESS;
    }

    private static int damage(final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();
        if (!(sender instanceof final Player player)) {
            plugin.msg(sender, "<red>[TestPlugin] Run this command in-game.</red>");
            return Command.SINGLE_SUCCESS;
        }

        final int slot = player.selectedSlot();
        final ItemStack held = player.inventory().get(slot);

        final int maxDamage = ItemDefaults.maxDamage(held.id(), held);

        if (held.isEmpty() || maxDamage <= 0) {
            plugin.msg(player, "[TestPlugin] This item cannot be damaged..");
            return Command.SINGLE_SUCCESS;
        }

        final ItemStack damaged = held.edit(
                components -> components.damage(Math.clamp(components.damage() + 1, 0, maxDamage)));
        player.inventory().set(slot, damaged);

        plugin.msg(player, "[TestPlugin] Damage inflicted: " + damaged.damage()
                + "/" + maxDamage
                + " (willBreak=" + (damaged.damage() >= maxDamage - 1) + ")");

        return Command.SINGLE_SUCCESS;
    }
}
