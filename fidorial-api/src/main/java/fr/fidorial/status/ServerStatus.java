package fr.fidorial.status;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public sealed interface ServerStatus permits SimpleServerStatus {
    @Contract(pure = true)
    Component description();

    @Contract(pure = true)
    Version version();

    @Contract(pure = true)
    Optional<Favicon> favicon();

    @Contract(pure = true)
    int maxPlayers();

    @Contract(pure = true)
    int players();

    @Unmodifiable
    @Contract(pure = true)
    List<SamplePlayer> samplePlayers();

    // todo: do we care about this here or always evaluate based on server configuration?
    @Contract(pure = true)
    default boolean enforceSecureChat() {
        return false;
    }

    @Contract(value = " -> new", pure = true)
    Builder toBuilder();

    @Contract(value = " -> new", pure = true)
    static Builder builder() {
        return new SimpleServerStatus.Builder();
    }

    sealed interface Builder permits SimpleServerStatus.Builder {
        @Contract(value = "_ -> this", mutates = "this")
        Builder description(Component description);

        @Contract(value = "_ -> this", mutates = "this")
        Builder version(Version version);

        @Contract(value = "_ -> this", mutates = "this")
        Builder favicon(@Nullable Favicon favicon);

        @Contract(value = "_ -> this", mutates = "this")
        Builder maxPlayers(int maxPlayers);

        @Contract(value = "_ -> this", mutates = "this")
        Builder players(int players);

        @Contract(value = "_ -> this", mutates = "this")
        Builder samplePlayers(List<SamplePlayer> samplePlayers);

        // fixme: name this method addSamplePlayer or just samplePlayer?
        @Contract(value = "_ -> this", mutates = "this")
        Builder samplePlayer(SamplePlayer samplePlayer);

        @Contract(value = " -> new", pure = true)
        ServerStatus build();
    }

    record SamplePlayer(String name, UUID id) {
    }

    record Version(String name, int protocolVersion) {
    }
}
