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
@SuppressWarnings("unused")
public final class SupportedRegistries {

  public static final RegistryTypeDefinition ARGUMENT_TYPE = registry("minecraft:command_argument_type", "ArgumentType");

  public static final RegistryTypeDefinition BLOCK = registry("minecraft:block", "BlockType");

  public static final RegistryTypeDefinition DIMENSION_TYPE = registry("minecraft:dimension_type", "DimensionType");

  public static final RegistryTypeDefinition ITEM = registry("minecraft:item", "Item");

  public static final List<RegistryTypeDefinition> ALL = List.of(
          registry("minecraft:attribute", "Attribute"),
          registry("minecraft:banner_pattern", "BannerPattern"),
          registry("minecraft:worldgen/biome", "Biome"),
          BLOCK,
          registry("minecraft:cat_sound_variant", "CatSoundVariant"),
          registry("minecraft:cat_variant", "CatVariant"),
          registry("minecraft:chat_type", "ChatType"),
          registry("minecraft:chicken_sound_variant", "ChickenSoundVariant"),
          registry("minecraft:chicken_variant", "ChickenVariant"),
          registry("minecraft:cow_sound_variant", "CowSoundVariant"),
          registry("minecraft:cow_variant", "CowVariant"),
          registry("minecraft:damage_type", "DamageType"),
          registry("minecraft:data_component_type", "DataComponentType"),
          registry("minecraft:dialog", "Dialog"),
          DIMENSION_TYPE,
          registry("minecraft:enchantment", "Enchantment"),
          registry("minecraft:frog_variant", "FrogVariant"),
          registry("minecraft:game_event", "GameEvent"),
          registry("minecraft:game_rule", "GameRule"),
          registry("minecraft:instrument", "Instrument"),
          ITEM,
          registry("minecraft:jukebox_song", "JukeboxSong"),
          registry("minecraft:map_decoration_type", "MapDecorationType"),
          registry("minecraft:menu", "MenuType"),
          registry("minecraft:mob_effect", "MobEffect"),
          registry("minecraft:painting_variant", "PaintingVariant"),
          registry("minecraft:pig_sound_variant", "PigSoundVariant"),
          registry("minecraft:pig_variant", "PigVariant"),
          registry("minecraft:sound_event", "SoundEvent"),
          registry("minecraft:timeline", "Timeline"),
          registry("minecraft:trim_material", "TrimMaterial"),
          registry("minecraft:trim_pattern", "TrimPattern"),
          registry("minecraft:villager_profession", "VillagerProfession"),
          registry("minecraft:villager_type", "VillagerType"),
          registry("minecraft:wolf_sound_variant", "WolfSoundVariant"),
          registry("minecraft:wolf_variant", "WolfVariant"),
          registry("minecraft:world_clock", "WorldClock"),
          registry("minecraft:zombie_nautilus_variant", "ZombieNautilusVariant"));

  private SupportedRegistries() {
  }

  private static RegistryTypeDefinition registry(final String identifier, final String qualifiedTypeName) {
    return RegistryTypeDefinition.parse(identifier, qualifiedTypeName);
  }
}
