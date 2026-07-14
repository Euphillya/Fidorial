package fr.euphyllia.fidorial.server.plugin;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import fr.euphyllia.fidorial.api.Server;
import fr.euphyllia.fidorial.api.plugin.Plugin;
import fr.euphyllia.fidorial.api.plugin.PluginContext;
import fr.euphyllia.fidorial.api.plugin.PluginManager;
import fr.euphyllia.fidorial.api.plugin.PluginMeta;
import fr.euphyllia.fidorial.api.service.ServiceRegistry;
import fr.euphyllia.fidorial.server.event.SimpleEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public final class JavaPluginManager implements PluginManager, AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaPluginManager.class);
    private static final String DESCRIPTOR = "fidorial.json";
    private static final Gson GSON = new Gson();

    private final Server server;
    private final SimpleEventBus events;
    private final ServiceRegistry services;
    private final Path pluginsFolder;

    private final Map<String, Loaded> plugins = new LinkedHashMap<>();

    public JavaPluginManager(Server server, SimpleEventBus events, ServiceRegistry services, Path pluginsFolder) {
        this.server = server;
        this.events = events;
        this.services = services;
        this.pluginsFolder = pluginsFolder;
    }

    private static void closeQuietly(URLClassLoader classLoader) {
        if (classLoader == null) {
            return;
        }
        try {
            classLoader.close();
        } catch (IOException ignored) {
            // rien a faire : on abandonnait deja ce plugin
        }
    }

    public void loadAll() throws IOException {
        Files.createDirectories(pluginsFolder);
        List<Candidate> candidates = new ArrayList<>();
        try (Stream<Path> jars = Files.list(pluginsFolder)) {
            for (Path jar : jars.filter(p -> p.toString().endsWith(".jar")).toList()) {
                readCandidate(jar).ifPresent(candidates::add);
            }
        }
        for (Candidate candidate : sortByDependencies(candidates)) {
            instantiate(candidate);
        }
        LOGGER.info("{} plugin(s) charge(s)", plugins.size());
    }

    public void enableAll() {
        for (Loaded loaded : plugins.values()) {
            try {
                events.withOwner(loaded.plugin, loaded.plugin::onEnable);
                loaded.enabled = true;
                LOGGER.info("Plugin active : {} v{}", loaded.meta.name(), loaded.meta.version());
            } catch (Throwable t) {
                LOGGER.error("Activation de {} impossible, plugin ignore", loaded.meta.id(), t);
            }
        }
    }

    public void disableAll() {
        List<Loaded> reversed = new ArrayList<>(plugins.values());
        for (int i = reversed.size() - 1; i >= 0; i--) {
            Loaded loaded = reversed.get(i);
            if (!loaded.enabled) {
                continue;
            }
            try {
                loaded.plugin.onDisable();
            } catch (Throwable t) {
                LOGGER.error("Erreur pendant onDisable de {}", loaded.meta.id(), t);
            } finally {
                loaded.enabled = false;
                events.unsubscribeAll(loaded.plugin);
                services.unregisterAll(loaded.plugin);
            }
        }
    }

    @Override
    public Collection<PluginMeta> loaded() {
        return plugins.values().stream().map(l -> l.meta).toList();
    }

    @Override
    public Optional<Plugin> plugin(String id) {
        Loaded loaded = plugins.get(id);
        return loaded == null ? Optional.empty() : Optional.of(loaded.plugin);
    }

    @Override
    public boolean isEnabled(String id) {
        Loaded loaded = plugins.get(id);
        return loaded != null && loaded.enabled;
    }

    @Override
    public void close() {
        disableAll();
        for (Loaded loaded : plugins.values()) {
            try {
                loaded.classLoader.close();
            } catch (IOException e) {
                LOGGER.warn("Fermeture du classloader de {} impossible", loaded.meta.id(), e);
            }
        }
        plugins.clear();
    }

    private Optional<Candidate> readCandidate(Path jar) {
        URLClassLoader classLoader = null;
        try {
            URL url = jar.toUri().toURL();
            classLoader = new URLClassLoader("fidorial-plugin:" + jar.getFileName(),
                    new URL[]{url}, getClass().getClassLoader());
            try (InputStream in = classLoader.getResourceAsStream(DESCRIPTOR)) {
                if (in == null) {
                    LOGGER.warn("{} ignore : pas de {} a la racine", jar.getFileName(), DESCRIPTOR);
                    classLoader.close();
                    return Optional.empty();
                }
                PluginMeta meta = GSON.fromJson(
                        new InputStreamReader(in, StandardCharsets.UTF_8), PluginMeta.class);
                return Optional.of(new Candidate(meta, classLoader));
            }
        } catch (JsonSyntaxException | NullPointerException | IllegalArgumentException e) {
            LOGGER.error("{} ignore : {} invalide", jar.getFileName(), DESCRIPTOR, e);
        } catch (IOException e) {
            LOGGER.error("{} illisible", jar.getFileName(), e);
        }
        closeQuietly(classLoader);
        return Optional.empty();
    }

    private void instantiate(Candidate candidate) {
        PluginMeta meta = candidate.meta;
        try {
            Class<?> mainClass = Class.forName(meta.main(), true, candidate.classLoader);
            if (!Plugin.class.isAssignableFrom(mainClass)) {
                LOGGER.error("{} ignore : {} n'implemente pas Plugin", meta.id(), meta.main());
                candidate.classLoader.close();
                return;
            }
            Plugin plugin = (Plugin) mainClass.getDeclaredConstructor().newInstance();
            PluginContext context = new SimplePluginContext(
                    meta, server, events, services, pluginsFolder.resolve(meta.id()));
            events.withOwner(plugin, () -> plugin.onLoad(context));
            plugins.put(meta.id(), new Loaded(meta, plugin, candidate.classLoader));
        } catch (Throwable t) {
            LOGGER.error("Chargement de {} impossible", meta.id(), t);
            closeQuietly(candidate.classLoader);
        }
    }

    /**
     * Tri topologique simple ; les dependances manquantes ou cycliques ecartent le plugin.
     */
    private List<Candidate> sortByDependencies(List<Candidate> candidates) {
        Map<String, Candidate> byId = new HashMap<>();
        for (Candidate candidate : candidates) {
            if (byId.putIfAbsent(candidate.meta.id(), candidate) != null) {
                LOGGER.error("Deux plugins declarent l'id '{}', le second est ignore", candidate.meta.id());
                closeQuietly(candidate.classLoader);
            }
        }
        List<Candidate> ordered = new ArrayList<>();
        Set<String> done = new HashSet<>();
        Set<String> visiting = new HashSet<>();
        for (Candidate candidate : byId.values()) {
            visit(candidate, byId, done, visiting, ordered);
        }
        return ordered;
    }

    private void visit(Candidate candidate, Map<String, Candidate> byId,
                       Set<String> done, Set<String> visiting, List<Candidate> ordered) {
        String id = candidate.meta.id();
        if (done.contains(id)) {
            return;
        }
        if (!visiting.add(id)) {
            LOGGER.error("Dependance cyclique detectee autour de '{}', plugin ignore", id);
            return;
        }
        for (String dependency : candidate.meta.depends()) {
            Candidate resolved = byId.get(dependency);
            if (resolved == null) {
                LOGGER.error("{} ignore : dependance '{}' introuvable", id, dependency);
                visiting.remove(id);
                return;
            }
            visit(resolved, byId, done, visiting, ordered);
        }
        visiting.remove(id);
        if (done.add(id)) {
            ordered.add(candidate);
        }
    }

    private record Candidate(PluginMeta meta, URLClassLoader classLoader) {
    }

    private static final class Loaded {
        final PluginMeta meta;
        final Plugin plugin;
        final URLClassLoader classLoader;
        boolean enabled;

        Loaded(PluginMeta meta, Plugin plugin, URLClassLoader classLoader) {
            this.meta = meta;
            this.plugin = plugin;
            this.classLoader = classLoader;
        }
    }
}
