package fr.fidorial.world.environment;

import org.jetbrains.annotations.Contract;

import java.util.Objects;

/**
 * One environment attribute value, together with the way it combines with the dimension's value.
 *
 * @param value    the value, or the modifier argument when {@code modifier} is not
 *                 {@link Modifier#OVERRIDE}
 * @param modifier how the value combines with the one already provided
 * @param <T>      the value type
 * @since 0.1.0
 */
public record Attribute<T>(T value, Modifier modifier) {

    public Attribute {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(modifier, "modifier");
    }

    /**
     * Creates an attribute replacing whatever the dimension provided.
     *
     * @param value the value
     * @param <T>   the value type
     * @return the attribute
     */
    @Contract(value = "_ -> new", pure = true)
    public static <T> Attribute<T> of(final T value) {
        return new Attribute<>(value, Modifier.OVERRIDE);
    }

    /**
     * Creates an attribute deriving from whatever the dimension provided.
     *
     * @param value    the modifier argument
     * @param modifier how to combine it
     * @param <T>      the value type
     * @return the attribute
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static <T> Attribute<T> of(final T value, final Modifier modifier) {
        return new Attribute<>(value, modifier);
    }
}
