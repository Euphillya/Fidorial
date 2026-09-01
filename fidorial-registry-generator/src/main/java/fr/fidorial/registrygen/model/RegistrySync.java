package fr.fidorial.registrygen.model;

/**
 * How a registry's contents reach the client, and therefore which dataset file a
 * registry is written to.
 *
 * <p>Nothing in Mojang's {@code registries.json} distinguishes these cases, so it
 * is declared per registry in {@link SupportedRegistries}.</p>
 *
 * @since 0.1.0
 */
public enum RegistrySync {

    /**
     * Not sent to the client: the registry exists only to give the server typed
     * keys. Omitted from the dataset entirely.
     */
    NONE,

    /**
     * Baked into the client. The server never sends these entries, so their order
     * <em>is</em> the network ID and must match the client exactly.
     */
    FROZEN,

    /**
     * Sent during the configuration phase through the registry data packets.
     */
    DYNAMIC
}
