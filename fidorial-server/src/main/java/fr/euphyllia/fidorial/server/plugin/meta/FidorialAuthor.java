package fr.euphyllia.fidorial.server.plugin.meta;

import fr.fidorial.plugin.PluginMeta;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.Nullable;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

public final class FidorialAuthor implements PluginMeta.Author {
    private final String name;
    private final @Nullable URI website;
    private final Map<String, String> contact;

    public FidorialAuthor(String name) {
        this(name, null, null);
    }

    public FidorialAuthor(String name, @Nullable URI website, @Nullable Map<String, String> contact) {
        this.name = name;
        this.website = website;
        this.contact = contact != null ? Map.copyOf(contact) : Map.of();
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Optional<URI> website() {
        return Optional.ofNullable(website);
    }

    @Override
    public @Unmodifiable Map<String, String> contact() {
        return contact;
    }
}
