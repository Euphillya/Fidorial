package fr.fidorial.registrygen.generate;

import com.palantir.javapoet.ArrayTypeName;
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.ParameterizedTypeName;
import com.palantir.javapoet.TypeSpec;
import fr.fidorial.registrygen.GenerationUtils;
import fr.fidorial.registrygen.model.BlockPropertyDefinition;
import fr.fidorial.registrygen.model.BlockReportDefinition;
import fr.fidorial.registrygen.model.SupportedRegistries;
import net.kyori.adventure.key.Key;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Generates block state files from Mojang's {@code reports/blocks.json}.
 *
 * @since 0.1.0
 */
public final class BlockStateGenerator {

    private static final String NETWORK_PACKAGE = "fr.euphyllia.fidorial.server.registry.data";
    private static final String CHUNK_PACKAGE = "fr.euphyllia.fidorial.server.world.chunk";
    private static final String PROTOCOL_IDS_CLASS_NAME = "BlockStateIds";
    private static final String PROPERTIES_CLASS_NAME = "BlockStateProperties";

    private static final int NETWORK_BLOCKS_PER_METHOD = 150; // 64kb limit
    private static final int STATES_PER_FILL_METHOD = 50;

    private static final ClassName KEY = ClassName.get(Key.class);
    private static final ClassName MAP = ClassName.get(Map.class);
    private static final ClassName HASH_MAP = ClassName.get(HashMap.class);
    private static final ClassName LIST = ClassName.get(List.class);

    private static final ClassName BLOCK_TYPE = ClassName.get("fr.fidorial.world.block", "BlockType");
    private static final ClassName BLOCK_PROPERTY = ClassName.get("fr.fidorial.world.block", "BlockProperty");
    private static final ClassName BLOCK_REGISTRY = ClassName.get("fr.fidorial.world.block", "BlockRegistry");
    private static final ClassName BLOCK_STATE = ClassName.get(CHUNK_PACKAGE, "BlockState");

    private static final ClassName BLOCK_TYPE_KEYS =
            ClassName.get(RegistryKeysGenerator.KEYS_PACKAGE, SupportedRegistries.BLOCK.keysClassName());

    private static final ParameterizedTypeName STATES_BY_KEY_TYPE =
            ParameterizedTypeName.get(MAP, KEY, ArrayTypeName.of(BLOCK_STATE));
    private static final ParameterizedTypeName DEFAULT_STATE_BY_KEY_TYPE =
            ParameterizedTypeName.get(MAP, KEY, BLOCK_STATE);

    /**
     * Generates both block state classes.
     *
     * @param blocks          parsed Mojang block definitions
     * @param outputDirectory generated Java source root
     *
     * @throws IOException if either generated file cannot be written
     */
    public void generate(final List<BlockReportDefinition> blocks, final Path outputDirectory) throws IOException {

        Objects.requireNonNull(blocks, "blocks");
        Objects.requireNonNull(outputDirectory, "outputDirectory");

        generateProtocolIds(blocks, outputDirectory);
        generateProperties(blocks, outputDirectory);
    }

    /**
     * Generates {@code BlockStateIds}, registering every block type and its
     * full network state table with a {@code BlockRegistry}.
     *
     * @param blocks          parsed Mojang block definitions
     * @param outputDirectory generated Java source root
     *
     * @throws IOException if the source file cannot be written
     */
    private void generateProtocolIds(final List<BlockReportDefinition> blocks, final Path outputDirectory) throws IOException {

        final TypeSpec.Builder protocolIds = TypeSpec.classBuilder(PROTOCOL_IDS_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addJavadoc("Registers every block type and its full network state table.\n\n")
                .addJavadoc("<p>Generated from Mojang's registry report; do not edit.</p>\n")
                .addMethod(createPrivateConstructor(PROTOCOL_IDS_CLASS_NAME));

        addProtocolIdRegistrationMethods(protocolIds, blocks);

        JavaFile.builder(NETWORK_PACKAGE, protocolIds.build()).indent("    ").skipJavaLangImports(true).build().writeTo(outputDirectory);
    }

    private static void addProtocolIdRegistrationMethods(final TypeSpec.Builder protocolIds,
                                                         final List<BlockReportDefinition> blocks) {

        final ParameterSpec registryParameter = ParameterSpec.builder(BLOCK_REGISTRY, "registry", Modifier.FINAL).build();

        final MethodSpec.Builder registerAll = MethodSpec.methodBuilder("registerAll")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(registryParameter)
                .addJavadoc("Registers every generated block type with the given registry.\n\n")
                .addJavadoc("@param registry the registry to populate\n");

        int chunkIndex = 0;
        for (int start = 0; start < blocks.size(); start += NETWORK_BLOCKS_PER_METHOD) {

            final int end = Math.min(start + NETWORK_BLOCKS_PER_METHOD, blocks.size());
            final String chunkMethodName = "register" + chunkIndex;

            protocolIds.addMethod(createRegistrationChunkMethod(chunkMethodName, registryParameter, blocks.subList(start, end)));
            registerAll.addStatement("$N($N)", chunkMethodName, registryParameter);

            chunkIndex++;
        }

        protocolIds.addMethod(registerAll.build());
    }

    private static MethodSpec createRegistrationChunkMethod(final String methodName,
                                                            final ParameterSpec registryParameter,
                                                            final List<BlockReportDefinition> blocks) {

        final MethodSpec.Builder chunkMethod = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .addParameter(registryParameter);

        for (final BlockReportDefinition block : blocks) {
            chunkMethod.addStatement("$N.register($L)", registryParameter, createBlockTypeInitializer(block));
        }

        return chunkMethod.build();
    }

    private static CodeBlock createBlockTypeInitializer(final BlockReportDefinition block) {

        return CodeBlock.of("$T.of($T.$N.key(), $L, $L, $L)",
                BLOCK_TYPE,
                BLOCK_TYPE_KEYS,
                keysFieldName(block.identifier()),
                createPropertiesInitializer(block.properties()),
                createStateIdsInitializer(block.stateIdsInOrder()),
                block.defaultOrdinal());
    }

    /**
     * Generates {@code BlockStateProperties}, registering every real block-state
     * permutation as a runtime chunk {@code BlockState}.
     *
     * @param blocks          parsed Mojang block definitions
     * @param outputDirectory generated Java source root
     *
     * @throws IOException if the source file cannot be written
     */
    private void generateProperties(final List<BlockReportDefinition> blocks, final Path outputDirectory) throws IOException {

        final TypeSpec.Builder properties = TypeSpec.classBuilder(PROPERTIES_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addJavadoc("Registers every real block-state permutation as a runtime {@code BlockState}.\n\n")
                .addJavadoc("<p>Generated from Mojang's blocks report; do not edit.</p>\n")
                .addField(createStateMapField("BY_KEY", STATES_BY_KEY_TYPE))
                .addField(createStateMapField("DEFAULT", DEFAULT_STATE_BY_KEY_TYPE))
                .addMethod(createPrivateConstructor(PROPERTIES_CLASS_NAME))
                .addMethod(createRegisterBlockHelper());

        final CodeBlock bootstrapBody = addPropertyRegistrationMethods(properties, blocks);

        properties.addMethod(createStatesOfMethod());
        properties.addMethod(createStateAtMethod());
        properties.addMethod(createDefaultStateOfMethod());
        properties.addMethod(createBootstrapMethod(bootstrapBody));

        JavaFile.builder(CHUNK_PACKAGE, properties.build()).indent("    ").skipJavaLangImports(true).build().writeTo(outputDirectory);
    }

    private static FieldSpec createStateMapField(final String name, final ParameterizedTypeName fieldType) {

        return FieldSpec.builder(fieldType, name, Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer("new $T<>()", HASH_MAP)
                .build();
    }

    private static MethodSpec createBootstrapMethod(final CodeBlock body) {
        return MethodSpec.methodBuilder("bootstrap")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addCode(body)
                .build();
    }

    private static CodeBlock addPropertyRegistrationMethods(final TypeSpec.Builder properties, final List<BlockReportDefinition> blocks) {

        final CodeBlock.Builder bootstrapInit = CodeBlock.builder();
        final Set<String> usedNames = new HashSet<>();

        for (final BlockReportDefinition block : blocks) {
            final String registerMethodName = uniqueMethodName("register" + GenerationUtils.className(block.identifier()), usedNames);
            createPropertyRegisterMethods(properties, registerMethodName, block);
            bootstrapInit.addStatement("$N()", registerMethodName);
        }

        return bootstrapInit.build();
    }

    private static void createPropertyRegisterMethods(final TypeSpec.Builder properties,
                                                      final String registerMethodName,
                                                      final BlockReportDefinition block) {

        final List<Map<String, String>> statePropertiesInOrder = block.statePropertiesInOrder();
        final String fieldName = keysFieldName(block.identifier());

        final MethodSpec.Builder registerMethod = MethodSpec.methodBuilder(registerMethodName)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC);

        if (statePropertiesInOrder.size() <= STATES_PER_FILL_METHOD) {
            registerMethod.addStatement("registerBlock($T.$N.key(), $L, $L)",
                    BLOCK_TYPE_KEYS, fieldName, block.defaultOrdinal(),
                    createStatesArrayInitializer(block));
            properties.addMethod(registerMethod.build());
            return;
        }

        // too many states for one method
        registerMethod.addStatement("final $T[] states = new $T[$L]",
                BLOCK_STATE, BLOCK_STATE, statePropertiesInOrder.size());

        int fillIndex = 0;
        for (int start = 0; start < statePropertiesInOrder.size(); start += STATES_PER_FILL_METHOD) {

            final int end = Math.min(start + STATES_PER_FILL_METHOD, statePropertiesInOrder.size());
            final String fillMethodName = registerMethodName + "Fill" + fillIndex;

            properties.addMethod(createFillMethod(fillMethodName, block, start, end));
            registerMethod.addStatement("$N(states)", fillMethodName);

            fillIndex++;
        }

        registerMethod.addStatement("registerBlock($T.$N.key(), $L, states)",
                BLOCK_TYPE_KEYS, fieldName, block.defaultOrdinal());

        properties.addMethod(registerMethod.build());
    }

    private static MethodSpec createFillMethod(final String methodName, final BlockReportDefinition block,
                                               final int start, final int end) {

        final List<Map<String, String>> statePropertiesInOrder = block.statePropertiesInOrder();
        final List<BlockPropertyDefinition> orderedProperties = block.properties();
        final String fieldName = keysFieldName(block.identifier());

        final ParameterSpec statesParameter =
                ParameterSpec.builder(ArrayTypeName.of(BLOCK_STATE), "states", Modifier.FINAL).build();

        final MethodSpec.Builder fillMethod = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .addParameter(statesParameter);

        for (int index = start; index < end; index++) {
            fillMethod.addStatement("states[$L] = $T.of($T.$N.key(), $L)",
                    index, BLOCK_STATE, BLOCK_TYPE_KEYS, fieldName,
                    createPropertiesMapInitializer(statePropertiesInOrder.get(index), orderedProperties));
        }

        return fillMethod.build();
    }

    private static MethodSpec createRegisterBlockHelper() {

        final ParameterSpec keyParameter = ParameterSpec.builder(KEY, "key", Modifier.FINAL).build();
        final ParameterSpec defaultOrdinalParameter = ParameterSpec.builder(int.class, "defaultOrdinal", Modifier.FINAL).build();
        final ParameterSpec statesParameter = ParameterSpec.builder(ArrayTypeName.of(BLOCK_STATE), "states", Modifier.FINAL).build();

        return MethodSpec.methodBuilder("registerBlock")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .addParameter(keyParameter)
                .addParameter(defaultOrdinalParameter)
                .addParameter(statesParameter)
                .addStatement("BY_KEY.put($N, $N)", "key", "states")
                .addStatement("DEFAULT.put($N, $N[$N])", "key", "states", "defaultOrdinal")
                .build();
    }

    private static CodeBlock createStatesArrayInitializer(final BlockReportDefinition block) {

        final List<Map<String, String>> statePropertiesInOrder = block.statePropertiesInOrder();
        final List<BlockPropertyDefinition> orderedProperties = block.properties();
        final String fieldName = keysFieldName(block.identifier());
        if (statePropertiesInOrder.size() == 1) {
            return CodeBlock.of("new $T[] { $T.of($T.$N.key(), $L) }",
                    BLOCK_STATE,
                    BLOCK_STATE,
                    BLOCK_TYPE_KEYS,
                    fieldName,
                    createPropertiesMapInitializer(statePropertiesInOrder.get(0), orderedProperties));
        }

        final CodeBlock.Builder initializer = CodeBlock.builder().add("new $T[] {\n", BLOCK_STATE).indent();

        for (int index = 0; index < statePropertiesInOrder.size(); index++) {

            initializer.add("$T.of($T.$N.key(), $L)",
                    BLOCK_STATE,
                    BLOCK_TYPE_KEYS,
                    fieldName,
                    createPropertiesMapInitializer(statePropertiesInOrder.get(index), orderedProperties));

            initializer.add(index < statePropertiesInOrder.size() - 1 ? ",\n" : "\n");
        }

        return initializer.unindent().add("}").build();
    }

    private static CodeBlock createPropertiesMapInitializer(final Map<String, String> properties,
                                                            final List<BlockPropertyDefinition> orderedProperties) {

        if (properties.isEmpty()) {
            return CodeBlock.of("$T.of()", MAP);
        }

        final CodeBlock.Builder initializer = CodeBlock.builder().add("$T.ofEntries(", MAP);

        for (int index = 0; index < orderedProperties.size(); index++) {

            final String name = orderedProperties.get(index).name();
            initializer.add("$T.entry($S, $S)", MAP, name, properties.get(name));

            if (index < orderedProperties.size() - 1) {
                initializer.add(", ");
            }
        }

        return initializer.add(")").build();
    }

    private static MethodSpec createStatesOfMethod() {

        final ParameterSpec keyParameter = ParameterSpec.builder(KEY, "key", Modifier.FINAL).build();

        return MethodSpec.methodBuilder("statesOf")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ArrayTypeName.of(BLOCK_STATE))
                .addParameter(keyParameter)
                .addJavadoc("Returns every state permutation for a block, in ordinal order.\n\n")
                .addJavadoc("@param key namespaced block identifier\n")
                .addJavadoc("@return the block's states, or {@code null} if unknown\n")
                .addStatement("return BY_KEY.get($N)", "key")
                .build();
    }

    private static MethodSpec createStateAtMethod() {

        final ParameterSpec keyParameter = ParameterSpec.builder(KEY, "key", Modifier.FINAL).build();
        final ParameterSpec ordinalParameter = ParameterSpec.builder(int.class, "ordinal", Modifier.FINAL).build();

        return MethodSpec.methodBuilder("stateAt")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(BLOCK_STATE)
                .addParameter(keyParameter)
                .addParameter(ordinalParameter)
                .addJavadoc("Returns the state at a specific ordinal for a block.\n\n")
                .addJavadoc("@param key     namespaced block identifier\n")
                .addJavadoc("@param ordinal state ordinal, see {@code BlockType}\n")
                .addJavadoc("@return the matching state\n")
                .addStatement("return BY_KEY.get($N)[$N]", "key", "ordinal")
                .build();
    }

    private static MethodSpec createDefaultStateOfMethod() {

        final ParameterSpec keyParameter = ParameterSpec.builder(KEY, "key", Modifier.FINAL).build();

        return MethodSpec.methodBuilder("defaultStateOf")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(BLOCK_STATE)
                .addParameter(keyParameter)
                .addJavadoc("Returns the default state for a block.\n\n")
                .addJavadoc("@param key namespaced block identifier\n")
                .addJavadoc("@return the default state, or {@code null} if unknown\n")
                .addStatement("return DEFAULT.get($N)", "key")
                .build();
    }

    private static String keysFieldName(final String identifier) {
        return GenerationUtils.constantName(identifier);
    }

    private static String uniqueMethodName(final String candidate, final Set<String> usedNames) {

        String name = candidate;
        int suffix = 2;

        while (!usedNames.add(name)) {
            name = candidate + suffix;
            suffix++;
        }

        return name;
    }

    private static MethodSpec createPrivateConstructor(final String className) {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addStatement("throw new $T($S)", UnsupportedOperationException.class, className + " cannot be instantiated.")
                .build();
    }

    private static CodeBlock createPropertiesInitializer(final List<BlockPropertyDefinition> properties) {

        if (properties.isEmpty()) {
            return CodeBlock.of("$T.of()", LIST);
        }

        final CodeBlock.Builder initializer = CodeBlock.builder().add("$T.of(\n", LIST).indent();

        for (int index = 0; index < properties.size(); index++) {

            final BlockPropertyDefinition property = properties.get(index);
            initializer.add("new $T($S, $L)", BLOCK_PROPERTY, property.name(), createValuesInitializer(property.values()));

            initializer.add(index < properties.size() - 1 ? ",\n" : "\n");
        }

        return initializer.unindent().add(")").build();
    }

    private static CodeBlock createValuesInitializer(final List<String> values) {

        final CodeBlock.Builder initializer = CodeBlock.builder().add("$T.of(", LIST);

        for (int index = 0; index < values.size(); index++) {
            initializer.add("$S", values.get(index));
            if (index < values.size() - 1) {
                initializer.add(", ");
            }
        }

        return initializer.add(")").build();
    }

    private static CodeBlock createStateIdsInitializer(final int[] stateIds) {

        final CodeBlock.Builder initializer = CodeBlock.builder().add("new int[] {");

        for (int index = 0; index < stateIds.length; index++) {
            initializer.add("$L", stateIds[index]);
            if (index < stateIds.length - 1) {
                initializer.add(", ");
            }
        }

        return initializer.add("}").build();
    }
}
