package fr.euphyllia.fidorial.server.network.listener;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.ServerConfig;
import fr.euphyllia.fidorial.server.entity.player.InventorySlots;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.inventory.ContainerMenu;
import fr.euphyllia.fidorial.server.inventory.EnderChestMenu;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.network.session.ChunkViewTracker;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundBlockChangedAckPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundBlockEventPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundCommandSuggestionsPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundContainerSetContentPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundGameEventPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundLoginPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundPlayerAbilitiesPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundPlayerInfoUpdatePacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundPlayerPositionPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSetEntityMetadataPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSetEntityMetadataPacket.Entry;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSoundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSystemChatPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.common.ServerboundClientInformationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundAcceptTeleportationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundChatCommandPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundChatPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundCommandSuggestionPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundContainerClickPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundContainerClosePacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundKeepAlivePacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundMovePlayerPosPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundMovePlayerPosRotPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundPlayerAbilitiesPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundPlayerActionPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundPlayerLoadedPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundSetCarriedItemPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundSetCreativeModeSlotPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundUseItemOnPacket;
import fr.euphyllia.fidorial.server.registry.Registry;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import fr.euphyllia.fidorial.server.world.ChunkNetworkSerializer;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.block.EnderChestBlock;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.fidorial.entity.GameMode;
import fr.fidorial.entity.PlayerProfile;
import fr.fidorial.event.player.BlockBreakEvent;
import fr.fidorial.event.player.BlockPlaceEvent;
import fr.fidorial.event.player.PlayerChatEvent;
import fr.fidorial.event.player.PlayerJoinEvent;
import fr.fidorial.event.player.PlayerOpenEnderChestEvent;
import fr.fidorial.event.player.PlayerQuitEvent;
import fr.fidorial.inventory.EnderChestInventory;
import fr.fidorial.inventory.ItemStack;
import fr.fidorial.inventory.PlayerInventory;
import fr.fidorial.storage.player.PlayerDataStorage;
import fr.fidorial.world.BlockFace;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.ChunkPos;
import fr.fidorial.world.Location;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.util.Locale;

public final class PlayPacketHandler implements PlayPacketListener {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(PlayPacketHandler.class);

    private final ClientConnection connection;
    private final FidorialServer server;
    private final ServerConfig config;

    private @Nullable ServerPlayer player;
    private @Nullable ChunkViewTracker chunkView;
    private @Nullable ChunkPos ticket;

    public PlayPacketHandler(final ClientConnection connection) {
        this.connection = connection;
        this.server = connection.server();
        this.config = server.config();
    }

    @Override
    public void onEnter() {
        final RegistryHolder dynamic = server.dynamicRegistries();
        if (dynamic.isEmpty()) {
            LOGGER.error("Missing dynamic registries (GeneratedRegistryData empty): unable to join the game");
            connection.close();
            return;
        }

        final ServerWorld world = server.worldManager().overworld();
        final Location spawn = new Location(config.spawnX(), config.spawnY(), config.spawnZ(), 0f, 0f);
        this.player = createPlayer(world, spawn);
        connection.setPlayer(player);
        world.addEntity(player);

        sendLoginSequence(dynamic, world);
        openChunkView(world, dynamic, spawn.chunk());
        spawnPlayer(spawn);

        connection.startKeepAlive();
        server.addPlayerConnection(connection);
        server.events().post(new PlayerJoinEvent(player));
        LOGGER.info("{} logged with uuid {}", player.name(), player.uuid());
    }

    @Override
    public void onDisconnect() {
        if (chunkView != null) {
            chunkView.world().removeViewer(chunkView);
            chunkView = null;
        }
        if (ticket != null) {
            server.regionizer().removeTicket(worldId(), ticket);
            ticket = null;
        }
        if (player != null) {
            closeOpenMenu(false);
            server.events().post(new PlayerQuitEvent(player));
            server.worldManager().overworld().removeEntity(player);
            player.permissions().revokeAll();
            player.remove();
        }
    }

    private ServerPlayer createPlayer(final ServerWorld world, final Location spawn) {
        final PlayerProfile profile = connection.profile();
        if (profile == null) {
            throw new IllegalStateException(
                    "Attempt to create a player without an authenticated profile (incomplete login)");
        }
        return new ServerPlayer(
                server.entityIds().allocate(),
                profile,
                loadInventory(profile),
                loadEnderChest(profile),
                loadPlayerData(profile).gameMode(),
                connection,
                world,
                spawn);
    }

    private EnderChestInventory loadEnderChest(final PlayerProfile profile) {
        try {
            return server.playerEnderChestStorage().load(profile.uuid());
        } catch (final Exception e) {
            LOGGER.error("Chargement de l'ender chest de {} impossible, conteneur vide utilise", profile.name(), e);
            return new EnderChestInventory();
        }
    }

    private PlayerInventory loadInventory(final PlayerProfile profile) {
        try {
            final PlayerInventory inventory = server.playerInventoryStorage().load(profile.uuid());
            if (!inventory.isEmpty()) {
                LOGGER.debug("Inventaire de {} recharge", profile.name());
            }
            return inventory;
        } catch (final Exception e) {
            LOGGER.error("Chargement de l'inventaire de {} impossible, inventaire vide utilise", profile.name(), e);
            return new PlayerInventory();
        }
    }

    private PlayerDataStorage.PlayerData loadPlayerData(final PlayerProfile profile) {
        final PlayerDataStorage.PlayerData defaults = new PlayerDataStorage.PlayerData(config.defaultGameMode());
        try {
            return server.playerDataStorage().load(profile.uuid(), defaults);
        } catch (final Exception e) {
            LOGGER.error("Chargement des donnees de {} impossible, valeurs par defaut utilisees", profile.name(), e);
            return defaults;
        }
    }

    private void sendLoginSequence(final RegistryHolder dynamic, final ServerWorld world) {
        final int dimensionType = Math.max(0, dynamic.networkId("minecraft:dimension_type", worldId().asString()));
        connection.send(new ClientboundLoginPacket(
                player.entityId(),
                worldId().asString(),
                dimensionType,
                config.viewDistance(),
                player.gameMode().id()));
        connection.send(new ClientboundPlayerInfoUpdatePacket(
                player.profile(), player.gameMode().id(), 0));
        connection.send(ClientboundPlayerAbilitiesPacket.forGameMode(player.gameMode()));
        connection.send(ClientboundSetEntityMetadataPacket.of(
                player.entityId(),
                Entry.ofByte(ServerPlayer.MD_DISPLAYED_SKIN_PARTS, connection.displayedSkinParts())));
        player.invalidatePermissions();
        connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.START_WAITING_FOR_CHUNKS, 0f));
        server.weatherEngine().syncTo(connection::send);
        server.dayNightEngine().syncTo(world, connection::send);
    }

    private void openChunkView(final ServerWorld world, final RegistryHolder dynamic, final ChunkPos spawnChunk) {
        final int biome = Math.max(0, dynamic.networkId("minecraft:worldgen/biome", "minecraft:plains"));
        this.chunkView = new ChunkViewTracker(
                connection,
                server.chunkWorker(),
                world,
                new ChunkNetworkSerializer(server.blockStateRegistry(), biome),
                config.sendDistance());
        this.ticket = spawnChunk;
        world.addViewer(chunkView);
        server.regionizer().addTicket(worldId(), ticket);
        chunkView.init(spawnChunk);
    }

    private void spawnPlayer(final Location spawn) {
        connection.send(new ClientboundPlayerPositionPacket(player.nextTeleportId(), spawn.x(), spawn.y(), spawn.z()));
        connection.send(ClientboundContainerSetContentPacket.ofPlayerInventory(
                player.inventory(), 0, ItemStack.EMPTY, server.registries().frozen()));
    }

    @Override
    public void handlePlayerLoaded(final ServerboundPlayerLoadedPacket packet) {
        LOGGER.debug("{} a fini de charger le terrain", player.name());
    }

    @Override
    public void handleAcceptTeleportation(final ServerboundAcceptTeleportationPacket packet) {
        // Confirmation du client : rien a faire tant que l'anti-cheat n'existe pas.
    }

    @Override
    public void handleKeepAlive(final ServerboundKeepAlivePacket packet) {
        // La reponse suffit a considerer la connexion vivante.
    }

    @Override
    public void handleClientInformation(final ServerboundClientInformationPacket packet) {
        connection.setLocale(Locale.forLanguageTag(packet.language().replace('_', '-')));
        connection.setDisplayedSkinParts(packet.displayedSkinParts());
        if (player != null) {
            player.setLocale(packet.language());
            connection.send(ClientboundSetEntityMetadataPacket.of(
                    player.entityId(),
                    Entry.ofByte(ServerPlayer.MD_DISPLAYED_SKIN_PARTS, packet.displayedSkinParts())));
        }
    }

    @Override
    public void handleSetCarriedItem(final ServerboundSetCarriedItemPacket packet) {
        final int slot = packet.slot();
        if (slot < 0 || slot > 8) {
            LOGGER.debug("{} annonce un slot de hotbar invalide : {}", player.name(), slot);
            return;
        }
        player.setSelectedSlot(slot);
    }

    @Override
    @SuppressWarnings("PatternValidation")
    public void handleSetCreativeModeSlot(final ServerboundSetCreativeModeSlotPacket packet) {
        if (player.gameMode() != GameMode.CREATIVE) {
            LOGGER.debug("{} envoie un paquet creatif hors mode creatif (ignore)", player.name());
            return;
        }
        final int slot = InventorySlots.fromWindow(packet.slot());
        if (slot == InventorySlots.INVALID || slot >= player.inventory().size()) {
            return;
        }
        if (packet.count() <= 0 || packet.itemId() < 0) {
            player.inventory().set(slot, ItemStack.EMPTY);
            return;
        }
        final Registry items = server.registries().frozen().get("minecraft:item");
        if (items == null || packet.itemId() >= items.entries().size()) {
            LOGGER.warn("{} envoie un id d'item hors borne : {}", player.name(), packet.itemId());
            return;
        }
        player.inventory().set(slot, new ItemStack(Key.key(items.entries().get(packet.itemId())), packet.count()));
    }

    @Override
    public void handleChatCommand(final ServerboundChatCommandPacket packet) {
        server.commandManager().dispatchAsync(player, packet.command());
    }

    @Override
    public void handleChat(final ServerboundChatPacket packet) {
        if (player == null) {
            return;
        }
        final Component message = packet.message();
        if (message.equals(Component.empty())) {
            return;
        }

        final Component formatted = Component.text("\\<" + player.name() + "> ").append(message);

        final PlayerChatEvent event = server.events().post(new PlayerChatEvent(player, formatted));
        if (event.isCancelled()) {
            return;
        }

        LOGGER.debug(Component.text("<" + player.name() + ">").appendSpace().append(event.message()));
        server.broadcast(new ClientboundSystemChatPacket(event.message(), false));
    }

    @Override
    public void handleUseItemOn(final ServerboundUseItemOnPacket packet) {
        if (player.gameMode() == GameMode.SPECTATOR) {
            connection.send(new ClientboundBlockChangedAckPacket(packet.sequence()));
            return;
        }
        if (interactWithBlock(packet.target())) {
            connection.send(new ClientboundBlockChangedAckPacket(packet.sequence()));
            return;
        }
        final BlockPos target = packet.target().relative(BlockFace.byId(packet.face()));
        final ItemStack held = player.inventory().get(player.selectedSlot());
        final BlockState state = held.isEmpty() ? null : blockToPlace(held, target);

        if (state != null) {
            final BlockPlaceEvent event = server.events()
                    .post(new BlockPlaceEvent(
                            player, target, server.blockStateRegistry().networkId(state)));
            if (!event.isCancelled()) {
                server.blockEdits().set(server.worldManager().overworld(), target, state);
            }
        }
        connection.send(new ClientboundBlockChangedAckPacket(packet.sequence()));
    }

    private @Nullable BlockState blockToPlace(final ItemStack held, final BlockPos target) {
        final BlockState state = server.blockStateRegistry().blockForItem(held.id());
        if (state == null) {
            return null;
        }
        if (EnderChestBlock.is(state)) {
            return EnderChestBlock.placedBy(player.location(), isWater(target));
        }
        return state;
    }

    private boolean isWater(final BlockPos pos) {
        try {
            return "minecraft:water"
                    .equals(server.worldManager().overworld().getBlock(pos.x(), pos.y(), pos.z()).name());
        } catch (final IOException e) {
            return false;
        }
    }

    private boolean interactWithBlock(final BlockPos pos) {
        final BlockState state;
        try {
            state = server.worldManager().overworld().getBlock(pos.x(), pos.y(), pos.z());
        } catch (final IOException e) {
            LOGGER.debug("Lecture du bloc {} impossible", pos, e);
            return false;
        }
        if (!EnderChestBlock.is(state)) {
            return false;
        }
        openEnderChest(pos);
        return true;
    }

    private void openEnderChest(final BlockPos pos) {
        if (EnderChestBlock.isBlockedAbove(server.worldManager().overworld(), pos)) {
            return;
        }

        final PlayerOpenEnderChestEvent event =
                server.events().post(new PlayerOpenEnderChestEvent(player, pos, player.enderChest()));
        if (event.isCancelled()) {
            return;
        }

        final EnderChestMenu menu = new EnderChestMenu(player, player.allocateWindowId(), pos);
        player.openMenu(menu);

        server.chestViewers().open(pos, this::broadcastLid);
        broadcastChestSound(pos, "block.ender_chest.open");
    }

    private void broadcastLid(final BlockPos pos, final int viewers) {
        server.broadcast(ClientboundBlockEventPacket.chestViewers(pos, viewers));
    }

    @SuppressWarnings("PatternValidation")
    private void broadcastChestSound(final BlockPos pos, final String soundId) {
        final Sound sound = Sound.sound(Key.key(soundId), Sound.Source.BLOCK, 0.5f, 1.0f);
        server.broadcast(new ClientboundSoundPacket(sound, pos.x() + 0.5, pos.y() + 0.5, pos.z() + 0.5));
    }

    private void closeOpenMenu(final boolean notifyClient) {
        final ContainerMenu menu = player.openMenu();
        if (menu == null) {
            return;
        }
        player.closeMenu(notifyClient);

        if (menu instanceof final EnderChestMenu enderChest) {
            server.chestViewers().close(enderChest.position(), this::broadcastLid);
            broadcastChestSound(enderChest.position(), "block.ender_chest.close");
        }
        connection.send(ClientboundContainerSetContentPacket.ofPlayerInventory(
                player.inventory(), 0, ItemStack.EMPTY, server.registries().frozen()));
    }

    @Override
    public void handleContainerClick(final ServerboundContainerClickPacket packet) {
        final ContainerMenu menu = player.openMenu();
        if (menu == null || menu.windowId() != packet.windowId()) {
            connection.send(ClientboundContainerSetContentPacket.ofPlayerInventory(
                    player.inventory(), 0, ItemStack.EMPTY, server.registries().frozen()));
            return;
        }
        menu.click(packet);
        connection.send(menu.buildSyncPacket(server.registries().frozen()));
    }

    @Override
    public void handleContainerClose(final ServerboundContainerClosePacket packet) {
        closeOpenMenu(false);
    }

    @Override
    public void handlePlayerAction(final ServerboundPlayerActionPacket packet) {
        final int status = packet.status();
        final boolean breaking =
                switch (player.gameMode()) {
                    case CREATIVE -> status == ServerboundPlayerActionPacket.START_DESTROY_BLOCK;
                    case SURVIVAL ->
                            status == ServerboundPlayerActionPacket.START_DESTROY_BLOCK && instantMine(packet.position())
                                    || status == ServerboundPlayerActionPacket.FINISH_DESTROY_BLOCK;
                    case ADVENTURE, SPECTATOR -> false;
                };
        if (breaking) {
            final BlockBreakEvent event = server.events().post(new BlockBreakEvent(player, packet.position()));
            if (!event.isCancelled()) {
                onBlockDestroyed(packet.position());
                server.blockEdits().set(server.worldManager().overworld(), packet.position(), BlockState.AIR);
            }
        }
        connection.send(new ClientboundBlockChangedAckPacket(packet.sequence()));
    }

    private void onBlockDestroyed(final BlockPos position) {
        final ContainerMenu menu = player.openMenu();
        if (menu instanceof final EnderChestMenu enderChest && enderChest.position().equals(position)) {
            closeOpenMenu(true);
        }
        server.chestViewers().forget(position);
    }

    @Override
    public void handleCommandSuggestion(final ServerboundCommandSuggestionPacket packet) {
        String input = packet.text();
        final boolean slash = input.startsWith("/");

        if (slash) {
            input = input.substring(1);
        }

        final int offset = slash ? 1 : 0;

        server.commandManager().offerBrigadierSuggestions(player, input).thenAccept(suggestions -> {
            final var entries = suggestions.getList().stream()
                    .map(suggestion -> new ClientboundCommandSuggestionsPacket.Entry(
                            suggestion.getText(), suggestion.getTooltip()))
                    .toList();

            connection.send(new ClientboundCommandSuggestionsPacket(
                    packet.id(),
                    suggestions.getRange().getStart() + offset,
                    suggestions.getRange().getLength(),
                    entries));
        });
    }

    @Override
    public void handlePlayerAbilities(final ServerboundPlayerAbilitiesPacket packet) {
        final ServerPlayer player = connection.player();

        player.setFlying(packet.isFlying());
    }

    private boolean instantMine(final BlockPos position) {
        return false;
    }

    @Override
    public void handleMovePlayerPos(final ServerboundMovePlayerPosPacket packet) {
        final Location old = player.location();
        onMoved(packet.x(), packet.y(), packet.z(), old.yaw(), old.pitch());
    }

    @Override
    public void handleMovePlayerPosRot(final ServerboundMovePlayerPosRotPacket packet) {
        onMoved(packet.x(), packet.y(), packet.z(), packet.yaw(), packet.pitch());
    }

    private void onMoved(final double x, final double y, final double z, final float yaw, final float pitch) {
        final Location previous = player.location();
        final Location current = new Location(x, y, z, yaw, pitch);
        player.setLocation(current);
        server.worldManager().overworld().entityManager().moved(player, previous.chunk(), current.chunk());

        final ChunkPos chunk = current.chunk();
        if (!chunkView.moveTo(chunk.x(), chunk.z())) {
            return;
        }
        server.regionizer().moveTicket(worldId(), ticket, chunk);
        ticket = chunk;
    }

    private Key worldId() {
        return server.worldManager().overworld().dimension().id();
    }
}
