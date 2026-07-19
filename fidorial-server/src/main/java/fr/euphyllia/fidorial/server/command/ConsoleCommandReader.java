package fr.euphyllia.fidorial.server.command;

import fr.euphyllia.fidorial.server.FidorialServer;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BooleanSupplier;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public class ConsoleCommandReader {

    private static final ComponentLogger LOGGER = getLogger(ConsoleCommandReader.class);

    private final CommandManager commandManager;
    private final BooleanSupplier serverRunning;
    private final AtomicBoolean started = new AtomicBoolean(false);

    public ConsoleCommandReader(CommandManager commandManager, BooleanSupplier serverRunning) {
        this.commandManager = commandManager;
        this.serverRunning = serverRunning;
    }

    public void start() {
        if (!started.compareAndSet(false, true)) return;
        Thread thread = new Thread(this::run, "fidorial-console");
        thread.setDaemon(true);
        thread.start();
        LOGGER.info("Console interactive prete");
    }

    private void run() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            String line;
            while (serverRunning.getAsBoolean() && (line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                commandManager.dispatch(FidorialServer.getInstance().getConsole(), line);
            }
        } catch (IOException e) {
            LOGGER.warn("Lecture de la console interrompue : {}", e.getMessage());
        }
    }
}
