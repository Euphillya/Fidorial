package fr.fidorial.entity;

import com.google.gson.JsonObject;
import org.jspecify.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public record PlayerProfileMeta(UUID id, String name) {

    public PlayerProfileMeta(final PlayerProfile profile) {
        this(profile.uuid(), profile.name());
    }

    public static @Nullable PlayerProfileMeta fromJson(final JsonObject object) {
        if (!object.has("uuid") || !object.has("name")) {
            return null;
        }

        final UUID uuid;

        try {
            uuid = UUID.fromString(object.get("uuid").getAsString());
        } catch (final Throwable ignored) {
            return null;
        }

        return new PlayerProfileMeta(uuid, object.get("name").getAsString());
    }

    public void appendTo(final JsonObject output) {
        output.addProperty("uuid", id.toString());
        output.addProperty("name", name);
    }

    public static PlayerProfileMeta createOffline(final String name) {
        return new PlayerProfileMeta(
                UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8)), name);
    }

}
