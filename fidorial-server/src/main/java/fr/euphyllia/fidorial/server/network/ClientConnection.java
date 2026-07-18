package fr.euphyllia.fidorial.server.network;

import fr.euphyllia.fidorial.api.entity.PlayerProfile;
import fr.euphyllia.fidorial.api.storage.player.PlayerDataStorage;
import fr.euphyllia.fidorial.api.translation.TranslationStore;
import fr.euphyllia.fidorial.auth.EncryptionUtils;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.network.codec.CipherDecoder;
import fr.euphyllia.fidorial.server.network.codec.CipherEncoder;
import fr.euphyllia.fidorial.server.network.codec.CompressionDecoder;
import fr.euphyllia.fidorial.server.network.codec.CompressionEncoder;
import fr.euphyllia.fidorial.server.network.listener.*;
import fr.euphyllia.fidorial.server.protocol.ProtocolMap;
import fr.euphyllia.fidorial.server.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.PacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPacket;
import fr.euphyllia.fidorial.server.protocol.packet.ServerboundPackets;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.login.ClientboundLoginDisconnectPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.play.ClientboundKeepAlivePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;
import java.util.Locale;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public final class ClientConnection extends SimpleChannelInboundHandler<ByteBuf> {

    private static final ComponentLogger LOGGER = getLogger(ClientConnection.class);

    private static final int KEEP_ALIVE_INTERVAL_SECONDS = 10;

    private final FidorialServer server;
    private final ProtocolMap protocol;

    private ChannelHandlerContext ctx;
    private ConnectionState state;
    private PacketListener listener;

    private int clientProtocol;
    private String username;
    private PlayerProfile profile;
    private ServerPlayer player;
    private int displayedSkinParts = 0x7F; // toutes les couches activees par defaut
    private String forwardedAddress;
    private Locale locale = TranslationStore.defaultLocale();
    private ScheduledFuture<?> keepAliveTask;

    public ClientConnection(FidorialServer server) {
        this.server = server;
        this.protocol = server.protocolMap();
        this.state = ConnectionState.HANDSHAKE;
        this.listener = new HandshakePacketHandler(this);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf raw) {
        PacketBuffer buf = new PacketBuffer(raw);
        int packetId = buf.readVarInt();

        String name = protocol.serverboundName(state, packetId);
        if (name == null) {
            LOGGER.trace("Paquet {} 0x{} inconnu (ignore)", state, Integer.toHexString(packetId));
            return;
        }
        ServerboundPacket packet = ServerboundPackets.decode(state, name, buf);
        if (packet == null) {
            LOGGER.trace("{} : {} recu (non gere, ignore)", state, name);
            return;
        }
        packet.handle(listener);
    }

    public void setState(ConnectionState newState) {
        this.state = newState;
        this.listener = createListener(newState);
        this.listener.onEnter();
    }

    private PacketListener createListener(ConnectionState newState) {
        return switch (newState) {
            case HANDSHAKE -> new HandshakePacketHandler(this);
            case STATUS -> new StatusPacketHandler(this);
            case LOGIN -> new LoginPacketHandler(this);
            case CONFIGURATION -> new ConfigurationPacketHandler(this);
            case PLAY -> new PlayPacketHandler(this);
        };
    }

    public void send(ClientboundPacket packet) {
        write(packet).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    public void sendAndClose(ClientboundPacket packet) {
        write(packet).addListener(ChannelFutureListener.CLOSE);
    }

    public void disconnect(String reason) {
        if (state == ConnectionState.LOGIN) {
            sendAndClose(ClientboundLoginDisconnectPacket.ofText(reason));
        } else {
            LOGGER.info("Deconnexion de {} : {}", username, reason);
            close();
        }
    }

    private ChannelFuture write(ClientboundPacket packet) {
        ByteBuf out = ctx.alloc().buffer();
        try {
            PacketBuffer p = new PacketBuffer(out);
            p.writeVarInt(protocol.clientboundId(state, packet.name()));
            packet.write(p);
        } catch (Throwable t) {
            out.release();
            throw t;
        }
        return ctx.writeAndFlush(out);
    }

    public void close() {
        ctx.close();
    }

    public void execute(Runnable task) {
        ctx.channel().eventLoop().execute(task);
    }

    public void installEncryption(SecretKey key) throws GeneralSecurityException {
        ctx.pipeline().addBefore("frame-decoder", "cipher-decoder",
                new CipherDecoder(EncryptionUtils.createStreamCipher(Cipher.DECRYPT_MODE, key)));
        ctx.pipeline().addBefore("frame-decoder", "cipher-encoder",
                new CipherEncoder(EncryptionUtils.createStreamCipher(Cipher.ENCRYPT_MODE, key)));
    }

    public void installCompression(int threshold) {
        ctx.pipeline().addBefore("handler", "decompress", new CompressionDecoder(threshold));
        ctx.pipeline().addBefore("handler", "compress", new CompressionEncoder(threshold));
    }

    public void startKeepAlive() {
        keepAliveTask = ctx.channel().eventLoop().scheduleAtFixedRate(
                () -> send(new ClientboundKeepAlivePacket(System.currentTimeMillis())),
                KEEP_ALIVE_INTERVAL_SECONDS, KEEP_ALIVE_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        if (keepAliveTask != null) {
            keepAliveTask.cancel(false);
        }
        if (listener != null) {
            try {
                listener.onDisconnect();
            } catch (Throwable t) {
                LOGGER.error("Erreur pendant onDisconnect", t);
            }
        }
        server.removePlayerConnection(this);
        saveInventoryOnDisconnect();
    }

    private void saveInventoryOnDisconnect() {
        ServerPlayer disconnecting = this.player;
        if (disconnecting == null) {
            return;
        }
        this.player = null;
        Thread.startVirtualThread(() -> {
            try {
                server.playerInventoryStorage().save(disconnecting.uuid(), disconnecting.inventory());
                server.playerDataStorage().save(disconnecting.uuid(),
                        new PlayerDataStorage.PlayerData(disconnecting.gameMode()));
                LOGGER.info("Inventaire et donnees de {} sauvegardés", disconnecting.name());
            } catch (Exception e) {
                LOGGER.error("Sauvegarde de l'inventaire de {} impossible", disconnecting.name(), e);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.debug("Connexion fermee ({})", cause.toString());
        ctx.close();
    }

    public FidorialServer server() {
        return server;
    }

    public ConnectionState state() {
        return state;
    }

    public int clientProtocol() {
        return clientProtocol;
    }

    public void setClientProtocol(int clientProtocol) {
        this.clientProtocol = clientProtocol;
    }

    public String username() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String forwardedAddress() {
        return forwardedAddress;
    }

    public void setForwardedAddress(String forwardedAddress) {
        this.forwardedAddress = forwardedAddress;
    }

    public PlayerProfile profile() {
        return profile;
    }

    public void setProfile(PlayerProfile profile) {
        this.profile = profile;
    }

    public int displayedSkinParts() {
        return displayedSkinParts;
    }

    public void setDisplayedSkinParts(int displayedSkinParts) {
        this.displayedSkinParts = displayedSkinParts;
    }

    public Locale locale() {
        return locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public ServerPlayer player() {
        return player;
    }

    public void setPlayer(ServerPlayer player) {
        this.player = player;
    }
}