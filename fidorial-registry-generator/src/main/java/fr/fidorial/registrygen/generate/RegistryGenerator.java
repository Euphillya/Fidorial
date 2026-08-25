package fr.fidorial.registrygen.generate;

import fr.fidorial.registrygen.model.BlockReportDefinition;
import fr.fidorial.registrygen.model.PacketCatalogs;
import fr.fidorial.registrygen.model.PrismarineBlockLightPropertiesDefinition;
import fr.fidorial.registrygen.model.ProtocolIdRegistries;
import fr.fidorial.registrygen.model.ProtocolIdTarget;
import fr.fidorial.registrygen.model.RegistriesHolder;
import fr.fidorial.registrygen.model.RegistryDefinition;
import fr.fidorial.registrygen.model.RegistryTypeDefinition;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Coordinates source generation from Mojang's
 * {@code reports/registries.json} file.
 *
 * @since 0.1.0
 */
public final class RegistryGenerator {

    private final RegistryReportParser parser;
    private final RegistryDataGenerator dataGenerator;
    private final RegistryKeysGenerator keysGenerator;
    private final RegistryKeyGenerator registryKeyGenerator;
    private final RegistryProtocolIdGenerator protocolIdGenerator;
    private final BlockReportParser blockReportParser;
    private final PrismarineBlockReportParser prismarineBlockReportParser;
    private final BlockStateGenerator blockStateGenerator;

    /**
     * Creates a registry generator using the standard parser and
     * JavaPoet generators.
     */
    public RegistryGenerator() {

        this(new RegistryReportParser(),
                new RegistryDataGenerator(),
                new RegistryKeysGenerator(),
                new RegistryKeyGenerator(),
                new RegistryProtocolIdGenerator(),
                new BlockReportParser(),
                new PrismarineBlockReportParser(),
                new BlockStateGenerator());
    }

    /**
     * Creates a registry generator with explicitly supplied components.
     *
     * <p>This constructor is useful for testing or replacing individual
     * generation stages.</p>
     *
     * @param parser              registry report parser
     * @param dataGenerator       marker-interface generator
     * @param keysGenerator       typed registry-entry key generator
     * @param registryKeyGenerator central registry-key generator
     * @param protocolIdGenerator raw protocol ID constant generator
     * @param blockReportParser   blocks report parser
     * @param prismarineBlockReportParser prismarine block report parser
     * @param blockStateGenerator block type registration generator
     */
    public RegistryGenerator(final RegistryReportParser parser,
                             final RegistryDataGenerator dataGenerator,
                             final RegistryKeysGenerator keysGenerator,
                             final RegistryKeyGenerator registryKeyGenerator,
                             final RegistryProtocolIdGenerator protocolIdGenerator,
                             final BlockReportParser blockReportParser,
                             final PrismarineBlockReportParser prismarineBlockReportParser,
                             final BlockStateGenerator blockStateGenerator) {

        this.parser = Objects.requireNonNull(parser, "parser");
        this.dataGenerator = Objects.requireNonNull(dataGenerator, "dataGenerator");
        this.keysGenerator = Objects.requireNonNull(keysGenerator, "keysGenerator");
        this.registryKeyGenerator = Objects.requireNonNull(registryKeyGenerator, "registryKeyGenerator");
        this.protocolIdGenerator = Objects.requireNonNull(protocolIdGenerator, "protocolIdGenerator");
        this.blockReportParser = Objects.requireNonNull(blockReportParser, "blockReportParser");
        this.prismarineBlockReportParser = Objects.requireNonNull(prismarineBlockReportParser, "prismarineBlockReportParser");
        this.blockStateGenerator = Objects.requireNonNull(blockStateGenerator, "blockStateGenerator");
    }

    /**
     * Parses a Mojang registry report and generates all configured
     * Fidorial registry source files.
     *
     * @param registriesJson  path to Mojang's {@code registries.json}
     * @param outputDirectory generated Java source root
     * @param registryTypes   the registries to generate
     * @param dataPackage the subpackage for generated registry marker interfaces
     * @param keysPackage the subpackage for generated registry keys
     * @param registryPackage the package for generated registries
     * @param generateRegistryKey whether to generate the {@code RegistryKey} class
     *
     * @throws IOException if parsing or source generation fails
     */
    public void generate(final Path registriesJson,
                         final Path outputDirectory,
                         final List<RegistryTypeDefinition> registryTypes,
                         final String registryPackage,
                         final String dataPackage,
                         final String keysPackage,
                         final boolean generateRegistryKey) throws IOException {

        Objects.requireNonNull(registriesJson, "registriesJson");
        Objects.requireNonNull(outputDirectory, "outputDirectory");
        Objects.requireNonNull(registryTypes, "registryTypes");
        Objects.requireNonNull(registryPackage, "registryPackage");
        Objects.requireNonNull(dataPackage, "dataPackage");
        Objects.requireNonNull(keysPackage, "keysPackage");

        validateInput(registriesJson);

        Files.createDirectories(outputDirectory);

        final RegistriesHolder registries = parser.parse(registriesJson);

        for (final RegistryTypeDefinition registryType : registryTypes) {

            final Optional<RegistryDefinition> registryDefinition = registries.registry(registryType.identifier());
            if (registryDefinition.isEmpty()) {
                System.out.println("Registry missing from report: " + registryType.identifier());
                continue;
            }

            /*
             * Registries that only ever travel over the wire as a VarInt are
             * emitted as plain int constants instead of typed keys.
             */
            final Optional<ProtocolIdTarget> protocolIdTarget =
                    ProtocolIdRegistries.byIdentifier(registryType.identifier());

            if (protocolIdTarget.isPresent()) {
                protocolIdGenerator.generate(registryDefinition.get(), protocolIdTarget.get(), outputDirectory);
                continue;
            }

            dataGenerator.generate(registryType, registryType.dataPackage(dataPackage), outputDirectory);
            keysGenerator.generate(registryType, registryDefinition.get(),
                    registryPackage, registryType.dataPackage(dataPackage), registryType.keysPackage(keysPackage),
                    outputDirectory);
        }

        /*
         * Protocol-ID-only registries have no marker type in the data package,
         * so they must not appear in RegistryKey.
         */
        final List<RegistryTypeDefinition> keyedRegistryTypes = registryTypes.stream()
                .filter(registryType -> ProtocolIdRegistries.byIdentifier(registryType.identifier()).isEmpty())
                .toList();

        if (!generateRegistryKey) {
            return;
        }

        registryKeyGenerator.generate(keyedRegistryTypes, registryPackage, dataPackage, outputDirectory);
    }

    /**
     * Parses a Mojang packets report and generates packet identifier
     * catalog classes.
     *
     * @param packetsJson     path to {@code packets.json}
     * @param outputDirectory generated Java source root
     *
     * @throws IOException if parsing or source generation fails
     */
    public void generatePackets(final Path packetsJson, final Path outputDirectory) throws IOException {

        final List<RegistryDefinition> packetCatalogs = new PacketReportParser().parse(packetsJson);

        for (final RegistryDefinition catalog : packetCatalogs) {
            final List<ProtocolIdTarget> targets = PacketCatalogs.byIdentifier(catalog.identifier());
            if (targets.isEmpty()) {
                System.out.println("No PacketCatalogs target configured for: " + catalog.identifier());
                continue;
            }
            for (final ProtocolIdTarget target : targets) {
                protocolIdGenerator.generate(catalog, target, outputDirectory);
            }
        }
    }

    /**
     * Parses a Mojang blocks report (and, optionally, a Prismarine {@code minecraft-data} blocks
     * report for light emission/opacity) and generates {@code BlockStateIds}/{@code BlockStateProperties}
     * (and, when Prismarine data is supplied, {@code BlockStateLightProperties}).
     *
     * @param blocksJson           path to Mojang's {@code blocks.json}
     * @param prismarineBlocksJson path to Prismarine's {@code blocks.json}, or {@code null} to skip lighting
     * @param outputDirectory      generated Java source root
     * @param generatedPackage     root package
     * @param registryDataPackage  package for {@code BlockStateIds}/{@code BlockStateProperties}/
     *                             {@code BlockStateLightProperties}
     * @param registryKeysPackage  package holding the typed {@code BlockType} keys class (e.g. {@code BlockTypeKeys})
     *
     * @throws IOException if parsing or source generation fails
     */
    public void generateBlockStates(final Path blocksJson,
                                    final Path prismarineBlocksJson,
                                    final Path outputDirectory,
                                    final String generatedPackage,
                                    final String registryDataPackage,
                                    final String registryKeysPackage) throws IOException {

        final List<BlockReportDefinition> blocks = blockReportParser.parse(blocksJson);

        final Map<String, PrismarineBlockLightPropertiesDefinition> lighting = (prismarineBlocksJson != null)
                ? prismarineBlockReportParser.parse(prismarineBlocksJson)
                : Map.of();

        blockStateGenerator.generate(blocks, lighting, generatedPackage, registryDataPackage, registryKeysPackage, outputDirectory);
    }

    /**
     * Verifies that the registry report exists and can be read.
     */
    private static void validateInput(final Path registriesJson) throws IOException {

        if (!Files.exists(registriesJson)) {
            throw new IOException("Mojang registry report does not exist: " + registriesJson);
        }

        if (!Files.isRegularFile(registriesJson)) {
            throw new IOException("Mojang registry report is not a regular file: " + registriesJson);
        }

        if (!Files.isReadable(registriesJson)) {
            throw new IOException("Mojang registry report is not readable: " + registriesJson);
        }
    }
}
