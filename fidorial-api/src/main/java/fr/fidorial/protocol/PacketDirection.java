package fr.fidorial.protocol;

/**
 * Travel direction of a packet, from the server's point of view.
 */
public enum PacketDirection {

    /**
     * Packet sent by the server to the client ("clientbound").
     */
    CLIENTBOUND,

    /**
     * Packet sent by the client to the server ("serverbound").
     */
    SERVERBOUND
}
