package fr.fidorial.combat;

import fr.fidorial.entity.Entity;
import fr.fidorial.registry.TypedKey;
import fr.fidorial.registry.data.DamageType;
import fr.fidorial.world.Location;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class DamageSource {

    private final TypedKey<DamageType> type;
    private final @Nullable Entity causingEntity;
    private final @Nullable Entity directEntity;
    private final @Nullable Location position;

    public DamageSource(final TypedKey<DamageType> type, final @Nullable Entity causingEntity, final @Nullable Entity directEntity, final @Nullable Location position) {
        this.type = Objects.requireNonNull(type, "type");
        this.causingEntity = causingEntity;
        this.directEntity = directEntity;
        this.position = position;
    }

    public TypedKey<DamageType> type() {
        return type;
    }

    public @Nullable Entity causingEntity() {
        return causingEntity;
    }

    public @Nullable Entity directEntity() {
        return directEntity;
    }

    public @Nullable Location position() {
        return position;
    }
}
