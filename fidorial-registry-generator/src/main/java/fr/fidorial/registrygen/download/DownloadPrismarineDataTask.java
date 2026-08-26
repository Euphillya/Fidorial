package fr.fidorial.registrygen.task;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

/**
 * Downloads PrismarineJS's {@code minecraft-data} {@code data/pc/<version>} directory in full
 * used to enrich generated sources with data Mojang's own reports don't expose.
 *
 * @since 0.1.0
 */
@CacheableTask
public abstract class DownloadPrismarineDataTask extends DefaultTask {

    private static final String CONTENTS_URL_TEMPLATE =
            "https://api.github.com/repos/PrismarineJS/minecraft-data/contents/data/pc/%s?ref=%s";

    /**
     * Prismarine {@code minecraft-data} version directory, e.g. {@code "26.1"}. See the
     * {@code data/pc} directory of the repository for available versions.
     */
    @Input
    public abstract Property<String> getPrismarineMinecraftData();

    /**
     * Git ref (branch or tag) of the {@code minecraft-data} repository to download from.
     */
    @Input
    public abstract Property<String> getRef();

    @OutputDirectory
    public abstract DirectoryProperty getDataDirectory();

    @TaskAction
    public void download() throws IOException {

        final String version = getPrismarineMinecraftData().get();
        final String ref = getRef().get();
        final String contentsUrl = CONTENTS_URL_TEMPLATE.formatted(version, ref);

        final Path outputDirectory = getDataDirectory().get().getAsFile().toPath();
        Files.createDirectories(outputDirectory);

        final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(30)).build();

        final JsonArray entries;
        try {
            final HttpRequest listing = HttpRequest.newBuilder(URI.create(contentsUrl))
                    .header("Accept", "application/vnd.github+json")
                    .header("User-Agent", "Fidorial/" + version + " (https://fidorial.euphyllia.moe)")
                    .timeout(Duration.ofSeconds(30))
                    .GET()
                    .build();

            final HttpResponse<String> response = client.send(listing, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() != 200) {
                throw new IOException("GitHub returned HTTP " + response.statusCode() + " listing " + contentsUrl);
            }

            entries = JsonParser.parseString(response.body()).getAsJsonArray();
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while listing " + contentsUrl, e);
        }

        for (final JsonElement element : entries) {

            final JsonObject entry = element.getAsJsonObject();
            if (!"file".equals(entry.get("type").getAsString())) {
                continue; // no subdirectories expected
            }

            final String name = entry.get("name").getAsString();
            final String downloadUrl = entry.get("download_url").getAsString();
            final Path destination = outputDirectory.resolve(name);

            try (final var input = URI.create(downloadUrl).toURL().openStream()) {
                Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING);
            } catch (final IOException e) {
                throw new IOException("Failed to download " + downloadUrl, e);
            }
        }
    }
}
