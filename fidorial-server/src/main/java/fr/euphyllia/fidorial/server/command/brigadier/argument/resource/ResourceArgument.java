package fr.euphyllia.fidorial.server.command.brigadier.argument.resource;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.command.brigadier.argument.util.ExceptionFactory;
import fr.euphyllia.fidorial.server.command.brigadier.argument.util.KeyReader;
import fr.euphyllia.fidorial.server.command.brigadier.argument.util.KeyReader.ParsedKey;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.fidorial.registry.Registry;
import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import net.kyori.adventure.key.Key;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public final class ResourceArgument<T> implements ArgumentType<T> {

    public static final Dynamic2CommandExceptionType ERROR_UNKNOWN_RESOURCE = ExceptionFactory.dynamic2("argument.resource.not_found");

    private final RegistryKey<T> registryKey;
    private final Registry<T> registryLookup;

    private ResourceArgument(final RegistryKey<T> registryKey, final Registry<T> registryLookup) {
        this.registryKey = registryKey;
        this.registryLookup = registryLookup;
    }

    public static <T> ResourceArgument<T> resource(final RegistryKey<T> registryKey) {
        final Registry<T> registry = FidorialServer.getInstance().registries().registry(registryKey);
        return new ResourceArgument<>(registryKey, registry);
    }

    @Override
    public T parse(final StringReader reader) throws CommandSyntaxException {
        final ParsedKey parsed = KeyReader.readKeyStringDetailed(reader);
        final Key key = parseKey(parsed);
        final TypedKey<T> typedKey = TypedKey.create(registryKey, key);

        return registryLookup
                .find(typedKey)
                .orElseThrow(() -> ERROR_UNKNOWN_RESOURCE.createWithContext(
                        reader, key.asString(), registryKey.key().asString()));
    }

    private Key parseKey(final ParsedKey parsed) {
        final String input = parsed.hasNamespace()
                ? parsed.value()
                : Key.MINECRAFT_NAMESPACE + Key.DEFAULT_SEPARATOR + parsed.value();
        return Key.key(input);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        final String remaining = builder.getRemaining().toLowerCase(Locale.ROOT);
        final boolean remainingHasNamespace = remaining.indexOf(Key.DEFAULT_SEPARATOR) >= 0;
        final String minecraftPrefix = Key.MINECRAFT_NAMESPACE + Key.DEFAULT_SEPARATOR;

        for (final T value : registryLookup.values()) {
            final String full = registryLookup.key(value).key().asString();
            final String path = full.startsWith(minecraftPrefix) ? full.substring(minecraftPrefix.length()) : full;

            if (remainingHasNamespace ? full.contains(remaining) : path.contains(remaining)) {
                builder.suggest(full);
            }
        }

        return builder.buildFuture();
    }

    public RegistryKey<T> registryKey() {
        return registryKey;
    }

    public static final class Info<T> implements ArgumentTypeRegistrar<ResourceArgument<T>, Info<T>.Spec> {

        @Override
        public Spec access(final ResourceArgument<T> argument) {
            return new Spec(argument.registryKey());
        }

        @Override
        public void serialize(final Spec spec, final PacketBuffer buf) {
            buf.writeRegistryKey(spec.registryKey);
        }

        @Override
        public Spec deserialize(final PacketBuffer buf) {
            final Key key = buf.readKey();
            return new Spec(RegistryKey.of(key));
        }

        @Override
        public void serializeJson(final Spec spec, final JsonObject json) {
            json.addProperty("registry", spec.registryKey.key().asString());
        }

        public final class Spec implements ArgumentTypeRegistrar.Spec<ResourceArgument<T>> {

            private final RegistryKey<T> registryKey;

            public Spec(final RegistryKey<T> registryKey) {
                this.registryKey = registryKey;
            }

            @Override
            public ResourceArgument<T> instantiate() {
                return ResourceArgument.resource(registryKey);
            }

            @Override
            public ArgumentTypeRegistrar<ResourceArgument<T>, ?> type() {
                return Info.this;
            }
        }
    }
}
