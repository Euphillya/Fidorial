package fr.fidorial.registrygen.model;

import java.util.Objects;

/**
 * Represents the definition for a specific type of registry.
 * A registry type is described using an identifier, a type name,
 * and the name of the class responsible for handling keys associated with the registry.
 *
 * @since 0.1.0
 */
public record RegistryTypeDefinition(String identifier, String typeName, String keysClassName, String packageSuffix) {

    public RegistryTypeDefinition {
        Objects.requireNonNull(identifier, "identifier");
        Objects.requireNonNull(typeName, "typeName");
        Objects.requireNonNull(keysClassName, "keysClassName");
        Objects.requireNonNull(packageSuffix, "packageSuffix");
    }

    public RegistryTypeDefinition(final String identifier, final String typeName, final String keysClassName) {
        this(identifier, typeName, keysClassName, "");
    }

    public static RegistryTypeDefinition parse(final String identifier, final String qualifiedTypeName) {

        Objects.requireNonNull(identifier, "identifier");
        Objects.requireNonNull(qualifiedTypeName, "qualifiedTypeName");

        final int lastDot = qualifiedTypeName.lastIndexOf('.');
        final String typeName = lastDot < 0 ? qualifiedTypeName : qualifiedTypeName.substring(lastDot + 1);
        final String packageSuffix = lastDot < 0 ? "" : qualifiedTypeName.substring(0, lastDot);

        return new RegistryTypeDefinition(identifier, typeName, typeName + "Keys", packageSuffix);
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
