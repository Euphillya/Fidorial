package fr.euphyllia.fidorial.server.world.anvil;

/**
 * Constantes du format « Region » / Anvil (fichiers {@code r.X.Z.mca}).
 */
public final class RegionConstants {

    /**
     * Taille d'un secteur, en octets.
     */
    public static final int SECTOR_BYTES = 4096;

    /**
     * L'en-tête occupe 2 secteurs : table des positions (4 Kio) + table des timestamps (4 Kio).
     */
    public static final int HEADER_SECTORS = 2;
    public static final int HEADER_BYTES = HEADER_SECTORS * SECTOR_BYTES;

    /**
     * 32×32 chunks par fichier région.
     */
    public static final int REGION_SIZE = 32;
    public static final int CHUNKS_PER_REGION = REGION_SIZE * REGION_SIZE;

    // Octets de compression (voir aussi la variante externe .mcc avec le bit de poids fort).
    public static final byte COMPRESSION_GZIP = 1;
    public static final byte COMPRESSION_ZLIB = 2;   // défaut vanilla
    public static final byte COMPRESSION_NONE = 3;

    private RegionConstants() {
    }

    /**
     * Index d'un chunk dans l'en-tête : (x & 31) + (z & 31) * 32.
     */
    public static int headerIndex(int chunkX, int chunkZ) {
        return (chunkX & (REGION_SIZE - 1)) + (chunkZ & (REGION_SIZE - 1)) * REGION_SIZE;
    }

    /**
     * Coordonnée de région pour une coordonnée de chunk (division arithmétique par 32).
     */
    public static int chunkToRegion(int chunkCoord) {
        return chunkCoord >> 5;
    }

    public static String fileName(int regionX, int regionZ) {
        return "r." + regionX + "." + regionZ + ".mca";
    }
}
