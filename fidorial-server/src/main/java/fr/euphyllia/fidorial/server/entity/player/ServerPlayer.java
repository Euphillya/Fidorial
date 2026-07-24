package fr.euphyllia.fidorial.server.entity.player;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.AbstractEntity;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundCommandsPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundEntityEventPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundGameEventPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundPlayerAbilitiesPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundPlayerInfoGameModePacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSoundEntityPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSoundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundStopSoundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSystemChatPacket;
import fr.fidorial.command.CommandSender;
import fr.fidorial.entity.Entity;
import fr.fidorial.entity.GameMode;
import fr.fidorial.entity.Player;
import fr.fidorial.entity.PlayerProfile;
import fr.fidorial.inventory.PlayerInventory;
import fr.fidorial.permission.PermissionResolver;
import fr.fidorial.permission.PermissionState;
import fr.fidorial.permission.PermissionStateHolder;
import fr.fidorial.translation.TranslationStore;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;

import java.util.Locale;

public final class ServerPlayer extends AbstractEntity implements Player, PermissionStateHolder {

    // https://minecraft.wiki/w/Java_Edition_protocol/Entity_metadata#Avatar
    public static final int MD_MAIN_HAND = 15; // Main hand (0: left, 1: right)
    public static final int MD_DISPLAYED_SKIN_PARTS =
            16; // The Displayed Skin Parts bit mask that is sent in Client Information

    private final PlayerProfile profile;
    private final PlayerInventory inventory;
    private final ClientConnection connection;
    private final PermissionState permissions;

    private volatile GameMode gameMode;
    private volatile float health = 20f;
    private volatile int selectedSlot;
    private volatile int lastTeleportId;
    private volatile boolean flying;

    private Locale locale;

    public ServerPlayer(
            final int entityId,
            final PlayerProfile profile,
            final PlayerInventory inventory,
            final GameMode gameMode,
            final ClientConnection connection,
            final World world,
            final Location location
    ) {
        super(entityId, profile.uuid(), EntityTypes.PLAYER, world, location);
        this.profile = profile;
        this.inventory = inventory;
        this.gameMode = gameMode;
        this.connection = connection;
        this.locale = connection.locale();
        this.permissions = new PermissionState(
                this,
                FidorialServer.getInstance().permissions(),
                () -> FidorialServer.getInstance()
                        .services()
                        .find(PermissionResolver.class)
                        .map(java.util.List::of)
                        .orElseGet(java.util.List::of));
    }

    @Override
    public PermissionState permissions() {
        return permissions;
    }

    @Override
    public boolean isOperator() {
        return FidorialServer.getInstance().operators().isOp(profile.uuid());
    }

    @Override
    public void setOperator(final boolean operator) {
        FidorialServer.getInstance().operators().setOp(profile.uuid(), profile.name(), operator);
        invalidatePermissions();
    }

    @Override
    public void invalidatePermissions() {
        permissions.invalidate();
        updateClientPermissionLevel();
        refreshCommands();
    }

    private void updateClientPermissionLevel() {
        final int level = isOperator() ? 4 : 0;
        connection.send(new ClientboundEntityEventPacket(entityId(), (byte) (24 + level)));
    }

    @Override
    public void refreshCommands() {
        connection.send(new ClientboundCommandsPacket(
                connection.server().commandManager().dispatcher(), this));
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
        final Location loc = location();

        final BlockPos below =
                new BlockPos((int) Math.floor(loc.x()), (int) Math.floor(loc.y() - 0.01), (int) Math.floor(loc.z()));

        final int stateId = world().getBlockStateId(below);

        return stateId != 0;
    }

    public void setFlying(final boolean flying) {
        this.flying = flying;
    }

    @Override
    public float health() {
        return health;
    }

    @Override
    public void setHealth(final float health) {
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
        } else if (emitter instanceof final Entity entity) {
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
    public void kick(final String reason) {
        connection.disconnect(reason);
    }

    @Override
    public GameMode gameMode() {
        return gameMode;
    }

    @Override
    public void setGameMode(final GameMode gameMode) {
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

    public void setSelectedSlot(final int selectedSlot) {
        if (selectedSlot < 0 || selectedSlot > 8) {
            throw new IllegalArgumentException("slot de hotbar invalide : " + selectedSlot);
        }
        this.selectedSlot = selectedSlot;
    }

    public int nextTeleportId() {
        final var id = lastTeleportId;
        lastTeleportId = id + 1;
        return lastTeleportId;
    }

    @Override
    public CommandSender sender() {
        return this;
    }
}
