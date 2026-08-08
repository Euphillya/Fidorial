package fr.fidorial.moderation;

import com.google.common.net.InetAddresses;
import org.jetbrains.annotations.Contract;

import java.net.InetAddress;
import java.util.Objects;
import java.util.UUID;

/**
 * What a {@link BanEntry} applies to.
 *
 * <p>Targets are used as identity: they are compared and hashed by value, so they can key a store
 * of bans directly. Anything that is display only, such as the last known name of a player, belongs
 * on the entry rather than here.</p>
 *
 * @since 0.1.0
 */
public sealed interface BanTarget permits BanTarget.Profile, BanTarget.Address {

    /**
     * Gets a name suitable for display.
     *
     * @return the label
     * @since 0.1.0
     */
    @Contract(pure = true)
    String label();

    /**
     * A player identity.
     *
     * @param uuid the banned identity
     * @since 0.1.0
     */
    record Profile(UUID uuid) implements BanTarget {

        @Contract(pure = true)
        public Profile {
            Objects.requireNonNull(uuid, "uuid");
        }

        @Contract(pure = true)
        @Override
        public String label() {
            return uuid.toString();
        }
    }

    /**
     * A client address.
     *
     * <p>The address is held in its parsed form, so that the textual differences between two
     * spellings of the same address do not produce two entries.</p>
     *
     * @param address the banned address
     * @since 0.1.0
     */
    record Address(InetAddress address) implements BanTarget {

        @Contract(pure = true)
        public Address {
            Objects.requireNonNull(address, "address");
        }

        /**
         * Creates a target from the textual form of an address.
         *
         * <p>Only literals are accepted: no name resolution is attempted, so this never blocks.</p>
         *
         * @param literal the address, in dotted quad or IPv6 form
         * @return the target
         * @throws IllegalArgumentException when the text is not an address literal
         * @since 0.1.0
         */
        @Contract(pure = true)
        public static Address of(final String literal) {
            return new Address(InetAddresses.forString(literal));
        }

        @Override
        public String label() {
            return address.getHostAddress();
        }
    }
}
