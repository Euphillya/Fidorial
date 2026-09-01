package fr.fidorial.registry.keys;

import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.BannerPattern;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

/**
 * Typed keys for entries in the {@code minecraft:banner_pattern} registry.
 */
public final class BannerPatternKeys {
    /**
     * Key for {@code minecraft:base}.
     */
    public static final TypedKey<BannerPattern> BASE = create("base");

    /**
     * Key for {@code minecraft:border}.
     */
    public static final TypedKey<BannerPattern> BORDER = create("border");

    /**
     * Key for {@code minecraft:bricks}.
     */
    public static final TypedKey<BannerPattern> BRICKS = create("bricks");

    /**
     * Key for {@code minecraft:circle}.
     */
    public static final TypedKey<BannerPattern> CIRCLE = create("circle");

    /**
     * Key for {@code minecraft:creeper}.
     */
    public static final TypedKey<BannerPattern> CREEPER = create("creeper");

    /**
     * Key for {@code minecraft:cross}.
     */
    public static final TypedKey<BannerPattern> CROSS = create("cross");

    /**
     * Key for {@code minecraft:curly_border}.
     */
    public static final TypedKey<BannerPattern> CURLY_BORDER = create("curly_border");

    /**
     * Key for {@code minecraft:diagonal_left}.
     */
    public static final TypedKey<BannerPattern> DIAGONAL_LEFT = create("diagonal_left");

    /**
     * Key for {@code minecraft:diagonal_right}.
     */
    public static final TypedKey<BannerPattern> DIAGONAL_RIGHT = create("diagonal_right");

    /**
     * Key for {@code minecraft:diagonal_up_left}.
     */
    public static final TypedKey<BannerPattern> DIAGONAL_UP_LEFT = create("diagonal_up_left");

    /**
     * Key for {@code minecraft:diagonal_up_right}.
     */
    public static final TypedKey<BannerPattern> DIAGONAL_UP_RIGHT = create("diagonal_up_right");

    /**
     * Key for {@code minecraft:flow}.
     */
    public static final TypedKey<BannerPattern> FLOW = create("flow");

    /**
     * Key for {@code minecraft:flower}.
     */
    public static final TypedKey<BannerPattern> FLOWER = create("flower");

    /**
     * Key for {@code minecraft:globe}.
     */
    public static final TypedKey<BannerPattern> GLOBE = create("globe");

    /**
     * Key for {@code minecraft:gradient}.
     */
    public static final TypedKey<BannerPattern> GRADIENT = create("gradient");

    /**
     * Key for {@code minecraft:gradient_up}.
     */
    public static final TypedKey<BannerPattern> GRADIENT_UP = create("gradient_up");

    /**
     * Key for {@code minecraft:guster}.
     */
    public static final TypedKey<BannerPattern> GUSTER = create("guster");

    /**
     * Key for {@code minecraft:half_horizontal}.
     */
    public static final TypedKey<BannerPattern> HALF_HORIZONTAL = create("half_horizontal");

    /**
     * Key for {@code minecraft:half_horizontal_bottom}.
     */
    public static final TypedKey<BannerPattern> HALF_HORIZONTAL_BOTTOM = create("half_horizontal_bottom");

    /**
     * Key for {@code minecraft:half_vertical}.
     */
    public static final TypedKey<BannerPattern> HALF_VERTICAL = create("half_vertical");

    /**
     * Key for {@code minecraft:half_vertical_right}.
     */
    public static final TypedKey<BannerPattern> HALF_VERTICAL_RIGHT = create("half_vertical_right");

    /**
     * Key for {@code minecraft:mojang}.
     */
    public static final TypedKey<BannerPattern> MOJANG = create("mojang");

    /**
     * Key for {@code minecraft:piglin}.
     */
    public static final TypedKey<BannerPattern> PIGLIN = create("piglin");

    /**
     * Key for {@code minecraft:rhombus}.
     */
    public static final TypedKey<BannerPattern> RHOMBUS = create("rhombus");

    /**
     * Key for {@code minecraft:skull}.
     */
    public static final TypedKey<BannerPattern> SKULL = create("skull");

    /**
     * Key for {@code minecraft:small_stripes}.
     */
    public static final TypedKey<BannerPattern> SMALL_STRIPES = create("small_stripes");

    /**
     * Key for {@code minecraft:square_bottom_left}.
     */
    public static final TypedKey<BannerPattern> SQUARE_BOTTOM_LEFT = create("square_bottom_left");

    /**
     * Key for {@code minecraft:square_bottom_right}.
     */
    public static final TypedKey<BannerPattern> SQUARE_BOTTOM_RIGHT = create("square_bottom_right");

    /**
     * Key for {@code minecraft:square_top_left}.
     */
    public static final TypedKey<BannerPattern> SQUARE_TOP_LEFT = create("square_top_left");

    /**
     * Key for {@code minecraft:square_top_right}.
     */
    public static final TypedKey<BannerPattern> SQUARE_TOP_RIGHT = create("square_top_right");

    /**
     * Key for {@code minecraft:straight_cross}.
     */
    public static final TypedKey<BannerPattern> STRAIGHT_CROSS = create("straight_cross");

    /**
     * Key for {@code minecraft:stripe_bottom}.
     */
    public static final TypedKey<BannerPattern> STRIPE_BOTTOM = create("stripe_bottom");

    /**
     * Key for {@code minecraft:stripe_center}.
     */
    public static final TypedKey<BannerPattern> STRIPE_CENTER = create("stripe_center");

    /**
     * Key for {@code minecraft:stripe_downleft}.
     */
    public static final TypedKey<BannerPattern> STRIPE_DOWNLEFT = create("stripe_downleft");

    /**
     * Key for {@code minecraft:stripe_downright}.
     */
    public static final TypedKey<BannerPattern> STRIPE_DOWNRIGHT = create("stripe_downright");

    /**
     * Key for {@code minecraft:stripe_left}.
     */
    public static final TypedKey<BannerPattern> STRIPE_LEFT = create("stripe_left");

    /**
     * Key for {@code minecraft:stripe_middle}.
     */
    public static final TypedKey<BannerPattern> STRIPE_MIDDLE = create("stripe_middle");

    /**
     * Key for {@code minecraft:stripe_right}.
     */
    public static final TypedKey<BannerPattern> STRIPE_RIGHT = create("stripe_right");

    /**
     * Key for {@code minecraft:stripe_top}.
     */
    public static final TypedKey<BannerPattern> STRIPE_TOP = create("stripe_top");

    /**
     * Key for {@code minecraft:triangles_bottom}.
     */
    public static final TypedKey<BannerPattern> TRIANGLES_BOTTOM = create("triangles_bottom");

    /**
     * Key for {@code minecraft:triangles_top}.
     */
    public static final TypedKey<BannerPattern> TRIANGLES_TOP = create("triangles_top");

    /**
     * Key for {@code minecraft:triangle_bottom}.
     */
    public static final TypedKey<BannerPattern> TRIANGLE_BOTTOM = create("triangle_bottom");

    /**
     * Key for {@code minecraft:triangle_top}.
     */
    public static final TypedKey<BannerPattern> TRIANGLE_TOP = create("triangle_top");

    /**
     * Entries in ascending {@code protocol_id} order - list index == network ID.
     */
    private static final List<TypedKey<BannerPattern>> VALUES = List.of(
        BASE,
        BORDER,
        BRICKS,
        CIRCLE,
        CREEPER,
        CROSS,
        CURLY_BORDER,
        DIAGONAL_LEFT,
        DIAGONAL_RIGHT,
        DIAGONAL_UP_LEFT,
        DIAGONAL_UP_RIGHT,
        FLOW,
        FLOWER,
        GLOBE,
        GRADIENT,
        GRADIENT_UP,
        GUSTER,
        HALF_HORIZONTAL,
        HALF_HORIZONTAL_BOTTOM,
        HALF_VERTICAL,
        HALF_VERTICAL_RIGHT,
        MOJANG,
        PIGLIN,
        RHOMBUS,
        SKULL,
        SMALL_STRIPES,
        SQUARE_BOTTOM_LEFT,
        SQUARE_BOTTOM_RIGHT,
        SQUARE_TOP_LEFT,
        SQUARE_TOP_RIGHT,
        STRAIGHT_CROSS,
        STRIPE_BOTTOM,
        STRIPE_CENTER,
        STRIPE_DOWNLEFT,
        STRIPE_DOWNRIGHT,
        STRIPE_LEFT,
        STRIPE_MIDDLE,
        STRIPE_RIGHT,
        STRIPE_TOP,
        TRIANGLE_BOTTOM,
        TRIANGLE_TOP,
        TRIANGLES_BOTTOM,
        TRIANGLES_TOP
    );

    /**
     * Namespaced tag identifier to flattened member entries.
     */
    private static final Map<Key, List<Key>> TAGS = Map.ofEntries(
        Map.entry(Key.key("no_item_required"), List.of(Key.key("border"), Key.key("circle"), Key.key("cross"), Key.key("diagonal_left"), Key.key("diagonal_right"), Key.key("diagonal_up_left"), Key.key("diagonal_up_right"), Key.key("gradient"), Key.key("gradient_up"), Key.key("half_horizontal"), Key.key("half_horizontal_bottom"), Key.key("half_vertical"), Key.key("half_vertical_right"), Key.key("rhombus"), Key.key("small_stripes"), Key.key("square_bottom_left"), Key.key("square_bottom_right"), Key.key("square_top_left"), Key.key("square_top_right"), Key.key("straight_cross"), Key.key("stripe_bottom"), Key.key("stripe_center"), Key.key("stripe_downleft"), Key.key("stripe_downright"), Key.key("stripe_left"), Key.key("stripe_middle"), Key.key("stripe_right"), Key.key("stripe_top"), Key.key("triangle_bottom"), Key.key("triangle_top"), Key.key("triangles_bottom"), Key.key("triangles_top"))),
        Map.entry(Key.key("pattern_item/bordure_indented"), List.of(Key.key("curly_border"))),
        Map.entry(Key.key("pattern_item/creeper"), List.of(Key.key("creeper"))),
        Map.entry(Key.key("pattern_item/field_masoned"), List.of(Key.key("bricks"))),
        Map.entry(Key.key("pattern_item/flow"), List.of(Key.key("flow"))),
        Map.entry(Key.key("pattern_item/flower"), List.of(Key.key("flower"))),
        Map.entry(Key.key("pattern_item/globe"), List.of(Key.key("globe"))),
        Map.entry(Key.key("pattern_item/guster"), List.of(Key.key("guster"))),
        Map.entry(Key.key("pattern_item/mojang"), List.of(Key.key("mojang"))),
        Map.entry(Key.key("pattern_item/piglin"), List.of(Key.key("piglin"))),
        Map.entry(Key.key("pattern_item/skull"), List.of(Key.key("skull")))
    );

    private BannerPatternKeys() {
        throw new UnsupportedOperationException("BannerPatternKeys cannot be instantiated.");
    }

    private static TypedKey<BannerPattern> create(@KeyPattern final String value) {
        return TypedKey.create(RegistryKey.BANNER_PATTERN, value);
    }

    /**
     * Returns a stream containing all keys declared by this class.
     *
     * @return a stream of registry keys
     */
    public static Stream<TypedKey<BannerPattern>> values() {
        return VALUES.stream();
    }

    /**
     * Returns this registry's tags (namespaced tag identifier to member entries).
     *
     * @return an immutable map of tags, or an empty map if this registry defines none
     */
    public static Map<Key, List<Key>> tags() {
        return TAGS;
    }
}
