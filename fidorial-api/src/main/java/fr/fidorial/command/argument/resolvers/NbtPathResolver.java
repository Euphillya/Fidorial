package fr.fidorial.command.argument.resolvers;

import net.kyori.adventure.nbt.BinaryTag;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

/**
 * A parsed NBT path, resolvable against any root {@link BinaryTag}.
 *
 * @since 0.1.0
 */
@ApiStatus.NonExtendable
public interface NbtPathResolver {

    /**
     * Resolves this path against the given root tag.
     *
     * @param root the tag to resolve against
     * @return the matched tags, or an empty list if any path segment matched nothing
     * @since 0.1.0
     */
    List<BinaryTag> resolve(BinaryTag root);

    /**
     * Returns a parseable string representation of this path.
     *
     * @since 0.1.0
     */
    String asString();
}
