package fr.fidorial.item.component;

import org.jspecify.annotations.Nullable;

import java.util.Locale;

/**
 * The sixteen dye colours, in the order the wire numbers them.
 *
 * @since 0.1.0
 */
public enum DyeColor {

    WHITE(0, "white"),
    ORANGE(1, "orange"),
    MAGENTA(2, "magenta"),
    LIGHT_BLUE(3, "light_blue"),
    YELLOW(4, "yellow"),
    LIME(5, "lime"),
    PINK(6, "pink"),
    GRAY(7, "gray"),
    LIGHT_GRAY(8, "light_gray"),
    CYAN(9, "cyan"),
    PURPLE(10, "purple"),
    BLUE(11, "blue"),
    BROWN(12, "brown"),
    GREEN(13, "green"),
    RED(14, "red"),
    BLACK(15, "black");

    private final int networkId;
    private final String serializedName;

    DyeColor(final int networkId, final String serializedName) {
        this.networkId = networkId;
        this.serializedName = serializedName;
    }

    public int networkId() {
        return networkId;
    }

    public String serializedName() {
        return serializedName;
    }

    /**
     * @param name the serialized name, may be {@code null}
     * @return the matching colour, or {@code null} when there is none
     * @since 0.1.0
     */
    public static @Nullable DyeColor byName(final @Nullable String name) {
        if (name == null) {
            return null;
        }
        final String lower = name.toLowerCase(Locale.ROOT);
        for (final DyeColor color : values()) {
            if (color.serializedName.equals(lower)) {
                return color;
            }
        }
        return null;
    }

    /**
     * @param networkId the wire id
     * @return the matching colour, or {@code null} when there is none
     * @since 0.1.0
     */
    public static @Nullable DyeColor byNetworkId(final int networkId) {
        final DyeColor[] values = values();
        return networkId >= 0 && networkId < values.length ? values[networkId] : null;
    }
}
