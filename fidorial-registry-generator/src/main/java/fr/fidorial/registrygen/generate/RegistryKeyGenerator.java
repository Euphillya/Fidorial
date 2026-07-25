package fr.fidorial.registrygen.generate;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.ParameterizedTypeName;
import com.palantir.javapoet.TypeSpec;
import com.palantir.javapoet.TypeVariableName;
import fr.fidorial.registrygen.GenerationUtils;
import fr.fidorial.registrygen.model.RegistryTypeDefinition;
import net.kyori.adventure.key.Key;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 * Generates the central {@code RegistryKey} record.
 *
 * @since 0.1.0
 */
public final class RegistryKeyGenerator {

    public static final String REGISTRY_PACKAGE = "fr.fidorial.registry";
    public static final String DATA_PACKAGE = "fr.fidorial.registry.data";

    private static final ClassName KEY = ClassName.get("net.kyori.adventure.key", "Key");
    private static final ClassName KEY_PATTERN = ClassName.get("net.kyori.adventure.key", "KeyPattern");
    private static final ClassName REGISTRY_KEY = ClassName.get(REGISTRY_PACKAGE, "RegistryKey");
    private static final ClassName ENTITY_TYPE = ClassName.get("fr.fidorial.entity", "EntityType");

    private static final ParameterizedTypeName ENTITY_TYPE_REGISTRY_KEY = ParameterizedTypeName.get(REGISTRY_KEY, ENTITY_TYPE);

    /**
     * Generates {@code RegistryKey.java}.
     *
     * @param registryTypes configured supported registries
     * @param outputDirectory generated source root
     *
     * @throws IOException if the generated file cannot be written
     */
    public void generate(final List<RegistryTypeDefinition> registryTypes, final Path outputDirectory) throws IOException {

        Objects.requireNonNull(registryTypes, "registryTypes");
        Objects.requireNonNull(outputDirectory, "outputDirectory");

        final MethodSpec constructor = MethodSpec.compactConstructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Key.class, "key")
                .addStatement("$T.requireNonNull(key, $S)", Objects.class, "key")
                .build();
        final TypeVariableName typeVariable = TypeVariableName.get("T");
        final TypeSpec.Builder registryKey = TypeSpec
                .recordBuilder("RegistryKey")
                .recordConstructor(constructor)
                .addModifiers(Modifier.PUBLIC)
                .addTypeVariable(typeVariable)
                .addJavadoc("Identifies a Minecraft registry.\n\n")
                .addJavadoc("@param <T> marker type for entries contained by the registry\n")
                .addJavadoc("@param key namespaced registry identifier\n");

        addRegistryFields(registryKey, registryTypes);
        registryKey.addField(createEntityTypeRegistryKey());
        registryKey.addMethod(createFactoryMethod());
        registryKey.addMethod(createFactoryKeyMethod());
        registryKey.addMethod(createToStringMethod());

        JavaFile.builder(REGISTRY_PACKAGE, registryKey.build()).indent("    ").skipJavaLangImports(true).build().writeTo(outputDirectory);
    }

    private static void addRegistryFields(final TypeSpec.Builder registryKey, final List<RegistryTypeDefinition> registryTypes) {

        for (final RegistryTypeDefinition registryType : registryTypes) {

            final ClassName markerType = ClassName.get(DATA_PACKAGE, registryType.typeName());
            final ParameterizedTypeName fieldType = ParameterizedTypeName.get(REGISTRY_KEY, markerType);


            final String fieldName = GenerationUtils.constantName(registryType.path(), true);
            registryKey.addField(FieldSpec.builder(fieldType, fieldName, Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                                         .initializer("of($S)", registryType.path())
                                         .addJavadoc("Registry key for {@code $L}.\n", registryType.identifier())
                                         .build());
        }
    }

    private static FieldSpec createEntityTypeRegistryKey() {

        return FieldSpec.builder(ENTITY_TYPE_REGISTRY_KEY,
                                 "ENTITY_TYPE",
                                 Modifier.PUBLIC,
                                 Modifier.STATIC,
                                 Modifier.FINAL)
                .initializer("new $T<>($T.key($S))", REGISTRY_KEY, Key.class, "entity_type")
                .addJavadoc("Registry key for {@code minecraft:entity_type}.\n")
                .build();
    }

    /**
     * Generates the record compact constructor:
     *
     * <pre>{@code
     * public RegistryKey {
     *     Objects.requireNonNull(key, "key");
     * }
     * }</pre>
     */
    private static MethodSpec createCompactConstructor() {

        return MethodSpec.compactConstructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("$T.requireNonNull($N, $S)", Objects.class, "key", "key")
                .build();
    }

    /**
     * Generates:
     *
     * <pre>{@code
     * private static <T> RegistryKey<T> of(
     *         @KeyPattern final String path
     * ) {
     *     return new RegistryKey<>(Key.key(path));
     * }
     * }</pre>
     */
    private static MethodSpec createFactoryMethod() {

        final TypeVariableName typeVariable = TypeVariableName.get("T");
        final ParameterizedTypeName returnType = ParameterizedTypeName.get(REGISTRY_KEY, typeVariable);
        final ParameterSpec pathParameter = ParameterSpec.builder(String.class, "path", Modifier.FINAL).addAnnotation(KEY_PATTERN).build();

        return MethodSpec.methodBuilder("of")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addTypeVariable(typeVariable)
                .returns(returnType)
                .addParameter(pathParameter)
                .addStatement("return new $T<>($T.key($N))", REGISTRY_KEY, KEY, "path")
                .build();
    }

    private static MethodSpec createFactoryKeyMethod() {

        final ClassName apiStatus = ClassName.get("org.jetbrains.annotations", "NotNull");

        final TypeVariableName typeVariable = TypeVariableName.get("T");
        final ParameterizedTypeName returnType = ParameterizedTypeName.get(REGISTRY_KEY, typeVariable);
        final ParameterSpec pathParameter = ParameterSpec.builder(Key.class, "key", Modifier.FINAL).addAnnotation(apiStatus).build();

        return MethodSpec.methodBuilder("of")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addTypeVariable(typeVariable)
                .returns(returnType)
                .addParameter(pathParameter)
                .addStatement("return new $T<>($N)", REGISTRY_KEY, "key")
                .build();
    }

    private static MethodSpec createToStringMethod() {

        return MethodSpec.methodBuilder("toString")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addStatement("return $S + $N + $S", "RegistryKey[", "key", "]")
                .build();
    }
}