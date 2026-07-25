package fr.fidorial.registrygen.generate;

import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.TypeSpec;
import fr.fidorial.registrygen.model.RegistryTypeDefinition;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Utility class designed to generate marker interfaces for registry types in a Minecraft-related
 * registry system. This class facilitates creating Java source files representing marker interfaces
 * that serve as the types for entries in specific registries.
 *
 * The generated marker interfaces are used as type-safe representations for the corresponding
 * registry entries and are associated with registry keys via generics.
 *
 * This class is immutable and cannot be instantiated.
 *
 * @since 0.1.0
 */
public final class RegistryDataGenerator {

    public static final String DATA_PACKAGE = "fr.fidorial.registry.data";

    /**
     * Generates the marker interface for a supported registry type.
     *
     * @param registryType    configured registry type
     * @param outputDirectory generated Java source root
     *
     * @throws IOException if the source file cannot be written
     */
    public void generate(final RegistryTypeDefinition registryType, final Path outputDirectory) throws IOException {

        Objects.requireNonNull(registryType, "registryType");
        Objects.requireNonNull(outputDirectory, "outputDirectory");

        final TypeSpec markerInterface = TypeSpec
                .interfaceBuilder(registryType.typeName())
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("Marker type for entries in the {@code $L} registry.\n", registryType.identifier())
                .addJavadoc("\n<p>This interface is used as the generic type for typed registry keys.</p>\n")
                .build();

        JavaFile.builder(DATA_PACKAGE, markerInterface).indent("    ").skipJavaLangImports(true).build().writeTo(outputDirectory);
    }
}