package fr.euphyllia.fidorial.server.entity.player;

import fr.euphyllia.fidorial.api.entity.Player;
import fr.euphyllia.fidorial.api.entity.PlayerProfile;
import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.entity.AbstractEntity;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSystemChatPacket;

public final class ServerPlayer extends AbstractEntity implements Player {

    private final PlayerProfile profile;
    private final PlayerInventory inventory;
    private final ClientConnection connection;

    private volatile float health = 20f;
    private volatile int selectedSlot;
    private volatile int lastTeleportId;

    public ServerPlayer(int entityId, PlayerProfile profile, PlayerInventory inventory,
                        ClientConnection connection, World world, Location location) {
        super(entityId, profile.uuid(), EntityTypes.PLAYER, world, location);
        this.profile = profile;
        this.inventory = inventory;
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
