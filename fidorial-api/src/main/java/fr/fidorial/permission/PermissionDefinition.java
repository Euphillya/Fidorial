package fr.fidorial.permission;

import net.kyori.adventure.util.TriState;

import java.util.Objects;

public record PermissionDefinition(
        PermissionNode node,
        String description,
        TriState regularDefault,
        TriState operatorDefault
) {

    public PermissionDefinition {
        Objects.requireNonNull(node, "node");
        Objects.requireNonNull(regularDefault, "regularDefault");
        Objects.requireNonNull(operatorDefault, "operatorDefault");
    }

    /**
     * Declares a permission only operators hold by default.
     *
     * @param node        node path
     * @param description description
     * @return the definition
     */
    public static PermissionDefinition operatorOnly(final String node, final String description) {
        return new PermissionDefinition(PermissionNode.of(node), description, TriState.NOT_SET, TriState.TRUE);
    }

    /**
     * Declares a permission everyone holds by default.
     *
     * @param node        node path
     * @param description description
     * @return the definition
     */
    public static PermissionDefinition everyone(final String node, final String description) {
        return new PermissionDefinition(PermissionNode.of(node), description, TriState.TRUE, TriState.TRUE);
    }

    /**
     * Declares a permission nobody holds by default, operators included. It has to be granted
     * explicitly through a {@link PermissionGrant} or an external {@link PermissionResolver}.
     *
     * @param node        node path
     * @param description description
     * @return the definition
     */
    public static PermissionDefinition explicitOnly(final String node, final String description) {
        return new PermissionDefinition(PermissionNode.of(node), description, TriState.NOT_SET, TriState.NOT_SET);
    }

    /**
     * Declares a permission that is actively refused unless a grant overrides it.
     *
     * @param node        node path
     * @param description description
     * @return the definition
     */
    public static PermissionDefinition refused(final String node, final String description) {
        return new PermissionDefinition(PermissionNode.of(node), description, TriState.FALSE, TriState.FALSE);
    }

    /**
     * Returns the default state for a holder.
     *
     * @param operator whether the holder is an operator
     * @return the applicable default
     */
    public TriState defaultFor(final boolean operator) {
        return operator ? operatorDefault : regularDefault;
    }
}
