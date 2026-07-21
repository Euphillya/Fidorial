package fr.fidorial.registrygen.download;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.fidorial.registrygen.model.ServerDownload;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static fr.fidorial.registrygen.RegistryGeneratorMain.DOWNLOAD_MANIFEST_URL;

public final class ServerJarURLGrabber {

    /**
     * Fetches the server download information for a specific Minecraft version.
     *
     * @param version the Minecraft version for which the server download information is to be fetched.
     *                Must not be null or blank.
     * @return a {@code ServerDownload} object containing the version, download URL, and SHA-1 checksum
     *         of the server JAR file.
     * @throws IllegalArgumentException if the version is null, blank, or not recognized.
     * @throws IOException if there is an error during the HTTP request or if the response
     *                     is malformed or does not contain the required data.
     */
    public static ServerDownload fetchServerDownload(final String version) throws IOException {

        if (version == null || version.isBlank()) {
            throw new IllegalArgumentException("Minecraft version cannot be null or blank.");
        }

        final String versionJsonUrl = fetchVersionJsonUrl(version);
        if (versionJsonUrl == null || versionJsonUrl.isBlank()) {
            throw new IllegalArgumentException("Unknown Minecraft version: " + version);
        }

        final URL manifestUrl = URI.create(versionJsonUrl).toURL();
        final HttpURLConnection connection = (HttpURLConnection) manifestUrl.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(15_000);
        connection.setReadTimeout(15_000);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("User-Agent", "Fidorial-Registry-Generator");

        try {

            final int responseCode = connection.getResponseCode();
            if (responseCode < 200 || responseCode >= 300) {
                throw new IOException("Failed to download Minecraft version metadata for " + version + ". HTTP status: " + responseCode);
            }

            try (final InputStreamReader reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)) {

                final JsonElement rootElement = JsonParser.parseReader(reader);
                if (!rootElement.isJsonObject()) {
                    throw new IOException("Minecraft version metadata root is not an object.");
                }

                final JsonObject rootObject = rootElement.getAsJsonObject();

                final JsonObject downloadsObject = requireObject(rootObject, "downloads", "Minecraft version metadata");
                final JsonObject serverObject = requireObject(downloadsObject, "server", "downloads");
                final String serverUrl = requireString(serverObject, "url", "downloads.server");
                final String serverSha1 = requireString(serverObject, "sha1", "downloads.server");

                return new ServerDownload(version, URI.create(serverUrl), serverSha1);
            }
        } finally {
            connection.disconnect();
        }
    }

    /**
     * Fetches the URL of the version JSON for the specified Minecraft version.
     *
     * @param version the Minecraft version for which the version JSON URL is to be fetched.
     *                Must not be null or blank.
     * @return the URL of the version JSON as a {@code String}, or {@code null} if no matching URL is found.
     * @throws IllegalArgumentException if the specified version is not recognized.
     * @throws IOException if there is an error during the HTTP request or if the response is malformed.
     */
    @Nullable
    private static String fetchVersionJsonUrl(final String version) throws IOException {

        final URL url = URI.create(DOWNLOAD_MANIFEST_URL).toURL();
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(15_000);
        connection.setReadTimeout(15_000);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("User-Agent", "Fidorial-Registry-Generator");

        try {
            final int responseCode = connection.getResponseCode();

            if (responseCode < 200 || responseCode >= 300) {
                throw new IOException("Failed to download Minecraft version manifest. HTTP status: " + responseCode);
            }

            try (final InputStreamReader reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)) {

                final JsonElement rootElement = JsonParser.parseReader(reader);
                final JsonElement versionsElement = getJsonElement(rootElement);

                for (final JsonElement versionElement : versionsElement.getAsJsonArray()) {

                    if (!versionElement.isJsonObject()) {
                        continue;
                    }

                    final JsonObject versionObject = versionElement.getAsJsonObject();

                    if (!versionObject.has("id") || !versionObject.has("url")) {
                        continue;
                    }

                    final String versionId = versionObject.get("id").getAsString();

                    if (version.equals(versionId)) {
                        return versionObject.get("url").getAsString();
                    }
                }
            }
        } finally {
            connection.disconnect();
        }

        throw new IllegalArgumentException("Unknown Minecraft version: " + version);
    }

    private static @NonNull JsonElement getJsonElement(final JsonElement rootElement) throws IOException {

        if (!rootElement.isJsonObject()) {
            throw new IOException("Minecraft version manifest root is not an object.");
        }

        final JsonObject rootObject = rootElement.getAsJsonObject();

        final JsonElement versionsElement = rootObject.get("versions");

        if (versionsElement == null || !versionsElement.isJsonArray()) {
            throw new IOException("Minecraft version manifest does not contain a valid versions array.");
        }
        return versionsElement;
    }

    private static JsonObject requireObject(final JsonObject parent,
                                            final String property,
                                            final String context) throws IOException {

        final JsonElement element = parent.get(property);
        if (element == null || !element.isJsonObject()) {
            throw new IOException(context + " does not contain a valid '" + property + "' object.");
        }

        return element.getAsJsonObject();
    }

    private static String requireString(final JsonObject parent,
                                        final String property,
                                        final String context) throws IOException {

        final JsonElement element = parent.get(property);
        if (element == null || !element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) {
            throw new IOException(context + " does not contain a valid '" + property + "' string.");
        }

        final String value = element.getAsString();
        if (value.isBlank()) {
            throw new IOException(context + " contains a blank '" + property + "' value.");
        }
        return value;
    }
}