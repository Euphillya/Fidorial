package fr.euphyllia.fidorial.server.network.protocol.catalog;

/**
 * Packet identifiers for the {@code login/clientbound} state/direction.
 *
 * <p>Generated from Mojang's packets report; do not edit.</p>
 */
public final class LoginClientboundPackets {
    /**
     * {@code minecraft:cookie_request}
     */
    public static final String COOKIE_REQUEST = "minecraft:cookie_request";

    /**
     * {@code minecraft:custom_query}
     */
    public static final String CUSTOM_QUERY = "minecraft:custom_query";

    /**
     * {@code minecraft:hello}
     */
    public static final String HELLO = "minecraft:hello";

    /**
     * {@code minecraft:login_compression}
     */
    public static final String LOGIN_COMPRESSION = "minecraft:login_compression";

    /**
     * {@code minecraft:login_disconnect}
     */
    public static final String LOGIN_DISCONNECT = "minecraft:login_disconnect";

    /**
     * {@code minecraft:login_finished}
     */
    public static final String LOGIN_FINISHED = "minecraft:login_finished";

    private LoginClientboundPackets() {
    }
}
