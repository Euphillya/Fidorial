package fr.euphyllia.fidorial.testplugin.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.euphyllia.fidorial.testplugin.TestPlugin;
import fr.euphyllia.fidorial.testplugin.items.MagicItems;
import fr.fidorial.command.CommandSender;
import fr.fidorial.command.CommandSource;
import fr.fidorial.entity.Player;
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

        final ItemStack magicSword = plugin.server().items().create(MagicItems.MAGIC_SWORD);

        player.inventory().set(player.selectedSlot(), magicSword);
        player.updateInventory();

        plugin.msg(player, "[TestPlugin] You receive " + magicSword);
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
        plugin.msg(player, "[TestPlugin] damageable=" + held.isDamageable()
                + " damage=" + held.damage()
                + "/" + held.maxDamage()
                + " restant=" + held.remainingDurability()
                + " willBreak=" + held.willBreak());
        plugin.msg(player, "[TestPlugin] maxStackSize=" + held.maxStackSize()
                + " hasCustomName=" + held.hasCustomName());

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

        if (held.isEmpty() || !held.isDamageable()) {
            plugin.msg(player, "[TestPlugin] This item cannot be damaged..");
            return Command.SINGLE_SUCCESS;
        }

        final ItemStack damaged = held.damaged(1);
        player.inventory().set(slot, damaged);

        plugin.msg(player, "[TestPlugin] Damage inflicted: " + damaged.damage()
                + "/" + damaged.maxDamage()
                + " (willBreak=" + damaged.willBreak() + ")");

        return Command.SINGLE_SUCCESS;
    }
}
