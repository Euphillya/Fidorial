package fr.euphyllia.fidorial.server.network.protocol.catalog;

import java.util.Map;
import net.kyori.adventure.key.Key;

/**
 * Network IDs for the {@code configuration/serverbound} state/direction.
 *
 * <p>Generated from Mojang's packets report; do not edit.</p>
 */
public interface ConfigurationServerboundPacketIds {
    /**
     * Returned by {@link #id(Key)} when the identifier is unknown.
     */
    int UNKNOWN = -1;

    /**
     * {@code minecraft:accept_code_of_conduct}
     */
    int ACCEPT_CODE_OF_CONDUCT_ID = 9;

    /**
     * {@code minecraft:client_information}
     */
    int CLIENT_INFORMATION_ID = 0;

    /**
     * {@code minecraft:cookie_response}
     */
    int COOKIE_RESPONSE_ID = 1;

    /**
     * {@code minecraft:custom_click_action}
     */
    int CUSTOM_CLICK_ACTION_ID = 8;

    /**
     * {@code minecraft:custom_payload}
     */
    int CUSTOM_PAYLOAD_ID = 2;

    /**
     * {@code minecraft:finish_configuration}
     */
    int FINISH_CONFIGURATION_ID = 3;

    /**
     * {@code minecraft:keep_alive}
     */
    int KEEP_ALIVE_ID = 4;

    /**
     * {@code minecraft:pong}
     */
    int PONG_ID = 5;

    /**
     * {@code minecraft:resource_pack}
     */
    int RESOURCE_PACK_ID = 6;

    /**
     * {@code minecraft:select_known_packs}
     */
    int SELECT_KNOWN_PACKS_ID = 7;

    /**
     * Immutable identifier to protocol ID lookup table.
     */
    Map<Key, Integer> BY_IDENTIFIER = Map.ofEntries(
        Map.entry(Key.key("accept_code_of_conduct"), ACCEPT_CODE_OF_CONDUCT_ID),
        Map.entry(Key.key("client_information"), CLIENT_INFORMATION_ID),
        Map.entry(Key.key("cookie_response"), COOKIE_RESPONSE_ID),
        Map.entry(Key.key("custom_click_action"), CUSTOM_CLICK_ACTION_ID),
        Map.entry(Key.key("custom_payload"), CUSTOM_PAYLOAD_ID),
        Map.entry(Key.key("finish_configuration"), FINISH_CONFIGURATION_ID),
        Map.entry(Key.key("keep_alive"), KEEP_ALIVE_ID),
        Map.entry(Key.key("pong"), PONG_ID),
        Map.entry(Key.key("resource_pack"), RESOURCE_PACK_ID),
        Map.entry(Key.key("select_known_packs"), SELECT_KNOWN_PACKS_ID)
    );

    /**
     * Resolves the protocol ID for a namespaced identifier.
     *
     * @param identifier namespaced identifier, e.g. {@code Key.key("minecraft", "chest")}
     * @return the protocol ID, or {@link #UNKNOWN} when the identifier is unknown
     */
    static int id(final Key identifier) {
        return BY_IDENTIFIER.getOrDefault(identifier, UNKNOWN);
    }
}
