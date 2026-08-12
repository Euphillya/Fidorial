package fr.euphyllia.fidorial.server.registry;

import fr.euphyllia.fidorial.server.world.nbt.Nbt;
import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

public record RegistryEntry(Key key, @Nullable Nbt data) {
}
