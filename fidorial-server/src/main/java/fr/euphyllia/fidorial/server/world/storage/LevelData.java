package fr.euphyllia.fidorial.server.world.storage;

import fr.euphyllia.fidorial.server.world.chunk.AnvilChunkSerializer;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import fr.euphyllia.fidorial.server.world.nbt.NbtIo;
import fr.euphyllia.fidorial.server.world.nbt.NbtList;
import fr.euphyllia.fidorial.server.world.nbt.NbtType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LevelData {

    public String levelName = "Fidorial";
    public long seed = 0L;
    public long time = 0L;
    public long dayTime = 0L;
    public int spawnX = 8;
    public int spawnY = -48;
    public int spawnZ = 8;
    public float spawnAngle = 0f;
    public int gameType = 0;       // 0 = survie
    public int difficulty = 2;     // 2 = normal
    public boolean hardcore = false;
    public boolean allowCommands = true;
    public int dataVersion = AnvilChunkSerializer.DATA_VERSION_26_2;
    public String versionName = "26.2";

    public static LevelData read(Path levelDat) throws IOException {
        NbtIo.Named named = NbtIo.readGzip(levelDat);
        NbtCompound data = named.compound().getCompound("Data");
        LevelData l = new LevelData();
        if (data == null) return l;

        l.dataVersion = data.getInt("DataVersion");
        l.levelName = data.getString("LevelName");
        l.time = data.getLong("Time");
        l.dayTime = data.getLong("DayTime");
        l.spawnX = data.getInt("SpawnX");
        l.spawnY = data.getInt("SpawnY");
        l.spawnZ = data.getInt("SpawnZ");
        l.spawnAngle = data.getFloat("SpawnAngle");
        l.gameType = data.getInt("GameType");
        l.difficulty = data.getByte("Difficulty");
        l.hardcore = data.getBoolean("hardcore");
        l.allowCommands = data.getBoolean("allowCommands");

        NbtCompound wgs = data.getCompound("WorldGenSettings");
        if (wgs != null) l.seed = wgs.getLong("seed");
        return l;
    }

    public void write(Path levelDat) throws IOException {
        Files.createDirectories(levelDat.getParent());

        NbtCompound data = new NbtCompound();
        data.putInt("DataVersion", dataVersion);

        NbtCompound version = new NbtCompound();
        version.putInt("Id", dataVersion);
        version.putString("Name", versionName);
        version.putString("Series", "main");
        version.putBoolean("Snapshot", false);
        data.put("Version", version);

        data.putInt("version", 19133); // version du format de niveau (Anvil)
        data.putBoolean("initialized", true);
        data.putString("LevelName", levelName);
        data.putLong("Time", time);
        data.putLong("DayTime", dayTime);
        data.putLong("LastPlayed", System.currentTimeMillis());

        data.putInt("SpawnX", spawnX);
        data.putInt("SpawnY", spawnY);
        data.putInt("SpawnZ", spawnZ);
        data.putFloat("SpawnAngle", spawnAngle);

        data.putInt("GameType", gameType);
        data.putBoolean("hardcore", hardcore);
        data.putByte("Difficulty", difficulty);
        data.putBoolean("DifficultyLocked", false);
        data.putBoolean("allowCommands", allowCommands);

        data.putInt("clearWeatherTime", 0);
        data.putInt("rainTime", 0);
        data.putBoolean("raining", false);
        data.putInt("thunderTime", 0);
        data.putBoolean("thundering", false);

        // Bordure de monde (valeurs par défaut vanilla)
        data.putDouble("BorderCenterX", 0d);
        data.putDouble("BorderCenterZ", 0d);
        data.putDouble("BorderSize", 59_999_968d);
        data.putDouble("BorderSafeZone", 5d);
        data.putDouble("BorderWarningBlocks", 5d);
        data.putDouble("BorderWarningTime", 15d);
        data.putDouble("BorderSizeLerpTarget", 59_999_968d);
        data.putLong("BorderSizeLerpTime", 0L);
        data.putDouble("BorderDamagePerBlock", 0.2d);

        NbtCompound dataPacks = new NbtCompound();
        NbtList enabled = new NbtList(NbtType.STRING);
        enabled.addString("vanilla");
        dataPacks.put("Enabled", enabled);
        dataPacks.put("Disabled", new NbtList(NbtType.STRING));
        data.put("DataPacks", dataPacks);

        NbtList serverBrands = new NbtList(NbtType.STRING);
        serverBrands.addString("Fidorial");
        data.put("ServerBrands", serverBrands);

        data.put("WorldGenSettings", buildWorldGenSettings());

        NbtCompound root = new NbtCompound();
        root.put("Data", data);

        NbtIo.writeGzip(levelDat, "", root);
    }

    private NbtCompound buildWorldGenSettings() {
        NbtCompound wgs = new NbtCompound();
        wgs.putLong("seed", seed);
        wgs.putBoolean("generate_features", true);
        wgs.putBoolean("bonus_chest", false);

        NbtCompound dimensions = new NbtCompound();
        dimensions.put("minecraft:overworld", flatDimension("minecraft:overworld", "minecraft:plains"));
        dimensions.put("minecraft:the_nether", flatDimension("minecraft:the_nether", "minecraft:nether_wastes"));
        dimensions.put("minecraft:the_end", flatDimension("minecraft:the_end", "minecraft:the_end"));
        wgs.put("dimensions", dimensions);
        return wgs;
    }

    private NbtCompound flatDimension(String typeId, String biome) {
        NbtCompound dim = new NbtCompound();
        dim.putString("type", typeId);

        NbtCompound generator = new NbtCompound();
        generator.putString("type", "minecraft:flat");

        NbtCompound settings = new NbtCompound();
        settings.putBoolean("features", false);
        settings.putBoolean("lakes", false);
        settings.putString("biome", biome);

        NbtList layers = new NbtList(NbtType.COMPOUND);
        NbtCompound layer = new NbtCompound();
        layer.putInt("height", 16);
        layer.putString("block", "minecraft:cobblestone");
        layers.add(layer);
        settings.put("layers", layers);

        settings.put("structure_overrides", new NbtList(NbtType.STRING));

        generator.put("settings", settings);
        dim.put("generator", generator);
        return dim;
    }

    public boolean exists(Path levelDat) {
        return Files.isRegularFile(levelDat);
    }
}
