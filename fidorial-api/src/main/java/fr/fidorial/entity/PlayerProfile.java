package fr.fidorial.entity;

import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public record PlayerProfile(UUID uuid, String name, List<Property> properties) {

    public PlayerProfile {
        properties = List.copyOf(properties);
    }

    public PlayerProfile(UUID uuid, String name) {
        this(uuid, name, List.of());
    }

    public PlayerProfile(PlayerProfileMeta meta) {
        this(meta.id(), meta.name(), List.of());
    }

    public record Property(
            String name, String value, @Nullable String signature) {
            }
}
