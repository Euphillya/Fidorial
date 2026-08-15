package fr.fidorial.world.environment;

/**
 * How an environment attribute value combines with the value provided by a lower-priority source.
 *
 * @since 0.1.0
 */
public enum Modifier {

    /**
     * Replaces the previous value outright. The default and the only form written bare.
     */
    OVERRIDE("override"),

    /**
     * Floats: adds. Colors: component-wise additive blending.
     */
    ADD("add"),

    /**
     * Floats: subtracts. Colors: component-wise subtractive blending.
     */
    SUBTRACT("subtract"),

    /**
     * Floats: multiplies. Colors: component-wise multiplicative blending.
     */
    MULTIPLY("multiply"),

    /**
     * Floats only: keeps the smaller of the two values.
     */
    MINIMUM("minimum"),

    /**
     * Floats only: keeps the larger of the two values.
     */
    MAXIMUM("maximum"),

    /**
     * Colors only: traditional alpha blending; the argument is an ARGB color.
     */
    ALPHA_BLEND("alpha_blend"),

    /**
     * Booleans only.
     */
    AND("and"),

    /**
     * Booleans only.
     */
    NAND("nand"),

    /**
     * Booleans only.
     */
    OR("or"),

    /**
     * Booleans only.
     */
    NOR("nor"),

    /**
     * Booleans only.
     */
    XOR("xor"),

    /**
     * Booleans only.
     */
    XNOR("xnor");

    private final String id;

    Modifier(final String id) {
        this.id = id;
    }

    /**
     * {@return the identifier expected on the wire}
     */
    public String id() {
        return id;
    }
}
