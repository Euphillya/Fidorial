package fr.euphyllia.fidorial.server.world.anvil;

import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import fr.euphyllia.fidorial.server.world.nbt.NbtIo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;


public final class RegionFile implements Closeable {

    private final RandomAccessFile raf;
    private final int[] offsets = new int[RegionConstants.CHUNKS_PER_REGION];
    private final int[] sectorCounts = new int[RegionConstants.CHUNKS_PER_REGION];
    private final int[] timestamps = new int[RegionConstants.CHUNKS_PER_REGION];
    private boolean[] usedSectors;

    public RegionFile(Path path) throws IOException {
        Files.createDirectories(path.getParent());
        this.raf = new RandomAccessFile(path.toFile(), "rw");

        if (raf.length() < RegionConstants.HEADER_BYTES) {
            raf.setLength(RegionConstants.HEADER_BYTES);
        }

        if (raf.length() % RegionConstants.SECTOR_BYTES != 0) {
            long padded = (raf.length() / RegionConstants.SECTOR_BYTES + 1) * RegionConstants.SECTOR_BYTES;
            raf.setLength(padded);
        }
        readHeader();
    }

    private void readHeader() throws IOException {
        raf.seek(0);
        for (int i = 0; i < RegionConstants.CHUNKS_PER_REGION; i++) {
            int packed = raf.readInt();
            offsets[i] = packed >>> 8;
            sectorCounts[i] = packed & 0xFF;
        }
        for (int i = 0; i < RegionConstants.CHUNKS_PER_REGION; i++) {
            timestamps[i] = raf.readInt();
        }
        rebuildSectorMap();
    }

    private void rebuildSectorMap() throws IOException {
        int totalSectors = (int) (raf.length() / RegionConstants.SECTOR_BYTES);
        usedSectors = new boolean[Math.max(totalSectors, RegionConstants.HEADER_SECTORS)];
        usedSectors[0] = true;
        usedSectors[1] = true;
        for (int i = 0; i < RegionConstants.CHUNKS_PER_REGION; i++) {
            if (offsets[i] == 0 || sectorCounts[i] == 0) continue;
            for (int s = 0; s < sectorCounts[i]; s++) {
                int sector = offsets[i] + s;
                if (sector < usedSectors.length) usedSectors[sector] = true;
            }
        }
    }

    public boolean hasChunk(int chunkX, int chunkZ) {
        int i = RegionConstants.headerIndex(chunkX, chunkZ);
        return offsets[i] != 0 && sectorCounts[i] != 0;
    }

    public NbtCompound readChunk(int chunkX, int chunkZ) throws IOException {
        int i = RegionConstants.headerIndex(chunkX, chunkZ);
        if (offsets[i] == 0 || sectorCounts[i] == 0) return null;

        raf.seek((long) offsets[i] * RegionConstants.SECTOR_BYTES);
        int length = raf.readInt();
        if (length <= 0) return null;
        byte compression = raf.readByte();

        byte[] payload = new byte[length - 1];
        raf.readFully(payload);

        DataInputStream in = switch (compression) {
            case RegionConstants.COMPRESSION_ZLIB ->
                    new DataInputStream(new InflaterInputStream(new ByteArrayInputStream(payload)));
            case RegionConstants.COMPRESSION_GZIP ->
                    new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(payload)));
            case RegionConstants.COMPRESSION_NONE ->
                    new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(payload)));
            default -> throw new IOException(
                    "Compression " + compression + " non gérée (chunk externe .mcc ?) pour "
                            + chunkX + "," + chunkZ);
        };
        try (in) {
            return NbtIo.read(in).compound();
        }
    }

    public int timestamp(int chunkX, int chunkZ) {
        return timestamps[RegionConstants.headerIndex(chunkX, chunkZ)];
    }


    public void writeChunk(int chunkX, int chunkZ, NbtCompound chunk) throws IOException {
        byte[] frame = buildFrame(chunk);
        int neededSectors = (frame.length + RegionConstants.SECTOR_BYTES - 1) / RegionConstants.SECTOR_BYTES;
        if (neededSectors >= 256) {
            throw new IOException("Chunk " + chunkX + "," + chunkZ
                    + " trop volumineux (" + neededSectors + " secteurs) : nécessiterait un fichier .mcc externe");
        }

        int i = RegionConstants.headerIndex(chunkX, chunkZ);

        freeSectors(offsets[i], sectorCounts[i]);

        int start = allocateSectors(neededSectors);

        raf.seek((long) start * RegionConstants.SECTOR_BYTES);
        raf.write(frame);

        int pad = neededSectors * RegionConstants.SECTOR_BYTES - frame.length;
        if (pad > 0) raf.write(new byte[pad]);

        offsets[i] = start;
        sectorCounts[i] = neededSectors;
        timestamps[i] = (int) (System.currentTimeMillis() / 1000L);
        writeHeaderEntry(i);
    }

    private byte[] buildFrame(NbtCompound chunk) throws IOException {
        ByteArrayOutputStream compressed = new ByteArrayOutputStream(8192);
        try (DataOutputStream nbtOut = new DataOutputStream(new DeflaterOutputStream(compressed))) {
            NbtIo.write(nbtOut, "", chunk);
        }
        byte[] data = compressed.toByteArray();

        ByteArrayOutputStream frame = new ByteArrayOutputStream(data.length + 5);
        DataOutputStream out = new DataOutputStream(frame);
        out.writeInt(data.length + 1);
        out.writeByte(RegionConstants.COMPRESSION_ZLIB);
        out.write(data);
        return frame.toByteArray();
    }

    private void freeSectors(int start, int count) {
        for (int s = 0; s < count; s++) {
            int sector = start + s;
            if (sector >= RegionConstants.HEADER_SECTORS && sector < usedSectors.length) {
                usedSectors[sector] = false;
            }
        }
    }

    private int allocateSectors(int count) throws IOException {
        int run = 0;
        int start = RegionConstants.HEADER_SECTORS;
        for (int s = RegionConstants.HEADER_SECTORS; s < usedSectors.length; s++) {
            if (!usedSectors[s]) {
                if (run == 0) start = s;
                if (++run == count) {
                    for (int k = 0; k < count; k++) usedSectors[start + k] = true;
                    return start;
                }
            } else {
                run = 0;
            }
        }
        // Pas assez d'espace : on ajoute des secteurs à la fin.
        int newStart = usedSectors.length;
        int newLength = newStart + count;
        usedSectors = Arrays.copyOf(usedSectors, newLength);
        for (int k = 0; k < count; k++) usedSectors[newStart + k] = true;
        raf.setLength((long) newLength * RegionConstants.SECTOR_BYTES);
        return newStart;
    }

    private void writeHeaderEntry(int i) throws IOException {
        raf.seek((long) i * 4);
        raf.writeInt((offsets[i] << 8) | (sectorCounts[i] & 0xFF));
        raf.seek(RegionConstants.SECTOR_BYTES + (long) i * 4);
        raf.writeInt(timestamps[i]);
    }

    @Override
    public void close() throws IOException {
        raf.getFD().sync();
        raf.close();
    }
}
