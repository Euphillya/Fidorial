package fr.euphyllia.fidorial.api.entity;

import fr.euphyllia.fidorial.api.command.CommandSender;
import fr.euphyllia.fidorial.api.inventory.PlayerInventory;

import java.util.Locale;
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
