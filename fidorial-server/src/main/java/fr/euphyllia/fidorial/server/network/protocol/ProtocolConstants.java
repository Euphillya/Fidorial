package fr.euphyllia.fidorial.server.network.protocol;

public final class ProtocolConstants {

    public static final String MINECRAFT_VERSION = "26.3-snapshot-10";
    public static final int PROTOCOL_VERSION = 1073742156;
    public static final int MAX_PACKET_SIZE = 2 * 1024 * 1024;
    public static final int COMPRESSION_THRESHOLD = 256;
    public static final int MAX_NBT_LENGTH = 0xFFFF;

    private ProtocolConstants() {
    }
}
