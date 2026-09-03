package fr.fidorial.item.component;

import net.kyori.adventure.key.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The patterns drawn on a banner or shield, under {@code minecraft:banner_patterns}.
 *
 * @param layers the patterns, bottom layer first
 * @since 0.1.0
 */
public record BannerPatterns(List<Layer> layers) {

    /**
     * No patterns: a plain banner in its base colour.
     */
    public static final BannerPatterns EMPTY = new BannerPatterns(List.of());

    public BannerPatterns {
        Objects.requireNonNull(layers, "layers");
        layers = List.copyOf(layers);
    }

    /**
     * @param layers the patterns, bottom layer first
     * @return those patterns
     * @since 0.1.0
     */
    public static BannerPatterns of(final Layer... layers) {
        return new BannerPatterns(List.of(layers));
    }

    /**
     * @param layers the patterns, bottom layer first
     * @return those patterns
     * @since 0.1.0
     */
    public static BannerPatterns of(final List<Layer> layers) {
        return new BannerPatterns(layers);
    }

    /**
     * @param pattern the key of a {@code minecraft:banner_pattern} entry
     * @param color   the colour to draw it in
     * @return a new set, one layer taller
     * @since 0.1.0
     */
    public BannerPatterns plus(final Key pattern, final DyeColor color) {
        return plus(new Layer(BannerPattern.reference(pattern), color));
    }

    /**
     * @param layer the layer to draw over the others
     * @return a new set, one layer taller
     * @since 0.1.0
     */
    public BannerPatterns plus(final Layer layer) {
        Objects.requireNonNull(layer, "layer");

        final List<Layer> copy = new ArrayList<>(layers);
        copy.add(layer);

        return new BannerPatterns(copy);
    }

    /**
     * @return {@code true} when nothing is drawn over the base colour
     * @since 0.1.0
     */
    public boolean isEmpty() {
        return layers.isEmpty();
    }

    /**
     * @return how many layers are drawn
     * @since 0.1.0
     */
    public int size() {
        return layers.size();
    }

    /**
     * One pattern in one colour.
     *
     * @param pattern the shape drawn
     * @param color   the colour it is drawn in
     * @since 0.1.0
     */
    public record Layer(BannerPattern pattern, DyeColor color) {

        public Layer {
            Objects.requireNonNull(pattern, "pattern");
            Objects.requireNonNull(color, "color");
        }

        /**
         * @param pattern the key of a {@code minecraft:banner_pattern} entry
         * @param color   the colour to draw it in
         * @return that pattern, in that colour
         * @since 0.1.0
         */
        public static Layer of(final Key pattern, final DyeColor color) {
            return new Layer(BannerPattern.reference(pattern), color);
        }
    }
}
