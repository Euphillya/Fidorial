package fr.euphyllia.fidorial.server.registry.data;

import net.kyori.adventure.key.Key;

import java.util.Map;

/**
 * Network IDs for entries in the {@code minecraft:block_entity_type} registry.
 *
 * <p>Generated from Mojang's registry report; do not edit.</p>
 */
public interface BlockEntityTypeIds {
    /**
     * Returned by {@link #id(Key)} when the identifier is unknown.
     */
    int UNKNOWN = -1;

    /**
     * {@code minecraft:banner}
     */
    int BANNER_BLOCK_ENTITY_ID = 20;

    /**
     * {@code minecraft:barrel}
     */
    int BARREL_BLOCK_ENTITY_ID = 26;

    /**
     * {@code minecraft:beacon}
     */
    int BEACON_BLOCK_ENTITY_ID = 15;

    /**
     * {@code minecraft:beehive}
     */
    int BEEHIVE_BLOCK_ENTITY_ID = 33;

    /**
     * {@code minecraft:bell}
     */
    int BELL_BLOCK_ENTITY_ID = 30;

    /**
     * {@code minecraft:blast_furnace}
     */
    int BLAST_FURNACE_BLOCK_ENTITY_ID = 28;

    /**
     * {@code minecraft:brewing_stand}
     */
    int BREWING_STAND_BLOCK_ENTITY_ID = 12;

    /**
     * {@code minecraft:brushable_block}
     */
    int BRUSHABLE_BLOCK_BLOCK_ENTITY_ID = 40;

    /**
     * {@code minecraft:calibrated_sculk_sensor}
     */
    int CALIBRATED_SCULK_SENSOR_BLOCK_ENTITY_ID = 35;

    /**
     * {@code minecraft:campfire}
     */
    int CAMPFIRE_BLOCK_ENTITY_ID = 32;

    /**
     * {@code minecraft:chest}
     */
    int CHEST_BLOCK_ENTITY_ID = 1;

    /**
     * {@code minecraft:chiseled_bookshelf}
     */
    int CHISELED_BOOKSHELF_BLOCK_ENTITY_ID = 38;

    /**
     * {@code minecraft:command_block}
     */
    int COMMAND_BLOCK_BLOCK_ENTITY_ID = 23;

    /**
     * {@code minecraft:comparator}
     */
    int COMPARATOR_BLOCK_ENTITY_ID = 19;

    /**
     * {@code minecraft:conduit}
     */
    int CONDUIT_BLOCK_ENTITY_ID = 25;

    /**
     * {@code minecraft:copper_golem_statue}
     */
    int COPPER_GOLEM_STATUE_BLOCK_ENTITY_ID = 47;

    /**
     * {@code minecraft:crafter}
     */
    int CRAFTER_BLOCK_ENTITY_ID = 42;

    /**
     * {@code minecraft:creaking_heart}
     */
    int CREAKING_HEART_BLOCK_ENTITY_ID = 10;

    /**
     * {@code minecraft:daylight_detector}
     */
    int DAYLIGHT_DETECTOR_BLOCK_ENTITY_ID = 17;

    /**
     * {@code minecraft:decorated_pot}
     */
    int DECORATED_POT_BLOCK_ENTITY_ID = 41;

    /**
     * {@code minecraft:dispenser}
     */
    int DISPENSER_BLOCK_ENTITY_ID = 5;

    /**
     * {@code minecraft:dropper}
     */
    int DROPPER_BLOCK_ENTITY_ID = 6;

    /**
     * {@code minecraft:enchanting_table}
     */
    int ENCHANTING_TABLE_BLOCK_ENTITY_ID = 13;

    /**
     * {@code minecraft:end_gateway}
     */
    int END_GATEWAY_BLOCK_ENTITY_ID = 22;

    /**
     * {@code minecraft:end_portal}
     */
    int END_PORTAL_BLOCK_ENTITY_ID = 14;

    /**
     * {@code minecraft:ender_chest}
     */
    int ENDER_CHEST_BLOCK_ENTITY_ID = 3;

    /**
     * {@code minecraft:furnace}
     */
    int FURNACE_BLOCK_ENTITY_ID = 0;

    /**
     * {@code minecraft:hanging_sign}
     */
    int HANGING_SIGN_BLOCK_ENTITY_ID = 8;

    /**
     * {@code minecraft:hopper}
     */
    int HOPPER_BLOCK_ENTITY_ID = 18;

    /**
     * {@code minecraft:jigsaw}
     */
    int JIGSAW_BLOCK_ENTITY_ID = 31;

    /**
     * {@code minecraft:jukebox}
     */
    int JUKEBOX_BLOCK_ENTITY_ID = 4;

    /**
     * {@code minecraft:lectern}
     */
    int LECTERN_BLOCK_ENTITY_ID = 29;

    /**
     * {@code minecraft:mob_spawner}
     */
    int MOB_SPAWNER_BLOCK_ENTITY_ID = 9;

    /**
     * {@code minecraft:piston}
     */
    int PISTON_BLOCK_ENTITY_ID = 11;

    /**
     * {@code minecraft:potent_sulfur}
     */
    int POTENT_SULFUR_BLOCK_ENTITY_ID = 48;

    /**
     * {@code minecraft:sculk_catalyst}
     */
    int SCULK_CATALYST_BLOCK_ENTITY_ID = 36;

    /**
     * {@code minecraft:sculk_sensor}
     */
    int SCULK_SENSOR_BLOCK_ENTITY_ID = 34;

    /**
     * {@code minecraft:sculk_shrieker}
     */
    int SCULK_SHRIEKER_BLOCK_ENTITY_ID = 37;

    /**
     * {@code minecraft:shelf}
     */
    int SHELF_BLOCK_ENTITY_ID = 39;

    /**
     * {@code minecraft:shulker_box}
     */
    int SHULKER_BOX_BLOCK_ENTITY_ID = 24;

    /**
     * {@code minecraft:sign}
     */
    int SIGN_BLOCK_ENTITY_ID = 7;

    /**
     * {@code minecraft:skull}
     */
    int SKULL_BLOCK_ENTITY_ID = 16;

    /**
     * {@code minecraft:smoker}
     */
    int SMOKER_BLOCK_ENTITY_ID = 27;

    /**
     * {@code minecraft:structure_block}
     */
    int STRUCTURE_BLOCK_BLOCK_ENTITY_ID = 21;

    /**
     * {@code minecraft:test_block}
     */
    int TEST_BLOCK_BLOCK_ENTITY_ID = 45;

    /**
     * {@code minecraft:test_instance_block}
     */
    int TEST_INSTANCE_BLOCK_BLOCK_ENTITY_ID = 46;

    /**
     * {@code minecraft:trapped_chest}
     */
    int TRAPPED_CHEST_BLOCK_ENTITY_ID = 2;

    /**
     * {@code minecraft:trial_spawner}
     */
    int TRIAL_SPAWNER_BLOCK_ENTITY_ID = 43;

    /**
     * {@code minecraft:vault}
     */
    int VAULT_BLOCK_ENTITY_ID = 44;

    /**
     * Immutable identifier to protocol ID lookup table.
     */
    Map<Key, Integer> BY_IDENTIFIER = Map.ofEntries(
            Map.entry(Key.key("minecraft:banner"), BANNER_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:barrel"), BARREL_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:beacon"), BEACON_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:beehive"), BEEHIVE_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:bell"), BELL_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:blast_furnace"), BLAST_FURNACE_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:brewing_stand"), BREWING_STAND_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:brushable_block"), BRUSHABLE_BLOCK_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:calibrated_sculk_sensor"), CALIBRATED_SCULK_SENSOR_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:campfire"), CAMPFIRE_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:chest"), CHEST_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:chiseled_bookshelf"), CHISELED_BOOKSHELF_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:command_block"), COMMAND_BLOCK_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:comparator"), COMPARATOR_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:conduit"), CONDUIT_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:copper_golem_statue"), COPPER_GOLEM_STATUE_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:crafter"), CRAFTER_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:creaking_heart"), CREAKING_HEART_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:daylight_detector"), DAYLIGHT_DETECTOR_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:decorated_pot"), DECORATED_POT_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:dispenser"), DISPENSER_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:dropper"), DROPPER_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:enchanting_table"), ENCHANTING_TABLE_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:end_gateway"), END_GATEWAY_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:end_portal"), END_PORTAL_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:ender_chest"), ENDER_CHEST_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:furnace"), FURNACE_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:hanging_sign"), HANGING_SIGN_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:hopper"), HOPPER_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:jigsaw"), JIGSAW_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:jukebox"), JUKEBOX_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:lectern"), LECTERN_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:mob_spawner"), MOB_SPAWNER_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:piston"), PISTON_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:potent_sulfur"), POTENT_SULFUR_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:sculk_catalyst"), SCULK_CATALYST_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:sculk_sensor"), SCULK_SENSOR_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:sculk_shrieker"), SCULK_SHRIEKER_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:shelf"), SHELF_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:shulker_box"), SHULKER_BOX_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:sign"), SIGN_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:skull"), SKULL_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:smoker"), SMOKER_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:structure_block"), STRUCTURE_BLOCK_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:test_block"), TEST_BLOCK_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:test_instance_block"), TEST_INSTANCE_BLOCK_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:trapped_chest"), TRAPPED_CHEST_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:trial_spawner"), TRIAL_SPAWNER_BLOCK_ENTITY_ID),
            Map.entry(Key.key("minecraft:vault"), VAULT_BLOCK_ENTITY_ID)
    );

    /**
     * Resolves the protocol ID for a namespaced identifier.
     *
     * @param identifier namespaced identifier, e.g. {@code Key.key("minecraft", "chest")}
     * @return the protocol ID, or {@link #UNKNOWN} when the identifier is unknown
     */
    static int id(final Key identifier) {
        return BY_IDENTIFIER.getOrDefault(identifier, UNKNOWN);
    }
}
