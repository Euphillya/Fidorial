package fr.fidorial.permission;

public enum TriState {

    /**
     * The node is explicitly granted.
     */
    ALLOW,

    /**
     * The node is explicitly refused; resolution stops here.
     */
    DENY,

    /**
     * No rule matched; resolution continues with the next source.
     */
    UNSET;

    /**
     * Maps a boolean to {@link #ALLOW} or {@link #DENY}.
     *
     * @param value boolean value
     * @return the matching state, never {@link #UNSET}
     */
    public static TriState of(final boolean value) {
        return value ? ALLOW : DENY;
    }

    /**
     * Parses a textual state as found in plugin descriptors.
     *
     * @param raw {@code allow}/{@code true}, {@code deny}/{@code false}, or anything else
     * @return the parsed state, {@link #UNSET} when unrecognised or {@code null}
     */
    public static TriState parse(final String raw) {
        if (raw == null) {
            return UNSET;
        }
        return switch (raw.trim().toLowerCase(java.util.Locale.ROOT)) {
            case "allow", "true", "yes", "grant" -> ALLOW;
            case "deny", "false", "no", "refuse" -> DENY;
            default -> UNSET;
        };
    }

    /**
     * @return {@code true} when this state is not {@link #UNSET}
     */
    public boolean isDecided() {
        return this != UNSET;
    }

    /**
     * Collapses this state to a boolean.
     *
     * @param fallback value used when this state is {@link #UNSET}
     * @return the decided value, or {@code fallback}
     */
    public boolean toBoolean(final boolean fallback) {
        return switch (this) {
            case ALLOW -> true;
            case DENY -> false;
            case UNSET -> fallback;
        };
    }

    /**
     * Returns this state if decided, otherwise {@code other}. Lets callers chain several sources
     * without nesting conditionals.
     *
     * @param other fallback state
     * @return the first decided state
     */
    public TriState orElse(final TriState other) {
        return isDecided() ? this : other;
    }
}
