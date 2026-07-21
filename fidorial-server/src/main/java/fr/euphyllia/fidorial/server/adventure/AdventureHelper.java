package fr.euphyllia.fidorial.server.adventure;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

public final class AdventureHelper {

    private AdventureHelper() {
    }

    public static final GsonComponentSerializer GSON_SERIALIZER = GsonComponentSerializer.gson();
    public static final ANSIComponentSerializer ANSI_SERIALIZER = ANSIComponentSerializer.ansi();
    public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public static MiniMessage miniMessage(MiniMessage.Preset preset) {
        return MiniMessage.miniMessage(preset);
    }

    public static ComponentLogger getLogger(Class<?> clazz) {
        return ComponentLogger.logger(clazz);
    }

    public static ComponentLogger getLogger(String name) {
        return ComponentLogger.logger(name);
    }
}
