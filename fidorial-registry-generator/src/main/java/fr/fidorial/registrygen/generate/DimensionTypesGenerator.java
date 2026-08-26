package fr.fidorial.registrygen.generate;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.TypeSpec;
import fr.fidorial.registrygen.GenerationUtils;
import fr.fidorial.registrygen.model.RegistryDefinition;
import fr.fidorial.registrygen.model.RegistryEntryDefinition;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Path;

public final class DimensionTypesGenerator {

    private static final ClassName DIMENSION_TYPE_DEFINITION =
            ClassName.get("fr.fidorial.world.dimension", "DimensionTypeDefinition");
    private static final ClassName DIMENSION_TYPE_KEYS =
            ClassName.get("fr.fidorial.registry.keys", "DimensionTypeKeys");

    public void generate(final RegistryDefinition registry, final Path outputDirectory) throws IOException {
        final TypeSpec.Builder type = TypeSpec.classBuilder("VanillaDimensionTypes")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addJavadoc("""
                        Provides the built-in Minecraft dimension type definitions.

                        <p>These definitions correspond to the vanilla dimension types registered
                        by Minecraft and can be used when constructing or referencing vanilla
                        dimensions.</p>

                        @apiNote The dimension types provided by this class mirror those registered
                        by vanilla and are not guaranteed to remain stable. New dimension types may
                        be added and existing dimension types may be removed in future Minecraft
                        versions without notice.

                        @since 0.1.0
                        """);

        for (final RegistryEntryDefinition entry : registry.entries()) {
            addDimensionTypeField(type, entry);
        }

        JavaFile.builder("fr.fidorial.world.dimension.types", type.build())
                .indent("    ")
                .skipJavaLangImports(true)
                .build()
                .writeTo(outputDirectory);
    }

    private static void addDimensionTypeField(
            final TypeSpec.Builder type,
            final RegistryEntryDefinition entry
    ) {
        final String fieldName = GenerationUtils.constantName(entry.identifier());

        type.addField(
                FieldSpec.builder(
                                DIMENSION_TYPE_DEFINITION,
                                fieldName,
                                Modifier.PUBLIC,
                                Modifier.STATIC,
                                Modifier.FINAL
                        )
                        .initializer(
                                "$T.builder($T.$N.key()).build()",
                                DIMENSION_TYPE_DEFINITION,
                                DIMENSION_TYPE_KEYS,
                                fieldName
                        )
                        .addJavadoc(
                                "The {@code $L} dimension type.\n\n@since 0.1.0\n",
                                entry.identifier()
                        )
                        .build()
        );
    }
}
