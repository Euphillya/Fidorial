package fr.euphyllia.fidorial.server.network.protocol.catalog;

import net.kyori.adventure.key.Key;

/**
 * Packet identifiers for the {@code login/clientbound} state/direction.
 *
 * <p>Generated from Mojang's packets report; do not edit.</p>
 */
public final class LoginClientboundPackets {
    /**
     * {@code minecraft:cookie_request}
     */
    public static final Key COOKIE_REQUEST = Key.key("cookie_request");

    /**
     * {@code minecraft:custom_query}
     */
    public static final Key CUSTOM_QUERY = Key.key("custom_query");

    /**
     * {@code minecraft:hello}
     */
    public static final Key HELLO = Key.key("hello");

    /**
     * {@code minecraft:login_compression}
     */
    public static final Key LOGIN_COMPRESSION = Key.key("login_compression");

    /**
     * {@code minecraft:login_disconnect}
     */
    public static final Key LOGIN_DISCONNECT = Key.key("login_disconnect");

    /**
     * {@code minecraft:login_finished}
     */
    public static final Key LOGIN_FINISHED = Key.key("login_finished");

    private LoginClientboundPackets() {
    }
}
