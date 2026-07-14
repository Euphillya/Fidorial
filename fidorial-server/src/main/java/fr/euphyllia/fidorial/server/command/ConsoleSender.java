package fr.euphyllia.fidorial.server.command;

import fr.euphyllia.fidorial.api.command.CommandSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleSender implements CommandSender {

    public static final ConsoleSender INSTANCE = new ConsoleSender();

    private static final Logger LOGGER = LoggerFactory.getLogger("Console");

    private ConsoleSender() {
    }

    @Override
    public String name() {
        return "Console";
    }

    @Override
    public void sendMessage(String message) {
        LOGGER.info(message);
    }

    @Override
    public boolean isConsole() {
        return true;
    }
}
