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

public final class ResourceKeyArgument<T> implements ArgumentType<TypedKey<T>> {

    private static final Collection<String> EXAMPLES = List.of(
            "minecraft:zombie",
            "zombie",
            "foo:bar"
    );

    public static final Dynamic2CommandExceptionType ERROR_UNKNOWN_RESOURCE =
            new Dynamic2CommandExceptionType(
                    (id, registry) ->
                            MSG_SERIALIZER.serialize(
                                    Component.translatable(
                                            "argument.resource.not_found",
                                            Component.text(id.toString()),
                                            Component.text(registry.toString())
                                    )
                            )
            );

    private final RegistryKey<T> registryKey;


    private ResourceKeyArgument(
            RegistryKey<T> registryKey
    ) {
        this.registryKey = registryKey;
    }


    public static <T> ResourceKeyArgument<T> resourceKey(
            RegistryKey<T> registryKey
    ) {
        return new ResourceKeyArgument<>(registryKey);
    }


    @Override
    public TypedKey<T> parse(StringReader reader)
            throws CommandSyntaxException {

        int start = reader.getCursor();

        while (reader.canRead() && isAllowedInKey(reader.peek())) {
            reader.skip();
        }

        String input = reader.getString()
                .substring(start, reader.getCursor());

        return TypedKey.create(
                registryKey,
                parseKey(input)
        );
    }

    private boolean isAllowedInKey(char c) {
        return Character.isLetterOrDigit(c)
                || c == '_'
                || c == '-'
                || c == '.'
                || c == ':'
                || c == '/';
    }

    private static final String SPLITTERS = "._/";

    private static boolean matchesSubStr(String pattern, String input) {
        int index = 0;

        while (!input.startsWith(pattern, index)) {
            int next = -1;

            for (char c : SPLITTERS.toCharArray()) {
                int i = input.indexOf(c, index);

                if (i != -1 && (next == -1 || i < next)) {
                    next = i;
                }
            }

            if (next == -1) {
                return false;
            }

            index = next + 1;
        }

        return true;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(
            CommandContext<S> context,
            SuggestionsBuilder builder
    ) {
        Registry<T> registry = registry();

        String remaining = builder.getRemaining()
                .toLowerCase(Locale.ROOT);

        for (T value : registry.values()) {

            String full = registry.key(value)
                    .key()
                    .asString();

            String path = full.startsWith("minecraft:")
                    ? full.substring("minecraft:".length())
                    : full;

            if (remaining.contains(":")) {
                if (matchesSubStr(remaining, full)) {
                    builder.suggest(full);
                }
            } else {
                if (matchesSubStr(remaining, path)) {
                    builder.suggest(full);
                }
            }
        }

        return builder.buildFuture();
    }


    private Key parseKey(String input) {

        if (!input.contains(":")) {
            input = "minecraft:" + input;
        }

        return Key.key(input);
    }


    private Registry<T> registry() {
        return FidorialServer.getInstance()
                .registries()
                .registry(registryKey);
    }


    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }


    public RegistryKey<T> registryKey() {
        return registryKey;
    }

    public static final class Info<T> implements ArgumentTypeRegistrar<ResourceKeyArgument<T>, Info<T>.Spec> {

        @Override
        public Spec access(ResourceKeyArgument<T> argument) {
            return new Spec(argument.registryKey());
        }

        @Override
        public void serialize(Spec spec, PacketBuffer buf) {
            System.out.println("ResourceKey registry = "
                    + spec.registryKey.key().asString());
            buf.writeRegistryKey(spec.registryKey);
        }

        @Override
        public Spec deserialize(PacketBuffer buf) {
            Key key = buf.readKey();
            return new Spec(RegistryKey.of(key));
        }

        @Override
        public void serializeJson(Spec spec, JsonObject value) {
            value.addProperty("registry", spec.registryKey.key().asString());
        }

        public final class Spec implements ArgumentTypeRegistrar.Spec<ResourceKeyArgument<T>> {

            private final RegistryKey<T> registryKey;

            public Spec(RegistryKey<T> registryKey) {
                this.registryKey = registryKey;
            }

            @Override
            public ResourceKeyArgument<T> instantiate() {
                return ResourceKeyArgument.resourceKey(registryKey);
            }

            @Override
            public ArgumentTypeRegistrar<ResourceKeyArgument<T>, ?> type() {
                return Info.this;
            }
        }
    }
}
