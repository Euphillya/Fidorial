package fr.fidorial.world.biome;

/**
 * Adjusts how the client interprets a biome temperature.
 *
 * @since 0.1.0
 */
public enum TemperatureModifier {

    /**
     * The temperature is used as-is.
     */
    NONE("none"),

    /**
     * Vanilla "frozen ocean" behaviour.
     */
    FROZEN("frozen");

    private final String id;

    TemperatureModifier(final String id) {
        this.id = id;
    }

    /**
     * {@return the identifier expected by the vanilla client}
     */
    public String id() {
        return id;
    }
}
