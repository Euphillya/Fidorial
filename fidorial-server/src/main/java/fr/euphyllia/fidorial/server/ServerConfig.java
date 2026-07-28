package fr.euphyllia.fidorial.server;

import fr.euphyllia.fidorial.server.world.WorldConstants;
import fr.fidorial.entity.GameMode;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Properties;

public record ServerConfig(
        int port,
        boolean onlineMode,
        int viewDistance,
        int sendDistance,
        int compressionThreshold,
        Path worldPath,
        Path pluginsPath,
        int autoSaveSeconds,
        int regionWorkers,
        int chunkWorkers,
        int aiWorkers,
        int regionShift,
        GameMode defaultGameMode,
        double spawnX,
        double spawnY,
        double spawnZ,
        String motd,
        int maxPlayers,
        ProxyMode proxyMode,
        @Nullable String velocitySecret,
        boolean useIoUring
) {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(ServerConfig.class);
    private static final String DEFAULT_FILE = "fidorial.properties";

    public ServerConfig {
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("port hors bornes : " + port);
        }
        if (sendDistance > viewDistance) {
            throw new IllegalArgumentException(
                    "send-distance (" + sendDistance + ") > view-distance (" + viewDistance + ")");
        }
        if (proxyMode == ProxyMode.VELOCITY && (velocitySecret == null || velocitySecret.isBlank())) {
            throw new IllegalArgumentException(
                    "proxy-mode=velocity requires velocity-secret (the content of the proxy's forwarding.secret file)");
        }
    }

    public enum ProxyMode {
        NONE,
        VELOCITY;

        static @Nullable ProxyMode byName(final String raw) {
            for (final ProxyMode mode : values()) {
                if (mode.name().equalsIgnoreCase(raw)) {
                    return mode;
                }
            }
            return null;
        }
    }

    public static ServerConfig defaults() {
        final int cpus = Runtime.getRuntime().availableProcessors();
        return new ServerConfig(
                25565,
                true,
                10,
                10,
                256,
                Path.of("world"),
                Path.of("plugins"),
                5,
                Math.max(2, cpus / 2),
                Math.max(2, cpus / 8),
                Math.max(2, cpus / 8),
                5,
                GameMode.SURVIVAL,
                WorldConstants.DEFAULT_SPAWN_X,
                WorldConstants.DEFAULT_SPAWN_Y,
                WorldConstants.DEFAULT_SPAWN_Z,
                "",
                100,
                ProxyMode.NONE,
                "",
        false);
    }

    public static ServerConfig load() throws IOException {
        final Path file = Path.of(DEFAULT_FILE);
        final ServerConfig config = read(file);
        config.write(file);
        return config;
    }

    public static ServerConfig read(final Path file) throws IOException {
        final ServerConfig defaults = defaults();
        if (!Files.isRegularFile(file)) {
            return defaults;
        }
        final Properties props = new Properties();
        try (final InputStream in = Files.newInputStream(file)) {
            props.load(in);
        }
        final ServerConfig config = new ServerConfig(
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
                readInt(props, "ai-workers", defaults.aiWorkers()),
                readInt(props, "region-section-shift", defaults.regionShift()),
                readGameMode(props, "default-game-mode", defaults.defaultGameMode()),
                readDouble(props, "spawn-x", defaults.spawnX()),
                readDouble(props, "spawn-y", defaults.spawnY()),
                readDouble(props, "spawn-z", defaults.spawnZ()),
                readString(props, "motd", "<red>Fidorial <white>| <blue>Alternative Minecraft Server"),
                readInt(props, "max-players", defaults.maxPlayers()),
                readProxyMode(props, "proxy-mode", defaults.proxyMode()),
                readString(props, "velocity-secret", "").strip(),
                readBool(props, "use-io-uring", false));
        LOGGER.info("Configuration loaded from {}", file);
        return config;
    }

    private static int readInt(final Properties props, final String key, final int fallback) {
        final String raw = props.getProperty(key);
        if (raw == null || raw.isBlank()) {
            return fallback;
        }
        try {
            return Integer.parseInt(raw.strip());
        } catch (final NumberFormatException e) {
            LOGGER.warn("{} = '{}' unreadable, default value {} used", key, raw, fallback);
            return fallback;
        }
    }

    private static double readDouble(final Properties props, final String key, final double fallback) {
        final String raw = props.getProperty(key);
        if (raw == null || raw.isBlank()) {
            return fallback;
        }
        try {
            return Double.parseDouble(raw.strip());
        } catch (final NumberFormatException e) {
            LOGGER.warn("{} = '{}' invalid, default value {} used", key, raw, fallback);
            return fallback;
        }
    }

    private static String readString(final Properties props, final String key, final String fallback) {
        final String raw = props.getProperty(key);
        if (raw == null || raw.isBlank()) {
            return fallback;
        }
        return raw;
    }

    private static GameMode readGameMode(final Properties props, final String key, final GameMode fallback) {
        final String raw = props.getProperty(key);
        if (raw == null || raw.isBlank()) {
            return fallback;
        }
        final GameMode mode = GameMode.byName(raw.strip());
        if (mode == null) {
            LOGGER.warn("{} = '{}' unknown, default value {} used", key, raw, fallback);
            return fallback;
        }
        return mode;
    }

    private static ProxyMode readProxyMode(final Properties props, final String key, final ProxyMode fallback) {
        final String raw = props.getProperty(key);
        if (raw == null || raw.isBlank()) {
            return fallback;
        }
        final ProxyMode mode = ProxyMode.byName(raw.strip());
        if (mode == null) {
            LOGGER.warn("{} = '{}' unknown (expected: none, velocity), default value {} used", key, raw, fallback);
            return fallback;
        }
        return mode;
    }

    private static boolean readBool(final Properties props, final String key, final boolean fallback) {
        final String raw = props.getProperty(key);
        return raw == null || raw.isBlank() ? fallback : Boolean.parseBoolean(raw.strip());
    }

    public void write(final Path file) throws IOException {
        final Properties props = new Properties();
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
        props.setProperty("ai-workers", Integer.toString(aiWorkers));
        props.setProperty("region-section-shift", Integer.toString(regionShift));
        props.setProperty("default-game-mode", defaultGameMode.name().toLowerCase(Locale.ROOT));
        props.setProperty("spawn-x", Double.toString(spawnX));
        props.setProperty("spawn-y", Double.toString(spawnY));
        props.setProperty("spawn-z", Double.toString(spawnZ));
        props.setProperty("motd", motd);
        props.setProperty("max-players", Integer.toString(maxPlayers));
        props.setProperty("proxy-mode", proxyMode.name().toLowerCase(Locale.ROOT));
        props.setProperty("velocity-secret", velocitySecret == null ? "" : velocitySecret);
        props.setProperty("use-io-uring", Boolean.toString(useIoUring));
        try (final OutputStream out = Files.newOutputStream(file)) {
            props.store(out, "Configuration Fidorial");
        }
    }
}
