package fr.fidorial.item.component;

import net.kyori.adventure.key.Key;

import java.util.Objects;

/**
 * One shape drawn on a banner, given either by name or spelled out in place.
 *
 * @since 0.1.0
 */
public sealed interface BannerPattern {

    /**
     * @param pattern the key of a {@code minecraft:banner_pattern} entry
     * @return a pattern pointing at that entry
     * @since 0.1.0
     */
    static BannerPattern reference(final Key pattern) {
        return new Reference(pattern);
    }

    /**
     * @param assetId        where the texture lives
     * @param translationKey the translation key the tooltip uses
     * @return a pattern spelled out in place
     * @since 0.1.0
     */
    static BannerPattern inline(final Key assetId, final String translationKey) {
        return new Inline(assetId, translationKey);
    }

    /**
     * A pattern named by its registry key.
     *
     * @param pattern the key of a {@code minecraft:banner_pattern} entry
     * @since 0.1.0
     */
    record Reference(Key pattern) implements BannerPattern {

        public Reference {
            Objects.requireNonNull(pattern, "pattern");
        }
    }

    /**
     * A pattern defined on the stack itself, bypassing the registry.
     *
     * @param assetId        where the texture lives
     * @param translationKey the translation key the tooltip uses
     * @since 0.1.0
     */
    record Inline(Key assetId, String translationKey) implements BannerPattern {

        public Inline {
            Objects.requireNonNull(assetId, "assetId");
            Objects.requireNonNull(translationKey, "translationKey");

            if (translationKey.isEmpty()) {
                throw new IllegalArgumentException("translationKey cannot be empty");
            }
        }
    }
}
