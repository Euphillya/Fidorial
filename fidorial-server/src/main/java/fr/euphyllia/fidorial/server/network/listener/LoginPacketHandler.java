package fr.euphyllia.fidorial.server.network.listener;

import fr.fidorial.entity.PlayerProfile;
import fr.euphyllia.fidorial.auth.EncryptionUtils;
import fr.euphyllia.fidorial.auth.GameProfile;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.ServerConfig;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.network.ConnectionState;
import fr.euphyllia.fidorial.server.network.proxy.VelocityForwarding;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.login.ClientboundCustomQueryPacket;
import fr.euphyllia.fidorial.server.protocol.ProtocolConstants;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.login.ClientboundHelloPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.login.ClientboundLoginCompressionPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.login.ClientboundLoginDisconnectPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.login.ClientboundLoginFinishedPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.LoginPacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.login.ServerboundCustomQueryAnswerPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.login.ServerboundHelloPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.login.ServerboundKeyPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.login.ServerboundLoginAcknowledgedPacket;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public final class LoginPacketHandler implements LoginPacketListener {

    private static final ComponentLogger LOGGER = getLogger(LoginPacketHandler.class);

    private final ClientConnection connection;
    private final FidorialServer server;

    private byte[] verifyToken;
    private String pendingUsername;
    private int velocityTransactionId = -1;

    public LoginPacketHandler(ClientConnection connection) {
        this.connection = connection;
        this.server = connection.server();
    }

    @Override
    public void handleHello(ServerboundHelloPacket packet) {
        this.pendingUsername = packet.username();
        connection.setUsername(pendingUsername);
        if (server.config().proxyMode() == ServerConfig.ProxyMode.VELOCITY) {
            sendVelocityForwardingRequest();
        } else {
            sendEncryptionRequest();
        }
    }

    private void sendVelocityForwardingRequest() {
        this.velocityTransactionId = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
        byte[] requestedVersion = {(byte) VelocityForwarding.MAX_SUPPORTED_VERSION};
        connection.send(new ClientboundCustomQueryPacket(
                velocityTransactionId, VelocityForwarding.CHANNEL, requestedVersion));
    }

    @Override
    public void handleCustomQueryAnswer(ServerboundCustomQueryAnswerPacket packet) {
        if (server.config().proxyMode() != ServerConfig.ProxyMode.VELOCITY
                || packet.transactionId() != velocityTransactionId) {
            LOGGER.trace("unexpected custom_query_answer (id {}) ignore", packet.transactionId());
            return;
        }
        velocityTransactionId = -1;
        if (!packet.understood()) {
            disconnect("This server only accepts connections via the Velocity proxy.");
            return;
        }
        try {
            VelocityForwarding.ForwardedData data =
                    VelocityForwarding.decode(packet.payload(), server.config().velocitySecret());
            connection.setForwardedAddress(data.remoteAddress());
            connection.setUsername(data.profile().name());
            this.pendingUsername = data.profile().name();
            LOGGER.info("Player transferred by Velocity: {} ({}) from {}",
                    data.profile().name(), data.profile().uuid(), data.remoteAddress());
            enableCompression();
            sendLoginSuccess(data.profile());
        } catch (VelocityForwarding.ForwardingException e) {
            LOGGER.warn("Forwarding Velocity refuses for {}: {}", pendingUsername, e.getMessage());
            disconnect("Invalid Velocity forwarding data");
        }
    }

    private void sendEncryptionRequest() {
        this.verifyToken = EncryptionUtils.generateVerifyToken();
        byte[] publicKey = server.keyPair().getPublic().getEncoded();
        connection.send(new ClientboundHelloPacket("", publicKey, verifyToken, true));
    }

    @Override
    public void handleKey(ServerboundKeyPacket packet) {
        try {
            byte[] token = EncryptionUtils.decryptRsa(server.keyPair().getPrivate(), packet.encryptedToken());
            if (!Arrays.equals(token, verifyToken)) {
                disconnect("Verify token invalide");
                return;
            }
            byte[] sharedSecret = EncryptionUtils.decryptRsa(server.keyPair().getPrivate(), packet.encryptedSecret());
            SecretKey key = EncryptionUtils.toAesKey(sharedSecret);
            connection.installEncryption(key);

            String serverHash = EncryptionUtils.computeServerHash("", sharedSecret, server.keyPair().getPublic());
            String username = pendingUsername;
            Thread.startVirtualThread(() -> authenticate(username, serverHash));
        } catch (Exception e) {
            LOGGER.warn("Echec du chiffrement pour {}", pendingUsername, e);
            connection.close();
        }
    }

    private void authenticate(String username, String serverHash) {
        try {
            Optional<GameProfile> profile = server.sessionService().hasJoined(username, serverHash);
            connection.execute(() -> {
                if (profile.isEmpty()) {
                    disconnect("Authentification Mojang refusee");
                } else {
                    enableCompression();
                    sendLoginSuccess(profile.get());
                }
            });
        } catch (Exception e) {
            LOGGER.warn("Session Mojang injoignable pour {}", username, e);
            connection.execute(() -> disconnect("Serveurs d'authentification indisponibles"));
        }
    }

    private void enableCompression() {
        int threshold = ProtocolConstants.COMPRESSION_THRESHOLD;
        if (threshold < 0) {
            return;
        }
        connection.send(new ClientboundLoginCompressionPacket(threshold));
        connection.installCompression(threshold);
        LOGGER.debug("Compression activee (seuil {}) pour {}", threshold, pendingUsername);
    }

    private void sendLoginSuccess(GameProfile profile) {
        LOGGER.info("Authentifie : {} ({})", profile.name(), profile.uuid());
        List<PlayerProfile.Property> properties = profile.properties().stream()
                .map(p -> new PlayerProfile.Property(p.name(), p.value(), p.signature()))
                .toList();
        connection.setProfile(new PlayerProfile(profile.uuid(), profile.name(), properties));
        connection.send(new ClientboundLoginFinishedPacket(profile));
    }

    @Override
    public void handleLoginAcknowledged(ServerboundLoginAcknowledgedPacket packet) {
        connection.setState(ConnectionState.CONFIGURATION);
    }

    private void disconnect(String reason) {
        connection.sendAndClose(ClientboundLoginDisconnectPacket.ofText(reason));
    }
}
