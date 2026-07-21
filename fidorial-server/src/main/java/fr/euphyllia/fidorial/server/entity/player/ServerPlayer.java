package fr.euphyllia.fidorial.server.entity.player;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.AbstractEntity;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.*;
import fr.fidorial.command.CommandSender;
import fr.fidorial.entity.Entity;
import fr.fidorial.entity.GameMode;
import fr.fidorial.entity.Player;
import fr.fidorial.entity.PlayerProfile;
import fr.fidorial.inventory.PlayerInventory;
import fr.fidorial.permission.PermissibleBase;
import fr.fidorial.permission.PermissibleBaseHolder;
import fr.fidorial.permission.Permission;
import fr.fidorial.permission.PermissionAttachment;
import fr.fidorial.permission.PermissionAttachmentInfo;
import fr.fidorial.permission.PermissionService;
import fr.fidorial.permission.ServerOperator;
import fr.fidorial.plugin.Plugin;
import fr.fidorial.translation.TranslationStore;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

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
    private volatile boolean flying;

    private Locale locale;

    public ServerPlayer(
            int entityId,
            PlayerProfile profile,
            PlayerInventory inventory,
            GameMode gameMode,
            ClientConnection connection,
            World world,
            Location location
    ) {
        super(entityId, profile.uuid(), EntityTypes.PLAYER, world, location);
        this.profile = profile;
        this.inventory = inventory;
        this.gameMode = gameMode;
        this.connection = connection;
        this.locale = connection.locale();
        this.perm = new PermissibleBase(
                new PlayerOperator(), this, FidorialServer.getInstance().plugins());
    }

    @Override
    public PermissibleBase permissionBase() {
        return perm;
    }

    private @Nullable PermissionService permissionService() {
        return FidorialServer.getInstance()
                .services()
                .find(PermissionService.class)
                .orElse(null);
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
        updateClientPermissionLevel();
        refreshCommands();
    }

    private void updateClientPermissionLevel() {
        int level = isOp() ? 4 : 0;
        connection.send(new ClientboundEntityEventPacket(entityId(), (byte) (24 + level)));
    }

    @Override
    public void refreshCommands() {
        connection.send(new ClientboundCommandsPacket(
                connection.server().commandManager().dispatcher(), this));
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

    public boolean isFlying() {
        return /*flying &&*/ !isOnGround(); // broken
    }

    private boolean isOnGround() {
        Location loc = location();

        BlockPos below =
                new BlockPos((int) Math.floor(loc.x()), (int) Math.floor(loc.y() - 0.01), (int) Math.floor(loc.z()));

        int stateId = world().getBlockStateId(below);

        return stateId != 0;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
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

    public void setLocale(final String language) {
        this.locale = Locale.forLanguageTag(language.replace('_', '-'));
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public Locale locale() {
        return this.locale;
    }

    @Override
    public void sendMessage(final Component message) {
        connection.send(new ClientboundSystemChatPacket(TranslationStore.render(message, locale()), false));
    }

    @Override
    public void playSound(final Sound sound) {
        final Location loc = location();
        connection.send(new ClientboundSoundPacket(sound, loc.x(), loc.y(), loc.z()));
    }

    @Override
    public void playSound(final Sound sound, final double x, final double y, final double z) {
        connection.send(new ClientboundSoundPacket(sound, x, y, z));
    }

    @Override
    public void playSound(final Sound sound, final Sound.Emitter emitter) {
        if (emitter == Sound.Emitter.self()) {
            connection.send(new ClientboundSoundEntityPacket(sound, entityId()));
        } else if (emitter instanceof Entity entity) {
            connection.send(new ClientboundSoundEntityPacket(sound, entity.entityId()));
        } else {
            playSound(sound);
        }
    }

    @Override
    public void stopSound(final SoundStop stop) {
        connection.send(new ClientboundStopSoundPacket(stop.source(), stop.sound()));
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
        if (gameMode == this.gameMode) {
            return;
        }
        this.gameMode = gameMode;
        connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.CHANGE_GAME_MODE, gameMode.id()));
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
        var id = lastTeleportId;
        lastTeleportId = id + 1;
        return lastTeleportId;
    }

    @Override
    public CommandSender sender() {
        return this;
    }

    @Override
    public boolean isConsole() {
        return false;
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
