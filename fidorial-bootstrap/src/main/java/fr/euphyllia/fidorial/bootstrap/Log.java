package fr.euphyllia.fidorial.bootstrap;

final class Log {

    private static final boolean QUIET = Boolean.getBoolean("fidorial.libraries.quiet");

    private Log() {
    }

    static void info(final String message) {
        if (!QUIET) {
            System.out.println("[fidorial] " + message);
        }
    }

    static void warn(final String message) {
        System.err.println("[fidorial] " + message);
    }
}
