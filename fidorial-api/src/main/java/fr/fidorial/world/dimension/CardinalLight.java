package fr.fidorial.world.dimension;

/**
 * Which direction cardinal light comes from, affecting how blocks are shaded.
 *
 * @since 0.1.0
 */
public enum CardinalLight {

    DEFAULT("default"),
    NETHER("nether");

    private final String id;

    CardinalLight(final String id) {
        this.id = id;
    }

    /**
     * {@return the identifier expected on the wire}
     */
    public String id() {
        return id;
    }
}
