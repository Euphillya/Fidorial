package fr.euphyllia.fidorial.server.command.brigadier.argument;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.EntityArgumentInternal;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.UuidArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.generic.TimeArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.location.Vec3Argument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.player.GameModeArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.player.PlayerProfileArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.resource.ResourceArgument;
import fr.euphyllia.fidorial.server.command.brigadier.argument.resource.ResourceKeyArgument;
import fr.fidorial.command.argument.ArgumentProvider;
import fr.fidorial.command.argument.predicate.ItemStackPredicate;
import fr.fidorial.command.argument.range.DoubleRangeProvider;
import fr.fidorial.command.argument.range.IntegerRangeProvider;
import fr.fidorial.command.argument.resolvers.AngleResolver;
import fr.fidorial.command.argument.resolvers.BlockPosResolver;
import fr.fidorial.command.argument.resolvers.PlayerProfileListResolver;
import fr.fidorial.command.argument.resolvers.PositionResolver;
import fr.fidorial.command.argument.resolvers.selector.EntitySelectorArgumentResolver;
import fr.fidorial.command.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import fr.fidorial.entity.Entity;
import fr.fidorial.entity.GameMode;
import fr.fidorial.entity.PlayerProfile;
import fr.fidorial.inventory.ItemStack;
import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.world.World;
import fr.fidorial.world.block.BlockData;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * TODO: Eventually convert all nulls to proper argument types.
 */
public class ArgumentProviderImpl implements ArgumentProvider {
    @Override
    public ArgumentType<EntitySelectorArgumentResolver> entity() {
        return this.wrap(EntityArgumentInternal.entity(), (result) -> source-> List.of(result.findSingleEntity(source)));
    }

    @Override
    public ArgumentType<EntitySelectorArgumentResolver> entities() {
        return this.wrap(
                EntityArgumentInternal.entities(),
                result -> source -> result.findEntities(source)
                        .stream()
                        .map(Entity.class::cast)
                        .toList()
        );
    }

    @Override
    public ArgumentType<PlayerSelectorArgumentResolver> player() {
        return this.wrap(
                EntityArgumentInternal.player(),
                result -> source -> List.of(result.findSinglePlayer(source))
        );
    }

    @Override
    public ArgumentType<PlayerSelectorArgumentResolver> players() {
        return this.wrap(
                EntityArgumentInternal.players(),
                result -> result::findPlayers
        );
    }


    @Override
    public ArgumentType<BlockPosResolver> blockPosition() {
        return null;
    }

    @Override
    public ArgumentType<AngleResolver> angle() {
        return null;
    }

    @Override
    public ArgumentType<ItemStack> itemStack() {
        return null;
    }

    @Override
    public ArgumentType<ItemStackPredicate> itemStackPredicate() {
        return null;
    }

    @Override
    public ArgumentType<NamedTextColor> namedColor() {
        return null;
    }

    @Override
    public ArgumentType<TextColor> hexColor() {
        return null;
    }

    @Override
    public ArgumentType<Component> component() {
        return null;
    }

    @Override
    public ArgumentType<Style> style() {
        return null;
    }

    @Override
    public ArgumentType<Key> key() {
        return null;
    }

    @Override
    public ArgumentType<IntegerRangeProvider> integerRange() {
        return null;
    }

    @Override
    public ArgumentType<DoubleRangeProvider> doubleRange() {
        return null;
    }

    @Override
    public ArgumentType<World> world() {
        return null;
    }

    @Override
    public ArgumentType<GameMode> gameMode() {
        return GameModeArgument.gameMode();
    }

    @Override
    public ArgumentType<UUID> uuid() {
        return UuidArgument.uuid();
    }

    @Override
    public ArgumentType<Integer> time(int minTicks) {
        return TimeArgument.time(minTicks);
    }

    @Override
    public <T> ArgumentType<TypedKey<T>> resourceKey(final RegistryKey<T> registryKey) {
        return this.wrap(
                ResourceKeyArgument.resourceKey(registryKey),
                internalRegistryKey -> internalRegistryKey);
    }

    @Override
    public <T> ArgumentType<T> resource(final RegistryKey<T> registryKey) {
        return this.wrap(ResourceArgument.resource(registryKey));
    }

    @Override
    public ArgumentType<PlayerProfileListResolver> playerProfiles() {
        return this.wrap(PlayerProfileArgument.playerProfile(), result -> {
            if (result instanceof PlayerProfileArgument.SelectorResult) {
                return source -> transformUnmodifiable(result.getNames(source), PlayerProfile::new);
            } else {
                return source -> transformUnmodifiable(result.getNames(source), PlayerProfile::new);
            }
        });
    }

    @Override
    public ArgumentType<BlockData> blockData() {
        return null;
    }

    @Override
    public ArgumentType<PositionResolver> position() {
        return Vec3Argument.vec3();
    }

    private <T> ArgumentType<T> wrap(final ArgumentType<T> base) {
        return this.wrap(base, identity -> identity);
    }

    private <B, C> ArgumentType<C> wrap(final ArgumentType<B> base, final ResultConverter<B, C> converter) {
        return new NativeWrapperArgumentType<>(base, converter);
    }

    public static <A, M> List<A> transformUnmodifiable(final List<? extends M> nms, final Function<? super M, ? extends A> converter) {
        return Collections.unmodifiableList(Lists.transform(nms, converter::apply));
    }

    public static <A, M> Collection<A> transformUnmodifiable(final Collection<? extends M> nms, final Function<? super M, ? extends A> converter) {
        return Collections.unmodifiableCollection(Collections2.transform(nms, converter::apply));
    }

    @FunctionalInterface
    interface ResultConverter<T, R> {
        R convert(T type) throws CommandSyntaxException;
    }

    public static final class NativeWrapperArgumentType<M, P> implements ArgumentType<P> {

        private final ArgumentType<M> vanilla;
        private final ResultConverter<M, P> converter;

        private NativeWrapperArgumentType(final ArgumentType<M> vanilla, final ResultConverter<M, P> converter) {
            this.vanilla = vanilla;
            this.converter = converter;
        }

        public ArgumentType<M> vanillaArgumentType() {
            return this.vanilla;
        }

        @Override
        public P parse(final StringReader reader) throws CommandSyntaxException {
            return this.converter.convert(this.vanilla.parse(reader));
        }

        @Override
        public <S> P parse(final StringReader reader, final S source) throws CommandSyntaxException {
            return this.converter.convert(this.vanilla.parse(reader, source));
        }

        @Override
        public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
            return this.vanilla.listSuggestions(context, builder);
        }

        @Override
        public Collection<String> getExamples() {
            return this.vanilla.getExamples();
        }
    }
}
