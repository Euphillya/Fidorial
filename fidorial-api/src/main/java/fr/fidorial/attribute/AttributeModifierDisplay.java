package fr.fidorial.attribute;

import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;

/**
 * How one {@link AttributeModifier} is drawn in an item's tooltip.
 *
 * <p>Note this is per modifier, and separate from {@code minecraft:tooltip_display},
 * which hides whole components at once.
 *
 * @param type  which of the three shapes this is
 * @param value the line to draw instead, non-{@code null} exactly when
 *              {@code type} is {@link Type#OVERRIDE}
 * @since 0.1.0
 */
public record AttributeModifierDisplay(Type type, @Nullable Component value) {

    /**
     * The game writes the line, as it does for a vanilla weapon.
     */
    public static final AttributeModifierDisplay DEFAULT = new AttributeModifierDisplay(Type.DEFAULT, null);

    /**
     * The modifier still applies, but nothing about it is drawn.
     */
    public static final AttributeModifierDisplay HIDDEN = new AttributeModifierDisplay(Type.HIDDEN, null);

    public AttributeModifierDisplay {
        Objects.requireNonNull(type, "type");

        if (type == Type.OVERRIDE && value == null) {
            throw new IllegalArgumentException("An override display needs a value");
        }

        if (type != Type.OVERRIDE && value != null) {
            throw new IllegalArgumentException("Only an override display carries a value, got one on " + type);
        }
    }

    /**
     * @param value the line to draw in place of the one the game would write
     * @return a display drawing {@code value}
     * @since 0.1.0
     */
    public static AttributeModifierDisplay override(final Component value) {
        return new AttributeModifierDisplay(Type.OVERRIDE, Objects.requireNonNull(value, "value"));
    }

    /**
     * @return {@code true} when nothing is drawn for this modifier
     * @since 0.1.0
     */
    public boolean isHidden() {
        return type == Type.HIDDEN;
    }

    /**
     * The three shapes a display comes in.
     *
     * @since 0.1.0
     */
    public enum Type {

        /**
         * Let the game write the line.
         */
        DEFAULT(0, "default"),

        /**
         * Draw nothing.
         */
        HIDDEN(1, "hidden"),

        /**
         * Draw {@link AttributeModifierDisplay#value()} instead.
         */
        OVERRIDE(2, "override");

        private final int networkId;
        private final String serializedName;

        Type(final int networkId, final String serializedName) {
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
         * @return the matching type, or {@code null} when there is none
         * @since 0.1.0
         */
        public static @Nullable Type byName(final @Nullable String name) {
            if (name == null) {
                return null;
            }
            final String lower = name.toLowerCase(Locale.ROOT);
            for (final Type type : values()) {
                if (type.serializedName.equals(lower)) {
                    return type;
                }
            }
            return null;
        }

        /**
         * @param networkId the wire id
         * @return the matching type, or {@code null} when there is none
         * @since 0.1.0
         */
        public static @Nullable Type byNetworkId(final int networkId) {
            for (final Type type : values()) {
                if (type.networkId == networkId) {
                    return type;
                }
            }
            return null;
        }
    }
}
