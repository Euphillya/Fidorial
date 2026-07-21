package fr.fidorial.protocol;

import fr.fidorial.entity.Player;
import fr.fidorial.event.EventPriority;
import fr.fidorial.event.Subscription;

public interface ProtocolManager {

    /**
     * Registers a listener for a specific packet type, at normal priority.
     *
     * @param type     packet type to watch
     * @param listener listener to invoke
     * @return a revocable subscription (closeable in a try-with-resources)
     */
    default Subscription addListener(PacketType type, PacketListener listener) {
        return addListener(EventPriority.NORMAL, type, listener);
    }

    /**
     * Registers a listener for a specific packet type, with a given priority.
     *
     * @param priority invocation priority (higher priorities run last and therefore
     *                 have the final say on cancellation)
     * @param type     packet type to watch
     * @param listener listener to invoke
     * @return a revocable subscription
     */
    Subscription addListener(EventPriority priority, PacketType type, PacketListener listener);

    /**
     * Registers a listener for <em>all</em> packets of a given direction.
     *
     * @param direction filtered direction
     * @param listener  listener to invoke
     * @return a revocable subscription
     */
    default Subscription addListener(PacketDirection direction, PacketListener listener) {
        return addListener(EventPriority.NORMAL, direction, listener);
    }

    /**
     * Registers a listener for all packets of a direction, with a priority.
     *
     * @param priority  invocation priority
     * @param direction filtered direction
     * @param listener  listener to invoke
     * @return a revocable subscription
     */
    Subscription addListener(EventPriority priority, PacketDirection direction, PacketListener listener);

    /**
     * Creates a blank container for a packet type, ready to be filled in and then sent
     * through {@link #sendPacket} or {@link #receivePacket}.
     *
     * @param type packet type to create
     * @return a container whose fields are initialised to their default values
     * @throws UnsupportedOperationException if this packet type cannot be built from the
     *                                       API (packet not implemented)
     */
    PacketContainer createPacket(PacketType type);

    /**
     * Sends a <em>clientbound</em> packet to a player, as if the server had produced it.
     * Registered clientbound listeners are invoked normally.
     *
     * @param player recipient player
     * @param packet packet to send (must be {@link PacketDirection#CLIENTBOUND})
     */
    void sendPacket(Player player, PacketContainer packet);

    /**
     * Injects a <em>serverbound</em> packet into the processing pipeline, as if the
     * client had sent it. Serverbound listeners are invoked normally.
     *
     * @param player simulated sending player
     * @param packet packet to inject (must be {@link PacketDirection#SERVERBOUND})
     */
    void receivePacket(Player player, PacketContainer packet);

    /**
     * Revokes all listeners registered by a given owner (usually a plugin), useful on
     * unload.
     *
     * @param owner owner of the subscriptions
     */
    void unregisterAll(Object owner);
}
