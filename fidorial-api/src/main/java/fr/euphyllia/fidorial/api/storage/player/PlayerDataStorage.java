package fr.euphyllia.fidorial.api.storage.player;

import fr.euphyllia.fidorial.api.entity.GameMode;

import java.io.IOException;
import java.util.UUID;

public interface PlayerDataStorage {

    record PlayerData(GameMode gameMode) {
    }

    PlayerData load(UUID uuid, PlayerData defaults) throws IOException;

    void save(UUID uuid, PlayerData data) throws IOException;
}
