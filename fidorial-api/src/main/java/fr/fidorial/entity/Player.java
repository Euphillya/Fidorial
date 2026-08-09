package fr.fidorial.entity;

import fr.fidorial.command.CommandSender;
import fr.fidorial.command.CommandSource;
import fr.fidorial.inventory.EnderChestInventory;
import fr.fidorial.inventory.PlayerInventory;
import fr.fidorial.permission.PermissionHolder;
import net.kyori.adventure.bossbar.BossBarViewer;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.object.ObjectContentsLike;

import java.net.InetAddress;
import java.util.UUID;

public interface Player extends LivingEntity, PermissionHolder, CommandSource, CommandSender, Identified, BossBarViewer, ObjectContentsLike {

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
    default Identity identity() {
        return Identity.identity(this.uuid());
    }

    @Override
    default UUID uuid() {
        return profile().uuid();
    }

    @Override
    default String name() {
        return profile().name();
    }

    /**
     * Gets the address this player is connected from.
     *
     * @return the client address
     * @throws IllegalStateException if the connection has no resolvable IP address
     *
     * @since 0.1.0
     */
    InetAddress address();

    void kick(Component reason);

    PlayerInventory inventory();

    EnderChestInventory enderChest();

    GameMode gameMode();

    void setGameMode(GameMode gameMode);
}
