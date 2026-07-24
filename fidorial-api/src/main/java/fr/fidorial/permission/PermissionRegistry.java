package fr.fidorial.permission;

import java.util.Collection;
import java.util.Optional;

public interface PermissionRegistry {

    /**
     * Declares a permission, replacing any previous declaration for the same node.
     *
     * @param definition the definition
     * @return the previous definition, if any
     */
    Optional<PermissionDefinition> define(PermissionDefinition definition);

    /**
     * Declares several permissions at once, bumping {@link #revision()} only once.
     *
     * @param definitions the definitions
     */
    void defineAll(Collection<PermissionDefinition> definitions);

    /**
     * Removes a declaration.
     *
     * @param node the node
     * @return {@code true} if something was removed
     */
    boolean undefine(PermissionNode node);

    /**
     * Looks up a declaration by exact node.
     *
     * @param node the node
     * @return the declaration, if any
     */
    Optional<PermissionDefinition> definition(PermissionNode node);

    /**
     * @return an immutable snapshot of every declaration
     */
    Collection<PermissionDefinition> definitions();

    /**
     * Resolves the declared default for a node, walking its {@link PermissionNode#lookupChain()} so
     * that a declaration on {@code fidorial.command.*} applies to {@code fidorial.command.weather}.
     *
     * @param node     the node
     * @param operator whether the holder is an operator
     * @return the declared default, {@link TriState#UNSET} when nothing is declared
     */
    default TriState declaredDefault(final PermissionNode node, final boolean operator) {
        for (final PermissionNode candidate : node.lookupChain()) {
            final TriState state = definition(candidate)
                    .map(definition -> definition.defaultFor(operator))
                    .orElse(TriState.UNSET);
            if (state.isDecided()) {
                return state;
            }
        }
        return TriState.UNSET;
    }

    /**
     * Monotonic counter bumped on every mutation. Holders compare it against the value they cached
     * to know whether their resolved permissions are stale.
     *
     * @return the current revision
     */
    long revision();
}
