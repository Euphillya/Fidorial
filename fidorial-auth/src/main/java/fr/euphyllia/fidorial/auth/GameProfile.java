package fr.euphyllia.fidorial.auth;

import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public record GameProfile(UUID uuid, String name, UUID sessionId, List<Property> properties) {

    public record Property(
            String name, String value, @Nullable String signature) {
            }
}
