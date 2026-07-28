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
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.fidorial.registry.Registry;
import fr.fidorial.registry.RegistryKey;
import fr.fidorial.registry.TypedKey;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class ResourceArgument<T> implements ArgumentType<T> {

    private static final Collection<String> EXAMPLES = List.of("minecraft:zombie", "zombie", "foo:bar");

    public static final Dynamic2CommandExceptionType ERROR_UNKNOWN_RESOURCE =
            new Dynamic2CommandExceptionType((id, registry) -> MSG_SERIALIZER.serialize(Component.translatable(
                    "argument.resource.not_found",
                    Component.text(id.toString()),
                    Component.text(registry.toString()))));

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
        final int start = reader.getCursor();

        while (reader.canRead() && isAllowedInKey(reader.peek())) {
            reader.skip();
        }

        final String input = reader.getString().substring(start, reader.getCursor());
        final Key key = parseKey(input);
        final TypedKey<T> typedKey = TypedKey.create(registryKey, key);

        return registryLookup
                .find(typedKey)
                .orElseThrow(() -> ERROR_UNKNOWN_RESOURCE.createWithContext(
                        reader, key.asString(), registryKey.key().asString()));
    }

    private boolean isAllowedInKey(final char c) {
        return Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.' || c == ':' || c == '/';
    }

    private Key parseKey(String input) {
        if (!input.contains(":")) {
            input = "minecraft:" + input;
        }
        return Key.key(input);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        final String remaining = builder.getRemaining().toLowerCase(Locale.ROOT);

        for (final T value : registryLookup.values()) {
            final String full = registryLookup.key(value).key().asString();
            final String path = full.startsWith("minecraft:") ? full.substring("minecraft:".length()) : full;

            if (remaining.contains(":") ? full.contains(remaining) : path.contains(remaining)) {
                builder.suggest(full);
            }
        }

        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
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
