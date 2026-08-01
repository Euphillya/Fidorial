package fr.euphyllia.fidorial.server.network.protocol.catalog;

/**
 * Packet identifiers for the {@code login/serverbound} state/direction.
 *
 * <p>Generated from Mojang's packets report; do not edit.</p>
 */
public final class LoginServerboundPackets {
    /**
     * {@code minecraft:cookie_response}
     */
    public static final String COOKIE_RESPONSE = "minecraft:cookie_response";

    /**
     * {@code minecraft:custom_query_answer}
     */
    public static final String CUSTOM_QUERY_ANSWER = "minecraft:custom_query_answer";

    /**
     * {@code minecraft:hello}
     */
    public static final String HELLO = "minecraft:hello";

    /**
     * {@code minecraft:key}
     */
    public static final String KEY = "minecraft:key";

    /**
     * {@code minecraft:login_acknowledged}
     */
    public static final String LOGIN_ACKNOWLEDGED = "minecraft:login_acknowledged";

    private LoginServerboundPackets() {
    }
}
