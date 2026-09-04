package fr.euphyllia.fidorial.server;

/**
 * Holds the constants for the current Minecraft version targeted by Fidorial.
 */
public final class VersionConstants {

    public static final String MINECRAFT_VERSION_ID = "26.3-pre-2";
    public static final String MINECRAFT_VERSION_NAME = "26.3 Pre-Release 2"; // pre -> Pre-Release, snapshot -> Snapshot, rc -> Release Candidate
    public static final boolean IS_RELEASE = MINECRAFT_VERSION_ID.matches("\\d+\\.\\d+(?:\\.\\d+)?"); // MAJOR.MINOR or MAJOR.MINOR.PATCH

    // https://github.com/PrismarineJS/minecraft-data/blob/master/data/pc/common/protocolVersions.json
    public static final int PROTOCOL_VERSION = 1073742158;
    public static final int DATA_VERSION = 5018;

    private VersionConstants() {
    }
}
