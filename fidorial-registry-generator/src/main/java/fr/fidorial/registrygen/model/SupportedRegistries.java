package fr.fidorial.registrygen.model;

import java.util.List;

/**
 * Represents a collection of supported registries used within a Minecraft-related
 * registry system. Each registry is defined through a {@link RegistryTypeDefinition}.
 *
 * This class provides a predefined list of registry types that are supported by
 * the system. Each registry is described using:
 * - An identifier: The unique string identifier for the registry.
 * - A type name: The descriptive name of the registry type.
 * - A keys class name: A derived name based on the type name, used for key management.
 *
 * @since 0.1.0
 */
public final class SupportedRegistries {

  public static final List<RegistryTypeDefinition> ALL = List.of(
          registry("minecraft:attribute", "Attribute"),
          registry("minecraft:banner_pattern", "BannerPattern"),
          registry("minecraft:worldgen/biome", "Biome"),
          registry("minecraft:cat_sound_variant", "CatSoundVariant"),
          registry("minecraft:cat_variant", "CatVariant"),
          registry("minecraft:chat_type", "ChatType"),
          registry("minecraft:chicken_sound_variant", "ChickenSoundVariant"),
          registry("minecraft:chicken_variant", "ChickenVariant"),
          registry("minecraft:cow_sound_variant", "CowSoundVariant"),
          registry("minecraft:cow_variant", "CowVariant"),
          registry("minecraft:damage_type", "DamageType"),
          registry("minecraft:dialog", "Dialog"),
          registry("minecraft:dimension_type", "DimensionType"),
          registry("minecraft:enchantment", "Enchantment"),
          registry("minecraft:frog_variant", "FrogVariant"),
          registry("minecraft:instrument", "Instrument"),
          registry("minecraft:item", "Item"),
          registry("minecraft:jukebox_song", "JukeboxSong"),
          registry("minecraft:painting_variant", "PaintingVariant"),
          registry("minecraft:pig_sound_variant", "PigSoundVariant"),
          registry("minecraft:pig_variant", "PigVariant"),
          registry("minecraft:timeline", "Timeline"),
          registry("minecraft:trim_material", "TrimMaterial"),
          registry("minecraft:trim_pattern", "TrimPattern"),
          registry("minecraft:wolf_sound_variant", "WolfSoundVariant"),
          registry("minecraft:wolf_variant", "WolfVariant"),
          registry("minecraft:world_clock", "WorldClock"),
          registry("minecraft:zombie_nautilus_variant", "ZombieNautilusVariant"));

  private SupportedRegistries() {
  }

  private static RegistryTypeDefinition registry(final String identifier, final String typeName) {

    return new RegistryTypeDefinition(identifier, typeName, typeName + "Keys");
  }
}