package fr.fidorial.command;

import com.mojang.brigadier.tree.CommandNode;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.util.Collection;

public interface CommandMeta {

    /**
     * Returns the description shown in command help.
     */
    @Nullable Component description();

    /**
     * Returns the usage text shown in command help.
     */
    @Nullable Component usage();

    /**
     * Returns aliases used to invoke this command.
     */
    Collection<String> aliases();

    /**
     * Returns additional hint nodes.
     */
    Collection<CommandNode<CommandSource>> hints();

    /**
     * Returns the plugin that registered this command.
     */
    @Nullable Object plugin();

    interface Builder {

        Builder description(Component description);

        Builder usage(Component usage);

        Builder aliases(String... aliases);

        Builder hint(CommandNode<CommandSource> node);

        Builder plugin(Object plugin);

        CommandMeta build();
    }
}
