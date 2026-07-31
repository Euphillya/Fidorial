package fr.fidorial.registrygen.generate;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.ParameterizedTypeName;
import com.palantir.javapoet.TypeSpec;
import fr.fidorial.registrygen.GenerationUtils;
import fr.fidorial.registrygen.model.ProtocolIdTarget;
import fr.fidorial.registrygen.model.RegistryDefinition;
import fr.fidorial.registrygen.model.RegistryEntryDefinition;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Generates a holder of {@code int} constants for every {@link RegistryEntryDefinition}
 * of a registry, e.g. {@code CHEST_BLOCK_ENTITY_ID = 8}.
 *
 * <p>In addition to the constants, an identifier &rarr; protocol ID lookup table is
 * emitted so callers can resolve an ID at runtime without depending on a
 * compile-time constant name that may disappear in a future Minecraft version.</p>
 *
 * @since 0.1.0
 */
public final class RegistryProtocolIdGenerator {

    private static final String LOOKUP_FIELD = "BY_IDENTIFIER";
    private static final String UNKNOWN_FIELD = "UNKNOWN";

    private static final ClassName KEY_CLASS_NAME = ClassName.get("net.kyori.adventure.key", "Key");

    /**
     * Generates the protocol ID holder for a registry.
     *
     * @param registry        parsed Mojang registry definition
     * @param target          generation target (package, class name, constant suffix)
     * @param outputDirectory generated Java source root
     *
     * @throws IOException if the source file cannot be written
     */
    public void generate(final RegistryDefinition registry,
                         final ProtocolIdTarget target,
                         final Path outputDirectory) throws IOException {

        Objects.requireNonNull(registry, "registry");
        Objects.requireNonNull(target, "target");
        Objects.requireNonNull(outputDirectory, "outputDirectory");

        if (!registry.identifier().equals(target.registryIdentifier())) {
            throw new IllegalArgumentException("Protocol ID target '" + target.registryIdentifier()
                    + "' does not match parsed registry identifier '"
                    + registry.identifier() + "'.");
        }

        final TypeSpec.Builder typeBuilder = TypeSpec.interfaceBuilder(target.className())
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("Network IDs for entries in the {@code $L} registry.\n", registry.identifier())
                .addJavadoc("\n<p>Generated from Mojang's registry report; do not edit.</p>\n");

        typeBuilder.addField(FieldSpec
                .builder(int.class, UNKNOWN_FIELD, Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$L", -1)
                .addJavadoc("Returned by {@link #id($T)} when the identifier is unknown.\n", KEY_CLASS_NAME)
                .build());

        final Map<String, String> constantsByIdentifier = new LinkedHashMap<>();

        for (final RegistryEntryDefinition entry : registry.entries()) {

            final String fieldName = GenerationUtils.constantName(entry.identifier()) + target.constantSuffix();

            if (constantsByIdentifier.putIfAbsent(entry.identifier(), fieldName) != null) {
                throw new IllegalStateException("Duplicate registry entry '" + entry.identifier()
                        + "' in registry '" + registry.identifier() + "'.");
            }

            typeBuilder.addField(FieldSpec
                    .builder(int.class, fieldName, Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$L", entry.protocolId())
                    .addJavadoc("{@code $L}\n", entry.identifier())
                    .build());
        }

        typeBuilder.addField(createLookupField(constantsByIdentifier));
        typeBuilder.addMethod(createLookupMethod());

        JavaFile.builder(target.packageName(), typeBuilder.build())
                .indent("    ")
                .skipJavaLangImports(true)
                .build()
                .writeTo(outputDirectory);
    }

    private static FieldSpec createLookupField(final Map<String, String> constantsByIdentifier) {

        final ClassName map = ClassName.get(Map.class);
        final ParameterizedTypeName mapType = ParameterizedTypeName.get(map,
                KEY_CLASS_NAME,
                ClassName.get(Integer.class));

        final CodeBlock.Builder initializer = CodeBlock.builder().add("$T.ofEntries(", map);

        if (!constantsByIdentifier.isEmpty()) {

            initializer.add("\n");

            int index = 0;
            final int last = constantsByIdentifier.size() - 1;

            for (final Map.Entry<String, String> entry : constantsByIdentifier.entrySet()) {

                initializer.add("    $T.entry($T.key($S), $N)", map, KEY_CLASS_NAME, entry.getKey(), entry.getValue());

                if (index < last) {
                    initializer.add(",");
                }

                initializer.add("\n");
                index++;
            }
        }

        initializer.add(")");

        return FieldSpec.builder(mapType, LOOKUP_FIELD, Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer(initializer.build())
                .addJavadoc("Immutable identifier to protocol ID lookup table.\n")
                .build();
    }

    private static MethodSpec createLookupMethod() {

        final ParameterSpec identifier = ParameterSpec.builder(KEY_CLASS_NAME, "identifier", Modifier.FINAL).build();

        return MethodSpec.methodBuilder("id")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(int.class)
                .addParameter(identifier)
                .addJavadoc("Resolves the protocol ID for a namespaced identifier.\n\n")
                .addJavadoc("@param identifier namespaced identifier, e.g. {@code Key.key(\"minecraft\", \"chest\")}\n")
                .addJavadoc("@return the protocol ID, or {@link #$L} when the identifier is unknown\n", UNKNOWN_FIELD)
                .addStatement("return $N.getOrDefault($N, $L)", LOOKUP_FIELD, "identifier", UNKNOWN_FIELD)
                .build();
    }
}
