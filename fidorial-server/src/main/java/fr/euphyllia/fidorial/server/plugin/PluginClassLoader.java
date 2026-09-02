package fr.euphyllia.fidorial.server.plugin;

import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

final class PluginClassLoader extends URLClassLoader {

    static {
        registerAsParallelCapable();
    }

    private final ClassLoader serverLoader;
    private final Set<String> parentFirstPackages;
    private final String pluginId;

    PluginClassLoader(
            final String pluginId,
            final URL[] urls,
            final ClassLoader serverLoader,
            final Set<String> parentFirstPackages
    ) {
        super("fidorial-plugin:" + pluginId, urls, serverLoader);
        this.pluginId = pluginId;
        this.serverLoader = serverLoader;
        this.parentFirstPackages = parentFirstPackages;
    }

    String pluginId() {
        return pluginId;
    }

    @Override
    protected Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> found = findLoadedClass(name);

            if (found == null) {
                final boolean parentFirst = isParentFirst(name);

                if (parentFirst) {
                    found = tryParent(name);
                }
                if (found == null) {
                    found = tryHere(name);
                }
                if (found == null && !parentFirst) {
                    found = tryParent(name);
                }
                if (found == null) {
                    throw new ClassNotFoundException(name + " (from plugin " + pluginId + ")");
                }
            }

            if (resolve) {
                resolveClass(found);
            }
            return found;
        }
    }

    private boolean isParentFirst(final String className) {
        if (className.startsWith("java.")
                || className.startsWith("javax.")
                || className.startsWith("jdk.")
                || className.startsWith("sun.")) {
            return true;
        }
        final int separator = className.lastIndexOf('.');
        if (separator < 0) {
            return true;
        }
        return parentFirstPackages.contains(className.substring(0, separator));
    }

    private @Nullable Class<?> tryParent(final String name) {
        try {
            return serverLoader.loadClass(name);
        } catch (final ClassNotFoundException e) {
            return null;
        }
    }

    private @Nullable Class<?> tryHere(final String name) {
        try {
            return findClass(name);
        } catch (final ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public @Nullable URL getResource(final String name) {
        final URL own = findResource(name);
        return own != null ? own : serverLoader.getResource(name);
    }

    @Override
    public Enumeration<URL> getResources(final String name) throws IOException {
        final List<URL> all = new ArrayList<>();
        all.addAll(Collections.list(findResources(name)));
        all.addAll(Collections.list(serverLoader.getResources(name)));
        return Collections.enumeration(all);
    }
}
