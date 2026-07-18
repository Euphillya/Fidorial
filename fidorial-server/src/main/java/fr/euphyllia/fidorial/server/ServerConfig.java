package fr.euphyllia.fidorial.server;

import fr.euphyllia.fidorial.api.entity.GameMode;
import fr.euphyllia.fidorial.server.world.WorldConstants;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Properties;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public record ServerConfig(int port,
                           boolean onlineMode,
                           int viewDistance,
                           int sendDistance,
                           int compressionThreshold,
                           Path worldPath,
                           Path pluginsPath,
                           int autoSaveSeconds,
                           int regionWorkers,
                           int chunkWorkers,
                           GameMode defaultGameMode,
                           double spawnX,
                           double spawnY,
                           double spawnZ,
                           String motd) {

    private static final ComponentLogger LOGGER = getLogger(ServerConfig.class);
    private static final String DEFAULT_FILE = "fidorial.properties";

    public ServerConfig {
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("port hors bornes : " + port);
        }
        if (sendDistance > viewDistance) {
            throw new IllegalArgumentException(
                    "send-distance (" + sendDistance + ") > view-distance (" + viewDistance + ")");
        }
    }

    public static ServerConfig defaults() {
        int cpus = Runtime.getRuntime().availableProcessors();
        return new ServerConfig(
                25565,
                true,
                8,
                3,
                256,
                Path.of("world"),
                Path.of("plugins"),
                5,
                Math.max(2, cpus / 2),
                Math.max(2, cpus / 2),
                GameMode.SURVIVAL,
                WorldConstants.DEFAULT_SPAWN_X, WorldConstants.DEFAULT_SPAWN_Y, WorldConstants.DEFAULT_SPAWN_Z,
                "");
    }

    public static ServerConfig load() throws IOException {
        return load(Path.of(DEFAULT_FILE));
    }

    public static ServerConfig load(Path file) throws IOException {
        ServerConfig defaults = defaults();
        if (!Files.isRegularFile(file)) {
            defaults.write(file);
            LOGGER.info("{} cree avec les valeurs par defaut", file);
            return defaults;
        }
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(file)) {
            props.load(in);
        }
        ServerConfig config = new ServerConfig(
                readInt(props, "port", defaults.port()),
                readBool(props, "online-mode", defaults.onlineMode()),
                readInt(props, "view-distance", defaults.viewDistance()),
                readInt(props, "send-distance", defaults.sendDistance()),
                readInt(props, "compression-threshold", defaults.compressionThreshold()),
                Path.of(props.getProperty("world-path", defaults.worldPath().toString())),
                Path.of(props.getProperty("plugins-path", defaults.pluginsPath().toString())),
                readInt(props, "auto-save-seconds", defaults.autoSaveSeconds()),
                readInt(props, "region-workers", defaults.regionWorkers()),
                readInt(props, "chunk-workers", defaults.chunkWorkers()),
                readGameMode(props, "default-game-mode", defaults.defaultGameMode()),
                readDouble(props, "spawn-x", defaults.spawnX()),
                readDouble(props, "spawn-y", defaults.spawnY()),
                readDouble(props, "spawn-z", defaults.spawnZ()),
                readString(props, "motd", "<red>Fidorial <white>| <blue>Alternative Minecraft Server"));
        LOGGER.info("Configuration chargee depuis {}", file);
        return config;
    }

    private static int readInt(Properties props, String key, int fallback) {
        String raw = props.getProperty(key);
        if (raw == null || raw.isBlank()) {
            return fallback;
        }
        try {
            return Integer.parseInt(raw.strip());
        } catch (NumberFormatException e) {
            LOGGER.warn("{} = '{}' illisible, valeur par defaut {} utilisee", key, raw, fallback);
            return fallback;
        }
    }

    private static double readDouble(Properties props, String key, double fallback) {
        String raw = props.getProperty(key);
        if (raw == null || raw.isBlank()) {
            return fallback;
        }
        try {
            return Double.parseDouble(raw.strip());
        } catch (NumberFormatException e) {
            LOGGER.warn("{} = '{}' invalide, valeur par defaut {} utilisee", key, raw, fallback);
            return fallback;
        }
    }

    private static String readString(Properties props, String key, String fallback) {
        String raw = props.getProperty(key);
        if (raw == null || raw.isBlank()) {
            return fallback;
        }
        return raw;
    }

    private static GameMode readGameMode(Properties props, String key, GameMode fallback) {
        String raw = props.getProperty(key);
        if (raw == null || raw.isBlank()) {
            return fallback;
        }
        GameMode mode = GameMode.byName(raw.strip());
        if (mode == null) {
            LOGGER.warn("{} = '{}' inconnu, valeur par defaut {} utilisee", key, raw, fallback);
            return fallback;
        }
        return mode;
    }

    private static boolean readBool(Properties props, String key, boolean fallback) {
        String raw = props.getProperty(key);
        return raw == null || raw.isBlank() ? fallback : Boolean.parseBoolean(raw.strip());
    }

    public void write(Path file) throws IOException {
        Properties props = new Properties();
        props.setProperty("port", Integer.toString(port));
        props.setProperty("online-mode", Boolean.toString(onlineMode));
        props.setProperty("view-distance", Integer.toString(viewDistance));
        props.setProperty("send-distance", Integer.toString(sendDistance));
        props.setProperty("compression-threshold", Integer.toString(compressionThreshold));
        props.setProperty("world-path", worldPath.toString());
        props.setProperty("plugins-path", pluginsPath.toString());
        props.setProperty("auto-save-seconds", Integer.toString(autoSaveSeconds));
        props.setProperty("region-workers", Integer.toString(regionWorkers));
        props.setProperty("chunk-workers", Integer.toString(chunkWorkers));
        props.setProperty("default-game-mode", defaultGameMode.name().toLowerCase(Locale.ROOT));
        props.setProperty("spawn-x", Double.toString(spawnX));
        props.setProperty("spawn-y", Double.toString(spawnY));
        props.setProperty("spawn-z", Double.toString(spawnZ));
        props.setProperty("motd", motd);
        try (OutputStream out = Files.newOutputStream(file)) {
            props.store(out, "Configuration Fidorial");
        }
    }
}
