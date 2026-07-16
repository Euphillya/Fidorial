package fr.euphyllia.fidorial.api.storage.player;

import fr.euphyllia.fidorial.api.inventory.PlayerInventory;

import java.io.IOException;
import java.util.UUID;

public interface PlayerInventoryStorage {

    PlayerInventory load(UUID uuid) throws IOException;

    void save(UUID uuid, PlayerInventory inventory) throws IOException;
}
