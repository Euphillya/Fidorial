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
import fr.fidorial.registrygen.model.RegistryDefinition;
import fr.fidorial.registrygen.model.RegistryEntryDefinition;
import fr.fidorial.registrygen.model.RegistryTagDefinition;
import fr.fidorial.registrygen.model.RegistryTypeDefinition;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    private static final ClassName KEY_PATTERN = ClassName.get(KeyPattern.class);

    /**
     * Generates a typed registry-entry key class.
     *
     * @param registryType    configured Java type information
     * @param registry        parsed Mojang registry definition
     * @param tags            this registry's resolved tags (identifier &rarr; member entry
     *                        identifiers), or an empty list if none/not generated - see
     *                        {@link RegistryTagReportParser}
     * @param registryPackage package holding {@code RegistryKey}/{@code TypedKey}
     * @param dataPackage     package holding the marker interface (already resolved, including
     *                        the registry type's own subpackage if any)
     * @param keysPackage     package this class is written to (already resolved, including
     *                        the registry type's own subpackage if any)
     * @param outputDirectory generated Java source root
     * @throws IOException if the source file cannot be written
     */
    public void generate(final RegistryTypeDefinition registryType,
                         final RegistryDefinition registry,
                         final List<RegistryTagDefinition> tags,
                         final String registryPackage,
                         final String dataPackage,
                         final String keysPackage,
                         final Path outputDirectory) throws IOException {

        Objects.requireNonNull(registryType, "registryType");
        Objects.requireNonNull(registry, "registry");
        Objects.requireNonNull(tags, "tags");
        Objects.requireNonNull(registryPackage, "registryPackage");
        Objects.requireNonNull(dataPackage, "dataPackage");
        Objects.requireNonNull(keysPackage, "keysPackage");
        Objects.requireNonNull(outputDirectory, "outputDirectory");

        if (!registryType.identifier().equals(registry.identifier())) {
            throw new IllegalArgumentException("Registry type identifier '" + registryType.identifier() + "' does not match parsed registry identifier '" + registry.identifier() + "'.");
        }

        final ClassName registryKey = ClassName.get(registryPackage, "RegistryKey");
        final ClassName typedKey = ClassName.get(registryPackage, "TypedKey");
        final ClassName markerType = ClassName.get(dataPackage, registryType.typeName());

        final ParameterizedTypeName typedKeyType = ParameterizedTypeName.get(typedKey, markerType);

        final TypeSpec.Builder keysClass = TypeSpec.classBuilder(registryType.keysClassName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addJavadoc("Typed keys for entries in the " + "{@code $L} registry.\n", registryType.identifier());

        final Map<String, String> fieldNamesByIdentifier = addEntryFields(keysClass, registry, typedKeyType);

        keysClass.addField(createValuesField(registry, fieldNamesByIdentifier, typedKeyType));

        if (!tags.isEmpty()) {
            keysClass.addField(createTagsField(tags));
        }

        keysClass.addMethod(createPrivateConstructor(registryType.keysClassName()));
        keysClass.addMethod(createFactoryMethod(registryType, typedKeyType, registryKey, typedKey));
        keysClass.addMethod(createValuesMethod(typedKeyType));
        keysClass.addMethod(createTagsMethod(!tags.isEmpty()));

        JavaFile.builder(keysPackage, keysClass.build())
                .indent("    ")
                .skipJavaLangImports(true)
                .build()
                .writeTo(outputDirectory);
    }

    /**
     * Declares one static field per registry entry and returns the identifier &rarr;
     * generated field name association.
     */
    private static Map<String, String> addEntryFields(final TypeSpec.Builder keysClass,
                                                      final RegistryDefinition registry,
                                                      final ParameterizedTypeName typedKeyType) {

        final Map<String, String> fieldNamesByIdentifier = new LinkedHashMap<>();
        final Set<String> generatedFieldNames = new HashSet<>();

        final List<RegistryEntryDefinition> alphabetical = registry.entries().stream()
                .sorted(Comparator.comparing(entry -> GenerationUtils.constantName(entry.identifier())))
                .toList();

        for (final RegistryEntryDefinition entry : alphabetical) {

            final String fieldName = GenerationUtils.constantName(entry.identifier());

            if (!generatedFieldNames.add(fieldName)) {
                throw new IllegalStateException("Multiple registry entries in '" + registry.identifier() + "' produce the Java field name '" + fieldName + "'.");
            }
            fieldNamesByIdentifier.put(entry.identifier(), fieldName);

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
        return fieldNamesByIdentifier;
    }

    /**
     * Builds the {@code VALUES} list (and therefore the order exposed by
     * {@code values()}) in strict ascending {@code protocol_id} order.
     */
    private static FieldSpec createValuesField(final RegistryDefinition registry,
                                               final Map<String, String> fieldNamesByIdentifier,
                                               final ParameterizedTypeName typedKeyType) {

        final List<String> protocolOrderedFieldNames = registry.entries().stream()
                .sorted(Comparator.comparingInt(RegistryEntryDefinition::protocolId))
                .map(entry -> fieldNamesByIdentifier.get(entry.identifier()))
                .toList();

        final ClassName listClass = ClassName.get(List.class);
        final ParameterizedTypeName listType = ParameterizedTypeName.get(listClass, typedKeyType);
        final CodeBlock.Builder initializer = CodeBlock.builder();

        initializer.add("$T.of(", listClass);

        if (!protocolOrderedFieldNames.isEmpty()) {

            initializer.add("\n");

            for (int index = 0; index < protocolOrderedFieldNames.size(); index++) {
                initializer.add("    $N", protocolOrderedFieldNames.get(index));

                if (index < protocolOrderedFieldNames.size() - 1) {
                    initializer.add(",");
                }

                initializer.add("\n");
            }
        }

        initializer.add(")");

        return FieldSpec.builder(listType, "VALUES", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer(initializer.build())
                .addJavadoc("Entries in ascending {@code protocol_id} order - list index == network ID.\n")
                .build();
    }

    /**
     * Builds the {@code TAGS} field: an immutable {@code Map<Key, List<Key>>} from
     * namespaced tag identifier to the tag's flattened member entries.
     *
     * <p>Only called when {@code tags} is non-empty - see {@link #generate}.</p>
     */
    private static FieldSpec createTagsField(final List<RegistryTagDefinition> tags) {

        final CodeBlock.Builder initializer = CodeBlock.builder().add("$T.ofEntries(", Map.class);

        if (!tags.isEmpty()) {

            initializer.add("\n");

            for (int index = 0; index < tags.size(); index++) {

                final RegistryTagDefinition tag = tags.get(index);
                initializer.add("    $T.entry($L, $L)", Map.class, keyInitializer(tag.identifier()), keyListInitializer(tag.entries()));

                if (index < tags.size() - 1) {
                    initializer.add(",");
                }

                initializer.add("\n");
            }
        }

        initializer.add(")");

        return FieldSpec.builder(tagsMapType(), "TAGS", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer(initializer.build())
                .addJavadoc("Namespaced tag identifier to flattened member entries.\n")
                .build();
    }

    private static CodeBlock keyListInitializer(final List<String> identifiers) {

        final CodeBlock.Builder listInitializer = CodeBlock.builder().add("$T.of(", List.class);

        for (int index = 0; index < identifiers.size(); index++) {

            listInitializer.add(keyInitializer(identifiers.get(index)));

            if (index < identifiers.size() - 1) {
                listInitializer.add(", ");
            }
        }

        listInitializer.add(")");
        return listInitializer.build();
    }

    private static CodeBlock keyInitializer(final String identifier) {

        final int separator = identifier.indexOf(':');

        if (separator < 0) {
            return CodeBlock.of("$T.key($S)", Key.class, identifier);
        }

        final String namespace = identifier.substring(0, separator);
        final String path = identifier.substring(separator + 1);

        if (namespace.equalsIgnoreCase("minecraft")) {
            return CodeBlock.of("$T.key($S)", Key.class, path);
        }

        return CodeBlock.of("$T.key($S, $S)", Key.class, namespace, path);
    }

    private static ParameterizedTypeName tagsMapType() {

        final ParameterizedTypeName listOfKey = ParameterizedTypeName.get(ClassName.get(List.class), ClassName.get(Key.class));
        return ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(Key.class), listOfKey);
    }

    private static MethodSpec createPrivateConstructor(final String className) {

        return MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE)
                .addStatement("throw new $T($S)", UnsupportedOperationException.class, className + " cannot be instantiated.")
                .build();
    }

    private static MethodSpec createFactoryMethod(final RegistryTypeDefinition registryType,
                                                  final ParameterizedTypeName typedKeyType,
                                                  final ClassName registryKey,
                                                  final ClassName typedKey) {

        final String registryFieldName = GenerationUtils.constantName(registryType.path(), true);
        final ParameterSpec valueParameter = ParameterSpec.builder(String.class, "value", Modifier.FINAL)
                .addAnnotation(KEY_PATTERN)
                .build();

        return MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .returns(typedKeyType)
                .addParameter(valueParameter)
                .addStatement("return $T.create($T.$N, $N)", typedKey, registryKey, registryFieldName, "value")
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
     * Generates the {@code tags()} accessor. Always emitted, even for registries with
     * no tags, so callers have a uniform API regardless of what a given Minecraft
     * version happens to define for this registry.
     */
    private static MethodSpec createTagsMethod(final boolean hasTags) {

        final ParameterizedTypeName mapType = tagsMapType();

        final MethodSpec.Builder method = MethodSpec.methodBuilder("tags")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(mapType)
                .addJavadoc("Returns this registry's tags (namespaced tag identifier to member entries).\n\n")
                .addJavadoc("@return an immutable map of tags, or an empty map if this registry defines none\n");

        if (hasTags) {
            method.addStatement("return TAGS");
        } else {
            method.addStatement("return $T.of()", Map.class);
        }

        return method.build();
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
