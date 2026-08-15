package fr.euphyllia.fidorial.server.registry;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jspecify.annotations.Nullable;

public record RegistryEntry(Key key, @Nullable CompoundBinaryTag data) {

    public static RegistryEntry known(final Key key) {
        return new RegistryEntry(key, null);
    }
}
