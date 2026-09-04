package fr.euphyllia.fidorial.bootstrap;

public final class BootstrapException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BootstrapException(final String message) {
        super(message);
    }

    public BootstrapException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
