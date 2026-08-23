package fr.euphyllia.fidorial.server.command.brigadier.argument.nbt;

import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import org.jspecify.annotations.Nullable;

final class NbtMatching {

    private NbtMatching() {
    }

    static boolean matches(final CompoundBinaryTag pattern, final @Nullable BinaryTag candidate) {
        return satisfies(pattern, candidate);
    }

    private static boolean satisfies(final @Nullable BinaryTag pattern, final @Nullable BinaryTag candidate) {
        if (pattern == null) {
            return true;
        }
        if (candidate == null) {
            return false;
        }

        return switch (pattern) {
            case final CompoundBinaryTag requiredFields -> candidate instanceof final CompoundBinaryTag actual && allFieldsSatisfied(requiredFields, actual);
            case final ListBinaryTag requiredElements -> candidate instanceof final ListBinaryTag actual && allElementsSatisfiable(requiredElements, actual);
            default -> pattern.equals(candidate);
        };
    }

    private static boolean allFieldsSatisfied(final CompoundBinaryTag required, final CompoundBinaryTag actual) {
        for (final String field : required.keySet()) {
            if (!satisfies(required.get(field), actual.get(field))) {
                return false;
            }
        }
        return true;
    }

    private static boolean allElementsSatisfiable(final ListBinaryTag required, final ListBinaryTag actual) {
        for (final BinaryTag requiredElement : required) {
            if (!satisfiableBySomeElement(requiredElement, actual)) {
                return false;
            }
        }
        return true;
    }

    private static boolean satisfiableBySomeElement(final BinaryTag requiredElement, final ListBinaryTag candidates) {
        for (final BinaryTag candidate : candidates) {
            if (satisfies(requiredElement, candidate)) {
                return true;
            }
        }
        return false;
    }
}
