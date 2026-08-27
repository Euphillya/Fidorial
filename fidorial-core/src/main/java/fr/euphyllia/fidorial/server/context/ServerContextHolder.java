package fr.euphyllia.fidorial.server.context;

final class ServerContextHolder {

    private static volatile ServerContext current;

    private ServerContextHolder() {
    }

    static void install(final ServerContext context) {
        current = context;
    }

    static void uninstall() {
        current = null;
    }

    static <T extends ServerContext> T require(final Class<T> type) {
        final ServerContext context = current;
        if (context == null) {
            throw new IllegalStateException(
                    "No ServerContext installed; FidorialServer#start must call "
                            + "ServerContext.install(this)");
        }
        if (!type.isInstance(context)) {
            throw new IllegalStateException(
                    "The installed context (" + context.getClass().getName()
                            + ") does not implement " + type.getSimpleName()
                            + "; a layer requires more than the context provides");
        }
        return type.cast(context);
    }
}
