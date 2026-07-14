package fr.euphyllia.fidorial.server.network.listener;

import fr.euphyllia.fidorial.api.entity.PlayerProfile;
import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.world.ChunkPos;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.ItemStack;
import fr.euphyllia.fidorial.server.entity.player.Player;
import fr.euphyllia.fidorial.server.entity.player.PlayerInventory;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.*;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.common.ServerboundClientInformationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.*;
import fr.euphyllia.fidorial.server.registry.Registry;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import fr.euphyllia.fidorial.server.world.*;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class PlayPacketHandler implements PlayPacketListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayPacketHandler.class);

    private static final int ENTITY_ID = 1;
    private static final int VIEW_DISTANCE = 8;
    private static final int CHUNK_RADIUS = 3;
    private static final String WORLD_NAME = "minecraft:overworld";

    private final ClientConnection connection;
    private final FidorialServer server;
    private final BlockStateRegistry blockRegistry = new BlockStateRegistry();
    private final Object chunkLock = new Object();
    private final Set<Long> sentChunks = new HashSet<>();
    private final Set<Long> pendingChunks = new HashSet<>();
    private int teleportId;
    private int selectedSlot;
    // Suivi du streaming de chunks : centre courant et chunks déjà envoyés au client.
    private ChunkNetworkSerializer chunkNet;
    private int centerChunkX;
    private int centerChunkZ;
    private ChunkPos ticketChunk;

    public PlayPacketHandler(ClientConnection connection) {
        this.connection = connection;
        this.server = connection.server();
    }

    private static long chunkKey(int cx, int cz) {
        return ((long) cz << 32) | (cx & 0xFFFFFFFFL);
    }

    private static int fromWindowSlot(int window) {
        if (window >= 36 && window <= 44) return window - 36; // hotbar -> 0..8
        if (window >= 9 && window <= 35) return window;       // inventaire principal
        if (window >= 5 && window <= 8) return 44 - window;  // armure : 5->39..8->36
        if (window == 45) return 40;           // main secondaire
        return -1;                                             // craft / resultat
    }

    @Override
    public void onEnter() {
        RegistryHolder dynamic = server.dynamicRegistries();
        if (dynamic.isEmpty()) {
            LOGGER.error("Registres dynamiques absents : impossible d'entrer en jeu "
                    + "(GeneratedRegistryData vide).");
            connection.close();
            return;
        }
        int dimType = Math.max(0, dynamic.networkId("minecraft:dimension_type", "minecraft:overworld"));
        int biome = Math.max(0, dynamic.networkId("minecraft:worldgen/biome", "minecraft:plains"));

        Player player = loadPlayer();
        connection.setPlayer(player);

        connection.send(new ClientboundLoginPacket(ENTITY_ID, "minecraft:overworld", dimType, VIEW_DISTANCE));
        connection.send(new ClientboundPlayerInfoUpdatePacket(player.profile(), 1, 0));
        connection.send(new ClientboundSetEntityDataPacket(ENTITY_ID, connection.displayedSkinParts()));
        connection.send(new ClientboundGameEventPacket(
                ClientboundGameEventPacket.START_WAITING_FOR_CHUNKS, 0f));
        this.chunkNet = new ChunkNetworkSerializer(blockRegistry, biome);
        int spawnChunkX = (int) Math.floor(FlatWorld.SPAWN_X) >> 4;
        int spawnChunkZ = (int) Math.floor(FlatWorld.SPAWN_Z) >> 4;
        synchronized (chunkLock) {
            this.centerChunkX = spawnChunkX;
            this.centerChunkZ = spawnChunkZ;
        }
        connection.send(new ClientboundSetChunkCacheCenterPacket(spawnChunkX, spawnChunkZ));

        this.ticketChunk = new ChunkPos(spawnChunkX, spawnChunkZ);
        server.regionizer().addTicket(WORLD_NAME, ticketChunk);

        streamChunksAround(spawnChunkX, spawnChunkZ);

        teleportId++;
        connection.send(new ClientboundPlayerPositionPacket(
                teleportId, FlatWorld.SPAWN_X, FlatWorld.SPAWN_Y, FlatWorld.SPAWN_Z));

        connection.send(new ClientboundContainerSetContentPacket(
                player.inventory(), server.registries().frozen()));

        connection.startKeepAlive();
        server.addPlayerConnection(connection);
        LOGGER.info("{} est en jeu (monde plat cobblestone)", connection.username());
    }

    @Override
    public void onDisconnect() {
        if (ticketChunk != null) {
            server.regionizer().removeTicket(WORLD_NAME, ticketChunk);
            ticketChunk = null;
        }
    }

    private Player loadPlayer() {
        PlayerProfile profile = connection.profile();
        if (profile == null) {
            // Filet de sécurité si l'on démarre sans phase de login complète.
            profile = new PlayerProfile(java.util.UUID.randomUUID(), connection.username());
        }
        PlayerInventory inventory;
        try {
            inventory = server.playerInventoryStorage().load(profile.uuid());
            if (!inventory.isEmpty()) {
                LOGGER.info("Inventaire de {} rechargé", profile.name());
            }
        } catch (Exception e) {
            LOGGER.error("Chargement de l'inventaire de {} impossible (inventaire vide utilisé)",
                    profile.name(), e);
            inventory = new PlayerInventory();
        }
        return new Player(profile, inventory);
    }

    @Override
    public void handlePlayerLoaded(ServerboundPlayerLoadedPacket packet) {
        LOGGER.info("{} a fini de charger le terrain", connection.username());
    }

    @Override
    public void handleAcceptTeleportation(ServerboundAcceptTeleportationPacket packet) {
        // Le client confirme le teleport ; rien a faire pour l'instant.
    }

    @Override
    public void handleKeepAlive(ServerboundKeepAlivePacket packet) {
        // Reponse au keep-alive : la connexion reste consideree comme vivante.
    }

    @Override
    public void handleClientInformation(ServerboundClientInformationPacket packet) {
        connection.setDisplayedSkinParts(packet.displayedSkinParts());
        if (connection.player() != null) {
            connection.send(new ClientboundSetEntityDataPacket(ENTITY_ID, packet.displayedSkinParts()));
        }
    }

    @Override
    public void handleSetCreativeModeSlot(ServerboundSetCreativeModeSlotPacket packet) {
        Player player = connection.player();
        if (player == null) {
            LOGGER.info("[CREATIVE] player == null, ignore");
            return;
        }

        int storageSlot = fromWindowSlot(packet.slot());
        LOGGER.info("[CREATIVE] fenetre={} count={} itemId={} -> stockage={}",
                packet.slot(), packet.count(), packet.itemId(), storageSlot);

        if (storageSlot < 0 || storageSlot >= player.inventory().size()) {
            LOGGER.info("[CREATIVE] slot ignore (craft/resultat/hors zone)");
            return;
        }

        if (packet.count() <= 0 || packet.itemId() < 0) {
            player.inventory().set(storageSlot, ItemStack.EMPTY);
            LOGGER.info("[CREATIVE] slot {} vide", storageSlot);
            return;
        }

        Registry items = server.registries().frozen().get("minecraft:item");
        if (items == null || packet.itemId() >= items.entries().size()) {
            LOGGER.info("[CREATIVE] registre item introuvable ou id hors borne (items={})",
                    items == null ? "null" : items.entries().size());
            return;
        }
        String key = items.entries().get(packet.itemId());
        player.inventory().set(storageSlot, new ItemStack(Key.parse(key), packet.count()));

        int nonEmpty = 0;
        for (int i = 0; i < player.inventory().size(); i++) {
            if (!player.inventory().get(i).isEmpty()) nonEmpty++;
        }
        LOGGER.info("[CREATIVE] pose {} x{} au slot {} -> inventaire non vide = {} slots",
                key, packet.count(), storageSlot, nonEmpty);
    }

    @Override
    public void handleChatCommand(ServerboundChatCommandPacket packet) {
        LOGGER.info("{} execute /{}", connection.username(), packet.command());
        server.getCommandManager().dispatch(
                new fr.euphyllia.fidorial.server.command.PlayerSender(connection),
                packet.command());
    }

    @Override
    public void handleSetCarriedItem(ServerboundSetCarriedItemPacket packet) {
        int slot = packet.slot();
        if (slot >= 0 && slot <= 8) {
            this.selectedSlot = slot;
        }
    }

    @Override
    public void handleUseItemOn(ServerboundUseItemOnPacket packet) {
        Player player = connection.player();
        if (player == null) {
            return;
        }

        BlockPos placedAt = packet.target().relative(BlockPos.Direction.byId(packet.face()));

        ItemStack held = player.inventory().get(selectedSlot);
        BlockState toPlace = held.isEmpty() ? null : blockRegistry.blockForItem(held.id());
        if (toPlace == null) {
            connection.send(new ClientboundBlockChangedAckPacket(packet.sequence()));
            return;
        }

        applyBlockChange(placedAt, toPlace, packet.sequence());
        LOGGER.info("{} pose {} en {},{},{}", connection.username(),
                toPlace.name(), placedAt.x(), placedAt.y(), placedAt.z());
    }

    @Override
    public void handlePlayerAction(ServerboundPlayerActionPacket packet) {
        int status = packet.status();
        // Cassage instantané (créatif) ou fin de minage (survie) -> le bloc devient de l'air.
        boolean broke = status == ServerboundPlayerActionPacket.START_DESTROY_BLOCK
                || status == ServerboundPlayerActionPacket.FINISH_DESTROY_BLOCK;
        if (!broke) {
            return;
        }

        applyBlockChange(packet.position(), BlockState.AIR, packet.sequence());
        LOGGER.info("{} casse le bloc en {},{},{}", connection.username(),
                packet.position().x(), packet.position().y(), packet.position().z());
    }

    @Override
    public void handleMovePlayerPos(ServerboundMovePlayerPosPacket packet) {
        onPlayerMoved(packet.x(), packet.z());
    }

    @Override
    public void handleMovePlayerPosRot(ServerboundMovePlayerPosRotPacket packet) {
        onPlayerMoved(packet.x(), packet.z());
    }

    private void onPlayerMoved(double x, double z) {
        int chunkX = (int) Math.floor(x) >> 4;
        int chunkZ = (int) Math.floor(z) >> 4;

        synchronized (chunkLock) {
            if (chunkX == centerChunkX && chunkZ == centerChunkZ) {
                return;
            }
            centerChunkX = chunkX;
            centerChunkZ = chunkZ;
        }

        ChunkPos newChunk = new ChunkPos(chunkX, chunkZ);
        if (ticketChunk != null) {
            server.regionizer().moveTicket(WORLD_NAME, ticketChunk, newChunk);
        } else {
            server.regionizer().addTicket(WORLD_NAME, newChunk);
        }
        ticketChunk = newChunk;

        connection.send(new ClientboundSetChunkCacheCenterPacket(chunkX, chunkZ));
        streamChunksAround(chunkX, chunkZ);
    }

    private void streamChunksAround(int centerX, int centerZ) {
        World overworld = server.worldManager().overworld();

        synchronized (chunkLock) {
            Iterator<Long> it = sentChunks.iterator();
            while (it.hasNext()) {
                long key = it.next();
                int cx = (int) key;
                int cz = (int) (key >> 32);
                if (Math.abs(cx - centerX) > CHUNK_RADIUS || Math.abs(cz - centerZ) > CHUNK_RADIUS) {
                    connection.send(new ClientboundForgetLevelChunkPacket(cx, cz));
                    it.remove();
                }
            }

            for (int r = 0; r <= CHUNK_RADIUS; r++) {
                for (int dx = -r; dx <= r; dx++) {
                    for (int dz = -r; dz <= r; dz++) {
                        if (Math.max(Math.abs(dx), Math.abs(dz)) != r) {
                            continue;
                        }
                        int cx = centerX + dx;
                        int cz = centerZ + dz;
                        long key = chunkKey(cx, cz);
                        if (sentChunks.contains(key) || !pendingChunks.add(key)) {
                            continue;
                        }
                        server.getThreadedChunkWorker().loadAsync(overworld, cx, cz)
                                .whenComplete((column, error) -> onChunkLoaded(cx, cz, column, error));
                    }
                }
            }
        }
    }

    private void onChunkLoaded(int cx, int cz, ChunkColumn column, Throwable error) {
        long key = chunkKey(cx, cz);
        synchronized (chunkLock) {
            pendingChunks.remove(key);
            if (error != null) {
                LOGGER.error("Chargement asynchrone du chunk {},{} impossible pour {}",
                        cx, cz, connection.username(), error);
                return;
            }
            if (Math.abs(cx - centerChunkX) > CHUNK_RADIUS
                    || Math.abs(cz - centerChunkZ) > CHUNK_RADIUS) {
                return;
            }
            if (sentChunks.add(key)) {
                connection.send(new ClientboundLevelChunkWithLightPacket(chunkNet, column));
            }
        }
    }

    private void applyBlockChange(BlockPos pos, BlockState state, int sequence) {
        World world = server.worldManager().overworld();
        try {
            boolean applied = world.setBlock(pos.x(), pos.y(), pos.z(), state);
            if (applied) {
                int stateId = blockRegistry.networkId(state);
                server.broadcast(new ClientboundBlockUpdatePacket(pos, stateId));
                server.fluids().notifyBlockChanged(WORLD_NAME, pos.x(), pos.y(), pos.z());
            }
        } catch (IOException e) {
            LOGGER.error("Changement de bloc impossible en {},{},{}",
                    pos.x(), pos.y(), pos.z(), e);
        } finally {
            connection.send(new ClientboundBlockChangedAckPacket(sequence));
        }
    }
}
