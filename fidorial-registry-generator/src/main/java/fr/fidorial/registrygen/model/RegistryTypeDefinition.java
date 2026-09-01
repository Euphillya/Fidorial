package fr.fidorial.registrygen.model;

import java.util.Objects;

/**
 * Represents the definition for a specific type of registry.
 * A registry type is described using an identifier, a type name,
 * the name of the class responsible for handling keys associated with the registry,
 * and how the registry reaches the client.
 *
 * @since 0.1.0
 */
public record RegistryTypeDefinition(String identifier, String typeName, String keysClassName, String packageSuffix,
                                     RegistrySync sync) {

    public RegistryTypeDefinition {
        Objects.requireNonNull(identifier, "identifier");
        Objects.requireNonNull(typeName, "typeName");
        Objects.requireNonNull(keysClassName, "keysClassName");
        Objects.requireNonNull(packageSuffix, "packageSuffix");
        Objects.requireNonNull(sync, "sync");
    }

    public RegistryTypeDefinition(final String identifier, final String typeName, final String keysClassName) {
        this(identifier, typeName, keysClassName, "", RegistrySync.NONE);
    }

    public static RegistryTypeDefinition parse(final String identifier, final String qualifiedTypeName) {
        return parse(identifier, qualifiedTypeName, RegistrySync.NONE);
    }

    public static RegistryTypeDefinition parse(final String identifier,
                                               final String qualifiedTypeName,
                                               final RegistrySync sync) {

        Objects.requireNonNull(identifier, "identifier");
        Objects.requireNonNull(qualifiedTypeName, "qualifiedTypeName");
        Objects.requireNonNull(sync, "sync");

        final int lastDot = qualifiedTypeName.lastIndexOf('.');
        final String typeName = lastDot < 0 ? qualifiedTypeName : qualifiedTypeName.substring(lastDot + 1);
        final String packageSuffix = lastDot < 0 ? "" : qualifiedTypeName.substring(0, lastDot);

        return new RegistryTypeDefinition(identifier, typeName, typeName + "Keys", packageSuffix, sync);
    }

    public String path() {
        final int separator = identifier.indexOf(':');
        return separator >= 0 ? identifier.substring(separator + 1) : identifier;
    }

    public String dataPackage(final String baseDataPackage) {
        return packageSuffix.isEmpty() ? baseDataPackage : baseDataPackage + "." + packageSuffix;
    }

    public String keysPackage(final String baseKeysPackage) {
        return packageSuffix.isEmpty() ? baseKeysPackage : baseKeysPackage + "." + packageSuffix;
    }
}
