package fr.euphyllia.fidorial.server.entity.player;

import fr.euphyllia.fidorial.server.world.chunk.AnvilChunkSerializer;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import fr.euphyllia.fidorial.server.world.nbt.NbtIo;
import fr.euphyllia.fidorial.server.world.nbt.NbtList;
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

public class PlayerInventoryStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerInventoryStorage.class);
    private static final String ROOT_NAME = "PlayerInventory";

    private final Path inventoriesDir;
    private final boolean gzip;


    public PlayerInventoryStorage(Path playerRoot, boolean gzip) {
        this.inventoriesDir = playerRoot.resolve("inventories");
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
        return inventoriesDir.resolve(uuid.toString());
    }

    public PlayerInventory load(UUID uuid) throws IOException {
        Path file = fileFor(uuid);
        PlayerInventory inventory = new PlayerInventory();
        if (!Files.isRegularFile(file)) {
            return inventory;
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
        NbtList slots = root.getList("Inventory");
        inventory.loadFromNbt(slots);
        return inventory;
    }

    public void save(UUID uuid, PlayerInventory inventory) throws IOException {
        Files.createDirectories(inventoriesDir);

        NbtCompound root = new NbtCompound();
        root.putInt("DataVersion", AnvilChunkSerializer.DATA_VERSION_26_2);
        root.put("Inventory", inventory.toNbt());

        byte[] data = NbtIo.writeToBytes(ROOT_NAME, root);
        if (gzip) {
            data = gzip(data);
        }

        Path file = fileFor(uuid);
        Path tmp = file.resolveSibling(file.getFileName() + ".tmp");
        Files.write(tmp, data);
        try {
            Files.move(tmp, file,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException atomicFailure) {
            Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING);
        }
        LOGGER.debug("Inventaire de {} sauvegardé ({} octets{})",
                uuid, data.length, gzip ? ", gzip" : "");
    }

    public boolean gzipEnabled() {
        return gzip;
    }

    public Path inventoriesDir() {
        return inventoriesDir;
    }


}