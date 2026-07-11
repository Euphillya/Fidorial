package fr.euphyllia.fidorial.server.world;

import fr.euphyllia.fidorial.server.world.nbt.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * dimension_type COMPLET (26.2) construit a la main avec les types NBT exacts
 * attendus par le client : couleurs en chaine hexa, cloud_height/offset/
 * ambient_light en FLOAT, coordinate_scale en DOUBLE, le reste INT/BYTE.
 * Genere depuis les .json vanilla 26.2 ; schema de types tire du codec Minestom.
 */
public final class DimensionTypeData {

    private DimensionTypeData() {
    }

    public static Map<String, NbtCompound> all() {
        Map<String, NbtCompound> map = new LinkedHashMap<>();
        map.put("minecraft:overworld", overworld());
        map.put("minecraft:overworld_caves", overworldCaves());
        map.put("minecraft:the_nether", nether());
        map.put("minecraft:the_end", end());
        return map;
    }

    private static NbtCompound overworld() {
        return new NbtCompound()
                .put("ambient_light", new NbtFloat(0.0f))
                .put("attributes", new NbtCompound()
                        .put("minecraft:audio/ambient_sounds", new NbtCompound()
                                .put("mood", new NbtCompound()
                                        .put("block_search_extent", new NbtInt(8))
                                        .put("offset", new NbtFloat(2.0f))
                                        .put("sound", new NbtString("minecraft:ambient.cave"))
                                        .put("tick_delay", new NbtInt(6000))))
                        .put("minecraft:audio/background_music", new NbtCompound()
                                .put("creative", new NbtCompound()
                                        .put("max_delay", new NbtInt(24000))
                                        .put("min_delay", new NbtInt(12000))
                                        .put("sound", new NbtString("minecraft:music.creative")))
                                .put("default", new NbtCompound()
                                        .put("max_delay", new NbtInt(24000))
                                        .put("min_delay", new NbtInt(12000))
                                        .put("sound", new NbtString("minecraft:music.game"))))
                        .put("minecraft:gameplay/bed_rule", new NbtCompound()
                                .put("can_set_spawn", new NbtString("always"))
                                .put("can_sleep", new NbtString("when_dark"))
                                .put("error_message", new NbtCompound()
                                        .put("translate", new NbtString("block.minecraft.bed.no_sleep"))))
                        .put("minecraft:gameplay/nether_portal_spawns_piglin", new NbtByte((byte) 1))
                        .put("minecraft:gameplay/respawn_anchor_works", new NbtByte((byte) 0))
                        .put("minecraft:visual/ambient_light_color", new NbtString("#0a0a0a"))
                        .put("minecraft:visual/cloud_color", new NbtString("#ccffffff"))
                        .put("minecraft:visual/cloud_height", new NbtFloat(192.33f))
                        .put("minecraft:visual/fog_color", new NbtString("#c0d8ff"))
                        .put("minecraft:visual/sky_color", new NbtString("#78a7ff")))
                .put("coordinate_scale", new NbtDouble(1.0d))
                .put("default_clock", new NbtString("minecraft:overworld"))
                .put("has_ceiling", new NbtByte((byte) 0))
                .put("has_ender_dragon_fight", new NbtByte((byte) 0))
                .put("has_skylight", new NbtByte((byte) 1))
                .put("height", new NbtInt(384))
                .put("infiniburn", new NbtString("#minecraft:infiniburn_overworld"))
                .put("logical_height", new NbtInt(384))
                .put("min_y", new NbtInt(-64))
                .put("monster_spawn_block_light_limit", new NbtInt(0))
                .put("monster_spawn_light_level", new NbtCompound()
                        .put("type", new NbtString("minecraft:uniform"))
                        .put("max_inclusive", new NbtInt(7))
                        .put("min_inclusive", new NbtInt(0)))
                .put("timelines", new NbtString("#minecraft:in_overworld"));
    }

    private static NbtCompound overworldCaves() {
        return new NbtCompound()
                .put("ambient_light", new NbtFloat(0.0f))
                .put("attributes", new NbtCompound()
                        .put("minecraft:audio/ambient_sounds", new NbtCompound()
                                .put("mood", new NbtCompound()
                                        .put("block_search_extent", new NbtInt(8))
                                        .put("offset", new NbtFloat(2.0f))
                                        .put("sound", new NbtString("minecraft:ambient.cave"))
                                        .put("tick_delay", new NbtInt(6000))))
                        .put("minecraft:audio/background_music", new NbtCompound()
                                .put("creative", new NbtCompound()
                                        .put("max_delay", new NbtInt(24000))
                                        .put("min_delay", new NbtInt(12000))
                                        .put("sound", new NbtString("minecraft:music.creative")))
                                .put("default", new NbtCompound()
                                        .put("max_delay", new NbtInt(24000))
                                        .put("min_delay", new NbtInt(12000))
                                        .put("sound", new NbtString("minecraft:music.game"))))
                        .put("minecraft:gameplay/bed_rule", new NbtCompound()
                                .put("can_set_spawn", new NbtString("always"))
                                .put("can_sleep", new NbtString("when_dark"))
                                .put("error_message", new NbtCompound()
                                        .put("translate", new NbtString("block.minecraft.bed.no_sleep"))))
                        .put("minecraft:gameplay/nether_portal_spawns_piglin", new NbtByte((byte) 1))
                        .put("minecraft:gameplay/respawn_anchor_works", new NbtByte((byte) 0))
                        .put("minecraft:visual/ambient_light_color", new NbtString("#0a0a0a"))
                        .put("minecraft:visual/cloud_color", new NbtString("#ccffffff"))
                        .put("minecraft:visual/cloud_height", new NbtFloat(192.33f))
                        .put("minecraft:visual/fog_color", new NbtString("#c0d8ff"))
                        .put("minecraft:visual/sky_color", new NbtString("#78a7ff")))
                .put("coordinate_scale", new NbtDouble(1.0d))
                .put("default_clock", new NbtString("minecraft:overworld"))
                .put("has_ceiling", new NbtByte((byte) 1))
                .put("has_ender_dragon_fight", new NbtByte((byte) 0))
                .put("has_skylight", new NbtByte((byte) 1))
                .put("height", new NbtInt(384))
                .put("infiniburn", new NbtString("#minecraft:infiniburn_overworld"))
                .put("logical_height", new NbtInt(384))
                .put("min_y", new NbtInt(-64))
                .put("monster_spawn_block_light_limit", new NbtInt(0))
                .put("monster_spawn_light_level", new NbtCompound()
                        .put("type", new NbtString("minecraft:uniform"))
                        .put("max_inclusive", new NbtInt(7))
                        .put("min_inclusive", new NbtInt(0)))
                .put("timelines", new NbtString("#minecraft:in_overworld"));
    }

    private static NbtCompound nether() {
        return new NbtCompound()
                .put("ambient_light", new NbtFloat(0.1f))
                .put("attributes", new NbtCompound()
                        .put("minecraft:gameplay/bed_rule", new NbtCompound()
                                .put("can_set_spawn", new NbtString("never"))
                                .put("can_sleep", new NbtString("never"))
                                .put("explodes", new NbtByte((byte) 1)))
                        .put("minecraft:gameplay/can_start_raid", new NbtByte((byte) 0))
                        .put("minecraft:gameplay/fast_lava", new NbtByte((byte) 1))
                        .put("minecraft:gameplay/piglins_zombify", new NbtByte((byte) 0))
                        .put("minecraft:gameplay/respawn_anchor_works", new NbtByte((byte) 1))
                        .put("minecraft:gameplay/sky_light_level", new NbtFloat(4.0f))
                        .put("minecraft:gameplay/snow_golem_melts", new NbtByte((byte) 1))
                        .put("minecraft:gameplay/water_evaporates", new NbtByte((byte) 1))
                        .put("minecraft:visual/ambient_light_color", new NbtString("#302821"))
                        .put("minecraft:visual/default_dripstone_particle", new NbtCompound()
                                .put("type", new NbtString("minecraft:dripping_dripstone_lava")))
                        .put("minecraft:visual/fog_end_distance", new NbtFloat(96.0f))
                        .put("minecraft:visual/fog_start_distance", new NbtFloat(10.0f))
                        .put("minecraft:visual/sky_light_color", new NbtString("#7a7aff"))
                        .put("minecraft:visual/sky_light_factor", new NbtFloat(0.0f)))
                .put("cardinal_light", new NbtString("nether"))
                .put("coordinate_scale", new NbtDouble(8.0d))
                .put("has_ceiling", new NbtByte((byte) 1))
                .put("has_ender_dragon_fight", new NbtByte((byte) 0))
                .put("has_fixed_time", new NbtByte((byte) 1))
                .put("has_skylight", new NbtByte((byte) 0))
                .put("height", new NbtInt(256))
                .put("infiniburn", new NbtString("#minecraft:infiniburn_nether"))
                .put("logical_height", new NbtInt(128))
                .put("min_y", new NbtInt(0))
                .put("monster_spawn_block_light_limit", new NbtInt(15))
                .put("monster_spawn_light_level", new NbtInt(7))
                .put("skybox", new NbtString("none"))
                .put("timelines", new NbtString("#minecraft:in_nether"));
    }

    private static NbtCompound end() {
        return new NbtCompound()
                .put("ambient_light", new NbtFloat(0.25f))
                .put("attributes", new NbtCompound()
                        .put("minecraft:audio/ambient_sounds", new NbtCompound()
                                .put("mood", new NbtCompound()
                                        .put("block_search_extent", new NbtInt(8))
                                        .put("offset", new NbtFloat(2.0f))
                                        .put("sound", new NbtString("minecraft:ambient.cave"))
                                        .put("tick_delay", new NbtInt(6000))))
                        .put("minecraft:audio/background_music", new NbtCompound()
                                .put("default", new NbtCompound()
                                        .put("max_delay", new NbtInt(24000))
                                        .put("min_delay", new NbtInt(6000))
                                        .put("replace_current_music", new NbtByte((byte) 1))
                                        .put("sound", new NbtString("minecraft:music.end"))))
                        .put("minecraft:gameplay/bed_rule", new NbtCompound()
                                .put("can_set_spawn", new NbtString("never"))
                                .put("can_sleep", new NbtString("never"))
                                .put("explodes", new NbtByte((byte) 1)))
                        .put("minecraft:gameplay/respawn_anchor_works", new NbtByte((byte) 0))
                        .put("minecraft:visual/ambient_light_color", new NbtString("#3f473f"))
                        .put("minecraft:visual/fog_color", new NbtString("#181318"))
                        .put("minecraft:visual/sky_color", new NbtString("#000000"))
                        .put("minecraft:visual/sky_light_color", new NbtString("#ac60cd"))
                        .put("minecraft:visual/sky_light_factor", new NbtFloat(0.0f)))
                .put("coordinate_scale", new NbtDouble(1.0d))
                .put("default_clock", new NbtString("minecraft:the_end"))
                .put("has_ceiling", new NbtByte((byte) 0))
                .put("has_ender_dragon_fight", new NbtByte((byte) 1))
                .put("has_fixed_time", new NbtByte((byte) 1))
                .put("has_skylight", new NbtByte((byte) 1))
                .put("height", new NbtInt(256))
                .put("infiniburn", new NbtString("#minecraft:infiniburn_end"))
                .put("logical_height", new NbtInt(256))
                .put("min_y", new NbtInt(0))
                .put("monster_spawn_block_light_limit", new NbtInt(0))
                .put("monster_spawn_light_level", new NbtInt(15))
                .put("skybox", new NbtString("end"))
                .put("timelines", new NbtString("#minecraft:in_end"));
    }
}