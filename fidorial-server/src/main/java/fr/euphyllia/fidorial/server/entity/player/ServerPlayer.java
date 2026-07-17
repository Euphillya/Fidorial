package fr.euphyllia.fidorial.server.entity.player;

import fr.euphyllia.fidorial.api.entity.GameMode;
import fr.euphyllia.fidorial.api.entity.Player;
import fr.euphyllia.fidorial.api.entity.PlayerProfile;
import fr.euphyllia.fidorial.api.inventory.PlayerInventory;
import fr.euphyllia.fidorial.api.permission.*;
import fr.euphyllia.fidorial.api.plugin.Plugin;
import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.api.world.World;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.AbstractEntity;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.language.LanguageManager;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundGameEventPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundPlayerAbilitiesPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundPlayerInfoGameModePacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSystemChatPacket;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;

import java.util.Locale;
import java.util.Set;

public final class ServerPlayer extends AbstractEntity implements Player, PermissibleBaseHolder {

    private final PlayerProfile profile;
    private final PlayerInventory inventory;
    private final ClientConnection connection;
    private final PermissibleBase perm;

    private volatile GameMode gameMode;
    private volatile float health = 20f;
    private volatile int selectedSlot;
    private volatile int lastTeleportId;

    private Locale locale;

    public ServerPlayer(int entityId, PlayerProfile profile, PlayerInventory inventory,
                        GameMode gameMode, ClientConnection connection, World world, Location location) {
        super(entityId, profile.uuid(), EntityTypes.PLAYER, world, location);
        this.profile = profile;
        this.inventory = inventory;
        this.gameMode = gameMode;
        this.connection = connection;
        this.locale = connection.locale();
        this.perm = new PermissibleBase(new PlayerOperator(), this,
                FidorialServer.getInstance().plugins());
    }

    @Override
    public PermissibleBase permissionBase() {
        return perm;
    }

    private PermissionService permissionService() {
        return FidorialServer.getInstance().services()
                .find(PermissionService.class).orElse(null);
    }

    @Override
    public boolean isOp() {
        return FidorialServer.getInstance().operators().isOp(profile.uuid());
    }

    @Override
    public void setOp(boolean value) {
        FidorialServer.getInstance().operators().setOp(profile.uuid(), profile.name(), value);
        recalculatePermissions();
    }

    @Override
    public boolean isPermissionSet(String name) {
        PermissionService service = permissionService();
        return service != null ? service.isPermissionSet(this, name) : perm.isPermissionSet(name);
    }

    @Override
    public boolean isPermissionSet(Permission permission) {
        PermissionService service = permissionService();
        return service != null ? service.isPermissionSet(this, permission) : perm.isPermissionSet(permission);
    }

    @Override
    public boolean hasPermission(String name) {
        PermissionService service = permissionService();
        return service != null ? service.hasPermission(this, name) : perm.hasPermission(name);
    }

    @Override
    public boolean hasPermission(Permission permission) {
        PermissionService service = permissionService();
        return service != null ? service.hasPermission(this, permission) : perm.hasPermission(permission);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return perm.addAttachment(plugin);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return perm.addAttachment(plugin, name, value);
    }

    @Override
    public void removeAttachment(PermissionAttachment attachment) {
        perm.removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        PermissionService service = permissionService();
        if (service != null) {
            service.recalculate(this);
        } else {
            perm.recalculatePermissions();
        }
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        PermissionService service = permissionService();
        return service != null ? service.effectivePermissions(this) : perm.getEffectivePermissions();
    }

    public void clearPermissions() {
        perm.clearPermissions();
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
    public void setLocale(final String language) {
        this.locale = Locale.forLanguageTag(language.replace('_', '-'));
    }

    @Override
    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    @Override
    public Locale locale() {
        return this.locale;
    }

    @Override
    public void sendMessage(final Component message) {
        connection.send(new ClientboundSystemChatPacket(LanguageManager.render(message, locale()), false));
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

    private final class PlayerOperator implements ServerOperator {

        @Override
        public boolean isOp() {
            return FidorialServer.getInstance().operators().isOp(profile.uuid());
        }

        @Override
        public void setOp(boolean value) {
            FidorialServer.getInstance().operators().setOp(profile.uuid(), profile.name(), value);
        }
    }
}