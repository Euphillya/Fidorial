package fr.euphyllia.fidorial.server.command.defaults;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.euphyllia.fidorial.server.FidorialServer;
import fr.fidorial.command.CommandSource;
import fr.fidorial.command.CommandTree;
import net.kyori.adventure.text.Component;

public class StopCommand {
    public static CommandTree create() {
        LiteralCommandNode<CommandSource> command = CommandTree.literal("stop")
                .requires(source -> source.sender().hasPermission("fidorial.command.stop"))
                .executes(StopCommand::stop)
                .build();
        return new CommandTree(command);
    }

    private static int stop(CommandContext<CommandSource> context) {
        FidorialServer.getInstance()
                .audiences()
                .forEach(audience -> audience.sendMessage(Component.translatable("command.stop.disabling")));
        FidorialServer.getInstance().shutdown();
        return Command.SINGLE_SUCCESS;
    }
}
