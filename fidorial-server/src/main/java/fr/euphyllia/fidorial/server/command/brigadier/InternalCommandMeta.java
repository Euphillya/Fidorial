package fr.euphyllia.fidorial.server.command.brigadier;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import fr.fidorial.command.CommandMeta;
import fr.fidorial.command.CommandSource;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

public final class InternalCommandMeta implements CommandMeta {

    public static final class Builder implements CommandMeta.Builder {

        private final ImmutableSet.Builder<String> aliases;
        private final ImmutableList.Builder<CommandNode<CommandSource>> hints;

        private Object plugin;
        private Component description;
        private Component usage;

        public Builder(final String alias) {
            Preconditions.checkNotNull(alias, "alias");

            this.aliases = ImmutableSet.<String>builder()
                    .add(alias.toLowerCase(Locale.ENGLISH));

            this.hints = ImmutableList.builder();

            this.plugin = null;
            this.description = null;
            this.usage = null;
        }

        @Override
        public CommandMeta.Builder description(final Component description) {
            this.description = Preconditions.checkNotNull(description, "description");
            return this;
        }

        @Override
        public CommandMeta.Builder usage(final Component usage) {
            this.usage = Preconditions.checkNotNull(usage, "usage");
            return this;
        }

        @Override
        public CommandMeta.Builder aliases(final String... aliases) {
            Preconditions.checkNotNull(aliases, "aliases");

            for (int i = 0; i < aliases.length; i++) {
                final String alias = aliases[i];

                Preconditions.checkNotNull(alias, "alias at index %s", i);

                this.aliases.add(alias.toLowerCase(Locale.ENGLISH));
            }

            return this;
        }

        @Override
        public CommandMeta.Builder hint(final CommandNode<CommandSource> node) {
            Preconditions.checkNotNull(node, "node");

            if (node.getCommand() != null) {
                throw new IllegalArgumentException("Cannot use executable node for hinting");
            }

            if (node.getRedirect() != null) {
                throw new IllegalArgumentException("Cannot use a node with a redirect for hinting");
            }

            this.hints.add(node);
            return this;
        }

        @Override
        public CommandMeta.Builder plugin(final Object plugin) {
            Preconditions.checkNotNull(plugin, "plugin");

            this.plugin = plugin;
            return this;
        }

        @Override
        public CommandMeta build() {
            return new InternalCommandMeta(
                    this.aliases.build(),
                    this.hints.build(),
                    this.plugin,
                    this.description,
                    this.usage
            );
        }
    }

    private static CommandNode<CommandSource> copyForHinting(
            final CommandNode<CommandSource> hint
    ) {
        final ArgumentBuilder<CommandSource, ?> builder = hint.createBuilder()
                .requires(_ -> false);

        for (final CommandNode<CommandSource> child : hint.getChildren()) {
            builder.then(copyForHinting(child));
        }

        return builder.build();
    }

    public static Stream<CommandNode<CommandSource>> copyHints(
            final CommandMeta meta
    ) {
        return meta.hints()
                .stream()
                .map(InternalCommandMeta::copyForHinting);
    }

    private final Set<String> aliases;
    private final List<CommandNode<CommandSource>> hints;
    private final Object plugin;

    private final @Nullable Component description;
    private final @Nullable Component usage;

    private InternalCommandMeta(
            final Set<String> aliases,
            final List<CommandNode<CommandSource>> hints,
            final @Nullable Object plugin,
            final @Nullable Component description,
            final @Nullable Component usage
    ) {
        this.aliases = aliases;
        this.hints = hints;
        this.plugin = plugin;
        this.description = description;
        this.usage = usage;
    }

    @Override
    public @Nullable Component description() {
        return this.description;
    }

    @Override
    public @Nullable Component usage() {
        return this.usage;
    }

    @Override
    public Collection<String> aliases() {
        return this.aliases;
    }

    @Override
    public Collection<CommandNode<CommandSource>> hints() {
        return this.hints;
    }

    @Override
    public @Nullable Object plugin() {
        return this.plugin;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final InternalCommandMeta that = (InternalCommandMeta) o;

        if (!this.aliases.equals(that.aliases)) {
            return false;
        }

        return this.hints.equals(that.hints);
    }

    @Override
    public int hashCode() {
        int result = this.aliases.hashCode();
        result = 31 * result + this.hints.hashCode();
        return result;
    }
}
