package fr.fidorial.command.argument.resolvers;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.fidorial.command.CommandSender;
import fr.fidorial.command.CommandSource;
import fr.fidorial.entity.Entity;
import java.util.List;

public class SelectorArgumentResolver implements ArgumentResolver<List<Entity>> {
    private final String argumentName;

    public SelectorArgumentResolver(String argumentName) {
        this.argumentName = argumentName;
    }

    @Override
    public List<Entity> resolve(CommandSource sender) throws CommandSyntaxException {
        if (!(sender instanceof CommandContext<?> ctx)) throw new IllegalArgumentException("Sender must be a CommandContext");
        return ctx.getArgument(argumentName, List.class);
    }
}