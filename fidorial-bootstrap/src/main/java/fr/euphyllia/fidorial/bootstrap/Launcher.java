package fr.euphyllia.fidorial.bootstrap;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

final class Launcher {

    private Launcher() {
    }

    static void launch(final List<Path> classpath, final String mainClass, final String[] args) throws Exception {
        final URL[] urls = new URL[classpath.size()];
        for (int i = 0; i < classpath.size(); i++) {
            final Path entry = classpath.get(i);
            if (!Files.isRegularFile(entry)) {
                throw new BootstrapException("classpath entry vanished: " + entry);
            }
            urls[i] = toUrl(entry);
        }

        System.setProperty("java.class.path", classpath.stream()
                .map(Path::toString)
                .collect(Collectors.joining(File.pathSeparator)));

        final URLClassLoader loader = new URLClassLoader("fidorial", urls, ClassLoader.getPlatformClassLoader());
        Thread.currentThread().setContextClassLoader(loader);

        final Class<?> type = Class.forName(mainClass, true, loader);
        final Method main = type.getDeclaredMethod("main", String[].class);
        main.setAccessible(true);
        try {
            main.invoke(null, (Object) args);
        } catch (final InvocationTargetException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof final Exception exception) {
                throw exception;
            }
            throw new BootstrapException("the server failed to start", cause);
        }
    }

    private static URL toUrl(final Path path) {
        try {
            return path.toUri().toURL();
        } catch (final MalformedURLException e) {
            throw new BootstrapException("cannot build a URL for " + path, e);
        }
    }
}
