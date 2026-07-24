package fr.fidorial.permission;

import fr.fidorial.plugin.Plugin;

import java.util.Map;

public interface PermissionStateHolder extends PermissionHolder {

    /**
     * @return the engine backing this holder
     */
    PermissionState permissions();

    @Override
    default TriState permissionState(final PermissionNode node) {
        return permissions().resolve(node);
    }

    @Override
    default PermissionGrant newGrant(final Plugin owner) {
        return permissions().newGrant(owner);
    }

    @Override
    default Map<PermissionNode, TriState> activeOverrides() {
        return permissions().activeOverrides();
    }

    @Override
    default void invalidatePermissions() {
        permissions().invalidate();
    }
}
