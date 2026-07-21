package fr.fidorial;

import fr.fidorial.command.CommandRegistry;
import fr.fidorial.entity.Player;
import fr.fidorial.event.EventBus;
import fr.fidorial.plugin.PluginManager;
import fr.fidorial.scheduler.RegionizedScheduler;
import fr.fidorial.service.ServiceRegistry;
import fr.fidorial.status.ServerStatus;
import fr.fidorial.translation.TranslationStore;
import fr.fidorial.world.World;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.chat.ChatType;
import net.kyori.adventure.chat.SignedMessage;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.pointer.Pointers;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Server /* extends ForwardingAudience */ { // we need more functions in the server to implement all the overrides properly

    /**
     * Gets the server name.
     *
     * @return server name
     * @since 0.1.0
     */
    @Contract(pure = true)
    String getName();

    String minecraftVersion();

    int protocolVersion();

    RegionizedScheduler scheduler();

    CommandRegistry commands();

    EventBus events();

    ServerStatus status();

    ServiceRegistry services();

    PluginManager plugins();

    Collection<? extends World> worlds();

    Optional<? extends World> world(Key key);

    Collection<? extends Player> onlinePlayers();

    Optional<? extends Player> player(UUID uuid);

    Optional<? extends Player> player(String name);

    boolean isRunning();

    void shutdown();

    TranslationStore translationStore();

    // remove once we extend ForwardingAudience

    @ApiStatus.OverrideOnly
    Iterable<? extends Audience> audiences();

    default Pointers pointers() {
        return Pointers.empty(); // unsupported
    }

    default Audience filterAudience(final Predicate<? super Audience> filter) {
        List<Audience> audiences = null;
        for (final Audience audience : this.audiences()) {
            if (filter.test(audience)) {
                final Audience filtered = audience.filterAudience(filter);
                if (filtered != Audience.empty()) {
                    if (audiences == null) {
                        audiences = new ArrayList<>();
                    }
                    audiences.add(filtered);
                }
            }
        }
        return audiences != null
                ? Audience.audience(audiences)
                : Audience.empty();
    }

    default void forEachAudience(final Consumer<? super Audience> action) {
        for (final Audience audience : this.audiences()) audience.forEachAudience(action);
    }

    default void sendMessage(final Component message) {
        for (final Audience audience : this.audiences()) audience.sendMessage(message);
    }

    default void sendMessage(final Component message, final ChatType.Bound boundChatType) {
        for (final Audience audience : this.audiences()) audience.sendMessage(message, boundChatType);
    }

    default void sendMessage(final SignedMessage signedMessage, final ChatType.Bound boundChatType) {
        for (final Audience audience : this.audiences()) audience.sendMessage(signedMessage, boundChatType);
    }

    default void deleteMessage(final SignedMessage.Signature signature) {
        for (final Audience audience : this.audiences()) audience.deleteMessage(signature);
    }
}
