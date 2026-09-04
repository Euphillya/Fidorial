package fr.fidorial.item.component;

import net.kyori.adventure.nbt.CompoundBinaryTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The entities shut inside a beehive or bee nest, under {@code minecraft:bees}.
 *
 * @param occupants the entities inside, in the order they leave
 * @since 0.1.0
 */
public record Bees(List<Occupant> occupants) {

    /**
     * What the tooltip counts up to. Not a limit on {@link #size()}.
     */
    public static final int TOOLTIP_CAPACITY = 3;

    /**
     * An empty hive.
     */
    public static final Bees EMPTY = new Bees(List.of());

    public Bees {
        Objects.requireNonNull(occupants, "occupants");
        occupants = List.copyOf(occupants);
    }

    /**
     * @param occupants the entities inside
     * @return those occupants
     * @since 0.1.0
     */
    public static Bees of(final Occupant... occupants) {
        return new Bees(List.of(occupants));
    }

    /**
     * @param occupants the entities inside
     * @return those occupants
     * @since 0.1.0
     */
    public static Bees of(final List<Occupant> occupants) {
        return new Bees(occupants);
    }

    /**
     * @param occupant the entity to shut in
     * @return a new hive, one occupant fuller
     * @since 0.1.0
     */
    public Bees plus(final Occupant occupant) {
        Objects.requireNonNull(occupant, "occupant");

        final List<Occupant> copy = new ArrayList<>(occupants);
        copy.add(occupant);

        return new Bees(copy);
    }

    /**
     * @return {@code true} when the hive is empty
     * @since 0.1.0
     */
    public boolean isEmpty() {
        return occupants.isEmpty();
    }

    /**
     * @return how many entities are inside
     * @since 0.1.0
     */
    public int size() {
        return occupants.size();
    }

    /**
     * One entity inside the hive.
     *
     * @param entityData     the entity, in the usual entity NBT format
     * @param minTicksInHive how long this entity must stay before it may leave
     * @param ticksInHive    how long it has stayed so far
     * @since 0.1.0
     */
    public record Occupant(CompoundBinaryTag entityData, int minTicksInHive, int ticksInHive) {

        public Occupant {
            Objects.requireNonNull(entityData, "entityData");

            if (minTicksInHive < 0) {
                throw new IllegalArgumentException("minTicksInHive cannot be negative, got " + minTicksInHive);
            }

            if (ticksInHive < 0) {
                throw new IllegalArgumentException("ticksInHive cannot be negative, got " + ticksInHive);
            }
        }

        /**
         * A freshly hived entity, with no time served.
         *
         * @param entityData     the entity, in the usual entity NBT format
         * @param minTicksInHive how long this entity must stay before it may leave
         * @return that occupant
         * @since 0.1.0
         */
        public static Occupant of(final CompoundBinaryTag entityData, final int minTicksInHive) {
            return new Occupant(entityData, minTicksInHive, 0);
        }
    }
}
