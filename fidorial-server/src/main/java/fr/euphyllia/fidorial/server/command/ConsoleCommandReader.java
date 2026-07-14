package fr.euphyllia.fidorial.server.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BooleanSupplier;

public class ConsoleCommandReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleCommandReader.class);

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
                commandManager.dispatch(ConsoleSender.INSTANCE, line);
            }
        } catch (IOException e) {
            LOGGER.warn("Lecture de la console interrompue : {}", e.getMessage());
        }
    }
}
