package fr.euphyllia.fidorial.server.network.listener;

import fr.euphyllia.fidorial.auth.EncryptionUtils;
import fr.euphyllia.fidorial.auth.GameProfile;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.network.ConnectionState;
import fr.euphyllia.fidorial.server.protocol.ProtocolConstants;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.login.ClientboundHelloPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.login.ClientboundLoginCompressionPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.login.ClientboundLoginDisconnectPacket;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.login.ClientboundLoginFinishedPacket;
import fr.euphyllia.fidorial.server.protocol.packet.listener.LoginPacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.login.ServerboundHelloPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.login.ServerboundKeyPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.login.ServerboundLoginAcknowledgedPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Optional;


public final class LoginPacketHandler implements LoginPacketListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPacketHandler.class);

    private final ClientConnection connection;
    private final FidorialServer server;

    private byte[] verifyToken;
    private String pendingUsername;

    public LoginPacketHandler(ClientConnection connection) {
        this.connection = connection;
        this.server = connection.server();
    }

    @Override
    public void handleHello(ServerboundHelloPacket packet) {
        this.pendingUsername = packet.username();
        connection.setUsername(pendingUsername);
        sendEncryptionRequest();
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
