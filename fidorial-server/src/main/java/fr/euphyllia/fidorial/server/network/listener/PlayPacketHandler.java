package fr.euphyllia.fidorial.server.network.listener;

import fr.euphyllia.fidorial.api.entity.GameMode;
import fr.euphyllia.fidorial.api.entity.PlayerProfile;
import fr.euphyllia.fidorial.api.event.player.*;
import fr.euphyllia.fidorial.api.inventory.ItemStack;
import fr.euphyllia.fidorial.api.inventory.PlayerInventory;
import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.storage.player.PlayerDataStorage;
import fr.euphyllia.fidorial.api.world.BlockFace;
import fr.euphyllia.fidorial.api.world.BlockPos;
import fr.euphyllia.fidorial.api.world.ChunkPos;
import fr.euphyllia.fidorial.api.world.Location;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.ServerConfig;
import fr.euphyllia.fidorial.server.entity.player.InventorySlots;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.network.session.ChunkViewTracker;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.*;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.common.ServerboundClientInformationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.*;
import fr.euphyllia.fidorial.server.registry.Registry;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import fr.euphyllia.fidorial.server.world.ChunkNetworkSerializer;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public final class PlayPacketHandler implements PlayPacketListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayPacketHandler.class);

    private final ClientConnection connection;
    private final FidorialServer server;
    private final ServerConfig config;

    private ServerPlayer player;
    private ChunkViewTracker chunkView;
    private ChunkPos ticket;

    public PlayPacketHandler(ClientConnection connection) {
        this.connection = connection;
        this.server = connection.server();
        this.config = server.config();
    }

    @Override
    public void onEnter() {
        RegistryHolder dynamic = server.dynamicRegistries();
        if (dynamic.isEmpty()) {
            LOGGER.error("Registres dynamiques absents (GeneratedRegistryData vide) : entree en jeu impossible");
            connection.close();
            return;
        }

        ServerWorld world = server.worldManager().overworld();
        Location spawn = new Location(config.spawnX(), config.spawnY(), config.spawnZ(), 0f, 0f);
        this.player = createPlayer(world, spawn);
        connection.setPlayer(player);
        world.addEntity(player);

        sendLoginSequence(dynamic);
        openChunkView(world, dynamic, spawn.chunk());
        spawnPlayer(spawn);

        connection.startKeepAlive();
        server.addPlayerConnection(connection);
        server.events().post(new PlayerJoinEvent(player));
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
        if (player != null) {
            server.events().post(new PlayerQuitEvent(player));
            server.worldManager().overworld().removeEntity(player);
            player.remove();
        }
    }

    private ServerPlayer createPlayer(ServerWorld world, Location spawn) {
        PlayerProfile profile = connection.profile();
        if (profile == null) {
            // Filet de securite si l'on demarre sans phase de login complete.
            profile = new PlayerProfile(UUID.randomUUID(), connection.username());
        }
        return new ServerPlayer(server.entityIds().allocate(), profile,
                loadInventory(profile), loadPlayerData(profile).gameMode(),
                connection, world, spawn);
    }

    private PlayerInventory loadInventory(PlayerProfile profile) {
        try {
            PlayerInventory inventory = server.playerInventoryStorage().load(profile.uuid());
            if (!inventory.isEmpty()) {
                LOGGER.debug("Inventaire de {} recharge", profile.name());
            }
            return inventory;
        } catch (Exception e) {
            LOGGER.error("Chargement de l'inventaire de {} impossible, inventaire vide utilise",
                    profile.name(), e);
            return new PlayerInventory();
        }
    }

    private PlayerDataStorage.PlayerData loadPlayerData(PlayerProfile profile) {
        PlayerDataStorage.PlayerData defaults =
                new PlayerDataStorage.PlayerData(config.defaultGameMode());
        try {
            return server.playerDataStorage().load(profile.uuid(), defaults);
        } catch (Exception e) {
            LOGGER.error("Chargement des donnees de {} impossible, valeurs par defaut utilisees",
                    profile.name(), e);
            return defaults;
        }
    }

    private void sendLoginSequence(RegistryHolder dynamic) {
        int dimensionType = Math.max(0, dynamic.networkId("minecraft:dimension_type", worldId()));
        connection.send(new ClientboundLoginPacket(
                player.entityId(), worldId(), dimensionType, config.viewDistance(),
                player.gameMode().id()));
        connection.send(new ClientboundPlayerInfoUpdatePacket(
                player.profile(), player.gameMode().id(), 0));
        connection.send(ClientboundPlayerAbilitiesPacket.forGameMode(player.gameMode()));
        connection.send(new ClientboundSetEntityDataPacket(
                player.entityId(), connection.displayedSkinParts()));
        connection.send(new ClientboundGameEventPacket(
                ClientboundGameEventPacket.START_WAITING_FOR_CHUNKS, 0f));
        server.weatherEngine().syncTo(connection::send);
    }

    private void openChunkView(ServerWorld world, RegistryHolder dynamic, ChunkPos spawnChunk) {
        int biome = Math.max(0, dynamic.networkId("minecraft:worldgen/biome", "minecraft:plains"));
        this.chunkView = new ChunkViewTracker(connection, server.chunkWorker(), world,
                new ChunkNetworkSerializer(server.blockStateRegistry(), biome), config.sendDistance());
        this.ticket = spawnChunk;
        world.addViewer(chunkView);
        server.regionizer().addTicket(worldId(), ticket);
        chunkView.init(spawnChunk);
    }

    private void spawnPlayer(Location spawn) {
        connection.send(new ClientboundPlayerPositionPacket(
                player.nextTeleportId(), spawn.x(), spawn.y(), spawn.z()));
        connection.send(new ClientboundContainerSetContentPacket(
                player.inventory(), server.registries().frozen()));
    }

    @Override
    public void handlePlayerLoaded(ServerboundPlayerLoadedPacket packet) {
        LOGGER.debug("{} a fini de charger le terrain", player.name());
    }

    @Override
    public void handleAcceptTeleportation(ServerboundAcceptTeleportationPacket packet) {
        // Confirmation du client : rien a faire tant que l'anti-cheat n'existe pas.
    }

    @Override
    public void handleKeepAlive(ServerboundKeepAlivePacket packet) {
        // La reponse suffit a considerer la connexion vivante.
    }

    @Override
    public void handleClientInformation(ServerboundClientInformationPacket packet) {
        connection.setDisplayedSkinParts(packet.displayedSkinParts());
        if (player != null) {
            connection.send(new ClientboundSetEntityDataPacket(
                    player.entityId(), packet.displayedSkinParts()));
        }
    }

    @Override
    public void handleSetCarriedItem(ServerboundSetCarriedItemPacket packet) {
        int slot = packet.slot();
        if (slot < 0 || slot > 8) {
            LOGGER.debug("{} annonce un slot de hotbar invalide : {}", player.name(), slot);
            return;
        }
        player.setSelectedSlot(slot);
    }

    @Override
    public void handleSetCreativeModeSlot(ServerboundSetCreativeModeSlotPacket packet) {
        if (player.gameMode() != GameMode.CREATIVE) {
            LOGGER.debug("{} envoie un paquet creatif hors mode creatif (ignore)", player.name());
            return;
        }
        int slot = InventorySlots.fromWindow(packet.slot());
        if (slot == InventorySlots.INVALID || slot >= player.inventory().size()) {
            return;
        }
        if (packet.count() <= 0 || packet.itemId() < 0) {
            player.inventory().set(slot, ItemStack.EMPTY);
            return;
        }
        Registry items = server.registries().frozen().get("minecraft:item");
        if (items == null || packet.itemId() >= items.entries().size()) {
            LOGGER.warn("{} envoie un id d'item hors borne : {}", player.name(), packet.itemId());
            return;
        }
        player.inventory().set(slot, new ItemStack(
                Key.parse(items.entries().get(packet.itemId())), packet.count()));
    }

    @Override
    public void handleChatCommand(ServerboundChatCommandPacket packet) {
        server.commandManager().dispatch(player, packet.command());
    }

    @Override
    public void handleChat(ServerboundChatPacket packet) {
        if (player == null) {
            return;
        }
        String message = packet.message().trim();
        if (message.isEmpty()) {
            return;
        }

        String formatted = "\\<" + player.name() + "> " + message;

        PlayerChatEvent event = server.events().post(new PlayerChatEvent(player, formatted));
        if (event.isCancelled()) {
            return;
        }

        LOGGER.debug("<{}> {}", player.name(), event.message());
        server.broadcast(new ClientboundSystemChatPacket(event.message(), false));
    }

    @Override
    public void handleUseItemOn(ServerboundUseItemOnPacket packet) {
        if (player.gameMode() == GameMode.SPECTATOR) {
            connection.send(new ClientboundBlockChangedAckPacket(packet.sequence()));
            return;
        }
        BlockPos target = packet.target().relative(BlockFace.byId(packet.face()));
        ItemStack held = player.inventory().get(player.selectedSlot());
        BlockState state = held.isEmpty() ? null : server.blockStateRegistry().blockForItem(held.id());

        if (state != null) {
            BlockPlaceEvent event = server.events().post(new BlockPlaceEvent(
                    player, target, server.blockStateRegistry().networkId(state)));
            if (!event.isCancelled()) {
                server.blockEdits().set(server.worldManager().overworld(), target, state);
            }
        }
        connection.send(new ClientboundBlockChangedAckPacket(packet.sequence()));
    }


    @Override
    public void handlePlayerAction(ServerboundPlayerActionPacket packet) {
        int status = packet.status();
        boolean breaking = switch (player.gameMode()) {
            case CREATIVE -> status == ServerboundPlayerActionPacket.START_DESTROY_BLOCK;
            case SURVIVAL -> status == ServerboundPlayerActionPacket.START_DESTROY_BLOCK
                    && instantMine(packet.position())
                    || status == ServerboundPlayerActionPacket.FINISH_DESTROY_BLOCK;
            case ADVENTURE, SPECTATOR -> false;
        };
        if (breaking) {
            BlockBreakEvent event = server.events().post(new BlockBreakEvent(player, packet.position()));
            if (!event.isCancelled()) {
                server.blockEdits().set(server.worldManager().overworld(),
                        packet.position(), BlockState.AIR);
            }
        }
        connection.send(new ClientboundBlockChangedAckPacket(packet.sequence()));
    }

    private boolean instantMine(BlockPos position) {
        return false;
    }

    @Override
    public void handleMovePlayerPos(ServerboundMovePlayerPosPacket packet) {
        onMoved(packet.x(), packet.y(), packet.z());
    }

    @Override
    public void handleMovePlayerPosRot(ServerboundMovePlayerPosRotPacket packet) {
        onMoved(packet.x(), packet.y(), packet.z());
    }

    private void onMoved(double x, double y, double z) {
        Location previous = player.location();
        Location current = new Location(x, y, z, previous.yaw(), previous.pitch());
        player.setLocation(current);
        server.worldManager().overworld().entityManager()
                .moved(player, previous.chunk(), current.chunk());

        ChunkPos chunk = current.chunk();
        if (!chunkView.moveTo(chunk.x(), chunk.z())) {
            return;
        }
        server.regionizer().moveTicket(worldId(), ticket, chunk);
        ticket = chunk;
    }

    private String worldId() {
        return server.worldManager().overworld().dimension().id();
    }
}