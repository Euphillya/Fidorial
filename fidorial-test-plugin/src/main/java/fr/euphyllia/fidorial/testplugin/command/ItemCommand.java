package fr.euphyllia.fidorial.testplugin.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.euphyllia.fidorial.testplugin.TestPlugin;
import fr.euphyllia.fidorial.testplugin.items.MagicItems;
import fr.fidorial.command.CommandSender;
import fr.fidorial.command.CommandSource;
import fr.fidorial.entity.Player;
import fr.fidorial.item.DataComponentTypes;
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
                .build();
    }

    private static int give(final CommandContext<CommandSource> ctx) {
        final CommandSender sender = ctx.getSource().sender();
        if (!(sender instanceof final Player player)) {
            plugin.msg(sender, "<red>[TestPlugin] Run this command in-game.</red>");
            return Command.SINGLE_SUCCESS;
        }

        final ItemStack sword = ItemStack.builder(ItemKeys.NETHERITE_SWORD.key())
                .customName(Component.text("Croc de Fidorial", NamedTextColor.LIGHT_PURPLE)
                        .decoration(TextDecoration.ITALIC, false))
                .itemName(Component.text("Epee legendaire"))
                .glint(true)
                .set(DataComponentTypes.MAX_DAMAGE, 250)
                .damage(80)
                .build();

        final ItemStack magicSword = plugin.server().items().create(MagicItems.MAGIC_SWORD);

        player.inventory().set(player.selectedSlot(), magicSword);
        player.updateInventory();

        plugin.msg(player, "[TestPlugin] Vous recevez " + magicSword);
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
            plugin.msg(player, "[TestPlugin] Vous ne tenez rien en main.");
            return Command.SINGLE_SUCCESS;
        }

        player.sendMessage(Component.text("[TestPlugin] Item en main: ").append(held.displayName()));
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
            plugin.msg(player, "[TestPlugin] Cet item ne peut pas etre endommage.");
            return Command.SINGLE_SUCCESS;
        }

        final ItemStack damaged = held.damaged(1);
        player.inventory().set(slot, damaged);

        plugin.msg(player, "[TestPlugin] Degat inflige: " + damaged.damage()
                + "/" + damaged.maxDamage()
                + " (willBreak=" + damaged.willBreak() + ")");

        return Command.SINGLE_SUCCESS;
    }


}
