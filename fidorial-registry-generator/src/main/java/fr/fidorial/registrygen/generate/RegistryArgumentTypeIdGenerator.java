package fr.fidorial.registrygen.generate;

import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.TypeSpec;
import fr.fidorial.registrygen.GenerationUtils;
import fr.fidorial.registrygen.model.RegistryDefinition;
import fr.fidorial.registrygen.model.RegistryEntryDefinition;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Generates a class of {@code int} constants for every entry in the
 * {@code minecraft:command_argument_type} registry, e.g. {@code BOOL_ARGUMENT_ID}.
 *
 * @since 0.1.0
 */
public final class RegistryArgumentTypeIdGenerator {

    private static final String PACKAGE = "fr.euphyllia.fidorial.server.registry.data";
    private static final String CLASS_NAME = "ArgumentTypeIds";
    private static final String SUFFIX = "_ARGUMENT_ID";

    public void generate(final RegistryDefinition registry, final Path outputDirectory) throws IOException {

        Objects.requireNonNull(registry, "registry");
        Objects.requireNonNull(outputDirectory, "outputDirectory");

        final TypeSpec.Builder typeBuilder = TypeSpec.interfaceBuilder(CLASS_NAME)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("Network IDs for entries in the {@code $L} registry.\n", registry.identifier());

        for (final RegistryEntryDefinition entry : registry.entries()) {

            final String fieldName = GenerationUtils.constantName(entry.identifier()) + SUFFIX;

            typeBuilder.addField(
                    FieldSpec.builder(int.class, fieldName, Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                            .initializer("$L", entry.protocolId())
                            .addJavadoc("{@code $L}\n", entry.identifier())
                            .build()
            );
        }

        JavaFile.builder(PACKAGE, typeBuilder.build())
                .indent("    ")
                .skipJavaLangImports(true)
                .build()
                .writeTo(outputDirectory);
    }
}
