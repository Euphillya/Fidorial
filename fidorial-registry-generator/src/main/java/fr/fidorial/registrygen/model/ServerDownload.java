package fr.fidorial.registrygen.model;

import java.net.URI;
import java.util.Objects;

public record ServerDownload(String version, URI url, String sha1) {

    public ServerDownload {

        Objects.requireNonNull(version, "version");
        Objects.requireNonNull(url, "url");
        Objects.requireNonNull(sha1, "sha1");

        if (version.isBlank()) {
            throw new IllegalArgumentException("Minecraft version cannot be blank.");
        }

        if (sha1.isBlank()) {
            throw new IllegalArgumentException("Server JAR SHA-1 cannot be blank.");
        }
    }
}