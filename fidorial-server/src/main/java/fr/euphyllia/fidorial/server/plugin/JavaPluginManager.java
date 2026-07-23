package fr.euphyllia.fidorial.server.plugin;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import fr.euphyllia.fidorial.server.event.SimpleEventBus;
import fr.fidorial.Server;
import fr.fidorial.permission.Permissible;
import fr.fidorial.permission.Permission;
import fr.fidorial.permission.PermissionDefault;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;
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

    private final Map<String, Permission> permissions = new ConcurrentHashMap<>();
    private final Map<Boolean, Set<Permission>> defaultPerms = new ConcurrentHashMap<>();
    private final Map<String, Map<Permissible, Boolean>> permSubs = new ConcurrentHashMap<>();
    private final Map<Boolean, Map<Permissible, Boolean>> defSubs = new ConcurrentHashMap<>();
    private final Map<String, List<Permission>> pluginPermissions = new ConcurrentHashMap<>();

    public JavaPluginManager(Server server, SimpleEventBus events, ServiceRegistry services, Path pluginsFolder) {
        this.server = server;
        this.events = events;
        this.services = services;
        this.pluginsFolder = pluginsFolder;
        this.defaultPerms.put(Boolean.TRUE, new LinkedHashSet<>());
        this.defaultPerms.put(Boolean.FALSE, new LinkedHashSet<>());
    }

    private static void closeQuietly(@Nullable URLClassLoader classLoader) {
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
                removePluginPermissions(loaded.meta.id());
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

    @Override
    public Optional<Permission> getPermission(@Nullable String name) {
        return name == null ? Optional.empty() : Optional.ofNullable(permissions.get(name.toLowerCase(Locale.ROOT)));
    }

    @Override
    public void addPermission(Permission perm) {
        Objects.requireNonNull(perm, "perm");
        String name = perm.getName().toLowerCase(Locale.ROOT);
        if (permissions.putIfAbsent(name, perm) != null) {
            throw new IllegalArgumentException("La permission '" + name + "' est deja definie");
        }
        perm.attach(this);
        calculatePermissionDefault(perm);
    }

    @Override
    public void removePermission(Permission perm) {
        Objects.requireNonNull(perm, "perm");
        removePermission(perm.getName());
    }

    @Override
    public void removePermission(String name) {
        Objects.requireNonNull(name, "name");
        Permission perm = permissions.remove(name.toLowerCase(Locale.ROOT));
        if (perm != null) {
            synchronized (defaultPerms) {
                defaultPerms.get(Boolean.TRUE).remove(perm);
                defaultPerms.get(Boolean.FALSE).remove(perm);
            }
            dirtyPermissibles(perm.getDefault());
        }
    }

    @Override
    public Set<Permission> getDefaultPermissions(boolean op) {
        synchronized (defaultPerms) {
            return new LinkedHashSet<>(defaultPerms.get(op));
        }
    }

    @Override
    public void recalculatePermissionDefaults(@Nullable Permission perm) {
        if (perm != null && permissions.containsKey(perm.getName().toLowerCase(Locale.ROOT))) {
            synchronized (defaultPerms) {
                defaultPerms.get(Boolean.TRUE).remove(perm);
                defaultPerms.get(Boolean.FALSE).remove(perm);
            }
            calculatePermissionDefault(perm);
        }
    }

    private void calculatePermissionDefault(Permission perm) {
        synchronized (defaultPerms) {
            if (perm.getDefault().getValue(true)) {
                defaultPerms.get(Boolean.TRUE).add(perm);
            }
            if (perm.getDefault().getValue(false)) {
                defaultPerms.get(Boolean.FALSE).add(perm);
            }
        }
        dirtyPermissibles(perm.getDefault());
    }

    private void dirtyPermissibles(PermissionDefault def) {
        if (def.getValue(true)) {
            for (Permissible permissible : getDefaultPermSubscriptions(true)) {
                permissible.recalculatePermissions();
            }
        }
        if (def.getValue(false)) {
            for (Permissible permissible : getDefaultPermSubscriptions(false)) {
                permissible.recalculatePermissions();
            }
        }
    }

    @Override
    public void subscribeToPermission(String permission, Permissible permissible) {
        String name = permission.toLowerCase(Locale.ROOT);
        permSubs.computeIfAbsent(name, k -> Collections.synchronizedMap(new WeakHashMap<>()))
                .put(permissible, Boolean.TRUE);
    }

    @Override
    public void unsubscribeFromPermission(String permission, Permissible permissible) {
        String name = permission.toLowerCase(Locale.ROOT);
        Map<Permissible, Boolean> map = permSubs.get(name);
        if (map != null) {
            map.remove(permissible);
            if (map.isEmpty()) {
                permSubs.remove(name, map);
            }
        }
    }

    @Override
    public Set<Permissible> getPermissionSubscriptions(String permission) {
        Map<Permissible, Boolean> map = permSubs.get(permission.toLowerCase(Locale.ROOT));
        if (map == null) {
            return Set.of();
        }
        synchronized (map) {
            return new HashSet<>(map.keySet());
        }
    }

    @Override
    public void subscribeToDefaultPerms(boolean op, Permissible permissible) {
        defSubs.computeIfAbsent(op, k -> Collections.synchronizedMap(new WeakHashMap<>()))
                .put(permissible, Boolean.TRUE);
    }

    @Override
    public void unsubscribeFromDefaultPerms(boolean op, Permissible permissible) {
        Map<Permissible, Boolean> map = defSubs.get(op);
        if (map != null) {
            map.remove(permissible);
        }
    }

    @Override
    public Set<Permissible> getDefaultPermSubscriptions(boolean op) {
        Map<Permissible, Boolean> map = defSubs.get(op);
        if (map == null) {
            return Set.of();
        }
        synchronized (map) {
            return new HashSet<>(map.keySet());
        }
    }

    @Override
    public Set<Permission> getPermissions() {
        return new HashSet<>(permissions.values());
    }

    private void registerDescriptorPermissions(PluginMeta meta) {
        if (meta.permissions().isEmpty()) {
            return;
        }
        PermissionDefault def = PermissionDefault.getByName(meta.defaultPermission());
        if (def == null) {
            def = Permission.DEFAULT_PERMISSION;
        }
        List<Permission> registered = new ArrayList<>();
        for (Map.Entry<String, Map<String, Object>> entry : meta.permissions().entrySet()) {
            try {
                List<Permission> inline = new ArrayList<>();
                Permission perm = Permission.loadPermission(entry.getKey(), entry.getValue(), def, inline);
                for (Permission child : inline) {
                    safeAdd(child, registered, meta);
                }
                safeAdd(perm, registered, meta);
            } catch (Exception e) {
                LOGGER.error("Permission '{}' du plugin {} invalide", entry.getKey(), meta.id(), e);
            }
        }
        if (!registered.isEmpty()) {
            pluginPermissions.put(meta.id(), registered);
        }
    }

    private void safeAdd(Permission perm, List<Permission> registered, PluginMeta meta) {
        try {
            addPermission(perm);
            registered.add(perm);
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Le plugin {} tente de redefinir la permission '{}', ignoree", meta.id(), perm.getName());
        }
    }

    private void removePluginPermissions(String pluginId) {
        List<Permission> registered = pluginPermissions.remove(pluginId);
        if (registered != null) {
            for (Permission perm : registered) {
                removePermission(perm);
            }
        }
    }

    private Optional<Candidate> readCandidate(Path jar) {
        URLClassLoader classLoader = null;
        try {
            URL url = jar.toUri().toURL();
            classLoader = new URLClassLoader(
                    "fidorial-plugin:" + jar.getFileName(),
                    new URL[] {url},
                    getClass().getClassLoader());
            try (InputStream in = classLoader.getResourceAsStream(DESCRIPTOR)) {
                if (in == null) {
                    LOGGER.warn("{} ignore : pas de {} a la racine", jar.getFileName(), DESCRIPTOR);
                    classLoader.close();
                    return Optional.empty();
                }
                PluginMeta meta = GSON.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), PluginMeta.class);
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
            PluginContext context =
                    new SimplePluginContext(meta, server, events, services, pluginsFolder.resolve(meta.id()));
            registerDescriptorPermissions(meta);
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

    private void visit(
            Candidate candidate,
            Map<String, Candidate> byId,
            Set<String> done,
            Set<String> visiting,
            List<Candidate> ordered
    ) {
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
