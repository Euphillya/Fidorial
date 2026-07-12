// GENERATED CODE - DO NOT EDIT.
// Regenerate: python3 tool/registry-generator/generate.py <project-root>
package fr.euphyllia.fidorial.server.registry;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Baked-in registry contents formerly loaded from datapack_registries.json.
 * Entry order is the network order (index == network id), and the registries
 * themselves are kept in datapack order so they are sent to the client in the
 * exact same sequence as before. Consumed by {@link Registries}.
 */
final class GeneratedRegistryData {

    private static final Map<String, Registry> DYNAMIC = build();

    private GeneratedRegistryData() {
    }

    static Map<String, Registry> dynamic() {
        return DYNAMIC;
    }

    private static Registry r_banner_pattern() {
        List<String> entries = List.of(
                "minecraft:base",
                "minecraft:border",
                "minecraft:bricks",
                "minecraft:circle",
                "minecraft:creeper",
                "minecraft:cross",
                "minecraft:curly_border",
                "minecraft:diagonal_left",
                "minecraft:diagonal_right",
                "minecraft:diagonal_up_left",
                "minecraft:diagonal_up_right",
                "minecraft:flow",
                "minecraft:flower",
                "minecraft:globe",
                "minecraft:gradient",
                "minecraft:gradient_up",
                "minecraft:guster",
                "minecraft:half_horizontal",
                "minecraft:half_horizontal_bottom",
                "minecraft:half_vertical",
                "minecraft:half_vertical_right",
                "minecraft:mojang",
                "minecraft:piglin",
                "minecraft:rhombus",
                "minecraft:skull",
                "minecraft:small_stripes",
                "minecraft:square_bottom_left",
                "minecraft:square_bottom_right",
                "minecraft:square_top_left",
                "minecraft:square_top_right",
                "minecraft:straight_cross",
                "minecraft:stripe_bottom",
                "minecraft:stripe_center",
                "minecraft:stripe_downleft",
                "minecraft:stripe_downright",
                "minecraft:stripe_left",
                "minecraft:stripe_middle",
                "minecraft:stripe_right",
                "minecraft:stripe_top",
                "minecraft:triangle_bottom",
                "minecraft:triangle_top",
                "minecraft:triangles_bottom",
                "minecraft:triangles_top");
        Map<String, List<String>> tags = Map.ofEntries(
                Map.entry("minecraft:no_item_required", List.of("minecraft:square_bottom_left", "minecraft:square_bottom_right", "minecraft:square_top_left", "minecraft:square_top_right", "minecraft:stripe_bottom", "minecraft:stripe_top", "minecraft:stripe_left", "minecraft:stripe_right", "minecraft:stripe_center", "minecraft:stripe_middle", "minecraft:stripe_downright", "minecraft:stripe_downleft", "minecraft:small_stripes", "minecraft:cross", "minecraft:straight_cross", "minecraft:triangle_bottom", "minecraft:triangle_top", "minecraft:triangles_bottom", "minecraft:triangles_top", "minecraft:diagonal_left", "minecraft:diagonal_up_right", "minecraft:diagonal_up_left", "minecraft:diagonal_right", "minecraft:circle", "minecraft:rhombus", "minecraft:half_vertical", "minecraft:half_horizontal", "minecraft:half_vertical_right", "minecraft:half_horizontal_bottom", "minecraft:border", "minecraft:gradient", "minecraft:gradient_up")),
                Map.entry("minecraft:pattern_item/bordure_indented", List.of("minecraft:curly_border")),
                Map.entry("minecraft:pattern_item/creeper", List.of("minecraft:creeper")),
                Map.entry("minecraft:pattern_item/field_masoned", List.of("minecraft:bricks")),
                Map.entry("minecraft:pattern_item/flow", List.of("minecraft:flow")),
                Map.entry("minecraft:pattern_item/flower", List.of("minecraft:flower")),
                Map.entry("minecraft:pattern_item/globe", List.of("minecraft:globe")),
                Map.entry("minecraft:pattern_item/guster", List.of("minecraft:guster")),
                Map.entry("minecraft:pattern_item/mojang", List.of("minecraft:mojang")),
                Map.entry("minecraft:pattern_item/piglin", List.of("minecraft:piglin")),
                Map.entry("minecraft:pattern_item/skull", List.of("minecraft:skull")));
        return new Registry("minecraft:banner_pattern", entries, tags);
    }

    private static Registry r_cat_sound_variant() {
        List<String> entries = List.of(
                "minecraft:classic",
                "minecraft:royal");
        return new Registry("minecraft:cat_sound_variant", entries, Map.of());
    }

    private static Registry r_cat_variant() {
        List<String> entries = List.of(
                "minecraft:all_black",
                "minecraft:black",
                "minecraft:british_shorthair",
                "minecraft:calico",
                "minecraft:jellie",
                "minecraft:persian",
                "minecraft:ragdoll",
                "minecraft:red",
                "minecraft:siamese",
                "minecraft:tabby",
                "minecraft:white");
        return new Registry("minecraft:cat_variant", entries, Map.of());
    }

    private static Registry r_chat_type() {
        List<String> entries = List.of(
                "minecraft:chat",
                "minecraft:emote_command",
                "minecraft:msg_command_incoming",
                "minecraft:msg_command_outgoing",
                "minecraft:say_command",
                "minecraft:team_msg_command_incoming",
                "minecraft:team_msg_command_outgoing");
        return new Registry("minecraft:chat_type", entries, Map.of());
    }

    private static Registry r_chicken_sound_variant() {
        List<String> entries = List.of(
                "minecraft:classic",
                "minecraft:picky");
        return new Registry("minecraft:chicken_sound_variant", entries, Map.of());
    }

    private static Registry r_chicken_variant() {
        List<String> entries = List.of(
                "minecraft:cold",
                "minecraft:temperate",
                "minecraft:warm");
        return new Registry("minecraft:chicken_variant", entries, Map.of());
    }

    private static Registry r_cow_sound_variant() {
        List<String> entries = List.of(
                "minecraft:classic",
                "minecraft:moody");
        return new Registry("minecraft:cow_sound_variant", entries, Map.of());
    }

    private static Registry r_cow_variant() {
        List<String> entries = List.of(
                "minecraft:cold",
                "minecraft:temperate",
                "minecraft:warm");
        return new Registry("minecraft:cow_variant", entries, Map.of());
    }

    private static Registry r_damage_type() {
        List<String> entries = List.of(
                "minecraft:arrow",
                "minecraft:bad_respawn_point",
                "minecraft:cactus",
                "minecraft:campfire",
                "minecraft:cramming",
                "minecraft:dragon_breath",
                "minecraft:drown",
                "minecraft:dry_out",
                "minecraft:ender_pearl",
                "minecraft:explosion",
                "minecraft:fall",
                "minecraft:falling_anvil",
                "minecraft:falling_block",
                "minecraft:falling_stalactite",
                "minecraft:fireball",
                "minecraft:fireworks",
                "minecraft:fly_into_wall",
                "minecraft:freeze",
                "minecraft:generic",
                "minecraft:generic_kill",
                "minecraft:hot_floor",
                "minecraft:in_fire",
                "minecraft:in_wall",
                "minecraft:indirect_magic",
                "minecraft:lava",
                "minecraft:lightning_bolt",
                "minecraft:mace_smash",
                "minecraft:magic",
                "minecraft:mob_attack",
                "minecraft:mob_attack_no_aggro",
                "minecraft:mob_projectile",
                "minecraft:on_fire",
                "minecraft:out_of_world",
                "minecraft:outside_border",
                "minecraft:player_attack",
                "minecraft:player_explosion",
                "minecraft:sonic_boom",
                "minecraft:spear",
                "minecraft:spit",
                "minecraft:stalagmite",
                "minecraft:starve",
                "minecraft:sting",
                "minecraft:sulfur_cube_hot",
                "minecraft:sweet_berry_bush",
                "minecraft:thorns",
                "minecraft:thrown",
                "minecraft:trident",
                "minecraft:unattributed_fireball",
                "minecraft:wind_charge",
                "minecraft:wither",
                "minecraft:wither_skull");
        Map<String, List<String>> tags = Map.ofEntries(
                Map.entry("minecraft:always_hurts_ender_dragons", List.of("minecraft:fireworks", "minecraft:explosion", "minecraft:player_explosion", "minecraft:bad_respawn_point")),
                Map.entry("minecraft:always_kills_armor_stands", List.of("minecraft:arrow", "minecraft:trident", "minecraft:fireball", "minecraft:wither_skull", "minecraft:wind_charge")),
                Map.entry("minecraft:always_most_significant_fall", List.of("minecraft:out_of_world")),
                Map.entry("minecraft:always_triggers_silverfish", List.of("minecraft:magic")),
                Map.entry("minecraft:avoids_guardian_thorns", List.of("minecraft:magic", "minecraft:thorns", "minecraft:fireworks", "minecraft:explosion", "minecraft:player_explosion", "minecraft:bad_respawn_point")),
                Map.entry("minecraft:burn_from_stepping", List.of("minecraft:campfire", "minecraft:hot_floor", "minecraft:sulfur_cube_hot")),
                Map.entry("minecraft:burns_armor_stands", List.of("minecraft:on_fire")),
                Map.entry("minecraft:bypasses_armor", List.of("minecraft:on_fire", "minecraft:in_wall", "minecraft:cramming", "minecraft:drown", "minecraft:fly_into_wall", "minecraft:generic", "minecraft:wither", "minecraft:dragon_breath", "minecraft:starve", "minecraft:fall", "minecraft:ender_pearl", "minecraft:freeze", "minecraft:stalagmite", "minecraft:magic", "minecraft:indirect_magic", "minecraft:out_of_world", "minecraft:generic_kill", "minecraft:sonic_boom", "minecraft:outside_border")),
                Map.entry("minecraft:bypasses_effects", List.of("minecraft:starve")),
                Map.entry("minecraft:bypasses_enchantments", List.of("minecraft:sonic_boom")),
                Map.entry("minecraft:bypasses_invulnerability", List.of("minecraft:out_of_world", "minecraft:generic_kill")),
                Map.entry("minecraft:bypasses_resistance", List.of("minecraft:out_of_world", "minecraft:generic_kill")),
                Map.entry("minecraft:bypasses_shield", List.of("minecraft:on_fire", "minecraft:in_wall", "minecraft:cramming", "minecraft:drown", "minecraft:fly_into_wall", "minecraft:generic", "minecraft:wither", "minecraft:dragon_breath", "minecraft:starve", "minecraft:fall", "minecraft:ender_pearl", "minecraft:freeze", "minecraft:stalagmite", "minecraft:magic", "minecraft:indirect_magic", "minecraft:out_of_world", "minecraft:generic_kill", "minecraft:sonic_boom", "minecraft:outside_border", "minecraft:cactus", "minecraft:campfire", "minecraft:dry_out", "minecraft:falling_anvil", "minecraft:falling_stalactite", "minecraft:hot_floor", "minecraft:sulfur_cube_hot", "minecraft:in_fire", "minecraft:lava", "minecraft:lightning_bolt", "minecraft:sweet_berry_bush")),
                Map.entry("minecraft:bypasses_wolf_armor", List.of("minecraft:out_of_world", "minecraft:generic_kill", "minecraft:cramming", "minecraft:drown", "minecraft:dry_out", "minecraft:freeze", "minecraft:in_wall", "minecraft:indirect_magic", "minecraft:magic", "minecraft:outside_border", "minecraft:starve", "minecraft:thorns", "minecraft:wither")),
                Map.entry("minecraft:can_break_armor_stand", List.of("minecraft:player_explosion", "minecraft:player_attack", "minecraft:spear", "minecraft:mace_smash")),
                Map.entry("minecraft:damages_helmet", List.of("minecraft:falling_anvil", "minecraft:falling_block", "minecraft:falling_stalactite")),
                Map.entry("minecraft:ignites_armor_stands", List.of("minecraft:in_fire", "minecraft:campfire")),
                Map.entry("minecraft:is_drowning", List.of("minecraft:drown")),
                Map.entry("minecraft:is_explosion", List.of("minecraft:fireworks", "minecraft:explosion", "minecraft:player_explosion", "minecraft:bad_respawn_point")),
                Map.entry("minecraft:is_fall", List.of("minecraft:fall", "minecraft:ender_pearl", "minecraft:stalagmite")),
                Map.entry("minecraft:is_fire", List.of("minecraft:in_fire", "minecraft:campfire", "minecraft:on_fire", "minecraft:lava", "minecraft:hot_floor", "minecraft:sulfur_cube_hot", "minecraft:unattributed_fireball", "minecraft:fireball")),
                Map.entry("minecraft:is_freezing", List.of("minecraft:freeze")),
                Map.entry("minecraft:is_lightning", List.of("minecraft:lightning_bolt")),
                Map.entry("minecraft:is_player_attack", List.of("minecraft:player_attack", "minecraft:spear", "minecraft:mace_smash")),
                Map.entry("minecraft:is_projectile", List.of("minecraft:arrow", "minecraft:trident", "minecraft:mob_projectile", "minecraft:unattributed_fireball", "minecraft:fireball", "minecraft:wither_skull", "minecraft:thrown", "minecraft:wind_charge")),
                Map.entry("minecraft:mace_smash", List.of("minecraft:mace_smash")),
                Map.entry("minecraft:no_anger", List.of("minecraft:mob_attack_no_aggro")),
                Map.entry("minecraft:no_impact", List.of("minecraft:drown")),
                Map.entry("minecraft:no_knockback", List.of("minecraft:explosion", "minecraft:player_explosion", "minecraft:bad_respawn_point", "minecraft:in_fire", "minecraft:lightning_bolt", "minecraft:on_fire", "minecraft:lava", "minecraft:hot_floor", "minecraft:sulfur_cube_hot", "minecraft:in_wall", "minecraft:cramming", "minecraft:drown", "minecraft:starve", "minecraft:cactus", "minecraft:fall", "minecraft:ender_pearl", "minecraft:fly_into_wall", "minecraft:out_of_world", "minecraft:generic", "minecraft:magic", "minecraft:wither", "minecraft:dragon_breath", "minecraft:dry_out", "minecraft:sweet_berry_bush", "minecraft:freeze", "minecraft:stalagmite", "minecraft:outside_border", "minecraft:generic_kill", "minecraft:campfire", "minecraft:spear")),
                Map.entry("minecraft:panic_causes", List.of("minecraft:cactus", "minecraft:freeze", "minecraft:hot_floor", "minecraft:sulfur_cube_hot", "minecraft:in_fire", "minecraft:lava", "minecraft:lightning_bolt", "minecraft:on_fire", "minecraft:arrow", "minecraft:dragon_breath", "minecraft:explosion", "minecraft:fireball", "minecraft:fireworks", "minecraft:indirect_magic", "minecraft:magic", "minecraft:mob_attack", "minecraft:mob_projectile", "minecraft:player_explosion", "minecraft:sonic_boom", "minecraft:sting", "minecraft:thrown", "minecraft:trident", "minecraft:unattributed_fireball", "minecraft:wind_charge", "minecraft:wither", "minecraft:wither_skull", "minecraft:player_attack", "minecraft:spear", "minecraft:mace_smash")),
                Map.entry("minecraft:panic_environmental_causes", List.of("minecraft:cactus", "minecraft:freeze", "minecraft:hot_floor", "minecraft:sulfur_cube_hot", "minecraft:in_fire", "minecraft:lava", "minecraft:lightning_bolt", "minecraft:on_fire")),
                Map.entry("minecraft:sulfur_cube_with_block_immune_to", List.of("minecraft:arrow", "minecraft:cactus", "minecraft:dry_out", "minecraft:fall", "minecraft:falling_anvil", "minecraft:falling_block", "minecraft:falling_stalactite", "minecraft:freeze", "minecraft:mace_smash", "minecraft:hot_floor", "minecraft:mob_attack", "minecraft:mob_attack_no_aggro", "minecraft:mob_projectile", "minecraft:player_attack", "minecraft:spear", "minecraft:spit", "minecraft:stalagmite", "minecraft:sting", "minecraft:sulfur_cube_hot", "minecraft:sweet_berry_bush", "minecraft:thrown", "minecraft:trident", "minecraft:wind_charge", "minecraft:fireworks", "minecraft:explosion", "minecraft:player_explosion", "minecraft:bad_respawn_point")),
                Map.entry("minecraft:witch_resistant_to", List.of("minecraft:magic", "minecraft:indirect_magic", "minecraft:sonic_boom", "minecraft:thorns")),
                Map.entry("minecraft:wither_immune_to", List.of("minecraft:drown")));
        return new Registry("minecraft:damage_type", entries, tags);
    }

    private static Registry r_dialog() {
        List<String> entries = List.of(
                "minecraft:custom_options",
                "minecraft:quick_actions",
                "minecraft:server_links");
        Map<String, List<String>> tags = Map.ofEntries(
                Map.entry("minecraft:pause_screen_additions", List.of()),
                Map.entry("minecraft:quick_actions", List.of()));
        return new Registry("minecraft:dialog", entries, tags);
    }

    private static Registry r_dimension_type() {
        List<String> entries = List.of(
                "minecraft:overworld",
                "minecraft:overworld_caves",
                "minecraft:the_end",
                "minecraft:the_nether");
        return new Registry("minecraft:dimension_type", entries, Map.of());
    }

    private static Registry r_enchantment() {
        List<String> entries = List.of(
                "minecraft:aqua_affinity",
                "minecraft:bane_of_arthropods",
                "minecraft:binding_curse",
                "minecraft:blast_protection",
                "minecraft:breach",
                "minecraft:channeling",
                "minecraft:density",
                "minecraft:depth_strider",
                "minecraft:efficiency",
                "minecraft:feather_falling",
                "minecraft:fire_aspect",
                "minecraft:fire_protection",
                "minecraft:flame",
                "minecraft:fortune",
                "minecraft:frost_walker",
                "minecraft:impaling",
                "minecraft:infinity",
                "minecraft:knockback",
                "minecraft:looting",
                "minecraft:loyalty",
                "minecraft:luck_of_the_sea",
                "minecraft:lunge",
                "minecraft:lure",
                "minecraft:mending",
                "minecraft:multishot",
                "minecraft:piercing",
                "minecraft:power",
                "minecraft:projectile_protection",
                "minecraft:protection",
                "minecraft:punch",
                "minecraft:quick_charge",
                "minecraft:respiration",
                "minecraft:riptide",
                "minecraft:sharpness",
                "minecraft:silk_touch",
                "minecraft:smite",
                "minecraft:soul_speed",
                "minecraft:sweeping_edge",
                "minecraft:swift_sneak",
                "minecraft:thorns",
                "minecraft:unbreaking",
                "minecraft:vanishing_curse",
                "minecraft:wind_burst");
        Map<String, List<String>> tags = Map.ofEntries(
                Map.entry("minecraft:curse", List.of("minecraft:binding_curse", "minecraft:vanishing_curse")),
                Map.entry("minecraft:double_trade_price", List.of("minecraft:binding_curse", "minecraft:vanishing_curse", "minecraft:swift_sneak", "minecraft:soul_speed", "minecraft:frost_walker", "minecraft:mending", "minecraft:wind_burst")),
                Map.entry("minecraft:exclusive_set/armor", List.of("minecraft:protection", "minecraft:blast_protection", "minecraft:fire_protection", "minecraft:projectile_protection")),
                Map.entry("minecraft:exclusive_set/boots", List.of("minecraft:frost_walker", "minecraft:depth_strider")),
                Map.entry("minecraft:exclusive_set/bow", List.of("minecraft:infinity", "minecraft:mending")),
                Map.entry("minecraft:exclusive_set/crossbow", List.of("minecraft:multishot", "minecraft:piercing")),
                Map.entry("minecraft:exclusive_set/damage", List.of("minecraft:sharpness", "minecraft:smite", "minecraft:bane_of_arthropods", "minecraft:impaling", "minecraft:density", "minecraft:breach")),
                Map.entry("minecraft:exclusive_set/mining", List.of("minecraft:fortune", "minecraft:silk_touch")),
                Map.entry("minecraft:exclusive_set/riptide", List.of("minecraft:loyalty", "minecraft:channeling")),
                Map.entry("minecraft:in_enchanting_table", List.of("minecraft:protection", "minecraft:fire_protection", "minecraft:feather_falling", "minecraft:blast_protection", "minecraft:projectile_protection", "minecraft:respiration", "minecraft:aqua_affinity", "minecraft:thorns", "minecraft:depth_strider", "minecraft:sharpness", "minecraft:smite", "minecraft:bane_of_arthropods", "minecraft:knockback", "minecraft:fire_aspect", "minecraft:looting", "minecraft:sweeping_edge", "minecraft:efficiency", "minecraft:silk_touch", "minecraft:unbreaking", "minecraft:fortune", "minecraft:power", "minecraft:punch", "minecraft:flame", "minecraft:infinity", "minecraft:luck_of_the_sea", "minecraft:lure", "minecraft:loyalty", "minecraft:impaling", "minecraft:riptide", "minecraft:channeling", "minecraft:multishot", "minecraft:quick_charge", "minecraft:piercing", "minecraft:density", "minecraft:breach", "minecraft:lunge")),
                Map.entry("minecraft:non_treasure", List.of("minecraft:protection", "minecraft:fire_protection", "minecraft:feather_falling", "minecraft:blast_protection", "minecraft:projectile_protection", "minecraft:respiration", "minecraft:aqua_affinity", "minecraft:thorns", "minecraft:depth_strider", "minecraft:sharpness", "minecraft:smite", "minecraft:bane_of_arthropods", "minecraft:knockback", "minecraft:fire_aspect", "minecraft:looting", "minecraft:sweeping_edge", "minecraft:efficiency", "minecraft:silk_touch", "minecraft:unbreaking", "minecraft:fortune", "minecraft:power", "minecraft:punch", "minecraft:flame", "minecraft:infinity", "minecraft:luck_of_the_sea", "minecraft:lure", "minecraft:loyalty", "minecraft:impaling", "minecraft:riptide", "minecraft:channeling", "minecraft:multishot", "minecraft:quick_charge", "minecraft:piercing", "minecraft:density", "minecraft:breach", "minecraft:lunge")),
                Map.entry("minecraft:on_mob_spawn_equipment", List.of("minecraft:protection", "minecraft:fire_protection", "minecraft:feather_falling", "minecraft:blast_protection", "minecraft:projectile_protection", "minecraft:respiration", "minecraft:aqua_affinity", "minecraft:thorns", "minecraft:depth_strider", "minecraft:sharpness", "minecraft:smite", "minecraft:bane_of_arthropods", "minecraft:knockback", "minecraft:fire_aspect", "minecraft:looting", "minecraft:sweeping_edge", "minecraft:efficiency", "minecraft:silk_touch", "minecraft:unbreaking", "minecraft:fortune", "minecraft:power", "minecraft:punch", "minecraft:flame", "minecraft:infinity", "minecraft:luck_of_the_sea", "minecraft:lure", "minecraft:loyalty", "minecraft:impaling", "minecraft:riptide", "minecraft:channeling", "minecraft:multishot", "minecraft:quick_charge", "minecraft:piercing", "minecraft:density", "minecraft:breach", "minecraft:lunge")),
                Map.entry("minecraft:on_random_loot", List.of("minecraft:protection", "minecraft:fire_protection", "minecraft:feather_falling", "minecraft:blast_protection", "minecraft:projectile_protection", "minecraft:respiration", "minecraft:aqua_affinity", "minecraft:thorns", "minecraft:depth_strider", "minecraft:sharpness", "minecraft:smite", "minecraft:bane_of_arthropods", "minecraft:knockback", "minecraft:fire_aspect", "minecraft:looting", "minecraft:sweeping_edge", "minecraft:efficiency", "minecraft:silk_touch", "minecraft:unbreaking", "minecraft:fortune", "minecraft:power", "minecraft:punch", "minecraft:flame", "minecraft:infinity", "minecraft:luck_of_the_sea", "minecraft:lure", "minecraft:loyalty", "minecraft:impaling", "minecraft:riptide", "minecraft:channeling", "minecraft:multishot", "minecraft:quick_charge", "minecraft:piercing", "minecraft:density", "minecraft:breach", "minecraft:lunge", "minecraft:binding_curse", "minecraft:vanishing_curse", "minecraft:frost_walker", "minecraft:mending")),
                Map.entry("minecraft:on_traded_equipment", List.of("minecraft:protection", "minecraft:fire_protection", "minecraft:feather_falling", "minecraft:blast_protection", "minecraft:projectile_protection", "minecraft:respiration", "minecraft:aqua_affinity", "minecraft:thorns", "minecraft:depth_strider", "minecraft:sharpness", "minecraft:smite", "minecraft:bane_of_arthropods", "minecraft:knockback", "minecraft:fire_aspect", "minecraft:looting", "minecraft:sweeping_edge", "minecraft:efficiency", "minecraft:silk_touch", "minecraft:unbreaking", "minecraft:fortune", "minecraft:power", "minecraft:punch", "minecraft:flame", "minecraft:infinity", "minecraft:luck_of_the_sea", "minecraft:lure", "minecraft:loyalty", "minecraft:impaling", "minecraft:riptide", "minecraft:channeling", "minecraft:multishot", "minecraft:quick_charge", "minecraft:piercing", "minecraft:density", "minecraft:breach", "minecraft:lunge")),
                Map.entry("minecraft:prevents_bee_spawns_when_mining", List.of("minecraft:silk_touch")),
                Map.entry("minecraft:prevents_decorated_pot_shattering", List.of("minecraft:silk_touch")),
                Map.entry("minecraft:prevents_ice_melting", List.of("minecraft:silk_touch")),
                Map.entry("minecraft:prevents_infested_spawns", List.of("minecraft:silk_touch")),
                Map.entry("minecraft:smelts_loot", List.of("minecraft:fire_aspect")),
                Map.entry("minecraft:tooltip_order", List.of("minecraft:binding_curse", "minecraft:vanishing_curse", "minecraft:riptide", "minecraft:channeling", "minecraft:wind_burst", "minecraft:frost_walker", "minecraft:lunge", "minecraft:sharpness", "minecraft:smite", "minecraft:bane_of_arthropods", "minecraft:impaling", "minecraft:power", "minecraft:density", "minecraft:breach", "minecraft:piercing", "minecraft:sweeping_edge", "minecraft:multishot", "minecraft:fire_aspect", "minecraft:flame", "minecraft:knockback", "minecraft:punch", "minecraft:protection", "minecraft:blast_protection", "minecraft:fire_protection", "minecraft:projectile_protection", "minecraft:feather_falling", "minecraft:fortune", "minecraft:looting", "minecraft:silk_touch", "minecraft:luck_of_the_sea", "minecraft:efficiency", "minecraft:quick_charge", "minecraft:lure", "minecraft:respiration", "minecraft:aqua_affinity", "minecraft:soul_speed", "minecraft:swift_sneak", "minecraft:depth_strider", "minecraft:thorns", "minecraft:loyalty", "minecraft:unbreaking", "minecraft:infinity", "minecraft:mending")),
                Map.entry("minecraft:tradeable", List.of("minecraft:protection", "minecraft:fire_protection", "minecraft:feather_falling", "minecraft:blast_protection", "minecraft:projectile_protection", "minecraft:respiration", "minecraft:aqua_affinity", "minecraft:thorns", "minecraft:depth_strider", "minecraft:sharpness", "minecraft:smite", "minecraft:bane_of_arthropods", "minecraft:knockback", "minecraft:fire_aspect", "minecraft:looting", "minecraft:sweeping_edge", "minecraft:efficiency", "minecraft:silk_touch", "minecraft:unbreaking", "minecraft:fortune", "minecraft:power", "minecraft:punch", "minecraft:flame", "minecraft:infinity", "minecraft:luck_of_the_sea", "minecraft:lure", "minecraft:loyalty", "minecraft:impaling", "minecraft:riptide", "minecraft:channeling", "minecraft:multishot", "minecraft:quick_charge", "minecraft:piercing", "minecraft:density", "minecraft:breach", "minecraft:lunge", "minecraft:binding_curse", "minecraft:vanishing_curse", "minecraft:frost_walker", "minecraft:mending")),
                Map.entry("minecraft:treasure", List.of("minecraft:binding_curse", "minecraft:vanishing_curse", "minecraft:swift_sneak", "minecraft:soul_speed", "minecraft:frost_walker", "minecraft:mending", "minecraft:wind_burst")));
        return new Registry("minecraft:enchantment", entries, tags);
    }

    private static Registry r_frog_variant() {
        List<String> entries = List.of(
                "minecraft:cold",
                "minecraft:temperate",
                "minecraft:warm");
        return new Registry("minecraft:frog_variant", entries, Map.of());
    }

    private static Registry r_instrument() {
        List<String> entries = List.of(
                "minecraft:admire_goat_horn",
                "minecraft:call_goat_horn",
                "minecraft:dream_goat_horn",
                "minecraft:feel_goat_horn",
                "minecraft:ponder_goat_horn",
                "minecraft:seek_goat_horn",
                "minecraft:sing_goat_horn",
                "minecraft:yearn_goat_horn");
        Map<String, List<String>> tags = Map.ofEntries(
                Map.entry("minecraft:goat_horns", List.of("minecraft:ponder_goat_horn", "minecraft:sing_goat_horn", "minecraft:seek_goat_horn", "minecraft:feel_goat_horn", "minecraft:admire_goat_horn", "minecraft:call_goat_horn", "minecraft:yearn_goat_horn", "minecraft:dream_goat_horn")),
                Map.entry("minecraft:regular_goat_horns", List.of("minecraft:ponder_goat_horn", "minecraft:sing_goat_horn", "minecraft:seek_goat_horn", "minecraft:feel_goat_horn")),
                Map.entry("minecraft:screaming_goat_horns", List.of("minecraft:admire_goat_horn", "minecraft:call_goat_horn", "minecraft:yearn_goat_horn", "minecraft:dream_goat_horn")));
        return new Registry("minecraft:instrument", entries, tags);
    }

    private static Registry r_jukebox_song() {
        List<String> entries = List.of(
                "minecraft:11",
                "minecraft:13",
                "minecraft:5",
                "minecraft:blocks",
                "minecraft:bounce",
                "minecraft:cat",
                "minecraft:chirp",
                "minecraft:creator",
                "minecraft:creator_music_box",
                "minecraft:far",
                "minecraft:lava_chicken",
                "minecraft:mall",
                "minecraft:mellohi",
                "minecraft:otherside",
                "minecraft:pigstep",
                "minecraft:precipice",
                "minecraft:relic",
                "minecraft:stal",
                "minecraft:strad",
                "minecraft:tears",
                "minecraft:wait",
                "minecraft:ward");
        return new Registry("minecraft:jukebox_song", entries, Map.of());
    }

    private static Registry r_painting_variant() {
        List<String> entries = List.of(
                "minecraft:alban",
                "minecraft:aztec",
                "minecraft:aztec2",
                "minecraft:backyard",
                "minecraft:baroque",
                "minecraft:bomb",
                "minecraft:bouquet",
                "minecraft:burning_skull",
                "minecraft:bust",
                "minecraft:cavebird",
                "minecraft:changing",
                "minecraft:cotan",
                "minecraft:courbet",
                "minecraft:creebet",
                "minecraft:dennis",
                "minecraft:donkey_kong",
                "minecraft:earth",
                "minecraft:endboss",
                "minecraft:fern",
                "minecraft:fighters",
                "minecraft:finding",
                "minecraft:fire",
                "minecraft:graham",
                "minecraft:humble",
                "minecraft:kebab",
                "minecraft:lowmist",
                "minecraft:match",
                "minecraft:meditative",
                "minecraft:orb",
                "minecraft:owlemons",
                "minecraft:passage",
                "minecraft:pigscene",
                "minecraft:plant",
                "minecraft:pointer",
                "minecraft:pond",
                "minecraft:pool",
                "minecraft:prairie_ride",
                "minecraft:sea",
                "minecraft:skeleton",
                "minecraft:skull_and_roses",
                "minecraft:stage",
                "minecraft:sunflowers",
                "minecraft:sunset",
                "minecraft:tides",
                "minecraft:unpacked",
                "minecraft:void",
                "minecraft:wanderer",
                "minecraft:wasteland",
                "minecraft:water",
                "minecraft:wind",
                "minecraft:wither");
        Map<String, List<String>> tags = Map.ofEntries(
                Map.entry("minecraft:placeable", List.of("minecraft:kebab", "minecraft:aztec", "minecraft:alban", "minecraft:aztec2", "minecraft:bomb", "minecraft:plant", "minecraft:wasteland", "minecraft:pool", "minecraft:courbet", "minecraft:sea", "minecraft:sunset", "minecraft:creebet", "minecraft:wanderer", "minecraft:graham", "minecraft:match", "minecraft:bust", "minecraft:stage", "minecraft:void", "minecraft:skull_and_roses", "minecraft:wither", "minecraft:fighters", "minecraft:pointer", "minecraft:pigscene", "minecraft:burning_skull", "minecraft:skeleton", "minecraft:donkey_kong", "minecraft:baroque", "minecraft:humble", "minecraft:meditative", "minecraft:prairie_ride", "minecraft:unpacked", "minecraft:backyard", "minecraft:bouquet", "minecraft:cavebird", "minecraft:changing", "minecraft:cotan", "minecraft:endboss", "minecraft:fern", "minecraft:finding", "minecraft:lowmist", "minecraft:orb", "minecraft:owlemons", "minecraft:passage", "minecraft:pond", "minecraft:sunflowers", "minecraft:tides", "minecraft:dennis")));
        return new Registry("minecraft:painting_variant", entries, tags);
    }

    private static Registry r_pig_sound_variant() {
        List<String> entries = List.of(
                "minecraft:big",
                "minecraft:classic",
                "minecraft:mini");
        return new Registry("minecraft:pig_sound_variant", entries, Map.of());
    }

    private static Registry r_pig_variant() {
        List<String> entries = List.of(
                "minecraft:cold",
                "minecraft:temperate",
                "minecraft:warm");
        return new Registry("minecraft:pig_variant", entries, Map.of());
    }

    private static Registry r_timeline() {
        List<String> entries = List.of(
                "minecraft:day",
                "minecraft:early_game",
                "minecraft:moon",
                "minecraft:villager_schedule");
        Map<String, List<String>> tags = Map.ofEntries(
                Map.entry("minecraft:in_end", List.of("minecraft:villager_schedule")),
                Map.entry("minecraft:in_nether", List.of("minecraft:villager_schedule")),
                Map.entry("minecraft:in_overworld", List.of("minecraft:villager_schedule", "minecraft:day", "minecraft:moon", "minecraft:early_game")),
                Map.entry("minecraft:universal", List.of("minecraft:villager_schedule")));
        return new Registry("minecraft:timeline", entries, tags);
    }

    private static Registry r_trim_material() {
        List<String> entries = List.of(
                "minecraft:amethyst",
                "minecraft:copper",
                "minecraft:diamond",
                "minecraft:emerald",
                "minecraft:gold",
                "minecraft:iron",
                "minecraft:lapis",
                "minecraft:netherite",
                "minecraft:quartz",
                "minecraft:redstone",
                "minecraft:resin");
        return new Registry("minecraft:trim_material", entries, Map.of());
    }

    private static Registry r_trim_pattern() {
        List<String> entries = List.of(
                "minecraft:bolt",
                "minecraft:coast",
                "minecraft:dune",
                "minecraft:eye",
                "minecraft:flow",
                "minecraft:host",
                "minecraft:raiser",
                "minecraft:rib",
                "minecraft:sentry",
                "minecraft:shaper",
                "minecraft:silence",
                "minecraft:snout",
                "minecraft:spire",
                "minecraft:tide",
                "minecraft:vex",
                "minecraft:ward",
                "minecraft:wayfinder",
                "minecraft:wild");
        return new Registry("minecraft:trim_pattern", entries, Map.of());
    }

    private static Registry r_wolf_sound_variant() {
        List<String> entries = List.of(
                "minecraft:angry",
                "minecraft:big",
                "minecraft:classic",
                "minecraft:cute",
                "minecraft:grumpy",
                "minecraft:puglin",
                "minecraft:sad");
        return new Registry("minecraft:wolf_sound_variant", entries, Map.of());
    }

    private static Registry r_wolf_variant() {
        List<String> entries = List.of(
                "minecraft:ashen",
                "minecraft:black",
                "minecraft:chestnut",
                "minecraft:pale",
                "minecraft:rusty",
                "minecraft:snowy",
                "minecraft:spotted",
                "minecraft:striped",
                "minecraft:woods");
        return new Registry("minecraft:wolf_variant", entries, Map.of());
    }

    private static Registry r_world_clock() {
        List<String> entries = List.of(
                "minecraft:overworld",
                "minecraft:the_end");
        return new Registry("minecraft:world_clock", entries, Map.of());
    }

    private static Registry r_worldgen_biome() {
        List<String> entries = List.of(
                "minecraft:badlands",
                "minecraft:bamboo_jungle",
                "minecraft:basalt_deltas",
                "minecraft:beach",
                "minecraft:birch_forest",
                "minecraft:cherry_grove",
                "minecraft:cold_ocean",
                "minecraft:crimson_forest",
                "minecraft:dark_forest",
                "minecraft:deep_cold_ocean",
                "minecraft:deep_dark",
                "minecraft:deep_frozen_ocean",
                "minecraft:deep_lukewarm_ocean",
                "minecraft:deep_ocean",
                "minecraft:desert",
                "minecraft:dripstone_caves",
                "minecraft:end_barrens",
                "minecraft:end_highlands",
                "minecraft:end_midlands",
                "minecraft:eroded_badlands",
                "minecraft:flower_forest",
                "minecraft:forest",
                "minecraft:frozen_ocean",
                "minecraft:frozen_peaks",
                "minecraft:frozen_river",
                "minecraft:grove",
                "minecraft:ice_spikes",
                "minecraft:jagged_peaks",
                "minecraft:jungle",
                "minecraft:lukewarm_ocean",
                "minecraft:lush_caves",
                "minecraft:mangrove_swamp",
                "minecraft:meadow",
                "minecraft:mushroom_fields",
                "minecraft:nether_wastes",
                "minecraft:ocean",
                "minecraft:old_growth_birch_forest",
                "minecraft:old_growth_pine_taiga",
                "minecraft:old_growth_spruce_taiga",
                "minecraft:pale_garden",
                "minecraft:plains",
                "minecraft:river",
                "minecraft:savanna",
                "minecraft:savanna_plateau",
                "minecraft:small_end_islands",
                "minecraft:snowy_beach",
                "minecraft:snowy_plains",
                "minecraft:snowy_slopes",
                "minecraft:snowy_taiga",
                "minecraft:soul_sand_valley",
                "minecraft:sparse_jungle",
                "minecraft:stony_peaks",
                "minecraft:stony_shore",
                "minecraft:sulfur_caves",
                "minecraft:sunflower_plains",
                "minecraft:swamp",
                "minecraft:taiga",
                "minecraft:the_end",
                "minecraft:the_void",
                "minecraft:warm_ocean",
                "minecraft:warped_forest",
                "minecraft:windswept_forest",
                "minecraft:windswept_gravelly_hills",
                "minecraft:windswept_hills",
                "minecraft:windswept_savanna",
                "minecraft:wooded_badlands");
        Map<String, List<String>> tags = Map.ofEntries(
                Map.entry("minecraft:allows_surface_slime_spawns", List.of("minecraft:swamp", "minecraft:mangrove_swamp")),
                Map.entry("minecraft:allows_tropical_fish_spawns_at_any_height", List.of("minecraft:lush_caves")),
                Map.entry("minecraft:has_structure/ancient_city", List.of("minecraft:deep_dark")),
                Map.entry("minecraft:has_structure/bastion_remnant", List.of("minecraft:crimson_forest", "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:warped_forest")),
                Map.entry("minecraft:has_structure/buried_treasure", List.of("minecraft:beach", "minecraft:snowy_beach")),
                Map.entry("minecraft:has_structure/desert_pyramid", List.of("minecraft:desert")),
                Map.entry("minecraft:has_structure/end_city", List.of("minecraft:end_highlands", "minecraft:end_midlands")),
                Map.entry("minecraft:has_structure/igloo", List.of("minecraft:snowy_taiga", "minecraft:snowy_plains", "minecraft:snowy_slopes")),
                Map.entry("minecraft:has_structure/jungle_temple", List.of("minecraft:bamboo_jungle", "minecraft:jungle")),
                Map.entry("minecraft:has_structure/mineshaft", List.of("minecraft:deep_frozen_ocean", "minecraft:deep_cold_ocean", "minecraft:deep_ocean", "minecraft:deep_lukewarm_ocean", "minecraft:frozen_ocean", "minecraft:ocean", "minecraft:cold_ocean", "minecraft:lukewarm_ocean", "minecraft:warm_ocean", "minecraft:river", "minecraft:frozen_river", "minecraft:beach", "minecraft:snowy_beach", "minecraft:meadow", "minecraft:frozen_peaks", "minecraft:jagged_peaks", "minecraft:stony_peaks", "minecraft:snowy_slopes", "minecraft:cherry_grove", "minecraft:windswept_hills", "minecraft:windswept_forest", "minecraft:windswept_gravelly_hills", "minecraft:taiga", "minecraft:snowy_taiga", "minecraft:old_growth_pine_taiga", "minecraft:old_growth_spruce_taiga", "minecraft:bamboo_jungle", "minecraft:jungle", "minecraft:sparse_jungle", "minecraft:forest", "minecraft:flower_forest", "minecraft:birch_forest", "minecraft:old_growth_birch_forest", "minecraft:dark_forest", "minecraft:pale_garden", "minecraft:grove", "minecraft:stony_shore", "minecraft:mushroom_fields", "minecraft:ice_spikes", "minecraft:windswept_savanna", "minecraft:desert", "minecraft:savanna", "minecraft:snowy_plains", "minecraft:plains", "minecraft:sunflower_plains", "minecraft:swamp", "minecraft:mangrove_swamp", "minecraft:savanna_plateau", "minecraft:dripstone_caves", "minecraft:lush_caves", "minecraft:sulfur_caves")),
                Map.entry("minecraft:has_structure/mineshaft_mesa", List.of("minecraft:badlands", "minecraft:eroded_badlands", "minecraft:wooded_badlands")),
                Map.entry("minecraft:has_structure/nether_fortress", List.of("minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas")),
                Map.entry("minecraft:has_structure/nether_fossil", List.of("minecraft:soul_sand_valley")),
                Map.entry("minecraft:has_structure/ocean_monument", List.of("minecraft:deep_frozen_ocean", "minecraft:deep_cold_ocean", "minecraft:deep_ocean", "minecraft:deep_lukewarm_ocean")),
                Map.entry("minecraft:has_structure/ocean_ruin_cold", List.of("minecraft:frozen_ocean", "minecraft:cold_ocean", "minecraft:ocean", "minecraft:deep_frozen_ocean", "minecraft:deep_cold_ocean", "minecraft:deep_ocean")),
                Map.entry("minecraft:has_structure/ocean_ruin_warm", List.of("minecraft:lukewarm_ocean", "minecraft:warm_ocean", "minecraft:deep_lukewarm_ocean")),
                Map.entry("minecraft:has_structure/pillager_outpost", List.of("minecraft:desert", "minecraft:plains", "minecraft:savanna", "minecraft:snowy_plains", "minecraft:taiga", "minecraft:meadow", "minecraft:frozen_peaks", "minecraft:jagged_peaks", "minecraft:stony_peaks", "minecraft:snowy_slopes", "minecraft:cherry_grove", "minecraft:grove")),
                Map.entry("minecraft:has_structure/ruined_portal_desert", List.of("minecraft:desert")),
                Map.entry("minecraft:has_structure/ruined_portal_jungle", List.of("minecraft:bamboo_jungle", "minecraft:jungle", "minecraft:sparse_jungle")),
                Map.entry("minecraft:has_structure/ruined_portal_mountain", List.of("minecraft:badlands", "minecraft:eroded_badlands", "minecraft:wooded_badlands", "minecraft:windswept_hills", "minecraft:windswept_forest", "minecraft:windswept_gravelly_hills", "minecraft:savanna_plateau", "minecraft:windswept_savanna", "minecraft:stony_shore", "minecraft:meadow", "minecraft:frozen_peaks", "minecraft:jagged_peaks", "minecraft:stony_peaks", "minecraft:snowy_slopes", "minecraft:cherry_grove")),
                Map.entry("minecraft:has_structure/ruined_portal_nether", List.of("minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas")),
                Map.entry("minecraft:has_structure/ruined_portal_ocean", List.of("minecraft:deep_frozen_ocean", "minecraft:deep_cold_ocean", "minecraft:deep_ocean", "minecraft:deep_lukewarm_ocean", "minecraft:frozen_ocean", "minecraft:ocean", "minecraft:cold_ocean", "minecraft:lukewarm_ocean", "minecraft:warm_ocean")),
                Map.entry("minecraft:has_structure/ruined_portal_standard", List.of("minecraft:beach", "minecraft:snowy_beach", "minecraft:river", "minecraft:frozen_river", "minecraft:taiga", "minecraft:snowy_taiga", "minecraft:old_growth_pine_taiga", "minecraft:old_growth_spruce_taiga", "minecraft:forest", "minecraft:flower_forest", "minecraft:birch_forest", "minecraft:old_growth_birch_forest", "minecraft:dark_forest", "minecraft:pale_garden", "minecraft:grove", "minecraft:mushroom_fields", "minecraft:ice_spikes", "minecraft:dripstone_caves", "minecraft:lush_caves", "minecraft:sulfur_caves", "minecraft:savanna", "minecraft:snowy_plains", "minecraft:plains", "minecraft:sunflower_plains")),
                Map.entry("minecraft:has_structure/ruined_portal_swamp", List.of("minecraft:swamp", "minecraft:mangrove_swamp")),
                Map.entry("minecraft:has_structure/shipwreck", List.of("minecraft:deep_frozen_ocean", "minecraft:deep_cold_ocean", "minecraft:deep_ocean", "minecraft:deep_lukewarm_ocean", "minecraft:frozen_ocean", "minecraft:ocean", "minecraft:cold_ocean", "minecraft:lukewarm_ocean", "minecraft:warm_ocean")),
                Map.entry("minecraft:has_structure/shipwreck_beached", List.of("minecraft:beach", "minecraft:snowy_beach")),
                Map.entry("minecraft:has_structure/stronghold", List.of("minecraft:mushroom_fields", "minecraft:deep_frozen_ocean", "minecraft:frozen_ocean", "minecraft:deep_cold_ocean", "minecraft:cold_ocean", "minecraft:deep_ocean", "minecraft:ocean", "minecraft:deep_lukewarm_ocean", "minecraft:lukewarm_ocean", "minecraft:warm_ocean", "minecraft:stony_shore", "minecraft:swamp", "minecraft:mangrove_swamp", "minecraft:snowy_slopes", "minecraft:snowy_plains", "minecraft:snowy_beach", "minecraft:windswept_gravelly_hills", "minecraft:grove", "minecraft:windswept_hills", "minecraft:snowy_taiga", "minecraft:windswept_forest", "minecraft:taiga", "minecraft:plains", "minecraft:meadow", "minecraft:beach", "minecraft:forest", "minecraft:old_growth_spruce_taiga", "minecraft:flower_forest", "minecraft:birch_forest", "minecraft:dark_forest", "minecraft:pale_garden", "minecraft:savanna_plateau", "minecraft:savanna", "minecraft:jungle", "minecraft:badlands", "minecraft:desert", "minecraft:wooded_badlands", "minecraft:jagged_peaks", "minecraft:stony_peaks", "minecraft:frozen_river", "minecraft:river", "minecraft:ice_spikes", "minecraft:old_growth_pine_taiga", "minecraft:sunflower_plains", "minecraft:old_growth_birch_forest", "minecraft:sparse_jungle", "minecraft:bamboo_jungle", "minecraft:eroded_badlands", "minecraft:windswept_savanna", "minecraft:cherry_grove", "minecraft:frozen_peaks", "minecraft:dripstone_caves", "minecraft:lush_caves", "minecraft:sulfur_caves", "minecraft:deep_dark")),
                Map.entry("minecraft:has_structure/swamp_hut", List.of("minecraft:swamp")),
                Map.entry("minecraft:has_structure/trail_ruins", List.of("minecraft:taiga", "minecraft:snowy_taiga", "minecraft:old_growth_pine_taiga", "minecraft:old_growth_spruce_taiga", "minecraft:old_growth_birch_forest", "minecraft:jungle")),
                Map.entry("minecraft:has_structure/trial_chambers", List.of("minecraft:mushroom_fields", "minecraft:deep_frozen_ocean", "minecraft:frozen_ocean", "minecraft:deep_cold_ocean", "minecraft:cold_ocean", "minecraft:deep_ocean", "minecraft:ocean", "minecraft:deep_lukewarm_ocean", "minecraft:lukewarm_ocean", "minecraft:warm_ocean", "minecraft:stony_shore", "minecraft:swamp", "minecraft:mangrove_swamp", "minecraft:snowy_slopes", "minecraft:snowy_plains", "minecraft:snowy_beach", "minecraft:windswept_gravelly_hills", "minecraft:grove", "minecraft:windswept_hills", "minecraft:snowy_taiga", "minecraft:windswept_forest", "minecraft:taiga", "minecraft:plains", "minecraft:meadow", "minecraft:beach", "minecraft:forest", "minecraft:old_growth_spruce_taiga", "minecraft:flower_forest", "minecraft:birch_forest", "minecraft:dark_forest", "minecraft:pale_garden", "minecraft:savanna_plateau", "minecraft:savanna", "minecraft:jungle", "minecraft:badlands", "minecraft:desert", "minecraft:wooded_badlands", "minecraft:jagged_peaks", "minecraft:stony_peaks", "minecraft:frozen_river", "minecraft:river", "minecraft:ice_spikes", "minecraft:old_growth_pine_taiga", "minecraft:sunflower_plains", "minecraft:old_growth_birch_forest", "minecraft:sparse_jungle", "minecraft:bamboo_jungle", "minecraft:eroded_badlands", "minecraft:windswept_savanna", "minecraft:cherry_grove", "minecraft:frozen_peaks", "minecraft:dripstone_caves", "minecraft:lush_caves", "minecraft:sulfur_caves")),
                Map.entry("minecraft:has_structure/village_desert", List.of("minecraft:desert")),
                Map.entry("minecraft:has_structure/village_plains", List.of("minecraft:plains", "minecraft:meadow")),
                Map.entry("minecraft:has_structure/village_savanna", List.of("minecraft:savanna")),
                Map.entry("minecraft:has_structure/village_snowy", List.of("minecraft:snowy_plains")),
                Map.entry("minecraft:has_structure/village_taiga", List.of("minecraft:taiga")),
                Map.entry("minecraft:has_structure/woodland_mansion", List.of("minecraft:dark_forest", "minecraft:pale_garden")),
                Map.entry("minecraft:is_badlands", List.of("minecraft:badlands", "minecraft:eroded_badlands", "minecraft:wooded_badlands")),
                Map.entry("minecraft:is_beach", List.of("minecraft:beach", "minecraft:snowy_beach")),
                Map.entry("minecraft:is_deep_ocean", List.of("minecraft:deep_frozen_ocean", "minecraft:deep_cold_ocean", "minecraft:deep_ocean", "minecraft:deep_lukewarm_ocean")),
                Map.entry("minecraft:is_end", List.of("minecraft:the_end", "minecraft:end_highlands", "minecraft:end_midlands", "minecraft:small_end_islands", "minecraft:end_barrens")),
                Map.entry("minecraft:is_forest", List.of("minecraft:forest", "minecraft:flower_forest", "minecraft:birch_forest", "minecraft:old_growth_birch_forest", "minecraft:dark_forest", "minecraft:pale_garden", "minecraft:grove")),
                Map.entry("minecraft:is_hill", List.of("minecraft:windswept_hills", "minecraft:windswept_forest", "minecraft:windswept_gravelly_hills")),
                Map.entry("minecraft:is_jungle", List.of("minecraft:bamboo_jungle", "minecraft:jungle", "minecraft:sparse_jungle")),
                Map.entry("minecraft:is_mountain", List.of("minecraft:meadow", "minecraft:frozen_peaks", "minecraft:jagged_peaks", "minecraft:stony_peaks", "minecraft:snowy_slopes", "minecraft:cherry_grove")),
                Map.entry("minecraft:is_nether", List.of("minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas")),
                Map.entry("minecraft:is_ocean", List.of("minecraft:deep_frozen_ocean", "minecraft:deep_cold_ocean", "minecraft:deep_ocean", "minecraft:deep_lukewarm_ocean", "minecraft:frozen_ocean", "minecraft:ocean", "minecraft:cold_ocean", "minecraft:lukewarm_ocean", "minecraft:warm_ocean")),
                Map.entry("minecraft:is_overworld", List.of("minecraft:mushroom_fields", "minecraft:deep_frozen_ocean", "minecraft:frozen_ocean", "minecraft:deep_cold_ocean", "minecraft:cold_ocean", "minecraft:deep_ocean", "minecraft:ocean", "minecraft:deep_lukewarm_ocean", "minecraft:lukewarm_ocean", "minecraft:warm_ocean", "minecraft:stony_shore", "minecraft:swamp", "minecraft:mangrove_swamp", "minecraft:snowy_slopes", "minecraft:snowy_plains", "minecraft:snowy_beach", "minecraft:windswept_gravelly_hills", "minecraft:grove", "minecraft:windswept_hills", "minecraft:snowy_taiga", "minecraft:windswept_forest", "minecraft:taiga", "minecraft:plains", "minecraft:meadow", "minecraft:beach", "minecraft:forest", "minecraft:old_growth_spruce_taiga", "minecraft:flower_forest", "minecraft:birch_forest", "minecraft:dark_forest", "minecraft:pale_garden", "minecraft:savanna_plateau", "minecraft:savanna", "minecraft:jungle", "minecraft:badlands", "minecraft:desert", "minecraft:wooded_badlands", "minecraft:jagged_peaks", "minecraft:stony_peaks", "minecraft:frozen_river", "minecraft:river", "minecraft:ice_spikes", "minecraft:old_growth_pine_taiga", "minecraft:sunflower_plains", "minecraft:old_growth_birch_forest", "minecraft:sparse_jungle", "minecraft:bamboo_jungle", "minecraft:eroded_badlands", "minecraft:windswept_savanna", "minecraft:cherry_grove", "minecraft:frozen_peaks", "minecraft:dripstone_caves", "minecraft:lush_caves", "minecraft:sulfur_caves", "minecraft:deep_dark")),
                Map.entry("minecraft:is_river", List.of("minecraft:river", "minecraft:frozen_river")),
                Map.entry("minecraft:is_savanna", List.of("minecraft:savanna", "minecraft:savanna_plateau", "minecraft:windswept_savanna")),
                Map.entry("minecraft:is_taiga", List.of("minecraft:taiga", "minecraft:snowy_taiga", "minecraft:old_growth_pine_taiga", "minecraft:old_growth_spruce_taiga")),
                Map.entry("minecraft:mineshaft_blocking", List.of("minecraft:deep_dark")),
                Map.entry("minecraft:more_frequent_drowned_spawns", List.of("minecraft:river", "minecraft:frozen_river")),
                Map.entry("minecraft:polar_bears_spawn_on_alternate_blocks", List.of("minecraft:frozen_ocean", "minecraft:deep_frozen_ocean")),
                Map.entry("minecraft:produces_corals_from_bonemeal", List.of("minecraft:warm_ocean")),
                Map.entry("minecraft:reduce_water_ambient_spawns", List.of("minecraft:river", "minecraft:frozen_river")),
                Map.entry("minecraft:required_ocean_monument_surrounding", List.of("minecraft:deep_frozen_ocean", "minecraft:deep_cold_ocean", "minecraft:deep_ocean", "minecraft:deep_lukewarm_ocean", "minecraft:frozen_ocean", "minecraft:ocean", "minecraft:cold_ocean", "minecraft:lukewarm_ocean", "minecraft:warm_ocean", "minecraft:river", "minecraft:frozen_river")),
                Map.entry("minecraft:spawns_cold_variant_farm_animals", List.of("minecraft:snowy_plains", "minecraft:ice_spikes", "minecraft:frozen_peaks", "minecraft:jagged_peaks", "minecraft:snowy_slopes", "minecraft:frozen_ocean", "minecraft:deep_frozen_ocean", "minecraft:grove", "minecraft:deep_dark", "minecraft:frozen_river", "minecraft:snowy_taiga", "minecraft:snowy_beach", "minecraft:the_end", "minecraft:end_highlands", "minecraft:end_midlands", "minecraft:small_end_islands", "minecraft:end_barrens", "minecraft:cold_ocean", "minecraft:deep_cold_ocean", "minecraft:old_growth_pine_taiga", "minecraft:old_growth_spruce_taiga", "minecraft:taiga", "minecraft:windswept_forest", "minecraft:windswept_gravelly_hills", "minecraft:windswept_hills", "minecraft:stony_peaks")),
                Map.entry("minecraft:spawns_cold_variant_frogs", List.of("minecraft:snowy_plains", "minecraft:ice_spikes", "minecraft:frozen_peaks", "minecraft:jagged_peaks", "minecraft:snowy_slopes", "minecraft:frozen_ocean", "minecraft:deep_frozen_ocean", "minecraft:grove", "minecraft:deep_dark", "minecraft:frozen_river", "minecraft:snowy_taiga", "minecraft:snowy_beach", "minecraft:the_end", "minecraft:end_highlands", "minecraft:end_midlands", "minecraft:small_end_islands", "minecraft:end_barrens")),
                Map.entry("minecraft:spawns_coral_variant_zombie_nautilus", List.of("minecraft:warm_ocean")),
                Map.entry("minecraft:spawns_gold_rabbits", List.of("minecraft:desert")),
                Map.entry("minecraft:spawns_snow_foxes", List.of("minecraft:snowy_plains", "minecraft:ice_spikes", "minecraft:frozen_ocean", "minecraft:snowy_taiga", "minecraft:frozen_river", "minecraft:snowy_beach", "minecraft:frozen_peaks", "minecraft:jagged_peaks", "minecraft:snowy_slopes", "minecraft:grove")),
                Map.entry("minecraft:spawns_warm_variant_farm_animals", List.of("minecraft:desert", "minecraft:warm_ocean", "minecraft:bamboo_jungle", "minecraft:jungle", "minecraft:sparse_jungle", "minecraft:savanna", "minecraft:savanna_plateau", "minecraft:windswept_savanna", "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas", "minecraft:badlands", "minecraft:eroded_badlands", "minecraft:wooded_badlands", "minecraft:mangrove_swamp", "minecraft:deep_lukewarm_ocean", "minecraft:lukewarm_ocean")),
                Map.entry("minecraft:spawns_warm_variant_frogs", List.of("minecraft:desert", "minecraft:warm_ocean", "minecraft:bamboo_jungle", "minecraft:jungle", "minecraft:sparse_jungle", "minecraft:savanna", "minecraft:savanna_plateau", "minecraft:windswept_savanna", "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas", "minecraft:badlands", "minecraft:eroded_badlands", "minecraft:wooded_badlands", "minecraft:mangrove_swamp")),
                Map.entry("minecraft:spawns_white_rabbits", List.of("minecraft:snowy_plains", "minecraft:ice_spikes", "minecraft:frozen_ocean", "minecraft:snowy_taiga", "minecraft:frozen_river", "minecraft:snowy_beach", "minecraft:frozen_peaks", "minecraft:jagged_peaks", "minecraft:snowy_slopes", "minecraft:grove")),
                Map.entry("minecraft:stronghold_biased_to", List.of("minecraft:plains", "minecraft:sunflower_plains", "minecraft:snowy_plains", "minecraft:ice_spikes", "minecraft:desert", "minecraft:forest", "minecraft:flower_forest", "minecraft:birch_forest", "minecraft:dark_forest", "minecraft:pale_garden", "minecraft:old_growth_birch_forest", "minecraft:old_growth_pine_taiga", "minecraft:old_growth_spruce_taiga", "minecraft:taiga", "minecraft:snowy_taiga", "minecraft:savanna", "minecraft:savanna_plateau", "minecraft:windswept_hills", "minecraft:windswept_gravelly_hills", "minecraft:windswept_forest", "minecraft:windswept_savanna", "minecraft:jungle", "minecraft:sparse_jungle", "minecraft:bamboo_jungle", "minecraft:badlands", "minecraft:eroded_badlands", "minecraft:wooded_badlands", "minecraft:meadow", "minecraft:cherry_grove", "minecraft:grove", "minecraft:snowy_slopes", "minecraft:frozen_peaks", "minecraft:jagged_peaks", "minecraft:stony_peaks", "minecraft:mushroom_fields", "minecraft:dripstone_caves", "minecraft:lush_caves", "minecraft:sulfur_caves")),
                Map.entry("minecraft:water_on_map_outlines", List.of("minecraft:deep_frozen_ocean", "minecraft:deep_cold_ocean", "minecraft:deep_ocean", "minecraft:deep_lukewarm_ocean", "minecraft:frozen_ocean", "minecraft:ocean", "minecraft:cold_ocean", "minecraft:lukewarm_ocean", "minecraft:warm_ocean", "minecraft:river", "minecraft:frozen_river", "minecraft:swamp", "minecraft:mangrove_swamp")),
                Map.entry("minecraft:without_wandering_trader_spawns", List.of("minecraft:the_void")),
                Map.entry("minecraft:without_zombie_sieges", List.of("minecraft:mushroom_fields")));
        return new Registry("minecraft:worldgen/biome", entries, tags);
    }

    private static Registry r_zombie_nautilus_variant() {
        List<String> entries = List.of(
                "minecraft:temperate",
                "minecraft:warm");
        return new Registry("minecraft:zombie_nautilus_variant", entries, Map.of());
    }

    private static Map<String, Registry> build() {
        Map<String, Registry> m = new LinkedHashMap<>();
        m.put("minecraft:banner_pattern", r_banner_pattern());
        m.put("minecraft:cat_sound_variant", r_cat_sound_variant());
        m.put("minecraft:cat_variant", r_cat_variant());
        m.put("minecraft:chat_type", r_chat_type());
        m.put("minecraft:chicken_sound_variant", r_chicken_sound_variant());
        m.put("minecraft:chicken_variant", r_chicken_variant());
        m.put("minecraft:cow_sound_variant", r_cow_sound_variant());
        m.put("minecraft:cow_variant", r_cow_variant());
        m.put("minecraft:damage_type", r_damage_type());
        m.put("minecraft:dialog", r_dialog());
        m.put("minecraft:dimension_type", r_dimension_type());
        m.put("minecraft:enchantment", r_enchantment());
        m.put("minecraft:frog_variant", r_frog_variant());
        m.put("minecraft:instrument", r_instrument());
        m.put("minecraft:jukebox_song", r_jukebox_song());
        m.put("minecraft:painting_variant", r_painting_variant());
        m.put("minecraft:pig_sound_variant", r_pig_sound_variant());
        m.put("minecraft:pig_variant", r_pig_variant());
        m.put("minecraft:timeline", r_timeline());
        m.put("minecraft:trim_material", r_trim_material());
        m.put("minecraft:trim_pattern", r_trim_pattern());
        m.put("minecraft:wolf_sound_variant", r_wolf_sound_variant());
        m.put("minecraft:wolf_variant", r_wolf_variant());
        m.put("minecraft:world_clock", r_world_clock());
        m.put("minecraft:worldgen/biome", r_worldgen_biome());
        m.put("minecraft:zombie_nautilus_variant", r_zombie_nautilus_variant());
        return m;
    }
}
