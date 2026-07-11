package fr.euphyllia.fidorial.server.network;

import fr.euphyllia.fidorial.auth.EncryptionUtils;
import fr.euphyllia.fidorial.auth.GameProfile;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.network.codec.CipherDecoder;
import fr.euphyllia.fidorial.server.network.codec.CipherEncoder;
import fr.euphyllia.fidorial.server.network.codec.CompressionDecoder;
import fr.euphyllia.fidorial.server.network.codec.CompressionEncoder;
import fr.euphyllia.fidorial.server.protocol.DynamicRegistries;
import fr.euphyllia.fidorial.server.protocol.PacketIds;
import fr.euphyllia.fidorial.server.protocol.ProtocolConstants;
import fr.euphyllia.fidorial.server.protocol.ProtocolMap;
import fr.euphyllia.fidorial.server.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayClientboundPackets;
import fr.euphyllia.fidorial.server.protocol.catalog.PlayServerboundPackets;
import fr.euphyllia.fidorial.server.status.StatusResponseBuilder;
import fr.euphyllia.fidorial.server.world.*;
import fr.euphyllia.fidorial.server.world.chunk.ChunkColumn;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class ClientConnection extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientConnection.class);

    private static final String P_BRAND = "minecraft:brand";
    private static final String C_CUSTOM = "minecraft:custom_payload";
    private static final String C_KNOWN_PACKS = "minecraft:select_known_packs";
    private static final String C_REGISTRY = "minecraft:registry_data";
    private static final String C_FINISH = "minecraft:finish_configuration";

    private static final int ENTITY_ID = 1;
    private static final int VIEW_DISTANCE = 8;
    private static final int CHUNK_RADIUS = 3;

    private final FidorialServer server;
    private final ProtocolMap protocol;

    private ConnectionState state = ConnectionState.HANDSHAKE;
    private int clientProtocol;
    private byte[] verifyToken;
    private String pendingUsername;
    private int teleportId;
    private ScheduledFuture<?> keepAliveTask;

    public ClientConnection(FidorialServer server) {
        this.server = server;
        this.protocol = server.protocolMap();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf raw) throws Exception {
        PacketBuffer buf = new PacketBuffer(raw);
        int packetId = buf.readVarInt();
        switch (state) {
            case HANDSHAKE -> handleHandshake(ctx, packetId, buf);
            case STATUS -> handleStatus(ctx, packetId, buf);
            case LOGIN -> handleLogin(ctx, packetId, buf);
            case CONFIGURATION -> handleConfiguration(ctx, packetId, buf);
            case PLAY -> handlePlay(ctx, packetId, buf);
        }
    }

    private void handleHandshake(ChannelHandlerContext ctx, int packetId, PacketBuffer buf) {
        if (packetId != PacketIds.Handshake.INTENTION) {
            ctx.close();
            return;
        }
        this.clientProtocol = buf.readVarInt();
        buf.readString(255);
        buf.readUShort();
        int nextState = buf.readVarInt();
        switch (nextState) {
            case 1 -> this.state = ConnectionState.STATUS;
            case 2 -> this.state = ConnectionState.LOGIN;
            default -> ctx.close();
        }
    }

    private void handleStatus(ChannelHandlerContext ctx, int packetId, PacketBuffer buf) {
        if (packetId == PacketIds.Status.Serverbound.REQUEST) {
            ByteBuf out = ctx.alloc().buffer();
            PacketBuffer p = new PacketBuffer(out);
            p.writeVarInt(PacketIds.Status.Clientbound.RESPONSE);
            p.writeString(StatusResponseBuilder.build(clientProtocol));
            ctx.writeAndFlush(out);
        } else if (packetId == PacketIds.Status.Serverbound.PING) {
            long payload = buf.readLong();
            ByteBuf out = ctx.alloc().buffer();
            new PacketBuffer(out).writeVarInt(PacketIds.Status.Clientbound.PONG).writeLong(payload);
            ctx.writeAndFlush(out).addListener(ChannelFutureListener.CLOSE);
        }
    }

    private void handleLogin(ChannelHandlerContext ctx, int packetId, PacketBuffer buf) {
        if (packetId == PacketIds.Login.Serverbound.LOGIN_START) {
            this.pendingUsername = buf.readString(16);
            buf.readUuid();
            sendEncryptionRequest(ctx);
        } else if (packetId == PacketIds.Login.Serverbound.ENCRYPTION_RESPONSE) {
            handleEncryptionResponse(ctx, buf);
        } else if (packetId == PacketIds.Login.Serverbound.LOGIN_ACKNOWLEDGED) {
            this.state = ConnectionState.CONFIGURATION;
            LOGGER.info("{} entre en phase Configuration", pendingUsername);
            if (!protocol.isAvailable()) {
                LOGGER.error("Table de protocole absente : impossible de configurer {}. "
                        + "Lance tools/extract-protocol.sh.", pendingUsername);
                ctx.close();
                return;
            }
            startConfiguration(ctx);
        }
    }

    private void sendEncryptionRequest(ChannelHandlerContext ctx) {
        this.verifyToken = EncryptionUtils.generateVerifyToken();
        byte[] publicKey = server.keyPair().getPublic().getEncoded();

        ByteBuf out = ctx.alloc().buffer();
        new PacketBuffer(out)
                .writeVarInt(PacketIds.Login.Clientbound.ENCRYPTION_REQUEST)
                .writeString("")
                .writeByteArray(publicKey)
                .writeByteArray(verifyToken)
                .writeBoolean(true);
        ctx.writeAndFlush(out);
    }

    private void handleEncryptionResponse(ChannelHandlerContext ctx, PacketBuffer buf) {
        byte[] encryptedSecret = buf.readByteArray(256);
        byte[] encryptedToken = buf.readByteArray(256);
        try {
            byte[] token = EncryptionUtils.decryptRsa(server.keyPair().getPrivate(), encryptedToken);
            if (!Arrays.equals(token, verifyToken)) {
                disconnectLogin(ctx, "Verify token invalide");
                return;
            }
            byte[] sharedSecret = EncryptionUtils.decryptRsa(server.keyPair().getPrivate(), encryptedSecret);
            SecretKey key = EncryptionUtils.toAesKey(sharedSecret);

            ctx.pipeline().addBefore("frame-decoder", "cipher-decoder",
                    new CipherDecoder(EncryptionUtils.createStreamCipher(Cipher.DECRYPT_MODE, key)));
            ctx.pipeline().addBefore("frame-decoder", "cipher-encoder",
                    new CipherEncoder(EncryptionUtils.createStreamCipher(Cipher.ENCRYPT_MODE, key)));

            String serverHash = EncryptionUtils.computeServerHash(
                    "", sharedSecret, server.keyPair().getPublic());
            String username = pendingUsername;

            Thread.startVirtualThread(() -> authenticate(ctx, username, serverHash));
        } catch (Exception e) {
            LOGGER.warn("Echec du chiffrement pour {}", pendingUsername, e);
            ctx.close();
        }
    }

    private void authenticate(ChannelHandlerContext ctx, String username, String serverHash) {
        try {
            Optional<GameProfile> profile = server.sessionService().hasJoined(username, serverHash);
            ctx.channel().eventLoop().execute(() -> {
                if (profile.isEmpty()) {
                    disconnectLogin(ctx, "Authentification Mojang refusee");
                } else {
                    enableCompression(ctx);
                    sendLoginSuccess(ctx, profile.get());
                }
            });
        } catch (Exception e) {
            LOGGER.warn("Session Mojang injoignable pour {}", username, e);
            ctx.channel().eventLoop().execute(() ->
                    disconnectLogin(ctx, "Serveurs d'authentification indisponibles"));
        }
    }

    private void enableCompression(ChannelHandlerContext ctx) {
        int threshold = ProtocolConstants.COMPRESSION_THRESHOLD;
        if (threshold < 0) return;

        ByteBuf out = ctx.alloc().buffer();
        new PacketBuffer(out)
                .writeVarInt(PacketIds.Login.Clientbound.SET_COMPRESSION)
                .writeVarInt(threshold);
        ctx.writeAndFlush(out);

        ctx.pipeline().addBefore("handler", "decompress", new CompressionDecoder(threshold));
        ctx.pipeline().addBefore("handler", "compress", new CompressionEncoder(threshold));
        LOGGER.debug("Compression activee (seuil {}) pour {}", threshold, pendingUsername);
    }

    private void sendLoginSuccess(ChannelHandlerContext ctx, GameProfile profile) {
        LOGGER.info("Authentifie : {} ({})", profile.name(), profile.uuid());
        ByteBuf out = ctx.alloc().buffer();
        PacketBuffer p = new PacketBuffer(out);

        p.writeVarInt(PacketIds.Login.Clientbound.LOGIN_SUCCESS);
        p.writeUuid(profile.uuid());
        p.writeString(profile.name());
        p.writeVarInt(profile.properties().size());
        for (GameProfile.Property prop : profile.properties()) {
            p.writeString(prop.name());
            p.writeString(prop.value());
            p.writeBoolean(prop.signature() != null);
            if (prop.signature() != null) p.writeString(prop.signature());
        }
        p.writeUuid(profile.sessionId());
        ctx.writeAndFlush(out);

    }

    private void disconnectLogin(ChannelHandlerContext ctx, String reason) {
        ByteBuf out = ctx.alloc().buffer();
        new PacketBuffer(out)
                .writeVarInt(PacketIds.Login.Clientbound.DISCONNECT)
                .writeString("{\"text\":\"" + reason + "\"}");
        ctx.writeAndFlush(out).addListener(ChannelFutureListener.CLOSE);
    }

    private void startConfiguration(ChannelHandlerContext ctx) {
        sendClientbound(ctx, C_CUSTOM, p -> p.writeIdentifier(P_BRAND).writeString("Fidorial"));
        sendClientbound(ctx, C_KNOWN_PACKS, p -> {
            p.writeVarInt(1);
            p.writeString("minecraft");
            p.writeString("core");
            p.writeString(ProtocolConstants.MINECRAFT_VERSION);
        });
    }

    private void handleConfiguration(ChannelHandlerContext ctx, int packetId, PacketBuffer buf)
            throws IOException {
        String name = protocol.serverboundName(ConnectionState.CONFIGURATION, packetId);
        if (name == null) {
            LOGGER.debug("Paquet Configuration 0x{} inconnu (ignore)", Integer.toHexString(packetId));
            return;
        }
        switch (name) {
            case C_KNOWN_PACKS -> {
                LOGGER.debug("Known Packs client recus -> envoi des registres");
                sendRegistries(ctx);
                sendTags(ctx);
                sendClientbound(ctx, C_FINISH, p -> {
                });
            }
            case C_FINISH -> {
                this.state = ConnectionState.PLAY;
                enterPlay(ctx);
            }
            default -> LOGGER.trace("Configuration : {} recu (ignore)", name);
        }
    }

    private void sendRegistries(ChannelHandlerContext ctx) {
        DynamicRegistries dynamic = server.dynamicRegistries();
        if (dynamic.isEmpty()) {
            LOGGER.warn("Aucun registre dynamique a envoyer (datapack_registries.json manquant).");
            return;
        }
        Map<String, NbtCompound> dimensionData = DimensionTypeData.all();

        for (Map.Entry<String, DynamicRegistries.Registry> reg : dynamic.registries().entrySet()) {
            String key = reg.getKey();
            if (key.contains("minecraft:enchantment")) continue;

            boolean isDimension = key.equals("minecraft:dimension_type");
            sendClientbound(ctx, C_REGISTRY, p -> {
                p.writeIdentifier(reg.getKey());
                p.writeVarInt(reg.getValue().entries().size());
                for (String entry : reg.getValue().entries()) {
                    p.writeIdentifier(entry);
                    p.writeBoolean(false);
                }
            });
        }
    }

    private void sendTags(ChannelHandlerContext ctx) {
        DynamicRegistries dynamic = server.dynamicRegistries();
        List<Map.Entry<String, DynamicRegistries.Registry>> withTags =
                dynamic.registries().entrySet().stream()
                        .filter(e -> !e.getValue().tags().isEmpty())
                        .filter(e -> !e.getKey().contains("enchantment"))
                        .toList();

        sendClientbound(ctx, ConfigurationClientboundPackets.UPDATE_TAGS, p -> {
            p.writeVarInt(withTags.size() + 1);

            for (Map.Entry<String, DynamicRegistries.Registry> reg : withTags) {
                List<String> entries = reg.getValue().entries();
                p.writeIdentifier(reg.getKey());
                p.writeVarInt(reg.getValue().tags().size());
                for (Map.Entry<String, List<String>> tag : reg.getValue().tags().entrySet()) {
                    p.writeIdentifier(tag.getKey());
                    p.writeVarInt(tag.getValue().size());
                    for (String entry : tag.getValue()) {
                        p.writeVarInt(entries.indexOf(entry));
                    }
                }
            }

            p.writeIdentifier("minecraft:block");
            p.writeVarInt(3);
            p.writeIdentifier("minecraft:infiniburn_overworld");
            p.writeVarInt(2).writeVarInt(285).writeVarInt(671);
            p.writeIdentifier("minecraft:infiniburn_nether");
            p.writeVarInt(2).writeVarInt(285).writeVarInt(671);
            p.writeIdentifier("minecraft:infiniburn_end");
            p.writeVarInt(3).writeVarInt(285).writeVarInt(671).writeVarInt(34);
        });
    }

    private void enterPlay(ChannelHandlerContext ctx) throws IOException {
        DynamicRegistries dynamic = server.dynamicRegistries();
        if (dynamic.isEmpty()) {
            LOGGER.error("Registres dynamiques absents : impossible d'entrer en jeu. "
                    + "Lance tools/extract-datapack-registries.py <minecraft.jar>.");
            ctx.close();
            return;
        }
        int dimType = Math.max(0, dynamic.networkId("minecraft:dimension_type", "minecraft:overworld"));
        int biome = Math.max(0, dynamic.networkId("minecraft:worldgen/biome", "minecraft:plains"));

        sendLoginPlay(ctx, dimType);
        sendClientbound(ctx, PlayClientboundPackets.GAME_EVENT, p -> p.writeByte(13).writeFloat(0f));
        sendClientbound(ctx, PlayClientboundPackets.SET_CHUNK_CACHE_CENTER,
                p -> p.writeVarInt(0).writeVarInt(0));

        ChunkNetworkSerializer chunkNet =
                new ChunkNetworkSerializer(new BlockStateRegistry(), biome);

        World overworld = server.worldManager().overworld();
        for (int cx = -CHUNK_RADIUS; cx <= CHUNK_RADIUS; cx++) {
            for (int cz = -CHUNK_RADIUS; cz <= CHUNK_RADIUS; cz++) {
                ChunkColumn column = overworld.getChunk(cx, cz); // disque -> sinon génère
                sendClientbound(ctx, PlayClientboundPackets.LEVEL_CHUNK_WITH_LIGHT,
                        p -> chunkNet.writeChunk(p, ctx.alloc(), column));
            }
        }

        teleportId++;
        sendClientbound(ctx, PlayClientboundPackets.PLAYER_POSITION, p -> {
            p.writeVarInt(teleportId);
            p.writeDouble(FlatWorld.SPAWN_X).writeDouble(FlatWorld.SPAWN_Y).writeDouble(FlatWorld.SPAWN_Z);
            p.writeDouble(0).writeDouble(0).writeDouble(0);
            p.writeFloat(0f).writeFloat(0f);
            p.writeInt(0);
        });

        startKeepAlive(ctx);
        LOGGER.info("{} est en jeu (monde plat cobblestone)", pendingUsername);
    }

    private void sendLoginPlay(ChannelHandlerContext ctx, int dimTypeId) {
        sendClientbound(ctx, PlayClientboundPackets.LOGIN, p -> {
            p.writeInt(ENTITY_ID);
            p.writeBoolean(false);
            p.writeVarInt(1);
            p.writeIdentifier("minecraft:overworld");
            p.writeVarInt(0);
            p.writeVarInt(VIEW_DISTANCE);
            p.writeVarInt(VIEW_DISTANCE);
            p.writeBoolean(false);
            p.writeBoolean(true);
            p.writeBoolean(false);
            p.writeVarInt(dimTypeId);
            p.writeIdentifier("minecraft:overworld");
            p.writeLong(0L);
            p.writeByte(1);
            p.writeByte(-1);
            p.writeBoolean(false);
            p.writeBoolean(true);
            p.writeBoolean(false);
            p.writeVarInt(0);
            p.writeVarInt(63);
            p.writeBoolean(false);
            p.writeBoolean(false);
        });
    }

    private void handlePlay(ChannelHandlerContext ctx, int packetId, PacketBuffer buf) {
        String name = protocol.serverboundName(ConnectionState.PLAY, packetId);
        if (name == null) return;
        switch (name) {
            case PlayServerboundPackets.PLAYER_LOADED ->
                    LOGGER.info("{} a fini de charger le terrain", pendingUsername);
            case PlayServerboundPackets.ACCEPT_TELEPORTATION, PlayServerboundPackets.KEEP_ALIVE -> {
            }
            default -> LOGGER.trace("Play : {} recu (ignore)", name);
        }
    }

    private void startKeepAlive(ChannelHandlerContext ctx) {
        keepAliveTask = ctx.channel().eventLoop().scheduleAtFixedRate(() -> {
            long id = System.currentTimeMillis();
            sendClientbound(ctx, PlayClientboundPackets.KEEP_ALIVE, p -> p.writeLong(id));
        }, 10, 10, TimeUnit.SECONDS);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        if (keepAliveTask != null) keepAliveTask.cancel(false);
    }

    private void sendClientbound(ChannelHandlerContext ctx, String name, Consumer<PacketBuffer> body) {
        ByteBuf out = ctx.alloc().buffer();
        PacketBuffer p = new PacketBuffer(out);
        p.writeVarInt(protocol.clientboundId(state, name));
        body.accept(p);
        ctx.writeAndFlush(out);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.debug("Connexion fermee ({})", cause.toString());
        ctx.close();
    }
}