package fr.euphyllia.fidorial.api.entity;

import fr.euphyllia.fidorial.api.command.CommandSender;

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
}
