package fr.euphyllia.fidorial.server.network.listener;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.adventure.ClickCallbackManager;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.network.ConnectionState;
import fr.euphyllia.fidorial.server.network.protocol.ProtocolConstants;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.configuration.ClientboundBrandPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.configuration.ClientboundFinishConfigurationPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.configuration.ClientboundRegistryDataPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.configuration.ClientboundSelectKnownPacksPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.configuration.ClientboundUpdateTagsPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.ConfigurationPacketListener;
import fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.common.ServerboundClientInformationPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.common.ServerboundCustomClickActionPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.configuration.ServerboundFinishConfigurationPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.configuration.ServerboundSelectKnownPacksPacket;
import fr.euphyllia.fidorial.server.registry.Registry;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.util.Locale;
import java.util.UUID;

public final class ConfigurationPacketHandler implements ConfigurationPacketListener {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(ConfigurationPacketHandler.class);

    private final ClientConnection connection;
    private final FidorialServer server;

    public ConfigurationPacketHandler(final ClientConnection connection) {
        this.connection = connection;
        this.server = connection.server();
    }

    @Override
    public void onEnter() {
        LOGGER.info("{} entre en phase Configuration", connection.username());
        if (!server.protocolMap().isAvailable()) {
            LOGGER.error(
                    "Protocol table missing: unable to configure {}.",
                    connection.username());
            connection.close();
            return;
        }
        connection.send(new ClientboundBrandPacket("Fidorial"));
        connection.send(
                new ClientboundSelectKnownPacksPacket("minecraft", "core", ProtocolConstants.MINECRAFT_VERSION));
    }

    @Override
    public void handleSelectKnownPacks(final ServerboundSelectKnownPacksPacket packet) {
        LOGGER.debug("Known Packs client received -> sending registers");
        sendRegistries();
        sendTags();
        connection.send(new ClientboundFinishConfigurationPacket());
    }

    private void sendRegistries() {
        final RegistryHolder dynamic = server.dynamicRegistries();
        if (dynamic.isEmpty()) {
            LOGGER.warn("No dynamic registry to send (GeneratedRegistryData is empty).");
            return;
        }
        for (final Registry reg : dynamic.all()) {
            if (reg.name().asString().contains("minecraft:enchantment")) { // Todo
                continue;
            }
            connection.send(new ClientboundRegistryDataPacket(reg.name(), reg.entries()));
        }
    }

    private void sendTags() {
        connection.send(new ClientboundUpdateTagsPacket(server.dynamicRegistries()));
    }

    @Override
    public void handleFinishConfiguration(final ServerboundFinishConfigurationPacket packet) {
        connection.setState(ConnectionState.PLAY);
    }

    @Override
    public void handleCustomClickAction(final ServerboundCustomClickActionPacket packet) {
        final UUID uuid;
        try {
            uuid = ClickCallbackManager.uuidFromPayload(packet.payload());
        } catch (final IllegalArgumentException e) {
            LOGGER.debug("{} sent an invalid click callback payload for {}", connection.username(), packet.id(), e);
            return;
        }
        server.clickCallbacksManager().handleClick(connection, packet.id(), uuid);
    }

    @Override
    public void handleClientInformation(final ServerboundClientInformationPacket packet) {
        connection.setLocale(Locale.forLanguageTag(packet.language().replace('_', '-')));
        connection.setDisplayedSkinParts(packet.displayedSkinParts());
    }
}
