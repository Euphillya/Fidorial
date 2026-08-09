package fr.euphyllia.fidorial.server.network.listener;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.adventure.ClickCallbackManager;
import fr.euphyllia.fidorial.server.network.ClientConnection;
import fr.euphyllia.fidorial.server.network.ConnectionState;
import fr.euphyllia.fidorial.server.network.protocol.ProtocolConstants;
import fr.euphyllia.fidorial.server.network.protocol.catalog.ConfigurationClientboundPackets;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.common.ClientboundResourcePackPushPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.configuration.ClientboundBrandPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.configuration.ClientboundFinishConfigurationPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.configuration.ClientboundRegistryDataPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.configuration.ClientboundSelectKnownPacksPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.clientbound.configuration.ClientboundUpdateTagsPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.listener.ConfigurationPacketListener;
import fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.common.ServerboundClientInformationPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.configuration.ServerboundCustomClickActionPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.configuration.ServerboundFinishConfigurationPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.configuration.ServerboundResourcePackPacket;
import fr.euphyllia.fidorial.server.network.protocol.packet.serverbound.configuration.ServerboundSelectKnownPacksPacket;
import fr.euphyllia.fidorial.server.registry.Registry;
import fr.euphyllia.fidorial.server.registry.RegistryHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.util.Locale;
import java.util.UUID;

public final class ConfigurationPacketHandler implements ConfigurationPacketListener {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(ConfigurationPacketHandler.class);

    private final ClientConnection connection;
    private final FidorialServer server;
    private volatile boolean awaitingResourcePackResponse = false;

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
        if (sendResourcePackIfConfigured()) {
            awaitingResourcePackResponse = true;
            return;
        }
        proceedToKnownPacks();
    }

    private void proceedToKnownPacks() {
        connection.send(new ClientboundSelectKnownPacksPacket("minecraft", "core", ProtocolConstants.MINECRAFT_VERSION));
    }

    @Override
    public void handleResourcePackResponse(final ServerboundResourcePackPacket packet) {
        LOGGER.debug("{}: resource pack response {} -> {}", connection.username(), packet.id(), packet.status());
        connection.notifyResourcePackResponse(packet.id(), packet.status());

        final boolean terminalFailure = switch (packet.status()) {
            case SUCCESSFULLY_LOADED, ACCEPTED, DOWNLOADED -> false;
            default -> true;
        };

        if (terminalFailure && server.config().resourcePackForced()) {
            connection.close();
            return;
        }

        if (awaitingResourcePackResponse) {
            awaitingResourcePackResponse = false;
            proceedToKnownPacks();
        }
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

    private boolean sendResourcePackIfConfigured() {
        final String url = server.config().resourcePackUrl();
        if (url == null || url.isBlank()) {
            return false;
        }
        final Component prompt = server.config().resourcePackPrompt();
        final String idRaw = server.config().resourcePackId();
        final UUID id = (idRaw == null || idRaw.isBlank()) ? UUID.randomUUID() : UUID.fromString(idRaw);
        connection.send(new ClientboundResourcePackPushPacket(
                ConfigurationClientboundPackets.RESOURCE_PACK_PUSH,
                id,
                url,
                server.config().resourcePackHash() == null ? "" : server.config().resourcePackHash(),
                server.config().resourcePackForced(),
                prompt));
        return true;
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
