package fr.euphyllia.fidorial.server.permission;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static fr.euphyllia.fidorial.server.adventure.AdventureHelper.getLogger;

public class OperatorList {

    private static final ComponentLogger LOGGER = getLogger(OperatorList.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Path file;
    private final Map<UUID, Entry> operators = new ConcurrentHashMap<>();

    public OperatorList(Path file) {
        this.file = Objects.requireNonNull(file, "file");
    }

    public void load() {
        if (!Files.exists(file)) {
            return;
        }
        try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            List<Entry> entries = GSON.fromJson(reader, new TypeToken<List<Entry>>() {}.getType());
            operators.clear();
            if (entries != null) {
                for (Entry entry : entries) {
                    operators.put(entry.uuid, entry);
                }
            }
            LOGGER.info("{} operateur(s) charge(s)", operators.size());
        } catch (Exception e) {
            LOGGER.error("Lecture de {} impossible", file, e);
        }
    }

    public synchronized void save() {
        try (Writer writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            GSON.toJson(new ArrayList<>(operators.values()), writer);
        } catch (IOException e) {
            LOGGER.error("Sauvegarde de {} impossible", file, e);
        }
    }

    public boolean isOp(UUID uuid) {
        return operators.containsKey(uuid);
    }

    public boolean setOp(UUID uuid, String name, boolean value) {
        Objects.requireNonNull(uuid, "uuid");
        boolean changed;
        if (value) {
            changed = operators.putIfAbsent(uuid, new Entry(uuid, name)) == null;
        } else {
            changed = operators.remove(uuid) != null;
        }
        if (changed) {
            save();
        }
        return changed;
    }

    private record Entry(UUID uuid, String name) {
    }
}
