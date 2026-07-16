package fr.euphyllia.fidorial.server.entity.player;

import fr.euphyllia.fidorial.api.entity.GameMode;
import fr.euphyllia.fidorial.server.world.chunk.AnvilChunkSerializer;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import fr.euphyllia.fidorial.server.world.nbt.NbtIo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class PlayerDataStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerDataStorage.class);
    private static final String ROOT_NAME = "PlayerData";


    public record PlayerData(GameMode gameMode) {
    }

    private final Path dataDir;
    private final boolean gzip;

    public PlayerDataStorage(Path playerRoot, boolean gzip) {
        this.dataDir = playerRoot.resolve("data");
        this.gzip = gzip;
    }

    private static byte[] gzip(byte[] plain) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(plain.length);
        try (GZIPOutputStream out = new GZIPOutputStream(baos)) {
            out.write(plain);
        }
        return baos.toByteArray();
    }

    private static byte[] gunzip(byte[] compressed) throws IOException {
        try (GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(compressed))) {
            return in.readAllBytes();
        }
    }

    private Path fileFor(UUID uuid) {
        return dataDir.resolve(uuid.toString());
    }

    public PlayerData load(UUID uuid, PlayerData defaults) throws IOException {
        Path file = fileFor(uuid);
        if (!Files.isRegularFile(file)) {
            return defaults;
        }

        byte[] data = Files.readAllBytes(file);

        boolean isGzip = data.length >= 2
                && data[0] == (byte) 0x1F
                && data[1] == (byte) 0x8B;
        if (isGzip) {
            data = gunzip(data);
        }

        NbtIo.Named named = NbtIo.readFromBytes(data);
        NbtCompound root = named.compound();

        GameMode gameMode = defaults.gameMode();
        if (root.contains("playerGameModeId")) {
            GameMode stored = GameMode.byId(root.getInt("playerGameModeId"));
            if (stored != null) {
                gameMode = stored;
            }
        }
        return new PlayerData(gameMode);
    }

    public void save(UUID uuid, PlayerData data) throws IOException {
        Files.createDirectories(dataDir);

        NbtCompound root = new NbtCompound();
        root.putInt("DataVersion", AnvilChunkSerializer.DATA_VERSION_26_2);
        root.putInt("playerGameModeId", data.gameMode().id());

        byte[] bytes = NbtIo.writeToBytes(ROOT_NAME, root);
        if (gzip) {
            bytes = gzip(bytes);
        }

        Path file = fileFor(uuid);
        Path tmp = file.resolveSibling(file.getFileName() + ".tmp");
        Files.write(tmp, bytes);
        try {
            Files.move(tmp, file,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException atomicFailure) {
            Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING);
        }
        LOGGER.debug("Donnees de {} sauvegardées ({} octets{})",
                uuid, bytes.length, gzip ? ", gzip" : "");
    }

    public Path dataDir() {
        return dataDir;
    }

}
