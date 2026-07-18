package fr.fidorial.storage.player;

import fr.fidorial.entity.GameMode;

import java.io.IOException;
import java.util.UUID;

public interface PlayerDataStorage {

    PlayerData load(UUID uuid, PlayerData defaults) throws IOException;

    void save(UUID uuid, PlayerData data) throws IOException;

    record PlayerData(GameMode gameMode) {
    }
}
