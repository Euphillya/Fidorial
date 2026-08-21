package fr.fidorial.item;

/**
 * Represents the result of an item interaction within the system. The result
 * indicates the outcome of an action performed on an item, such as using it
 * directly, interacting with a block, or initiating a multi-tick action.
 * This result determines the next course of action in the interaction process.
 *
 * @since 0.1.0
 */
public enum InteractionResult {

    /**
     * The item did nothing.
     * Continue processing.
     */
    PASS,

    /**
     * The interaction succeeded.
     */
    SUCCESS,

    /**
     * The interaction failed.
     */
    FAIL,

    /**
     * The interaction has started an action that will
     * continue over multiple ticks (e.g. eating, drawing a bow).
     */
    CONSUME
}