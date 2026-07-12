package fr.euphyllia.fidorial.server.entity.player;

import fr.euphyllia.fidorial.api.entity.PlayerProfile;
import fr.euphyllia.fidorial.server.entity.Entity;

import java.util.UUID;

public record Player(PlayerProfile profile, PlayerInventory inventory) implements Entity {

    public Player(PlayerProfile profile) {
        this(profile, new PlayerInventory());
    }

    public UUID uuid() {
        return profile.uuid();
    }

    public String name() {
        return profile.name();
    }

}
