package fr.euphyllia.fidorial.server.registry;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;
import org.jspecify.annotations.Nullable;

public record RegistryEntry(Key key, @Nullable BinaryTag data) {
}
