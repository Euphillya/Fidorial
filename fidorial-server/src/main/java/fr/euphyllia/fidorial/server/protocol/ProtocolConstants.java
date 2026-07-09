package fr.euphyllia.fidorial.server.protocol;

public final class ProtocolConstants {

    public static final String MINECRAFT_VERSION = "26.2";
    public static final int PROTOCOL_VERSION = 776;
    public static final int MAX_PACKET_SIZE = 2 * 1024 * 1024;
    public static final int COMPRESSION_THRESHOLD = 256;

    private ProtocolConstants() {
    }
}
