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

    /**
     * Gets the offline handle for this player's identity.
     *
     * @return the handle for this player's identity
     * @since 0.1.0
     */
    default OfflinePlayer offline() {
        return server().offlinePlayers().of(this);
    }

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
