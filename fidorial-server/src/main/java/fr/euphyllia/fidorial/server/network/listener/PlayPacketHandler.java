package fr.euphyllia.fidorial.server.network.listener;

import fr.euphyllia.fidorial.api.entity.PlayerProfile;
import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.ItemStack;
import fr.euphyllia.fidorial.server.entity.player.Player;
import fr.euphyllia.fidorial.server.entity.player.PlayerInventory;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.*;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundAcceptTeleportationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundKeepAlivePacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundPlayerActionPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundPlayerLoadedPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundSetCarriedItemPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundSetCreativeModeSlotPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundUseItemOnPacket;
import fr.euphyllia.fidorial.server.registry.Registry;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import fr.euphyllia.fidorial.server.world.BlockPos;
import fr.euphyllia.fidorial.server.world.BlockStateRegistry;
import fr.euphyllia.fidorial.server.world.ChunkNetworkSerializer;
import fr.euphyllia.fidorial.server.world.FlatWorld;
import fr.euphyllia.fidorial.server.world.World;
import fr.euphyllia.fidorial.server.world.chunk.BlockState;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class PlayPacketHandler implements PlayPacketListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayPacketHandler.class);

    private static final int ENTITY_ID = 1;
    private static final int VIEW_DISTANCE = 8;
    private static final int CHUNK_RADIUS = 3;

    private final ClientConnection connection;
    private final FidorialServer server;
    private final BlockStateRegistry blockRegistry = new BlockStateRegistry();

    private int teleportId;
    private int selectedSlot;

    public PlayPacketHandler(ClientConnection connection) {
        this.connection = connection;
        this.server = connection.server();
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
        connection.send(new ClientboundGameEventPacket(
                ClientboundGameEventPacket.START_WAITING_FOR_CHUNKS, 0f));
        connection.send(new ClientboundSetChunkCacheCenterPacket(0, 0));

        ChunkNetworkSerializer chunkNet = new ChunkNetworkSerializer(blockRegistry, biome);
        World overworld = server.worldManager().overworld();
        try {
            for (int cx = -CHUNK_RADIUS; cx <= CHUNK_RADIUS; cx++) {
                for (int cz = -CHUNK_RADIUS; cz <= CHUNK_RADIUS; cz++) {
                    ChunkColumn column = overworld.getChunk(cx, cz); // disque -> sinon genere
                    connection.send(new ClientboundLevelChunkWithLightPacket(chunkNet, column));
                }
            }
        } catch (IOException e) {
            LOGGER.error("Lecture d'un chunk impossible pour {}", connection.username(), e);
            connection.close();
            return;
        }

        teleportId++;
        connection.send(new ClientboundPlayerPositionPacket(
                teleportId, FlatWorld.SPAWN_X, FlatWorld.SPAWN_Y, FlatWorld.SPAWN_Z));

        connection.send(new ClientboundContainerSetContentPacket(
                player.inventory(), server.registries().frozen()));

        connection.startKeepAlive();
        server.addPlayerConnection(connection);
        LOGGER.info("{} est en jeu (monde plat cobblestone)", connection.username());
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

    private void applyBlockChange(BlockPos pos, BlockState state, int sequence) {
        World world = server.worldManager().overworld();
        try {
            boolean applied = world.setBlock(pos.x(), pos.y(), pos.z(), state);
            if (applied) {
                int stateId = blockRegistry.networkId(state);
                server.broadcast(new ClientboundBlockUpdatePacket(pos, stateId));
            }
        } catch (IOException e) {
            LOGGER.error("Changement de bloc impossible en {},{},{}",
                    pos.x(), pos.y(), pos.z(), e);
        } finally {
            // Toujours acquitter, même en cas d'échec, pour éviter un blocage client.
            connection.send(new ClientboundBlockChangedAckPacket(sequence));
        }
    }
}
