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
import fr.fidorial.entity.Entity;
import fr.fidorial.entity.GameMode;
import fr.fidorial.entity.Player;
import fr.fidorial.inventory.ItemStack;
import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.world.World;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.ApiStatus;

import java.time.Duration;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * Modeled after Paper's <a href="https://github.com/PaperMC/Paper/blob/main/paper-api/src/main/java/io/papermc/paper/command/brigadier/argument/VanillaArgumentProvider.java">ArgumentTypes</a>
 * Originally contributed in <a href="https://github.com/PaperMC/Paper/pull/8235">#8235</a>, licensed under the MIT license.
 */
@ApiStatus.Internal
public interface ArgumentProvider {

    Optional<ArgumentProvider> PROVIDER = ServiceLoader.load(ArgumentProvider.class, ArgumentProvider.class.getClassLoader()).findFirst();

    static ArgumentProvider provider() {
        return PROVIDER.orElseThrow();
    }

    ArgumentType<EntitySelectorArgumentResolver> entity();

    ArgumentType<EntitySelectorArgumentResolver> entity(Predicate<Entity> filter);

    ArgumentType<PlayerSelectorArgumentResolver> player();

    ArgumentType<PlayerSelectorArgumentResolver> player(Predicate<Player> filter);

    ArgumentType<EntitySelectorArgumentResolver> entities();

    ArgumentType<EntitySelectorArgumentResolver> entities(Predicate<Entity> filter);

    ArgumentType<PlayerSelectorArgumentResolver> players();

    ArgumentType<PlayerSelectorArgumentResolver> players(Predicate<Player> filter);

    ArgumentType<BlockPosResolver> blockPosition();

    ArgumentType<AngleResolver> angle();

    ArgumentType<ItemStack> itemStack();

    ArgumentType<ItemStackPredicate> itemStackPredicate();

    ArgumentType<NamedTextColor> namedColor();

    ArgumentType<TextColor> hexColor();

    ArgumentType<BossBar.Color> bossBarColor();

    ArgumentType<BossBar.Overlay> bossBarOverlay();

    ArgumentType<BossBar.Flag> bossBarFlag();

    ArgumentType<Component> component();

    ArgumentType<Style> style();

    ArgumentType<Key> key();

    ArgumentType<String> word();

    ArgumentType<String> string();

    ArgumentType<String> greedyString();

    ArgumentType<Boolean> bool();

    ArgumentType<Integer> integer(int min, int max);

    ArgumentType<Long> longArg(long min, long max);

    ArgumentType<Float> floatArg(float min, float max);

    ArgumentType<Double> doubleArg(double min, double max);

    ArgumentType<IntegerRangeProvider> integerRange();

    ArgumentType<DoubleRangeProvider> doubleRange();

    ArgumentType<World> world();

    ArgumentType<GameMode> gameMode();

    ArgumentType<UUID> uuid();

    ArgumentType<Integer> time(int minTicks);

    ArgumentType<Duration> duration();

    <T> ArgumentType<TypedKey<T>> resourceKey(RegistryKey<T> registryKey);

    <T> ArgumentType<T> resource(RegistryKey<T> registryKey);

    ArgumentType<PlayerProfileListResolver> playerProfiles();

    ArgumentType<PlayerProfileListResolver> playerProfiles(Predicate<Player> filter);

    //ArgumentType<BlockState> blockState();

    ArgumentType<PositionResolver> position();
}
