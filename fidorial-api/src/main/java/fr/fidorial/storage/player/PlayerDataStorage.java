package fr.fidorial.storage.player;

import fr.fidorial.entity.GameMode;

import java.io.IOException;
import java.util.UUID;

public interface PlayerDataStorage {

    PlayerData load(UUID uuid, PlayerData defaults) throws IOException;

    void save(UUID uuid, PlayerData data) throws IOException;

    /**
     * Checks whether saved data exists for an identity, without loading it.
     *
     * @param uuid the player identity
     * @return {@code true} if data has been saved for that identity
     * @throws IOException if the check fails
     */
    default boolean exists(final UUID uuid) throws IOException {
        return true;
    }

    record PlayerData(GameMode gameMode) {
    }
}
