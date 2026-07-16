package fr.euphyllia.fidorial.server.entity.player;

import fr.euphyllia.fidorial.api.entity.GameMode;
import fr.euphyllia.fidorial.api.entity.Player;
import fr.euphyllia.fidorial.api.entity.PlayerProfile;
import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.entity.AbstractEntity;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundGameEventPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundPlayerAbilitiesPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundPlayerInfoGameModePacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSystemChatPacket;

public final class ServerPlayer extends AbstractEntity implements Player {

    private final PlayerProfile profile;
    private final PlayerInventory inventory;
    private final ClientConnection connection;

    private volatile GameMode gameMode;
    private volatile float health = 20f;
    private volatile int selectedSlot;
    private volatile int lastTeleportId;

    public ServerPlayer(int entityId, PlayerProfile profile, PlayerInventory inventory,
                        GameMode gameMode, ClientConnection connection, World world, Location location) {
        super(entityId, profile.uuid(), EntityTypes.PLAYER, world, location);
        this.profile = profile;
        this.inventory = inventory;
        this.gameMode = gameMode;
        this.connection = connection;
    }

    @Override
    public PlayerProfile profile() {
        return profile;
    }

    public PlayerInventory inventory() {
        return inventory;
    }

    public ClientConnection connection() {
        return connection;
    }

    @Override
    public float health() {
        return health;
    }

    @Override
    public void setHealth(float health) {
        this.health = Math.clamp(health, 0f, maxHealth());
    }

    @Override
    public float maxHealth() {
        return 20f;
    }

    @Override
    public void sendMessage(String message) {
        connection.send(new ClientboundSystemChatPacket(message, false));
    }

    @Override
    public void kick(String reason) {
        connection.disconnect(reason);
    }

    @Override
    public GameMode gameMode() {
        return gameMode;
    }

    @Override
    public void setGameMode(GameMode gameMode) {
        if (gameMode == null || gameMode == this.gameMode) {
            return;
        }
        this.gameMode = gameMode;
        connection.send(new ClientboundGameEventPacket(
                ClientboundGameEventPacket.CHANGE_GAME_MODE, gameMode.id()));
        connection.send(ClientboundPlayerAbilitiesPacket.forGameMode(gameMode));
        connection.server().broadcast(new ClientboundPlayerInfoGameModePacket(uuid(), gameMode.id()));
    }

    public int selectedSlot() {
        return selectedSlot;
    }

    public void setSelectedSlot(int selectedSlot) {
        if (selectedSlot < 0 || selectedSlot > 8) {
            throw new IllegalArgumentException("slot de hotbar invalide : " + selectedSlot);
        }
        this.selectedSlot = selectedSlot;
    }

    public int nextTeleportId() {
        return ++lastTeleportId;
    }
}
