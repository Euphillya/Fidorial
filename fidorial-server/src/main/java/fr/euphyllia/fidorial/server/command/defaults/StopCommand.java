package fr.euphyllia.fidorial.server.command.defaults;

import fr.euphyllia.fidorial.api.command.CommandExecutor;
import fr.euphyllia.fidorial.api.command.CommandSender;
import fr.euphyllia.fidorial.server.FidorialServer;
import net.kyori.adventure.text.Component;

public class StopCommand implements CommandExecutor {

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("fidorial.command.stop")) {
            sender.sendMessage(Component.translatable("command.error.nopermission"));
            return;
        }
        FidorialServer.getInstance().audiences().forEach(audience ->
                audience.sendMessage(Component.translatable("command.stop.disabling")));
        FidorialServer.getInstance().shutdown();
        sender.sendMessage(Component.translatable("command.stop.stopped"));
    }
}
