package fr.euphyllia.fidorial.server.command.brigadier.argument.chat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.EntitySelector;
import fr.euphyllia.fidorial.server.command.brigadier.argument.selector.EntitySelectorParser;
import fr.euphyllia.fidorial.server.command.brigadier.packet.registry.ArgumentTypeRegistrar;
import fr.euphyllia.fidorial.server.network.PacketBuffer;
import fr.fidorial.command.CommandSource;
import fr.fidorial.entity.Entity;
import fr.fidorial.entity.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.SelectorComponent;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static fr.euphyllia.fidorial.server.adventure.brigadier.BrigadierAdventureHelper.MSG_SERIALIZER;

public final class ComponentArgument implements ArgumentType<Component> {

    private static final Collection<String> EXAMPLES =
            Arrays.asList("\"hello world\"", "'hello world'", "\"\"", "{\"text\":\"hello world\"}");

    public static final DynamicCommandExceptionType ERROR_INVALID_COMPONENT = new DynamicCommandExceptionType(
            message -> MSG_SERIALIZER.serialize(
                    net.kyori.adventure.text.Component.translatable("argument.component.invalid")
                            .append(net.kyori.adventure.text.Component.text(message.toString()))));

    private ComponentArgument() {
    }

    public static ComponentArgument textComponent() {
        return new ComponentArgument();
    }

    @Override
    public Component parse(StringReader reader) throws CommandSyntaxException {
        String raw = AdventureJsonReader.readRawJson(reader, ERROR_INVALID_COMPONENT);

        try {
            return GsonComponentSerializer.gson().deserialize(raw);
        } catch (JsonParseException | IllegalArgumentException ex) {
            throw ERROR_INVALID_COMPONENT.createWithContext(reader, raw);
        }
    }

    public static Component getRawComponent(CommandContext<CommandSource> context, String name) {
        return context.getArgument(name, Component.class);
    }

    public static Component getResolvedComponent(CommandContext<CommandSource> context, String name) throws CommandSyntaxException {
        Component raw = getRawComponent(context, name);
        return resolveSelectors(raw, context.getSource());
    }

    private static Component resolveSelectors(Component component, CommandSource source) throws CommandSyntaxException {
        Component result = component;

        if (component instanceof SelectorComponent selector) {
            StringReader selectorReader = new StringReader(selector.pattern());
            EntitySelector parsed = new EntitySelectorParser(selectorReader).parse();
            Collection<? extends Entity> entities = parsed.findEntities(source);

            Component separator = selector.separator() != null ? selector.separator() : Component.text(", ");
            Component joined = Component.empty();
            boolean first = true;

            for (Entity entity : entities) {
                if (!first) {
                    joined = joined.append(separator);
                }
                joined = joined.append(displayNameOf(entity));
                first = false;
            }

            result = joined.style(component.style());
        }

        List<Component> children = component.children();
        if (!children.isEmpty()) {
            List<Component> resolvedChildren = new ArrayList<>(children.size());
            for (Component child : children) {
                resolvedChildren.add(resolveSelectors(child, source));
            }
            result = result.children(resolvedChildren);
        }

        return result;
    }

    private static Component displayNameOf(Entity entity) {
        if (entity instanceof Player player) {
            return Component.text(player.name());
        }
        return Component.text(entity.type().key().value());
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static final class Info implements ArgumentTypeRegistrar<ComponentArgument, Info.Spec> {

        @Override
        public void serialize(Spec spec, PacketBuffer buf) {
        }

        @Override
        public Spec deserialize(PacketBuffer buf) {
            return new Spec();
        }

        @Override
        public void serializeJson(Spec spec, JsonObject json) {
        }

        @Override
        public Spec access(ComponentArgument argument) {
            return new Spec();
        }

        public record Spec() implements ArgumentTypeRegistrar.Spec<ComponentArgument> {
            @Override
            public ComponentArgument instantiate() {
                return new ComponentArgument();
            }

            @Override
            public ArgumentTypeRegistrar<ComponentArgument, ?> type() {
                return new Info();
            }
        }
    }
}
