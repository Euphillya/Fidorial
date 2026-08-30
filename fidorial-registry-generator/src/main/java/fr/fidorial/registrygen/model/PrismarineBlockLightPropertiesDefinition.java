package fr.fidorial.registrygen.model;

/**
 * Per-block lighting data sourced from PrismarineJS's {@code minecraft-data} blocks report.
 *
 * <p>Mojang's own reports don't expose light emission or opacity, so this is sourced from an
 * independent, community-maintained project instead.</p>
 *
 * @param name        unnamespaced block identifier, e.g. {@code "oak_log"}
 * @param emitLight   light level emitted by the block, {@code 0-15}
 * @param filterLight light removed by the block as it passes through it, {@code 0-15} (opacity)
 * @since 0.1.0
 */
public record PrismarineBlockLightPropertiesDefinition(String name, int emitLight, int filterLight) {
}
