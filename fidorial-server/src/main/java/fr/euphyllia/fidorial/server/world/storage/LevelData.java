package fr.euphyllia.fidorial.server.world.storage;

import fr.euphyllia.fidorial.server.world.chunk.AnvilChunkSerializer;
import fr.euphyllia.fidorial.server.world.nbt.Nbt;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import fr.euphyllia.fidorial.server.world.nbt.NbtIo;
import fr.euphyllia.fidorial.server.world.nbt.NbtList;
import fr.euphyllia.fidorial.server.world.nbt.NbtType;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class LevelData {

    private static final String FIDORIAL = "Fidorial";
    private static final String WORLD_CLOCKS = "WorldClocks";

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

    public boolean raining = false;
    public int rainTime = 0;
    public boolean thundering = false;
    public int thunderTime = 0;
    public int clearWeatherTime = 0;

    public boolean doDaylightCycle = true;

    public final Map<String, WorldTime> worldTimes = new LinkedHashMap<>();

    public @Nullable WorldTime worldTime(final String dimensionId) {
        final WorldTime stored = worldTimes.get(dimensionId);
        if (stored != null) {
            return stored;
        }
        if (Dimension.OVERWORLD.id().equals(dimensionId)) {
            return new WorldTime(time, dayTime, doDaylightCycle);
        }
        return null;
    }

    public void setWorldTime(final String dimensionId, final long worldAge, final long dayTime, final boolean doDaylightCycle) {
        worldTimes.put(dimensionId, new WorldTime(worldAge, dayTime, doDaylightCycle));
        if (Dimension.OVERWORLD.id().equals(dimensionId)) {
            this.time = worldAge;
            this.dayTime = dayTime;
            this.doDaylightCycle = doDaylightCycle;
        }
    }

    public record WorldTime(long worldAge, long dayTime, boolean doDaylightCycle) {
    }

    public static LevelData read(final Path levelDat) throws IOException {
        final NbtIo.Named named = NbtIo.readGzip(levelDat);
        final NbtCompound data = named.compound().getCompound("Data");
        final LevelData l = new LevelData();
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
        l.raining = data.getBoolean("raining");
        l.rainTime = data.getInt("rainTime");
        l.thundering = data.getBoolean("thundering");
        l.thunderTime = data.getInt("thunderTime");
        l.clearWeatherTime = data.getInt("clearWeatherTime");

        final NbtCompound gameRules = data.getCompound("GameRules");
        if (gameRules != null && gameRules.contains("doDaylightCycle")) {
            l.doDaylightCycle = !"false".equals(gameRules.getString("doDaylightCycle"));
        }
        l.readWorldClocks(data);

        final NbtCompound wgs = data.getCompound("WorldGenSettings");
        if (wgs != null) l.seed = wgs.getLong("seed");
        return l;
    }

    private void readWorldClocks(final NbtCompound data) {
        final NbtCompound fidorial = data.getCompound(FIDORIAL);
        if (fidorial == null) {
            return;
        }
        final NbtList clocks = fidorial.getList(WORLD_CLOCKS);
        if (clocks == null) {
            return;
        }
        for (final Nbt entry : clocks) {
            if (!(entry instanceof final NbtCompound clock)) {
                continue;
            }
            final String dimension = clock.getString("Dimension");
            if (dimension.isEmpty()) {
                continue;
            }
            worldTimes.put(
                    dimension,
                    new WorldTime(
                            clock.getLong("WorldAge"),
                            clock.getLong("DayTime"),
                            !clock.contains("DoDaylightCycle") || clock.getBoolean("DoDaylightCycle")));
        }
    }

    public void write(final Path levelDat) throws IOException {
        Files.createDirectories(levelDat.getParent());

        final NbtCompound data = new NbtCompound();
        data.putInt("DataVersion", dataVersion);

        final NbtCompound version = new NbtCompound();
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

        data.putInt("clearWeatherTime", clearWeatherTime);
        data.putInt("rainTime", rainTime);
        data.putBoolean("raining", raining);
        data.putInt("thunderTime", thunderTime);
        data.putBoolean("thundering", thundering);

        final NbtCompound gameRules = new NbtCompound();
        gameRules.putString("doDaylightCycle", Boolean.toString(doDaylightCycle));
        data.put("GameRules", gameRules);

        data.put(FIDORIAL, buildFidorialData());

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

        final NbtCompound dataPacks = new NbtCompound();
        final NbtList enabled = new NbtList(NbtType.STRING);
        enabled.addString("vanilla");
        dataPacks.put("Enabled", enabled);
        dataPacks.put("Disabled", new NbtList(NbtType.STRING));
        data.put("DataPacks", dataPacks);

        final NbtList serverBrands = new NbtList(NbtType.STRING);
        serverBrands.addString("Fidorial");
        data.put("ServerBrands", serverBrands);

        data.put("WorldGenSettings", buildWorldGenSettings());

        final NbtCompound root = new NbtCompound();
        root.put("Data", data);

        NbtIo.writeGzip(levelDat, "", root);
    }

    private NbtCompound buildFidorialData() {
        final NbtList clocks = new NbtList(NbtType.COMPOUND);
        for (final Map.Entry<String, WorldTime> entry : worldTimes.entrySet()) {
            final WorldTime value = entry.getValue();
            final NbtCompound clock = new NbtCompound();
            clock.putString("Dimension", entry.getKey());
            clock.putLong("WorldAge", value.worldAge());
            clock.putLong("DayTime", value.dayTime());
            clock.putBoolean("DoDaylightCycle", value.doDaylightCycle());
            clocks.addCompound(clock);
        }
        final NbtCompound fidorial = new NbtCompound();
        fidorial.put(WORLD_CLOCKS, clocks);
        return fidorial;
    }

    private NbtCompound buildWorldGenSettings() {
        final NbtCompound wgs = new NbtCompound();
        wgs.putLong("seed", seed);
        wgs.putBoolean("generate_features", true);
        wgs.putBoolean("bonus_chest", false);

        final NbtCompound dimensions = new NbtCompound();
        dimensions.put("minecraft:overworld", flatDimension("minecraft:overworld", "minecraft:plains"));
        dimensions.put("minecraft:the_nether", flatDimension("minecraft:the_nether", "minecraft:nether_wastes"));
        dimensions.put("minecraft:the_end", flatDimension("minecraft:the_end", "minecraft:the_end"));
        wgs.put("dimensions", dimensions);
        return wgs;
    }

    private NbtCompound flatDimension(final String typeId, final String biome) {
        final NbtCompound dim = new NbtCompound();
        dim.putString("type", typeId);

        final NbtCompound generator = new NbtCompound();
        generator.putString("type", "minecraft:flat");

        final NbtCompound settings = new NbtCompound();
        settings.putBoolean("features", false);
        settings.putBoolean("lakes", false);
        settings.putString("biome", biome);

        final NbtList layers = new NbtList(NbtType.COMPOUND);
        final NbtCompound layer = new NbtCompound();
        layer.putInt("height", 16);
        layer.putString("block", "minecraft:cobblestone");
        layers.add(layer);
        settings.put("layers", layers);

        settings.put("structure_overrides", new NbtList(NbtType.STRING));

        generator.put("settings", settings);
        dim.put("generator", generator);
        return dim;
    }

    public boolean exists(final Path levelDat) {
        return Files.isRegularFile(levelDat);
    }
}
