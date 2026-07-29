package fr.euphyllia.fidorial.server.network;

import fr.euphyllia.fidorial.auth.EncryptionUtils;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.network.codec.CipherDecoder;
import fr.euphyllia.fidorial.server.network.codec.CipherEncoder;
import fr.euphyllia.fidorial.server.network.codec.CompressionDecoder;
import fr.euphyllia.fidorial.server.network.codec.CompressionEncoder;
import fr.euphyllia.fidorial.server.network.listener.ConfigurationPacketHandler;
import fr.euphyllia.fidorial.server.network.listener.HandshakePacketHandler;
import fr.euphyllia.fidorial.server.network.listener.LoginPacketHandler;
import fr.euphyllia.fidorial.server.network.listener.PlayPacketHandler;
import fr.euphyllia.fidorial.server.network.listener.StatusPacketHandler;
import fr.euphyllia.fidorial.server.network.protocol.ProtocolMap;
import fr.euphyllia.fidorial.server.network.protocol.packet.ClientboundPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.ServerboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.login.ClientboundLoginDisconnectPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.play.ClientboundKeepAlivePacket;
import fr.euphyllia.fidorial.server.world.ServerWorld;
import fr.fidorial.entity.PlayerProfile;
import fr.fidorial.protocol.PacketListener;
import fr.fidorial.protocol.ServerboundPacket;
import fr.fidorial.storage.player.PlayerDataStorage;
import fr.fidorial.translation.TranslationStore;
import fr.fidorial.world.Location;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;
import java.util.Locale;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

// implement resource pack, pointers and dialog methods in the future from audience
public final class ClientConnection extends SimpleChannelInboundHandler<ByteBuf> implements Audience {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(ClientConnection.class);

    private static final int KEEP_ALIVE_INTERVAL_SECONDS = 10;

    private final FidorialServer server;
    private final ProtocolMap protocol;

    private ChannelHandlerContext ctx;
    private ConnectionState state;
    private PacketListener listener;

    private int clientProtocol;
    private @Nullable String username;
    private @Nullable PlayerProfile profile;
    private @Nullable ServerPlayer player;
    private int displayedSkinParts = 0x7F; // toutes les couches activees par defaut
    private @Nullable String forwardedAddress;
    private Locale locale = TranslationStore.defaultLocale();
    private @Nullable ScheduledFuture<?> keepAliveTask;

    public ClientConnection(final FidorialServer server) {
        this.server = server;
        this.protocol = server.protocolMap();
        this.state = ConnectionState.HANDSHAKE;
        this.listener = new HandshakePacketHandler(this);
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final ByteBuf raw) {
        final PacketBuffer buf = new PacketBuffer(raw);
        final int packetId = buf.readVarInt();

        final String name = protocol.serverboundName(state, packetId);
        if (name == null) {
            LOGGER.trace("Unknown packet {} 0x{} (ignored)", state, Integer.toHexString(packetId));
            return;
        }
        final ServerboundPacket packet = ServerboundPackets.decode(state, name, buf);
        if (packet == null) {
            LOGGER.trace("{}: {} received (unmanaged, ignored)", state, name);
            return;
        }
        packet.handle(listener);
    }

    public void setState(final ConnectionState newState) {
        this.state = newState;
        this.listener = createListener(newState);
        this.listener.onEnter();
    }

    private PacketListener createListener(final ConnectionState newState) {
        return switch (newState) {
            case HANDSHAKE -> new HandshakePacketHandler(this);
            case STATUS -> new StatusPacketHandler(this);
            case LOGIN -> new LoginPacketHandler(this);
            case CONFIGURATION -> new ConfigurationPacketHandler(this);
            case PLAY -> new PlayPacketHandler(this);
        };
    }

    public void send(final ClientboundPacket packet) {
        write(packet).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    public void sendAndClose(final ClientboundPacket packet) {
        write(packet).addListener(ChannelFutureListener.CLOSE);
    }

    public void disconnect(final String reason) {
        if (state == ConnectionState.LOGIN) {
            sendAndClose(ClientboundLoginDisconnectPacket.ofText(reason));
        } else {
            LOGGER.info("Disconnection of {}: {}", username, reason);
            close();
        }
    }

    private ChannelFuture write(final ClientboundPacket packet) {
        final ByteBuf out = ctx.alloc().buffer();
        try {
            final PacketBuffer p = new PacketBuffer(out);
            p.writeVarInt(protocol.clientboundId(state, packet.name()));
            packet.write(p);
        } catch (final Throwable t) {
            out.release();
            throw t;
        }
        return ctx.writeAndFlush(out);
    }

    public void close() {
        ctx.close();
    }

    public void execute(final Runnable task) {
        ctx.channel().eventLoop().execute(task);
    }

    public void installEncryption(final SecretKey key) throws GeneralSecurityException {
        ctx.pipeline()
                .addBefore(
                        "frame-decoder",
                        "cipher-decoder",
                        new CipherDecoder(EncryptionUtils.createStreamCipher(Cipher.DECRYPT_MODE, key)));
        ctx.pipeline()
                .addBefore(
                        "frame-decoder",
                        "cipher-encoder",
                        new CipherEncoder(EncryptionUtils.createStreamCipher(Cipher.ENCRYPT_MODE, key)));
    }

    public void installCompression(final int threshold) {
        ctx.pipeline().addBefore("handler", "decompress", new CompressionDecoder(threshold));
        ctx.pipeline().addBefore("handler", "compress", new CompressionEncoder(threshold));
    }

    public void startKeepAlive() {
        keepAliveTask = ctx.channel()
                .eventLoop()
                .scheduleAtFixedRate(
                        () -> send(new ClientboundKeepAlivePacket(System.currentTimeMillis())),
                        KEEP_ALIVE_INTERVAL_SECONDS,
                        KEEP_ALIVE_INTERVAL_SECONDS,
                        TimeUnit.SECONDS);
    }

    @Override
    public void handlerRemoved(final ChannelHandlerContext ctx) {
        if (keepAliveTask != null) {
            keepAliveTask.cancel(false);
        }
        try {
            listener.onDisconnect();
        } catch (final Throwable t) {
            LOGGER.error("Error during onDisconnect", t);
        }
        server.removePlayerConnection(this);
        saveInventoryOnDisconnect();
    }

    private void saveInventoryOnDisconnect() {
        final ServerPlayer disconnecting = this.player;
        if (disconnecting == null) {
            return;
        }
        this.player = null;
        Thread.startVirtualThread(() -> {
            try {
                server.playerInventoryStorage().save(disconnecting.uuid(), disconnecting.inventory());
                server.playerEnderChestStorage().save(disconnecting.uuid(), disconnecting.enderChest());
                server.playerDataStorage()
                        .save(disconnecting.uuid(), new PlayerDataStorage.PlayerData(disconnecting.gameMode()));
                LOGGER.debug("Inventory + Ender Chest and data for {} saved", disconnecting.name());
            } catch (final Exception e) {
                LOGGER.error("Unable to save inventory for {}", disconnecting.name(), e);
            }
        });
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        LOGGER.debug("Connexion closed", cause);
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

    public void setClientProtocol(final int clientProtocol) {
        this.clientProtocol = clientProtocol;
    }

    public @Nullable String username() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public @Nullable String forwardedAddress() {
        return forwardedAddress;
    }

    public void setForwardedAddress(final String forwardedAddress) {
        this.forwardedAddress = forwardedAddress;
    }

    public @Nullable PlayerProfile profile() {
        return profile;
    }

    public void setProfile(final PlayerProfile profile) {
        this.profile = profile;
    }

    public int displayedSkinParts() {
        return displayedSkinParts;
    }

    public void setDisplayedSkinParts(final int displayedSkinParts) {
        this.displayedSkinParts = displayedSkinParts;
    }

    public Locale locale() {
        return locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public @Nullable ServerPlayer player() {
        return player;
    }

    public void setPlayer(final ServerPlayer player) {
        this.player = player;
    }

    public boolean teleport(final ServerWorld target, final Location location) {
        if (listener instanceof final PlayPacketHandler play) {
            return play.teleport(target, location);
        }
        return false;
    }
}
