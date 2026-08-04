package fr.euphyllia.fidorial.server.network.protocol.catalog;

import net.kyori.adventure.key.Key;

/**
 * Packet identifiers for the {@code configuration/serverbound} state/direction.
 *
 * <p>Generated from Mojang's packets report; do not edit.</p>
 */
public final class ConfigurationServerboundPackets {
    /**
     * {@code minecraft:accept_code_of_conduct}
     */
    public static final Key ACCEPT_CODE_OF_CONDUCT = Key.key("accept_code_of_conduct");

    /**
     * {@code minecraft:client_information}
     */
    public static final Key CLIENT_INFORMATION = Key.key("client_information");

    /**
     * {@code minecraft:cookie_response}
     */
    public static final Key COOKIE_RESPONSE = Key.key("cookie_response");

    /**
     * {@code minecraft:custom_click_action}
     */
    public static final Key CUSTOM_CLICK_ACTION = Key.key("custom_click_action");

    /**
     * {@code minecraft:custom_payload}
     */
    public static final Key CUSTOM_PAYLOAD = Key.key("custom_payload");

    /**
     * {@code minecraft:finish_configuration}
     */
    public static final Key FINISH_CONFIGURATION = Key.key("finish_configuration");

    /**
     * {@code minecraft:keep_alive}
     */
    public static final Key KEEP_ALIVE = Key.key("keep_alive");

    /**
     * {@code minecraft:pong}
     */
    public static final Key PONG = Key.key("pong");

    /**
     * {@code minecraft:resource_pack}
     */
    public static final Key RESOURCE_PACK = Key.key("resource_pack");

    /**
     * {@code minecraft:select_known_packs}
     */
    public static final Key SELECT_KNOWN_PACKS = Key.key("select_known_packs");

    private ConfigurationServerboundPackets() {
    }
}
