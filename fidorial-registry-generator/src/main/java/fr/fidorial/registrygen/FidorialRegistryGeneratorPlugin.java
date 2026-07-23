package fr.fidorial.registrygen;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;

/**
 * Configuration exposed by the plugin.
 *
 * @since 0.1.0
 */
public abstract class FidorialRegistryGeneratorPlugin {

    /**
     * Minecraft version to resolve from Mojang's official version manifest.
     */
    public abstract Property<String> getMinecraftVersion();

    /**
     * Persistent workspace root. Defaults to {@code build/working}.
     */
    public abstract DirectoryProperty getWorkingDirectory();

    /**
     * Final generated Java source root.
     */
    public abstract DirectoryProperty getGeneratedSourcesDirectory();

    /**
     * Package used for generated classes.
     */
    public abstract Property<String> getGeneratedPackage();

    /**
     * Registry identifier to generated class name.
     * Example: {@code minecraft:entity_type -> EntityTypes}.
     */
    public abstract MapProperty<String, String> getRegistries();

    /**
     * Additional arguments passed to Mojang's data generator.
     */
    public abstract ListProperty<String> getDataGeneratorArguments();
}