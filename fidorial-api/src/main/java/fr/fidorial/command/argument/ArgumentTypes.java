package fr.fidorial.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import fr.fidorial.command.argument.predicate.ItemStackPredicate;
import fr.fidorial.command.argument.range.DoubleRangeProvider;
import fr.fidorial.command.argument.range.IntegerRangeProvider;
import fr.fidorial.command.argument.resolvers.AngleResolver;
import fr.fidorial.command.argument.resolvers.BlockPosResolver;
import fr.fidorial.command.argument.resolvers.PlayerProfileListResolver;
import fr.fidorial.command.argument.resolvers.PositionResolver;
import fr.fidorial.command.argument.resolvers.selector.EntitySelectorArgumentResolver;
import fr.fidorial.command.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import fr.fidorial.entity.GameMode;
import fr.fidorial.inventory.ItemStack;
import fr.fidorial.registry.Registry;
import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.world.World;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;

import java.util.UUID;

import static fr.fidorial.command.argument.ArgumentProvider.provider;

public final class ArgumentTypes {
    /**
     * Represents a selector that can capture any
     * single entity.
     *
     * @return argument that takes one entity
     */
    public static ArgumentType<EntitySelectorArgumentResolver> entity() {
        return provider().entity();
    }

    /**
     * Represents a selector that can capture multiple
     * entities.
     *
     * @return argument that takes multiple entities
     */
    public static ArgumentType<EntitySelectorArgumentResolver> entities() {
        return provider().entities();
    }

    /**
     * Represents a selector that can capture a
     * singular player entity.
     *
     * @return argument that takes one player
     */
    public static ArgumentType<PlayerSelectorArgumentResolver> player() {
        return provider().player();
    }

    /**
     * Represents a selector that can capture multiple
     * player entities.
     *
     * @return argument that takes multiple players
     */
    public static ArgumentType<PlayerSelectorArgumentResolver> players() {
        return provider().players();
    }

    /**
     * A selector argument that provides a list
     * of player profiles.
     *
     * @return player profile argument
     */
    public static ArgumentType<PlayerProfileListResolver> playerProfiles() {
        return provider().playerProfiles();
    }

    /**
     * A block position argument.
     *
     * @return block position argument
     */
    public static ArgumentType<BlockPosResolver> blockPosition() {
        return provider().blockPosition();
    }

    /**
     * An angle argument.
     *
     * @return angle argument
     */
    public static ArgumentType<AngleResolver> angle() {
        return provider().angle();
    }

    /**
     * A block state argument which will provide rich parsing for specifying
     * the specific block variant and then the block entity NBT if applicable.
     *
     * @return argument
     */
    //public static ArgumentType<BlockState> blockState() {
        //return provider().blockState();
    //}

    /**
     * An ItemStack argument which provides rich parsing for
     * specifying item material and item NBT information.
     *
     * @return argument
     */
    public static ArgumentType<ItemStack> itemStack() {
        return provider().itemStack();
    }

    /**
     * An item predicate argument.
     *
     * @return argument
     */
    public static ArgumentType<ItemStackPredicate> itemPredicate() {
        return provider().itemStackPredicate();
    }

    /**
     * An argument for parsing {@link NamedTextColor}s.
     *
     * @return argument
     */
    public static ArgumentType<NamedTextColor> namedColor() {
        return provider().namedColor();
    }

    /**
     * A hex color argument.
     *
     * @return argument
     */
    public static ArgumentType<TextColor> hexColor() {
        return provider().hexColor();
    }

    /**
     * A component argument.
     *
     * @return argument
     */
    public static ArgumentType<Component> component() {
        return provider().component();
    }

    /**
     * A key argument.
     *
     * @return argument
     */
    public static ArgumentType<Key> key() {
        return provider().key();
    }

    /**
     * A style argument.
     *
     * @return argument
     */
    public static ArgumentType<Style> style() {
        return provider().style();
    }

    /**
     * A boolean argument.
     *
     * @return argument
     */
    public static ArgumentType<Boolean> bool() {
        return provider().bool();
    }

    /**
     * A single unquoted word string argument.
     *
     * @return argument
     */
    public static ArgumentType<String> word() {
        return provider().word();
    }

    /**
     * A string argument, either a single word or a quoted phrase.
     *
     * @return argument
     */
    public static ArgumentType<String> string() {
        return provider().string();
    }

    /**
     * A string argument that consumes the remainder of the input.
     *
     * @return argument
     */
    public static ArgumentType<String> greedyString() {
        return provider().greedyString();
    }

    /**
     * An unbounded integer argument.
     *
     * @return argument
     */
    public static ArgumentType<Integer> integer() {
        return integer(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * An integer argument bounded between {@code min} and {@code max}, inclusive.
     *
     * @param min the minimum value, inclusive
     * @param max the maximum value, inclusive
     * @return argument
     */
    public static ArgumentType<Integer> integer(final int min, final int max) {
        return provider().integer(min, max);
    }

    /**
     * An unbounded long argument.
     *
     * @return argument
     */
    public static ArgumentType<Long> longArg() {
        return longArg(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    /**
     * A long argument bounded between {@code min} and {@code max}, inclusive.
     *
     * @param min the minimum value, inclusive
     * @param max the maximum value, inclusive
     * @return argument
     */
    public static ArgumentType<Long> longArg(final long min, final long max) {
        return provider().longArg(min, max);
    }

    /**
     * An unbounded float argument.
     *
     * @return argument
     */
    public static ArgumentType<Float> floatArg() {
        return floatArg(-Float.MAX_VALUE, Float.MAX_VALUE);
    }

    /**
     * A float argument bounded between {@code min} and {@code max}, inclusive.
     *
     * @param min the minimum value, inclusive
     * @param max the maximum value, inclusive
     * @return argument
     */
    public static ArgumentType<Float> floatArg(final float min, final float max) {
        return provider().floatArg(min, max);
    }

    /**
     * An unbounded double argument.
     *
     * @return argument
     */
    public static ArgumentType<Double> doubleArg() {
        return doubleArg(-Double.MAX_VALUE, Double.MAX_VALUE);
    }

    /**
     * A double argument bounded between {@code min} and {@code max}, inclusive.
     *
     * @param min the minimum value, inclusive
     * @param max the maximum value, inclusive
     * @return argument
     */
    public static ArgumentType<Double> doubleArg(final double min, final double max) {
        return provider().doubleArg(min, max);
    }

    /**
     * An inclusive range of integers that may be unbounded on either end.
     *
     * @return argument
     */
    public static ArgumentType<IntegerRangeProvider> integerRange() {
        return provider().integerRange();
    }

    /**
     * An inclusive range of doubles that may be unbounded on either end.
     *
     * @return argument
     */
    public static ArgumentType<DoubleRangeProvider> doubleRange() {
        return provider().doubleRange();
    }

    /**
     * A world argument.
     *
     * @return argument
     */
    public static ArgumentType<World> world() {
        return provider().world();
    }

    /**
     * A game mode argument.
     *
     * @return argument
     */
    public static ArgumentType<GameMode> gameMode() {
        return provider().gameMode();
    }

    /**
     * A uuid argument.
     *
     * @return argument
     */
    public static ArgumentType<UUID> uuid() {
        return provider().uuid();
    }

    /**
     * A time argument, returning the number of ticks.
     * <p>Examples:
     * <ul>
     * <li> "1d"
     * <li> "5s"
     * <li> "2"
     * <li> "6t"
     * </ul>
     *
     * @return argument
     */
    public static ArgumentType<Integer> time() {
        return time(0);
    }

    /**
     * A time argument, returning the number of ticks.
     * <p>Examples:
     * <ul>
     * <li> "1d"
     * <li> "5s"
     * <li> "2"
     * <li> "6t"
     * </ul>
     *
     * @param mintime The minimum time required for this argument.
     * @return argument
     */
    public static ArgumentType<Integer> time(final int mintime) {
        return provider().time(mintime);
    }

    /**
     * An argument for a typed key for a {@link Registry}, without resolving
     * it against the registry at parse time. The referenced entry is not
     * required to exist — validation/resolution is deferred to whoever
     * consumes the argument.
     *
     * @param registryKey the registry's key
     * @return argument
     * @param <T> the registry value type
     * @see RegistryArgumentExtractor#getTypedKey(com.mojang.brigadier.context.CommandContext, RegistryKey, String)
     */
    public static <T> ArgumentType<TypedKey<T>> resourceKey(final RegistryKey<T> registryKey) {
        return provider().resourceKey(registryKey);
    }

    /**
     * An argument for a value from a {@link Registry}, resolved against
     * the registry at parse time. Parsing fails immediately if no entry
     * with the given key exists in the registry, and suggestions are
     * populated from the registry's current contents.
     *
     * @param registryKey the registry's key
     * @return argument
     * @param <T> the registry value type
     */
    public static <T> ArgumentType<T> resource(final RegistryKey<T> registryKey) {
        return provider().resource(registryKey);
    }

    /**
     * A 3D position argument, accepting absolute, relative ({@code ~}),
     * and mixed coordinates for x, y, and z.
     *
     * @return argument
     */
    public static ArgumentType<PositionResolver> position() {
        return provider().position();
    }
}
