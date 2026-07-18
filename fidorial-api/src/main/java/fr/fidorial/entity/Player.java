package fr.fidorial.entity;

import fr.fidorial.command.CommandSender;
import fr.fidorial.inventory.PlayerInventory;

import java.util.UUID;

public interface Player extends LivingEntity, CommandSender {

    PlayerProfile profile();

    @Override
    default UUID uuid() {
        return profile().uuid();
    }

    @Override
    default String name() {
        return profile().name();
    }

    void kick(String reason);

    PlayerInventory inventory();

    GameMode gameMode();

    void setGameMode(GameMode gameMode);

}
