package fr.euphyllia.fidorial.server.command.brigadier.packet.util;

import fr.fidorial.command.CommandSender;
import fr.fidorial.permission.PermissionGrant;
import fr.fidorial.permission.PermissionNode;
import fr.fidorial.permission.TriState;
import fr.fidorial.plugin.Plugin;

import java.util.Map;

/**
 * A sender that holds nothing, used when building the command tree sent to a client before its
 * permissions are known.
 *
 * <p>Every node resolves to {@link TriState#DENY} rather than {@link TriState#UNSET}: the point is
 * to produce a definitive "no" that no resolver can override, not to defer the question.
 */
public final class PermissionlessCommandSender implements CommandSender {

    static final PermissionlessCommandSender INSTANCE = new PermissionlessCommandSender();

    private PermissionlessCommandSender() {
    }

    @Override
    public TriState permissionState(final PermissionNode node) {
        return TriState.DENY;
    }

    @Override
    public PermissionGrant newGrant(final Plugin owner) {
        throw new UnsupportedOperationException("Cannot grant permissions to the permissionless sender");
    }

    @Override
    public Map<PermissionNode, TriState> activeOverrides() {
        return Map.of();
    }

    @Override
    public void invalidatePermissions() {
        // nothing is cached
    }

    @Override
    public String name() {
        return "Permissionless";
    }

    @Override
    public boolean isOperator() {
        return false;
    }

    @Override
    public void setOperator(final boolean operator) {
        throw new UnsupportedOperationException("Cannot op the permissionless sender");
    }
}
