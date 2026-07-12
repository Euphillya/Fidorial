package fr.euphyllia.fidorial.server.network.listener;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.network.ConnectionState;
import fr.euphyllia.fidorial.server.protocol.ProtocolConstants;
import fr.euphyllia.fidorial.server.protocol.packet.clientbound.configuration.*;
import fr.euphyllia.fidorial.server.protocol.packet.listener.ConfigurationPacketListener;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.configuration.ServerboundFinishConfigurationPacket;
import fr.euphyllia.fidorial.server.protocol.packet.serverbound.configuration.ServerboundSelectKnownPacksPacket;
import fr.euphyllia.fidorial.server.registry.Registry;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConfigurationPacketHandler implements ConfigurationPacketListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationPacketHandler.class);

    private final ClientConnection connection;
    private final FidorialServer server;

    public ConfigurationPacketHandler(ClientConnection connection) {
        this.connection = connection;
        this.server = connection.server();
    }

    @Override
    public void onEnter() {
        LOGGER.info("{} entre en phase Configuration", connection.username());
        if (!server.protocolMap().isAvailable()) {
            LOGGER.error("Table de protocole absente : impossible de configurer {}. "
                    + "Lance tools/extract-protocol.sh.", connection.username());
            connection.close();
            return;
        }
        connection.send(new ClientboundBrandPacket("Fidorial"));
        connection.send(new ClientboundSelectKnownPacksPacket(
                "minecraft", "core", ProtocolConstants.MINECRAFT_VERSION));
    }

    @Override
    public void handleSelectKnownPacks(ServerboundSelectKnownPacksPacket packet) {
        LOGGER.debug("Known Packs client recus -> envoi des registres");
        sendRegistries();
        sendTags();
        connection.send(new ClientboundFinishConfigurationPacket());
    }

    private void sendRegistries() {
        RegistryHolder dynamic = server.dynamicRegistries();
        if (dynamic.isEmpty()) {
            LOGGER.warn("Aucun registre dynamique a envoyer (GeneratedRegistryData vide).");
            return;
        }
        for (Registry reg : dynamic.all()) {
            if (reg.name().contains("minecraft:enchantment")) {
                continue;
            }
            connection.send(new ClientboundRegistryDataPacket(reg.name(), reg.entries()));
        }
    }

    private void sendTags() {
        connection.send(new ClientboundUpdateTagsPacket(server.dynamicRegistries()));
    }

    @Override
    public void handleFinishConfiguration(ServerboundFinishConfigurationPacket packet) {
        connection.setState(ConnectionState.PLAY);
    }
}
