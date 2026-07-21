package fr.fidorial.status;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Server status data returned to during the status ping.
 *
 * @since 0.1.0
 */
public sealed interface ServerStatus permits SimpleServerStatus {
    /**
     * Gets the status description (also referred to as MOTD).
     *
     * @return status description
     * @since 0.1.0
     */
    @Contract(pure = true)
    Component description();

    /**
     * Gets the server version used in the status response.
     *
     * @return server version
     * @since 0.1.0
     */
    @Contract(pure = true)
    Version version();

    /**
     * Gets the optional server favicon.
     *
     * @return favicon, or an empty optional if none is configured
     * @since 0.1.0
     */
    @Contract(pure = true)
    Optional<Favicon> favicon();

    /**
     * Gets the maximum player count shown in the status response.
     *
     * @return displayed maximum player count
     * @since 0.1.0
     */
    @Contract(pure = true)
    int maxPlayers();

    /**
     * Gets the online player count shown in the status response.
     *
     * @return displayed online player count
     * @since 0.1.0
     */
    @Contract(pure = true)
    int players();

    /**
     * Gets the sample player entries shown in the status response.
     *
     * @return unmodifiable sample player list
     * @since 0.1.0
     */
    @Unmodifiable
    @Contract(pure = true)
    List<SamplePlayer> samplePlayers();

    /**
     * Gets whether secure chat is enforced on this server.
     *
     * @return {@code true} if secure chat is enforced
     * @since 0.1.0
     */
    @Contract(pure = true)
    boolean enforceSecureChat();

    /**
     * Creates a builder initialized with this status.
     *
     * @return new builder containing this status data
     * @since 0.1.0
     */
    @Contract(value = " -> new", pure = true)
    Builder toBuilder();

    /**
     * Creates a new server status builder.
     *
     * @return new builder
     * @since 0.1.0
     */
    @Contract(value = " -> new", pure = true)
    static Builder builder() {
        return new SimpleServerStatus.Builder();
    }

    /**
     * Builds immutable server status instances.
     *
     * @since 0.1.0
     */
    sealed interface Builder permits SimpleServerStatus.Builder {
        /**
         * Sets the status description.
         *
         * @param description status description
         * @return this builder
         * @since 0.1.0
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder description(Component description);

        /**
         * Sets the displayed server version.
         *
         * @param version displayed version
         * @return this builder
         * @since 0.1.0
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder version(Version version);

        /**
         * Sets the favicon.
         *
         * @param favicon favicon to display, or {@code null} to omit one
         * @return this builder
         * @since 0.1.0
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder favicon(@Nullable Favicon favicon);

        /**
         * Sets the displayed maximum player count.
         *
         * @param maxPlayers maximum player count
         * @return this builder
         * @since 0.1.0
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder maxPlayers(int maxPlayers);

        /**
         * Sets the displayed online player count.
         *
         * @param players online player count
         * @return this builder
         * @since 0.1.0
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder players(int players);

        /**
         * Replaces the sample player list.
         *
         * @param samplePlayers sample players to display
         * @return this builder
         * @since 0.1.0
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder samplePlayers(List<SamplePlayer> samplePlayers);

        // fixme: name this method addSamplePlayer or just samplePlayer?

        /**
         * Adds a sample player entry.
         *
         * @param samplePlayer sample player to add
         * @return this builder
         * @since 0.1.0
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder samplePlayer(SamplePlayer samplePlayer);

        /**
         * Sets whether secure chat is enforced.
         *
         * @param enforceSecureChat enforce secure chat
         * @return this builder
         * @since 0.1.0
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder enforceSecureChat(boolean enforceSecureChat);

        /**
         * Builds a server status instance.
         *
         * @return server status
         * @since 0.1.0
         */
        @Contract(value = " -> new", pure = true)
        ServerStatus build();
    }

    /**
     * A player entry shown in the status sample list.
     *
     * @param name displayed player name
     * @param id   displayed player UUID
     * @since 0.1.0
     */
    record SamplePlayer(String name, UUID id) {
    }

    /**
     * Version information used in the status response.
     *
     * @param name            displayed version name
     * @param protocolVersion displayed protocol version
     * @since 0.1.0
     */
    record Version(String name, int protocolVersion) {
    }
}
