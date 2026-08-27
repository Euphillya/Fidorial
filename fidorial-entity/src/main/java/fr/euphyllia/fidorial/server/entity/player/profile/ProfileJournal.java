package fr.euphyllia.fidorial.server.entity.player.profile;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.UUID;
import java.util.zip.CRC32;

public final class ProfileJournal implements Closeable {

    static final byte TYPE_UPSERT = 1;
    static final byte TYPE_REMOVE = 2;

    private static final ComponentLogger LOGGER = ComponentLogger.logger(ProfileJournal.class);

    private static final int MAGIC = 0x46445031; // FDP1
    private static final short VERSION = 1;
    private static final int HEADER_BYTES = 8;
    /**
     * type(1) + uuid(16) + firstSeen(8) + lastSeen(8) + nameLen(1)
     */
    private static final int FIXED_BYTES = 34;
    private static final int MAX_NAME_BYTES = 255;

    private final Path file;
    private final Object lock = new Object();

    private @Nullable OutputStream out;
    private long liveRecords;
    private long totalRecords;
    private boolean dirty;

    /**
     * Creates a journal over the given file. The file is neither read nor created until
     * {@link #open(Replay)} is called.
     *
     * @param file the log file
     */
    public ProfileJournal(final Path file) {
        this.file = file;
    }

    /**
     * Replays the log and opens it for appending.
     *
     * @param replay called once per record, in file order; the caller is responsible for applying
     *               last write wins
     * @throws IOException if the file cannot be read, or holds a log of an unrecognised format or
     *                     version
     */
    public void open(final Replay replay) throws IOException {
        synchronized (lock) {
            final Path parent = file.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            if (!Files.isRegularFile(file) || Files.size(file) < HEADER_BYTES) {
                rewriteHeader();
                openForAppend();
                return;
            }

            final Scan scan = scan(file, replay);

            if (scan.truncated()) {
                try (final FileChannel channel = FileChannel.open(file, StandardOpenOption.WRITE)) {
                    channel.truncate(scan.validBytes());
                }
                LOGGER.warn("Profile log repaired: {} records kept, {} trailing bytes dropped",
                        scan.records(), scan.fileBytes() - scan.validBytes());
            }

            totalRecords = scan.records();
            openForAppend();
        }
    }

    /**
     * Replays a log without modifying it.
     *
     * @param file   the log file
     * @param replay called once per intact record, in file order
     * @return the outcome of the scan
     * @throws IOException if the file cannot be read, or holds a log of an unrecognised format or
     *                     version
     */
    public static Scan scan(final Path file, final Replay replay) throws IOException {
        final long size = Files.size(file);
        if (size < HEADER_BYTES) {
            throw new IOException(file + " is too short to be a profile log");
        }

        long good = HEADER_BYTES;
        long count = 0;
        boolean torn = false;

        try (final InputStream raw = Files.newInputStream(file, StandardOpenOption.READ);
             final DataInputStream in = new DataInputStream(new BufferedInputStream(raw, 64 * 1024))) {

            final int magic = in.readInt();
            final short version = in.readShort();
            in.readShort(); // flags, reserved

            if (magic != MAGIC) {
                throw new IOException(file + " is not a Fidorial profile log");
            }
            if (version != VERSION) {
                throw new IOException("Unsupported profile log version " + version
                        + " (expected " + VERSION + ")");
            }

            final byte[] fixed = new byte[FIXED_BYTES];
            final CRC32 crc32 = new CRC32();

            while (true) {
                final int firstByte = in.read();
                if (firstByte < 0) {
                    break;
                }
                fixed[0] = (byte) firstByte;
                try {
                    in.readFully(fixed, 1, FIXED_BYTES - 1);
                } catch (final EOFException tornHeader) {
                    torn = true;
                    break;
                }
                final int nameLen = fixed[FIXED_BYTES - 1] & 0xFF;
                final byte[] nameBytes = new byte[nameLen];
                final int expected;
                try {
                    in.readFully(nameBytes);
                    expected = in.readInt();
                } catch (final EOFException tornEnd) {
                    torn = true;
                    break;
                }

                crc32.reset();
                crc32.update(fixed);
                crc32.update(nameBytes);
                if ((int) crc32.getValue() != expected) {
                    torn = true;
                    break;
                }

                final ByteBuffer buf = ByteBuffer.wrap(fixed);
                final byte type = buf.get();
                final UUID uuid = new UUID(buf.getLong(), buf.getLong());
                final long firstSeen = buf.getLong();
                final long lastSeen = buf.getLong();
                final String name = nameLen == 0 ? null : new String(nameBytes, StandardCharsets.UTF_8);

                replay.accept(new ProfileEntry(uuid, name, firstSeen, lastSeen), type == TYPE_REMOVE);

                good += FIXED_BYTES + nameLen + Integer.BYTES;
                count++;
            }
        }

        return new Scan(count, good, size, torn);
    }

    /**
     * The outcome of a {@link #scan(Path, Replay)}.
     *
     * @param records    the number of intact records read
     * @param validBytes the length of the intact prefix of the file, in bytes
     * @param fileBytes  the total length of the file, in bytes
     * @param truncated  {@code true} if the file ends with an incomplete or corrupt record
     */
    public record Scan(long records, long validBytes, long fileBytes, boolean truncated) {

        /**
         * Checks whether the whole file was readable.
         *
         * @return {@code true} if no trailing damage was found
         */
        public boolean intact() {
            return !truncated;
        }

        /**
         * Gets the number of trailing bytes that no intact record accounts for.
         *
         * @return the number of unreadable trailing bytes
         */
        public long danglingBytes() {
            return fileBytes - validBytes;
        }
    }

    /**
     * Appends a single record.
     *
     * @param entry   the entry to record
     * @param removed {@code true} to record a removal rather than an insertion or replacement
     * @throws IOException if the log is closed, the name exceeds the encodable length, or the write
     *                     fails
     */
    public void append(final ProfileEntry entry, final boolean removed) throws IOException {
        final byte[] record = encode(entry, removed);
        synchronized (lock) {
            final OutputStream stream = out;
            if (stream == null) {
                throw new IOException("Profile log is closed");
            }
            stream.write(record);
            totalRecords++;
            dirty = true;
        }
    }

    private static byte[] encode(final ProfileEntry entry, final boolean removed) throws IOException {
        final byte[] nameBytes = entry.name() == null
                ? new byte[0]
                : entry.name().getBytes(StandardCharsets.UTF_8);

        if (nameBytes.length > MAX_NAME_BYTES) {
            throw new IOException("Name too long for the profile log: " + nameBytes.length + " bytes");
        }

        final ByteBuffer buf = ByteBuffer.allocate(FIXED_BYTES + nameBytes.length + Integer.BYTES);
        buf.put(removed ? TYPE_REMOVE : TYPE_UPSERT);
        buf.putLong(entry.uuid().getMostSignificantBits());
        buf.putLong(entry.uuid().getLeastSignificantBits());
        buf.putLong(entry.firstSeen());
        buf.putLong(entry.lastSeen());
        buf.put((byte) nameBytes.length);
        buf.put(nameBytes);

        final CRC32 crc32 = new CRC32();
        crc32.update(buf.array(), 0, FIXED_BYTES + nameBytes.length);
        buf.putInt((int) crc32.getValue());

        return buf.array();
    }

    /**
     * Pushes buffered records to the operating system. Does nothing when no record has been
     * appended since the last call.
     *
     * @throws IOException if the write fails
     */
    public void flush() throws IOException {
        synchronized (lock) {
            if (dirty && out != null) {
                out.flush();
                dirty = false;
            }
        }
    }

    /**
     * Checks whether the log holds enough superseded records to be worth compacting.
     *
     * @param live the number of entries currently held in memory
     * @return {@code true} if compaction would reclaim a worthwhile amount of the file
     */
    public boolean shouldCompact(final int live) {
        synchronized (lock) {
            return totalRecords > 1024 && totalRecords > 2L * Math.max(live, 1);
        }
    }

    /**
     * Rewrites the log with a single record per entry and replaces the existing file.
     *
     * @param live the entries to retain
     * @throws IOException if the replacement log cannot be written or moved into place
     */
    public void compact(final Collection<ProfileEntry> live) throws IOException {
        synchronized (lock) {
            closeOut();

            final Path tmp = file.resolveSibling(file.getFileName() + ".compact");
            try (final OutputStream stream = new BufferedOutputStream(
                    Files.newOutputStream(tmp,
                            StandardOpenOption.CREATE,
                            StandardOpenOption.TRUNCATE_EXISTING,
                            StandardOpenOption.WRITE), 64 * 1024)) {
                stream.write(header());
                for (final ProfileEntry entry : live) {
                    stream.write(encode(entry, false));
                }
                stream.flush();
            }

            try {
                Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (final IOException notAtomic) {
                Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING);
            }

            liveRecords = live.size();
            totalRecords = live.size();
            dirty = false;
            openForAppend();
            LOGGER.debug("Profile log compacted to {} records", liveRecords);
        }
    }

    /**
     * Flushes and closes the log. Further appends are rejected.
     *
     * @throws IOException if the final flush fails
     */
    @Override
    public void close() throws IOException {
        synchronized (lock) {
            closeOut();
        }
    }

    private void closeOut() throws IOException {
        if (out != null) {
            out.flush();
            out.close();
            out = null;
            dirty = false;
        }
    }

    private void openForAppend() throws IOException {
        out = new BufferedOutputStream(
                Files.newOutputStream(file, StandardOpenOption.WRITE, StandardOpenOption.APPEND),
                32 * 1024);
    }

    private void rewriteHeader() throws IOException {
        Files.write(file, header(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE);
        totalRecords = 0;
    }

    private static byte[] header() {
        return ByteBuffer.allocate(HEADER_BYTES)
                .putInt(MAGIC)
                .putShort(VERSION)
                .putShort((short) 0)
                .array();
    }

    @FunctionalInterface
    public interface Replay {

        /**
         * Accepts one replayed record.
         *
         * @param entry   the recorded entry
         * @param removed {@code true} if the record is a removal
         */
        void accept(ProfileEntry entry, boolean removed);
    }
}
