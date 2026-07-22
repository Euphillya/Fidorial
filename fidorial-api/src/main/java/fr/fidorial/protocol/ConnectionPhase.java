package fr.fidorial.protocol;

/**
 * Protocol phase a connection is currently in. A single numeric packet id may map
 * to different packets depending on the phase, which is why a {@link PacketType} is
 * always qualified by a phase.
 *
 * <p>The order follows the real connection lifecycle:
 * {@link #HANDSHAKE} → ({@link #STATUS} | {@link #LOGIN} → {@link #CONFIGURATION} → {@link #PLAY}).</p>
 */
public enum ConnectionPhase {

    /**
     * Initial handshake: the client announces its intent (status or login).
     */
    HANDSHAKE,

    /**
     * Status request (multiplayer menu ping).
     */
    STATUS,

    /**
     * Authentication and encryption/compression setup.
     */
    LOGIN,

    /**
     * Negotiation of registries, data packs and features before gameplay.
     */
    CONFIGURATION,

    /**
     * Actual gameplay phase.
     */
    PLAY
}
