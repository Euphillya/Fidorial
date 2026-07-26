package fr.fidorial.registrygen.generate;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.ParameterizedTypeName;
import com.palantir.javapoet.TypeSpec;
import fr.fidorial.registrygen.GenerationUtils;
import fr.fidorial.registrygen.model.RegistryDefinition;
import fr.fidorial.registrygen.model.RegistryEntryDefinition;
import fr.fidorial.registrygen.model.RegistryTypeDefinition;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Generates typed registry-entry key classes such as
 * {@code ItemKeys}, {@code BiomeKeys}, and {@code AttributeKeys}.
 *
 * @since 0.1.0
 */
public final class RegistryKeysGenerator {

    public static final String REGISTRY_PACKAGE = "fr.fidorial.registry";
    public static final String DATA_PACKAGE = "fr.fidorial.registry.data";
    public static final String KEYS_PACKAGE = "fr.fidorial.registry.keys";

    private static final ClassName REGISTRY_KEY = ClassName.get(REGISTRY_PACKAGE, "RegistryKey");
    private static final ClassName TYPED_KEY = ClassName.get(REGISTRY_PACKAGE, "TypedKey");
    private static final ClassName KEY_PATTERN = ClassName.get("net.kyori.adventure.key", "KeyPattern");

    /**
     * Generates a typed registry-entry key class.
     *
     * @param registryType      configured Java type information
     * @param registry          parsed Mojang registry definition
     * @param outputDirectory   generated Java source root
     *
     * @throws IOException if the source file cannot be written
     */
    public void generate(final RegistryTypeDefinition registryType,
                         final RegistryDefinition registry,
                         final Path outputDirectory) throws IOException {

        Objects.requireNonNull(registryType, "registryType");
        Objects.requireNonNull(registry, "registry");
        Objects.requireNonNull(outputDirectory, "outputDirectory");

        if (!registryType.identifier().equals(registry.identifier())) {
            throw new IllegalArgumentException("Registry type identifier '" + registryType.identifier() + "' does not match parsed registry identifier '" + registry.identifier() + "'.");
        }

        final ClassName markerType = ClassName.get(DATA_PACKAGE, registryType.typeName());

        final ParameterizedTypeName typedKeyType = ParameterizedTypeName.get(TYPED_KEY, markerType);

        final TypeSpec.Builder keysClass = TypeSpec.classBuilder(registryType.keysClassName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addJavadoc("Typed keys for entries in the " + "{@code $L} registry.\n", registryType.identifier());

        final List<String> fieldNames = addEntryFields(keysClass, registry, typedKeyType);

        keysClass.addField(createValuesField(fieldNames, typedKeyType));
        keysClass.addMethod(createPrivateConstructor(registryType.keysClassName()));
        keysClass.addMethod(createFactoryMethod(registryType, markerType, typedKeyType));
        keysClass.addMethod(createValuesMethod(typedKeyType));

        JavaFile.builder(KEYS_PACKAGE, keysClass.build())
                .indent("    ")
                .skipJavaLangImports(true)
                .build()
                .writeTo(outputDirectory);
    }

    private static List<String> addEntryFields(final TypeSpec.Builder keysClass,
                                       final RegistryDefinition registry,
                                       final ParameterizedTypeName typedKeyType) {

        final LinkedHashSet<String> generatedFieldNames = new LinkedHashSet<>();

        for (final RegistryEntryDefinition entry : registry.entries()) {

            final String fieldName = GenerationUtils.constantName(entry.identifier());

            if (!generatedFieldNames.add(fieldName)) {
                throw new IllegalStateException("Multiple registry entries in '" + registry.identifier() + "' produce the Java field name '" + fieldName + "'.");
            }

            final String keyValue = keyValue(entry.identifier());
            keysClass.addField(FieldSpec.builder(typedKeyType,
                                                 fieldName,
                                                 Modifier.PUBLIC,
                                                 Modifier.STATIC,
                                                 Modifier.FINAL)
                                       .initializer("create($S)", keyValue)
                                       .addJavadoc("Key for {@code $L}.\n", entry.identifier())
                                       .build());
        }
        return List.copyOf(generatedFieldNames);
    }

    private static FieldSpec createValuesField(final List<String> fieldNames, final ParameterizedTypeName typedKeyType) {

        final ClassName LIST = ClassName.get(List.class);
        final ParameterizedTypeName listType = ParameterizedTypeName.get(LIST, typedKeyType);
        final CodeBlock.Builder initializer = CodeBlock.builder();

        initializer.add("$T.of(", LIST);

        if (!fieldNames.isEmpty()) {

            initializer.add("\n");

            for (int index = 0; index < fieldNames.size(); index++) {
                initializer.add("    $N", fieldNames.get(index));

                if (index < fieldNames.size() - 1) {
                    initializer.add(",");
                }

                initializer.add("\n");
            }
        }

        initializer.add(")");

        return FieldSpec.builder(listType, "VALUES", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer(initializer.build())
                .build();
    }

    private static MethodSpec createPrivateConstructor(final String className) {

        return MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE)
                .addStatement("throw new $T($S)", UnsupportedOperationException.class, className + " cannot be instantiated.")
                .build();
    }

    private static MethodSpec createFactoryMethod(final RegistryTypeDefinition registryType,
                                                  final ClassName markerType,
                                                  final ParameterizedTypeName typedKeyType) {

        final String registryFieldName = GenerationUtils.constantName(registryType.path(), true);
        final ParameterSpec valueParameter = ParameterSpec.builder(String.class, "value", Modifier.FINAL)
                .addAnnotation(KEY_PATTERN)
                .build();

        return MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .returns(typedKeyType)
                .addParameter(valueParameter)
                .addStatement("return $T.create($T.$N, $N)", TYPED_KEY, REGISTRY_KEY, registryFieldName, "value")
                .build();
    }

    private static MethodSpec createValuesMethod(final ParameterizedTypeName typedKeyType) {

        final ParameterizedTypeName streamType = ParameterizedTypeName.get(ClassName.get(Stream.class), typedKeyType);

        return MethodSpec.methodBuilder("values")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(streamType)
                .addJavadoc("Returns a stream containing all keys declared by this class.\n\n")
                .addJavadoc("@return a stream of registry keys\n")
                .addStatement("return VALUES.stream()")
                .build();
    }

    /**
     * Returns the value passed to {@code TypedKey.create}.
     *
     * <p>Minecraft identifiers use only their path:</p>
     *
     * <pre>{@code
     * minecraft:diamond_sword -> diamond_sword
     * }</pre>
     *
     * <p>Identifiers from other namespaces preserve the complete
     * namespaced value:</p>
     *
     * <pre>{@code
     * fidorial:custom_item -> fidorial:custom_item
     * }</pre>
     */
    private static String keyValue(final String identifier) {

        final int separator = identifier.indexOf(':');

        if (separator < 0) {
            return identifier;
        }

        final String namespace = identifier.substring(0, separator);
        if (namespace.equalsIgnoreCase("minecraft")) {
            return identifier.substring(separator + 1);
        }

        return identifier;
    }
}