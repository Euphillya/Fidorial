package fr.euphyllia.fidorial.server.plugin;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import fr.euphyllia.fidorial.server.event.SimpleEventBus;
import fr.fidorial.Server;
import fr.fidorial.permission.PermissionDefinition;
import fr.fidorial.permission.PermissionNode;
import fr.fidorial.permission.PermissionRegistry;
import fr.fidorial.permission.TriState;
import fr.fidorial.plugin.Plugin;
import fr.fidorial.plugin.PluginContext;
import fr.fidorial.plugin.PluginManager;
import fr.fidorial.plugin.PluginMeta;
import fr.fidorial.service.ServiceRegistry;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public final class JavaPluginManager implements PluginManager, AutoCloseable {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(JavaPluginManager.class);
    private static final String DESCRIPTOR = "fidorial.json";
    private static final Gson GSON = new Gson();

    private final Server server;
    private final SimpleEventBus events;
    private final ServiceRegistry services;
    private final Path pluginsFolder;

    private final Map<String, Loaded> plugins = new LinkedHashMap<>();

    private final PermissionRegistry permissions;
    private final Map<String, List<PermissionNode>> declaredByPlugin = new ConcurrentHashMap<>();

    public JavaPluginManager(
            final Server server,
            final SimpleEventBus events,
            final ServiceRegistry services,
            final PermissionRegistry permissions,
            final Path pluginsFolder
    ) {
        this.server = server;
        this.events = events;
        this.services = services;
        this.permissions = permissions;
        this.pluginsFolder = pluginsFolder;
    }

    private static void closeQuietly(@Nullable final URLClassLoader classLoader) {
        if (classLoader == null) {
            return;
        }
        try {
            classLoader.close();
        } catch (final IOException ignored) {
            // rien a faire : on abandonnait deja ce plugin
        }
    }

    public void loadAll() throws IOException {
        Files.createDirectories(pluginsFolder);
        final List<Candidate> candidates = new ArrayList<>();
        try (final Stream<Path> jars = Files.list(pluginsFolder)) {
            for (final Path jar : jars.filter(p -> p.toString().endsWith(".jar")).toList()) {
                readCandidate(jar).ifPresent(candidates::add);
            }
        }
        for (final Candidate candidate : sortByDependencies(candidates)) {
            instantiate(candidate);
        }
        LOGGER.info("{} plugin(s) charge(s)", plugins.size());
    }

    public void enableAll() {
        for (final Loaded loaded : plugins.values()) {
            try {
                events.withOwner(loaded.plugin, loaded.plugin::onEnable);
                loaded.enabled = true;
                LOGGER.info("Plugin active : {} v{}", loaded.meta.name(), loaded.meta.version());
            } catch (final Throwable t) {
                LOGGER.error("Activation de {} impossible, plugin ignore", loaded.meta.id(), t);
            }
        }
    }

    public void disableAll() {
        final List<Loaded> reversed = new ArrayList<>(plugins.values());
        for (int i = reversed.size() - 1; i >= 0; i--) {
            final Loaded loaded = reversed.get(i);
            if (!loaded.enabled) {
                continue;
            }
            try {
                loaded.plugin.onDisable();
            } catch (final Throwable t) {
                LOGGER.error("Erreur pendant onDisable de {}", loaded.meta.id(), t);
            } finally {
                loaded.enabled = false;
                events.unsubscribeAll(loaded.plugin);
                services.unregisterAll(loaded.plugin);
                removePluginPermissions(loaded.meta.id());
            }
        }
    }

    @Override
    public Collection<PluginMeta> loaded() {
        return plugins.values().stream().map(l -> l.meta).toList();
    }

    @Override
    public Optional<Plugin> plugin(final String id) {
        final Loaded loaded = plugins.get(id);
        return loaded == null ? Optional.empty() : Optional.of(loaded.plugin);
    }

    @Override
    public boolean isEnabled(final String id) {
        final Loaded loaded = plugins.get(id);
        return loaded != null && loaded.enabled;
    }

    @Override
    public void close() {
        disableAll();
        for (final Loaded loaded : plugins.values()) {
            try {
                loaded.classLoader.close();
            } catch (final IOException e) {
                LOGGER.warn("Fermeture du classloader de {} impossible", loaded.meta.id(), e);
            }
        }
        plugins.clear();
    }

    private void registerDescriptorPermissions(final PluginMeta meta) {
        if (meta.permissions().isEmpty()) {
            return;
        }
        final List<PermissionDefinition> definitions = new ArrayList<>();
        final List<PermissionNode> nodes = new ArrayList<>();
        for (final Map.Entry<String, PluginMeta.PermissionEntry> entry : meta.permissions().entrySet()) {
            try {
                final PermissionNode node = PermissionNode.of(entry.getKey());
                final PluginMeta.PermissionEntry declaration = entry.getValue();
                definitions.add(new PermissionDefinition(
                        node,
                        declaration.description(),
                        TriState.parse(declaration.regular()),
                        TriState.parse(declaration.operator())));
                nodes.add(node);
            } catch (final IllegalArgumentException e) {
                LOGGER.error("Permission '{}' du plugin {} invalide", entry.getKey(), meta.id(), e);
            }
        }
        if (!definitions.isEmpty()) {
            permissions.defineAll(definitions);
            declaredByPlugin.put(meta.id(), List.copyOf(nodes));
        }
    }

    private void removePluginPermissions(final String pluginId) {
        final List<PermissionNode> nodes = declaredByPlugin.remove(pluginId);
        if (nodes != null) {
            nodes.forEach(permissions::undefine);
        }
    }

    private Optional<Candidate> readCandidate(final Path jar) {
        URLClassLoader classLoader = null;
        try {
            final URL url = jar.toUri().toURL();
            classLoader = new URLClassLoader(
                    "fidorial-plugin:" + jar.getFileName(),
                    new URL[] {url},
                    getClass().getClassLoader());
            try (final InputStream in = classLoader.getResourceAsStream(DESCRIPTOR)) {
                if (in == null) {
                    LOGGER.warn("{} ignore : pas de {} a la racine", jar.getFileName(), DESCRIPTOR);
                    classLoader.close();
                    return Optional.empty();
                }
                final PluginMeta meta = GSON.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), PluginMeta.class);
                return Optional.of(new Candidate(meta, classLoader));
            }
        } catch (final JsonSyntaxException | NullPointerException | IllegalArgumentException e) {
            LOGGER.error("{} ignore : {} invalide", jar.getFileName(), DESCRIPTOR, e);
        } catch (final IOException e) {
            LOGGER.error("{} illisible", jar.getFileName(), e);
        }
        closeQuietly(classLoader);
        return Optional.empty();
    }

    private void instantiate(final Candidate candidate) {
        final PluginMeta meta = candidate.meta;
        try {
            final Class<?> mainClass = Class.forName(meta.main(), true, candidate.classLoader);
            if (!Plugin.class.isAssignableFrom(mainClass)) {
                LOGGER.error("{} ignore : {} n'implemente pas Plugin", meta.id(), meta.main());
                candidate.classLoader.close();
                return;
            }
            final Plugin plugin = (Plugin) mainClass.getDeclaredConstructor().newInstance();
            final PluginContext context =
                    new SimplePluginContext(meta, server, events, services, pluginsFolder.resolve(meta.id()));
            registerDescriptorPermissions(meta);
            events.withOwner(plugin, () -> plugin.onLoad(context));
            plugins.put(meta.id(), new Loaded(meta, plugin, candidate.classLoader));
        } catch (final Throwable t) {
            LOGGER.error("Chargement de {} impossible", meta.id(), t);
            closeQuietly(candidate.classLoader);
        }
    }

    /**
     * Tri topologique simple ; les dependances manquantes ou cycliques ecartent le plugin.
     */
    private List<Candidate> sortByDependencies(final List<Candidate> candidates) {
        final Map<String, Candidate> byId = new HashMap<>();
        for (final Candidate candidate : candidates) {
            if (byId.putIfAbsent(candidate.meta.id(), candidate) != null) {
                LOGGER.error("Deux plugins declarent l'id '{}', le second est ignore", candidate.meta.id());
                closeQuietly(candidate.classLoader);
            }
        }
        final List<Candidate> ordered = new ArrayList<>();
        final Set<String> done = new HashSet<>();
        final Set<String> visiting = new HashSet<>();
        for (final Candidate candidate : byId.values()) {
            visit(candidate, byId, done, visiting, ordered);
        }
        return ordered;
    }

    private void visit(
            final Candidate candidate,
            final Map<String, Candidate> byId,
            final Set<String> done,
            final Set<String> visiting,
            final List<Candidate> ordered
    ) {
        final String id = candidate.meta.id();
        if (done.contains(id)) {
            return;
        }
        if (!visiting.add(id)) {
            LOGGER.error("Dependance cyclique detectee autour de '{}', plugin ignore", id);
            return;
        }
        for (final String dependency : candidate.meta.depends()) {
            final Candidate resolved = byId.get(dependency);
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

        Loaded(final PluginMeta meta, final Plugin plugin, final URLClassLoader classLoader) {
            this.meta = meta;
            this.plugin = plugin;
            this.classLoader = classLoader;
        }
    }
}
