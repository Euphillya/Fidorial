package fr.fidorial.entity;

import com.google.gson.JsonObject;

import java.util.UUID;

public record PlayerProfileMeta(UUID id, String name) {

    public PlayerProfileMeta(PlayerProfile profile) {
        this(profile.uuid(), profile.name());
    }

    public static PlayerProfileMeta fromJson(JsonObject object) {
        if (!object.has("uuid") || !object.has("name")) {
            return null;
        }

        UUID uuid;

        try {
            uuid = UUID.fromString(object.get("uuid").getAsString());
        } catch (Throwable ignored) {
            return null;
        }

        return new PlayerProfileMeta(uuid, object.get("name").getAsString());
    }

    public void appendTo(JsonObject output) {
        output.addProperty("uuid", id.toString());
        output.addProperty("name", name);
    }

    public static PlayerProfileMeta createOffline(String name) {
        return new PlayerProfileMeta(
                UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes()),
                name
        );
    }
}
