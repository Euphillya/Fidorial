package fr.euphyllia.fidorial.server.world.storage;

import fr.euphyllia.fidorial.server.world.chunk.AnvilChunkSerializer;
import fr.euphyllia.fidorial.server.world.entity.AnvilEntitySerializer;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.IntArrayBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import net.kyori.adventure.nbt.StringBinaryTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class LevelData {

    private static final String FIDORIAL = "Fidorial";
    private static final String WORLD_CLOCKS = "WorldClocks";
    private static final String CUSTOM_BOSS_EVENTS = "CustomBossEvents";

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

    public final Map<Key, WorldTime> worldTimes = new LinkedHashMap<>();
    public final Map<Key, BossBarData> bossBars = new LinkedHashMap<>();

    public @Nullable WorldTime worldTime(final Key dimensionId) {
        final WorldTime stored = worldTimes.get(dimensionId);
        if (stored != null) {
            return stored;
        }
        if (Dimension.OVERWORLD.id().equals(dimensionId)) {
            return new WorldTime(time, dayTime, doDaylightCycle);
        }
        return null;
    }

    public void setWorldTime(final Key dimensionId, final long worldAge, final long dayTime, final boolean doDaylightCycle) {
        worldTimes.put(dimensionId, new WorldTime(worldAge, dayTime, doDaylightCycle));
        if (Dimension.OVERWORLD.id().equals(dimensionId)) {
            this.time = worldAge;
            this.dayTime = dayTime;
            this.doDaylightCycle = doDaylightCycle;
        }
    }

    public record WorldTime(long worldAge, long dayTime, boolean doDaylightCycle) {
    }

    public record BossBarData(
            Component name,
            int value,
            int max,
            BossBar.Color color,
            BossBar.Overlay overlay,
            Set<BossBar.Flag> flags,
            boolean visible,
            Set<UUID> players
    ) {
    }

    public static LevelData read(final Path levelDat) throws IOException {
        final Map.Entry<String, CompoundBinaryTag> named =
                BinaryTagIO.reader().readNamed(levelDat, BinaryTagIO.Compression.GZIP);
        final CompoundBinaryTag data = named.getValue().getCompound("Data");
        final LevelData l = new LevelData();

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

        final CompoundBinaryTag gameRules = data.getCompound("GameRules");
        if (gameRules.contains("doDaylightCycle")) {
            l.doDaylightCycle = !"false".equals(gameRules.getString("doDaylightCycle"));
        }
        l.readWorldClocks(data);
        l.readCustomBossEvents(data);

        l.seed = data.getCompound("WorldGenSettings").getLong("seed");
        return l;
    }

    private void readWorldClocks(final CompoundBinaryTag data) {
        final CompoundBinaryTag fidorial = data.getCompound(FIDORIAL);

        for (final BinaryTag entry : fidorial.getList(WORLD_CLOCKS)) {
            if (!(entry instanceof final CompoundBinaryTag clock)) continue;
            final String dimension = clock.getString("Dimension");
            if (dimension.isEmpty()) continue;
            final Key key = Key.key(dimension);
            worldTimes.put(key, new WorldTime(
                    clock.getLong("WorldAge"),
                    clock.getLong("DayTime"),
                    !clock.contains("DoDaylightCycle") || clock.getBoolean("DoDaylightCycle")));
        }
    }

    private void readCustomBossEvents(final CompoundBinaryTag data) {
        final CompoundBinaryTag events = data.getCompound(CUSTOM_BOSS_EVENTS);

        for (final String id : events.keySet()) {
            final CompoundBinaryTag bar = events.getCompound(id);

            final Set<UUID> players = new HashSet<>();
            for (final BinaryTag entry : bar.getList("Players")) {
                if (entry instanceof IntArrayBinaryTag iat) {
                    players.add(AnvilEntitySerializer.uuidFromInts(iat.value()));
                }
            }

            final Set<BossBar.Flag> flags = new HashSet<>();
            if (bar.getBoolean("DarkenScreen")) flags.add(BossBar.Flag.DARKEN_SCREEN);
            if (bar.getBoolean("PlayBossMusic")) flags.add(BossBar.Flag.PLAY_BOSS_MUSIC);
            if (bar.getBoolean("CreateWorldFog")) flags.add(BossBar.Flag.CREATE_WORLD_FOG);

            final int max = bar.contains("Max") ? bar.getInt("Max") : 100;
            final int value = bar.getInt("Value");

            bossBars.put(Key.key(id), new BossBarData(
                    GsonComponentSerializer.gson().deserialize(bar.getString("Name")),
                    value,
                    max,
                    BossBar.Color.valueOf(bar.getString("Color").toUpperCase(Locale.ROOT)),
                    BossBar.Overlay.valueOf(bar.getString("Overlay").toUpperCase(Locale.ROOT)),
                    flags,
                    bar.getBoolean("Visible"),
                    players));
        }
    }

    public void write(final Path levelDat) throws IOException {
        Files.createDirectories(levelDat.getParent());

        final CompoundBinaryTag.Builder data = CompoundBinaryTag.builder();
        data.putInt("DataVersion", dataVersion);

        final CompoundBinaryTag.Builder version = CompoundBinaryTag.builder();
        version.putInt("Id", dataVersion);
        version.putString("Name", versionName);
        version.putString("Series", "main");
        version.putBoolean("Snapshot", false);
        data.put("Version", version.build());

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
        data.putByte("Difficulty", (byte) difficulty);
        data.putBoolean("DifficultyLocked", false);
        data.putBoolean("allowCommands", allowCommands);

        data.putInt("clearWeatherTime", clearWeatherTime);
        data.putInt("rainTime", rainTime);
        data.putBoolean("raining", raining);
        data.putInt("thunderTime", thunderTime);
        data.putBoolean("thundering", thundering);

        final CompoundBinaryTag.Builder gameRules = CompoundBinaryTag.builder();
        gameRules.putString("doDaylightCycle", Boolean.toString(doDaylightCycle));
        data.put("GameRules", gameRules.build());

        data.put(FIDORIAL, buildFidorialData());
        data.put(CUSTOM_BOSS_EVENTS, buildCustomBossEvents());

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

        final CompoundBinaryTag.Builder dataPacks = CompoundBinaryTag.builder();
        dataPacks.put("Enabled", ListBinaryTag.builder().add(StringBinaryTag.stringBinaryTag("vanilla")).build());
        dataPacks.put("Disabled", ListBinaryTag.empty());
        data.put("DataPacks", dataPacks.build());

        data.put("ServerBrands", ListBinaryTag.builder().add(StringBinaryTag.stringBinaryTag("Fidorial")).build());

        data.put("WorldGenSettings", buildWorldGenSettings());

        final CompoundBinaryTag root = CompoundBinaryTag.builder().put("Data", data.build()).build();

        BinaryTagIO.writer().writeNamed(Map.entry("", root), levelDat, BinaryTagIO.Compression.GZIP);
    }

    private CompoundBinaryTag buildFidorialData() {
        final ListBinaryTag.Builder<BinaryTag> clocks = ListBinaryTag.builder();
        for (final Map.Entry<Key, WorldTime> entry : worldTimes.entrySet()) {
            final WorldTime value = entry.getValue();
            final CompoundBinaryTag.Builder clock = CompoundBinaryTag.builder();
            clock.putString("Dimension", entry.getKey().asString());
            clock.putLong("WorldAge", value.worldAge());
            clock.putLong("DayTime", value.dayTime());
            clock.putBoolean("DoDaylightCycle", value.doDaylightCycle());
            clocks.add(clock.build());
        }
        return CompoundBinaryTag.builder().put(WORLD_CLOCKS, clocks.build()).build();
    }

    // this is the framework for built-in /bossbars command which DOES persist, unlike plugin ones
    private CompoundBinaryTag buildCustomBossEvents() {
        final CompoundBinaryTag.Builder root = CompoundBinaryTag.builder();

        for (final Map.Entry<Key, BossBarData> entry : bossBars.entrySet()) {
            final BossBarData value = entry.getValue();
            final CompoundBinaryTag.Builder bar = CompoundBinaryTag.builder();

            final ListBinaryTag.Builder<BinaryTag> players = ListBinaryTag.builder();
            for (final UUID uuid : value.players()) {
                players.add(IntArrayBinaryTag.intArrayBinaryTag(AnvilEntitySerializer.uuidToInts(uuid)));
            }
            bar.put("Players", players.build());

            bar.putString("Color", value.color().name().toLowerCase(Locale.ROOT));
            bar.putString("Overlay", value.overlay().name().toLowerCase(Locale.ROOT));
            bar.putBoolean("CreateWorldFog", value.flags().contains(BossBar.Flag.CREATE_WORLD_FOG));
            bar.putBoolean("DarkenScreen", value.flags().contains(BossBar.Flag.DARKEN_SCREEN));
            bar.putBoolean("PlayBossMusic", value.flags().contains(BossBar.Flag.PLAY_BOSS_MUSIC));
            bar.putInt("Max", value.max());
            bar.putInt("Value", value.value());
            bar.putString("Name", GsonComponentSerializer.gson().serialize(value.name()));
            bar.putBoolean("Visible", value.visible());

            root.put(entry.getKey().asString(), bar.build());
        }

        return root.build();
    }

    private CompoundBinaryTag buildWorldGenSettings() {
        final CompoundBinaryTag.Builder wgs = CompoundBinaryTag.builder();
        wgs.putLong("seed", seed);
        wgs.putBoolean("generate_features", true);
        wgs.putBoolean("bonus_chest", false);

        final CompoundBinaryTag.Builder dimensions = CompoundBinaryTag.builder();
        dimensions.put("minecraft:overworld", flatDimension("minecraft:overworld", "minecraft:plains"));
        dimensions.put("minecraft:the_nether", flatDimension("minecraft:the_nether", "minecraft:nether_wastes"));
        dimensions.put("minecraft:the_end", flatDimension("minecraft:the_end", "minecraft:the_end"));
        wgs.put("dimensions", dimensions.build());
        return wgs.build();
    }

    private CompoundBinaryTag flatDimension(final String typeId, final String biome) {
        final CompoundBinaryTag.Builder dim = CompoundBinaryTag.builder();
        dim.putString("type", typeId);

        final CompoundBinaryTag.Builder generator = CompoundBinaryTag.builder();
        generator.putString("type", "minecraft:flat");

        final CompoundBinaryTag.Builder settings = CompoundBinaryTag.builder();
        settings.putBoolean("features", false);
        settings.putBoolean("lakes", false);
        settings.putString("biome", biome);

        final ListBinaryTag.Builder<BinaryTag> layers = ListBinaryTag.builder();
        final CompoundBinaryTag.Builder layer = CompoundBinaryTag.builder();
        layer.putInt("height", 16);
        layer.putString("block", "minecraft:cobblestone");
        layers.add(layer.build());
        settings.put("layers", layers.build());

        settings.put("structure_overrides", ListBinaryTag.empty());

        generator.put("settings", settings.build());
        dim.put("generator", generator.build());
        return dim.build();
    }

    public boolean exists(final Path levelDat) {
        return Files.isRegularFile(levelDat);
    }
}
