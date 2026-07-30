package fr.euphyllia.fidorial.server.entity.player.storage;

import fr.euphyllia.fidorial.server.world.chunk.AnvilChunkSerializer;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import fr.euphyllia.fidorial.server.world.nbt.NbtIo;
import fr.fidorial.entity.GameMode;
import fr.fidorial.storage.player.PlayerDataStorage;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NbtPlayerDataStorage implements PlayerDataStorage {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(NbtPlayerDataStorage.class);
    private static final String ROOT_NAME = "PlayerData";

    private final Path dataDir;
    private final boolean gzip;

    public NbtPlayerDataStorage(final Path playerRoot, final boolean gzip) {
        this.dataDir = playerRoot.resolve("data");
        this.gzip = gzip;
    }

    private static byte[] gzip(final byte[] plain) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(plain.length);
        try (final GZIPOutputStream out = new GZIPOutputStream(baos)) {
            out.write(plain);
        }
        return baos.toByteArray();
    }

    private static byte[] gunzip(final byte[] compressed) throws IOException {
        try (final GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(compressed))) {
            return in.readAllBytes();
        }
    }

    private Path fileFor(final UUID uuid) {
        return dataDir.resolve(uuid.toString());
    }

    @Override
    public boolean exists(final UUID uuid) {
        return Files.isRegularFile(fileFor(uuid));
    }

    @Override
    public PlayerData load(final UUID uuid, final PlayerData defaults) throws IOException {
        final Path file = fileFor(uuid);
        if (!Files.isRegularFile(file)) {
            return defaults;
        }

        byte[] data = Files.readAllBytes(file);

        final boolean isGzip = data.length >= 2 && data[0] == (byte) 0x1F && data[1] == (byte) 0x8B;
        if (isGzip) {
            data = gunzip(data);
        }

        final NbtIo.Named named = NbtIo.readFromBytes(data);
        final NbtCompound root = named.compound();

        GameMode gameMode = defaults.gameMode();
        if (root.contains("playerGameModeId")) {
            final GameMode stored = GameMode.byId(root.getInt("playerGameModeId"));
            if (stored != null) {
                gameMode = stored;
            }
        }
        return new PlayerData(gameMode);
    }

    @Override
    public void save(final UUID uuid, final PlayerData data) throws IOException {
        Files.createDirectories(dataDir);

        final NbtCompound root = new NbtCompound();
        root.putInt("DataVersion", AnvilChunkSerializer.DATA_VERSION_26_2);
        root.putInt("playerGameModeId", data.gameMode().id());

        byte[] bytes = NbtIo.writeToBytes(ROOT_NAME, root);
        if (gzip) {
            bytes = gzip(bytes);
        }

        final Path file = fileFor(uuid);
        final Path tmp = file.resolveSibling(file.getFileName() + ".tmp");
        Files.write(tmp, bytes);
        try {
            Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (final IOException atomicFailure) {
            Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING);
        }
        LOGGER.debug("Data for {} saved ({} bytes{})", uuid, bytes.length, gzip ? ", gzip" : "");
    }

    public Path dataDir() {
        return dataDir;
    }
}
