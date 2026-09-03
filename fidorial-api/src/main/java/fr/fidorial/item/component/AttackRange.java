package fr.fidorial.item.component;

/**
 * How far a weapon reaches, under {@code minecraft:attack_range}.
 *
 * <p>Every field is a distance in blocks except {@link #mobFactor()}, which is a
 * multiplier. Careful with {@link #hitboxMargin()}: a stack <em>without</em> this
 * component has no margin at all, so the {@value #DEFAULT_HITBOX_MARGIN} default
 * below only ever applies to a stack that carries the component but leaves the
 * field out.
 *
 * @param minReach         the closest a target may be and still be hit
 * @param maxReach         the furthest a target may be and still be hit
 * @param minCreativeReach {@link #minReach()} for a player in creative mode
 * @param maxCreativeReach {@link #maxReach()} for a player in creative mode
 * @param hitboxMargin     how far outside its own hitbox a target can still be hit
 * @param mobFactor        what {@link #minReach()} and {@link #maxReach()} are
 *                         multiplied by when a mob holds the item
 * @since 0.1.0
 */
public record AttackRange(
        float minReach,
        float maxReach,
        float minCreativeReach,
        float maxCreativeReach,
        float hitboxMargin,
        float mobFactor) {

    /**
     * What {@link #minReach()} is when unset.
     */
    public static final float DEFAULT_MIN_REACH = 0.0F;

    /**
     * What {@link #maxReach()} is when unset, and what a player reaches holding an
     * item that carries no {@code minecraft:attack_range} at all.
     */
    public static final float DEFAULT_MAX_REACH = 3.0F;

    /**
     * What {@link #minCreativeReach()} is when unset.
     */
    public static final float DEFAULT_MIN_CREATIVE_REACH = 0.0F;

    /**
     * What {@link #maxCreativeReach()} is when unset.
     */
    public static final float DEFAULT_MAX_CREATIVE_REACH = 5.0F;

    /**
     * What {@link #hitboxMargin()} is when the component is present but the field
     * is left out. An item with no component has a margin of {@code 0.0} instead.
     */
    public static final float DEFAULT_HITBOX_MARGIN = 0.3F;

    /**
     * What {@link #mobFactor()} is when unset: mobs reach as far as players do.
     */
    public static final float DEFAULT_MOB_FACTOR = 1.0F;

    /**
     * The furthest any of the four reaches may be set to.
     */
    public static final float MAX_REACH_LIMIT = 64.0F;

    /**
     * The largest {@link #hitboxMargin()} accepted.
     */
    public static final float MAX_HITBOX_MARGIN = 1.0F;

    /**
     * The largest {@link #mobFactor()} accepted.
     */
    public static final float MAX_MOB_FACTOR = 2.0F;

    /**
     * The reach of a plain weapon: {@value #DEFAULT_MAX_REACH} blocks, no margin
     * beyond the target's hitbox.
     */
    public static final AttackRange DEFAULT = new AttackRange(
            DEFAULT_MIN_REACH,
            DEFAULT_MAX_REACH,
            DEFAULT_MIN_CREATIVE_REACH,
            DEFAULT_MAX_CREATIVE_REACH,
            DEFAULT_HITBOX_MARGIN,
            DEFAULT_MOB_FACTOR);

    public AttackRange {
        minReach = requireInRange(minReach, MAX_REACH_LIMIT, "minReach");
        maxReach = requireInRange(maxReach, MAX_REACH_LIMIT, "maxReach");
        minCreativeReach = requireInRange(minCreativeReach, MAX_REACH_LIMIT, "minCreativeReach");
        maxCreativeReach = requireInRange(maxCreativeReach, MAX_REACH_LIMIT, "maxCreativeReach");
        hitboxMargin = requireInRange(hitboxMargin, MAX_HITBOX_MARGIN, "hitboxMargin");
        mobFactor = requireInRange(mobFactor, MAX_MOB_FACTOR, "mobFactor");
    }

    /**
     * The common case: a weapon that only changes how far it reaches.
     *
     * @param maxReach the furthest a target may be and still be hit
     * @return a reach with every other field left at its default
     * @since 0.1.0
     */
    public static AttackRange of(final float maxReach) {
        return new AttackRange(
                DEFAULT_MIN_REACH,
                maxReach,
                DEFAULT_MIN_CREATIVE_REACH,
                DEFAULT_MAX_CREATIVE_REACH,
                DEFAULT_HITBOX_MARGIN,
                DEFAULT_MOB_FACTOR);
    }

    /**
     * @param minReach the closest a target may be and still be hit
     * @param maxReach the furthest a target may be and still be hit
     * @return a reach with every other field left at its default
     * @since 0.1.0
     */
    public static AttackRange of(final float minReach, final float maxReach) {
        return new AttackRange(
                minReach,
                maxReach,
                DEFAULT_MIN_CREATIVE_REACH,
                DEFAULT_MAX_CREATIVE_REACH,
                DEFAULT_HITBOX_MARGIN,
                DEFAULT_MOB_FACTOR);
    }

    /**
     * @return a builder starting from {@link #DEFAULT}
     * @since 0.1.0
     */
    public static Builder builder() {
        return new Builder(DEFAULT);
    }

    /**
     * @return a builder pre-populated with this reach
     * @since 0.1.0
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * @param creative whether the attacker is a player in creative mode
     * @return the closest a target may be and still be hit
     * @since 0.1.0
     */
    public float minReachFor(final boolean creative) {
        return creative ? minCreativeReach : minReach;
    }

    /**
     * @param creative whether the attacker is a player in creative mode
     * @return the furthest a target may be and still be hit
     * @since 0.1.0
     */
    public float maxReachFor(final boolean creative) {
        return creative ? maxCreativeReach : maxReach;
    }

    /**
     * @return the closest a target may be and still be hit by a mob
     * @since 0.1.0
     */
    public float minReachForMob() {
        return minReach * mobFactor;
    }

    /**
     * @return the furthest a target may be and still be hit by a mob
     * @since 0.1.0
     */
    public float maxReachForMob() {
        return maxReach * mobFactor;
    }

    private static float requireInRange(final float value, final float max, final String name) {
        if (!(value >= 0.0F) || value > max) {
            throw new IllegalArgumentException(
                    name + " must be between 0.0 and " + max + ", got " + value);
        }
        return value;
    }

    /**
     * Assembles an {@link AttackRange} without spelling out all six fields.
     *
     * @since 0.1.0
     */
    public static final class Builder {

        private float minReach;
        private float maxReach;
        private float minCreativeReach;
        private float maxCreativeReach;
        private float hitboxMargin;
        private float mobFactor;

        private Builder(final AttackRange from) {
            this.minReach = from.minReach;
            this.maxReach = from.maxReach;
            this.minCreativeReach = from.minCreativeReach;
            this.maxCreativeReach = from.maxCreativeReach;
            this.hitboxMargin = from.hitboxMargin;
            this.mobFactor = from.mobFactor;
        }

        /**
         * @param minReach the closest a target may be and still be hit
         * @return this builder
         * @since 0.1.0
         */
        public Builder minReach(final float minReach) {
            this.minReach = minReach;
            return this;
        }

        /**
         * @param maxReach the furthest a target may be and still be hit
         * @return this builder
         * @since 0.1.0
         */
        public Builder maxReach(final float maxReach) {
            this.maxReach = maxReach;
            return this;
        }

        /**
         * @param minCreativeReach {@link #minReach(float)} for a player in creative mode
         * @return this builder
         * @since 0.1.0
         */
        public Builder minCreativeReach(final float minCreativeReach) {
            this.minCreativeReach = minCreativeReach;
            return this;
        }

        /**
         * @param maxCreativeReach {@link #maxReach(float)} for a player in creative mode
         * @return this builder
         * @since 0.1.0
         */
        public Builder maxCreativeReach(final float maxCreativeReach) {
            this.maxCreativeReach = maxCreativeReach;
            return this;
        }

        /**
         * @param hitboxMargin how far outside its own hitbox a target can still be hit
         * @return this builder
         * @since 0.1.0
         */
        public Builder hitboxMargin(final float hitboxMargin) {
            this.hitboxMargin = hitboxMargin;
            return this;
        }

        /**
         * @param mobFactor what the two non-creative reaches are multiplied by when a
         *                  mob holds the item
         * @return this builder
         * @since 0.1.0
         */
        public Builder mobFactor(final float mobFactor) {
            this.mobFactor = mobFactor;
            return this;
        }

        /**
         * @return the assembled reach
         * @throws IllegalArgumentException if any field is out of range
         * @since 0.1.0
         */
        public AttackRange build() {
            return new AttackRange(
                    minReach, maxReach, minCreativeReach, maxCreativeReach, hitboxMargin, mobFactor);
        }
    }
}
