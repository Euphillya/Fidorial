package fr.euphyllia.fidorial.server.network.listener;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.*;
import fr.euphyllia.fidorial.server.protocol.packet.listener.PlayPacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundAcceptTeleportationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundKeepAlivePacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.play.ServerboundPlayerLoadedPacket;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import fr.euphyllia.fidorial.server.world.BlockStateRegistry;
import fr.euphyllia.fidorial.server.world.ChunkNetworkSerializer;
import fr.euphyllia.fidorial.server.world.FlatWorld;
import fr.euphyllia.fidorial.server.world.World;
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

    private int teleportId;

    public PlayPacketHandler(ClientConnection connection) {
        this.connection = connection;
        this.server = connection.server();
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

        connection.send(new ClientboundLoginPacket(ENTITY_ID, "minecraft:overworld", dimType, VIEW_DISTANCE));
        connection.send(new ClientboundGameEventPacket(
                ClientboundGameEventPacket.START_WAITING_FOR_CHUNKS, 0f));
        connection.send(new ClientboundSetChunkCacheCenterPacket(0, 0));

        ChunkNetworkSerializer chunkNet = new ChunkNetworkSerializer(new BlockStateRegistry(), biome);
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

        connection.startKeepAlive();
        LOGGER.info("{} est en jeu (monde plat cobblestone)", connection.username());
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
}
