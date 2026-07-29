package fr.fidorial.entity;

import fr.fidorial.command.CommandSender;
import fr.fidorial.command.CommandSource;
import fr.fidorial.inventory.EnderChestInventory;
import fr.fidorial.inventory.PlayerInventory;
import fr.fidorial.permission.PermissionHolder;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.UUID;

public interface Player extends LivingEntity, PermissionHolder, CommandSource, CommandSender {

    void refreshCommands();

    PlayerProfile profile();

    @Override
    default UUID uuid() {
        return profile().uuid();
    }

    @Override
    default String name() {
        return profile().name();
    }

    void kick(Component reason);

    PlayerInventory inventory();

    EnderChestInventory enderChest();

    GameMode gameMode();

    void setGameMode(GameMode gameMode);

    Collection<? extends BossBar> bossBars();
}
