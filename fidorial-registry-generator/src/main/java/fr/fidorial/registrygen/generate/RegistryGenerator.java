package fr.fidorial.registrygen.generate;

import fr.fidorial.registrygen.model.RegistriesHolder;
import fr.fidorial.registrygen.model.RegistryDefinition;
import fr.fidorial.registrygen.model.RegistryTypeDefinition;
import fr.fidorial.registrygen.model.SupportedRegistries;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    /**
     * Creates a registry generator using the standard parser and
     * JavaPoet generators.
     */
    public RegistryGenerator() {

        this(new RegistryReportParser(),
             new RegistryDataGenerator(),
             new RegistryKeysGenerator(),
             new RegistryKeyGenerator());
    }

    /**
     * Creates a registry generator with explicitly supplied components.
     *
     * <p>This constructor is useful for testing or replacing individual
     * generation stages.</p>
     *
     * @param parser               registry report parser
     * @param dataGenerator        marker-interface generator
     * @param keysGenerator        typed registry-entry key generator
     * @param registryKeyGenerator central registry-key generator
     */
    public RegistryGenerator(final RegistryReportParser parser,
                             final RegistryDataGenerator dataGenerator,
                             final RegistryKeysGenerator keysGenerator,
                             final RegistryKeyGenerator registryKeyGenerator) {

        this.parser = Objects.requireNonNull(parser, "parser");
        this.dataGenerator = Objects.requireNonNull(dataGenerator, "dataGenerator");
        this.keysGenerator = Objects.requireNonNull(keysGenerator, "keysGenerator");
        this.registryKeyGenerator = Objects.requireNonNull(registryKeyGenerator, "registryKeyGenerator");
    }

    /**
     * Parses a Mojang registry report and generates all configured
     * Fidorial registry source files.
     *
     * @param registriesJson  path to Mojang's {@code registries.json}
     * @param outputDirectory generated Java source root
     *
     * @throws IOException if parsing or source generation fails
     */
    public void generate(final Path registriesJson, final Path outputDirectory) throws IOException {

        Objects.requireNonNull(registriesJson, "registriesJson");
        Objects.requireNonNull(outputDirectory, "outputDirectory");

        validateInput(registriesJson);

        Files.createDirectories(outputDirectory);

        final RegistriesHolder registries = parser.parse(registriesJson);

        for (final RegistryTypeDefinition registryType : SupportedRegistries.ALL) {

            final Optional<RegistryDefinition> registryDefinition = registries.registry(registryType.identifier());
            if (registryDefinition.isEmpty()) {
                System.out.println("Registry missing from report: " + registryType.identifier());
                continue;
            }


            dataGenerator.generate(registryType, outputDirectory);
            keysGenerator.generate(registryType, registryDefinition.get(), outputDirectory);
        }

        registryKeyGenerator.generate(SupportedRegistries.ALL, outputDirectory);
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