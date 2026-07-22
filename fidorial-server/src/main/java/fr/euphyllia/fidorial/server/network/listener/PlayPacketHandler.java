package fr.euphyllia.fidorial.server.network.listener;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.ServerConfig;
import fr.euphyllia.fidorial.server.entity.player.InventorySlots;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.network.session.ChunkViewTracker;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundAddEntityPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundBlockChangedAckPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundCommandSuggestionsPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundContainerSetContentPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundGameEventPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundLoginPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundPlayerAbilitiesPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundPlayerInfoUpdatePacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundPlayerPositionPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSetEntityMetadataPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSetEntityMetadataPacket.Entry;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundSystemChatPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.common.ServerboundClientInformationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundAcceptTeleportationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundChatCommandPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundChatPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundCommandSuggestionPacket;
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
import fr.euphyllia.fidorial.server.registry.entity.EntityTypeRegistry;
import fr.euphyllia.fidorial.server.world.ChunkNetworkSerializer;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.fidorial.entity.GameMode;
import fr.fidorial.entity.PlayerProfile;
import fr.fidorial.event.player.BlockBreakEvent;
import fr.fidorial.event.player.BlockPlaceEvent;
import fr.fidorial.event.player.PlayerChatEvent;
import fr.fidorial.event.player.PlayerJoinEvent;
import fr.fidorial.event.player.PlayerQuitEvent;
import fr.fidorial.inventory.ItemStack;
import fr.fidorial.inventory.PlayerInventory;
import fr.fidorial.registry.RegistryKey;
import fr.fidorial.storage.player.PlayerDataStorage;
import fr.fidorial.world.BlockFace;
import fr.fidorial.world.BlockPos;
import fr.fidorial.world.ChunkPos;
import fr.fidorial.world.Location;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.util.Locale;
import java.util.UUID;

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
        ServerPlayer player = createPlayer(world, spawn);
        this.player = player;
        connection.setPlayer(player);
        world.addEntity(player);

        sendLoginSequence(dynamic, world);
        openChunkView(world, dynamic, spawn.chunk());
        spawnPlayer(spawn);

        connection.startKeepAlive();
        server.addPlayerConnection(connection);
        server.events().fireAndForget(PlayerJoinEvent.class, () -> new PlayerJoinEvent(player));
        LOGGER.info("{} est en jeu", player.name());
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
        ServerPlayer player = this.player;
        if (player != null) {
            server.events().fireAndForget(PlayerQuitEvent.class, () -> new PlayerQuitEvent(player));
            server.worldManager().overworld().removeEntity(player);
            player.clearPermissions();
            player.remove();
        }
    }

    private ServerPlayer createPlayer(final ServerWorld world, final Location spawn) {
        PlayerProfile profile = connection.profile();
        if (profile == null) {
            // Filet de securite si l'on demarre sans phase de login complete.
            profile = new PlayerProfile(UUID.randomUUID(), connection.username());
        }
        return new ServerPlayer(
                server.entityIds().allocate(),
                profile,
                loadInventory(profile),
                loadPlayerData(profile).gameMode(),
                connection,
                world,
                spawn);
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
        player.recalculatePermissions();
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
        connection.send(new ClientboundContainerSetContentPacket(
                player.inventory(), server.registries().frozen()));
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

        final PlayerChatEvent event = server.events().fire(new PlayerChatEvent(player, formatted));
        if (event.isCancelled()) {
            return;
        }

        LOGGER.debug(Component.text("<" + player.name() + ">").appendSpace().append(event.message()));
        server.broadcast(new ClientboundSystemChatPacket(event.message(), false));
    }

    @Override
    public void handleUseItemOn(final ServerboundUseItemOnPacket packet) {
        var player = this.player;
        if (player.gameMode() == GameMode.SPECTATOR) {
            connection.send(new ClientboundBlockChangedAckPacket(packet.sequence()));
            return;
        }
        final BlockPos target = packet.target().relative(BlockFace.byId(packet.face()));
        final ItemStack held = player.inventory().get(player.selectedSlot());
        final BlockState state = held.isEmpty() ? null : server.blockStateRegistry().blockForItem(held.id());

        if (state != null && !server.events().fire(BlockPlaceEvent.class, () -> {
            final int stateId = server.blockStateRegistry().networkId(state);
            return new BlockPlaceEvent(player, target, stateId);
        }).map(BlockPlaceEvent::isCancelled).orElse(false)) {
            server.blockEdits().set(server.worldManager().overworld(), target, state);
        }
        connection.send(new ClientboundBlockChangedAckPacket(packet.sequence()));
    }

    @Override
    public void handlePlayerAction(ServerboundPlayerActionPacket packet) {
        int status = packet.status();
        final ServerPlayer player = this.player;
        final boolean breaking = switch (player.gameMode()) {
            case CREATIVE -> status == ServerboundPlayerActionPacket.START_DESTROY_BLOCK;
            case SURVIVAL -> status == ServerboundPlayerActionPacket.START_DESTROY_BLOCK
                    && instantMine(packet.position())
                    || status == ServerboundPlayerActionPacket.FINISH_DESTROY_BLOCK;
            case ADVENTURE, SPECTATOR -> false;
        };
        if (breaking && !server.events().fire(BlockBreakEvent.class, () -> {
            return new BlockBreakEvent(player, packet.position());
        }).map(BlockBreakEvent::isCancelled).orElse(false)) {
            server.blockEdits().set(server.worldManager().overworld(),
                    packet.position(), BlockState.AIR);
        }
        connection.send(new ClientboundBlockChangedAckPacket(packet.sequence()));
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
        onMoved(packet.x(), packet.y(), packet.z());
    }

    @Override
    public void handleMovePlayerPosRot(final ServerboundMovePlayerPosRotPacket packet) {
        onMoved(packet.x(), packet.y(), packet.z());
    }

    private void onMoved(final double x, final double y, final double z) {
        final Location previous = player.location();
        final Location current = new Location(x, y, z, previous.yaw(), previous.pitch());
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
