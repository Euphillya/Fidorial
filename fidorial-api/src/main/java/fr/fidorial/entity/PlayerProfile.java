package fr.fidorial.entity;

import java.util.List;
import java.util.UUID;

public record PlayerProfile(UUID uuid, String name, List<Property> properties) {

    public PlayerProfile {
        properties = properties == null ? List.of() : List.copyOf(properties);
    }

    public PlayerProfile(UUID uuid, String name) {
        this(uuid, name, List.of());
    }

    public record Property(String name, String value, String signature) {
    }
}