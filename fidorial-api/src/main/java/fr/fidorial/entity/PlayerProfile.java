package fr.fidorial.entity;

import net.kyori.adventure.text.object.ObjectContents;
import net.kyori.adventure.text.object.ObjectContentsLike;
import net.kyori.adventure.text.object.PlayerHeadObjectContents;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

import static net.kyori.adventure.text.object.PlayerHeadObjectContents.property;

public record PlayerProfile(UUID uuid, String name, List<Property> properties) implements PlayerHeadObjectContents.SkinSource, ObjectContentsLike {

    public PlayerProfile {
        properties = List.copyOf(properties);
    }

    public PlayerProfile(UUID uuid, String name) {
        this(uuid, name, List.of());
    }

    public PlayerProfile(PlayerProfileMeta meta) {
        this(meta.id(), meta.name(), List.of());
    }

    @Override
    public void applySkinToPlayerHeadContents(final PlayerHeadObjectContents.Builder builder) {
        if (!this.properties.isEmpty()) {
            builder.profileProperties(
                    this.properties.stream()
                            .map(p -> property(p.name(), p.value(), p.signature()))
                            .toList()
            );
        }
        builder.id(this.uuid)
                .name(this.name);
    }

    @Override
    public ObjectContents asObjectContents() {
        return ObjectContents.playerHead(this);
    }

    public record Property(
            String name, String value, @Nullable String signature) {
            }
}
