package fr.euphyllia.fidorial.server.world.block.blockentity;

import fr.euphyllia.fidorial.server.registry.data.BlockEntityTypeIds;
import net.kyori.adventure.key.Key;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Resolves which {@code minecraft:block_entity_type} a block carries.
 *
 * <p>Mojang's registry report exposes block entity <em>types</em> and their
 * protocol IDs, but not the block &rarr; block entity association: that link
 * lives in code. This class rebuilds it from an explicit table for one-off
 * blocks plus a handful of suffix rules covering the large colour/wood
 * families (signs, beds, banners, shulker boxes, &hellip;).</p>
 *
 * <p>Protocol IDs are never hard-coded here: they are read from the generated
 * {@link BlockEntityTypeIds} lookup table, so a Minecraft update that reorders
 * the registry is picked up automatically.</p>
 *
 * @since 0.1.0
 */
public final class BlockEntityTypes {

    /**
     * Suffix rules, evaluated in insertion order. The first match wins, so the
     * more specific suffixes must be declared first.
     */
    private static final Map<String, Key> SUFFIX_RULES = suffixRules();

    private static final Map<Key, Key> EXACT_BLOCKS = exactBlocks();

    /**
     * Blocks whose name matches a suffix rule but which carry no block entity.
     */
    private static final Set<Key> SUFFIX_EXCLUSIONS = Set.of(Key.key("piston_head"));

    private BlockEntityTypes() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the block entity type identifier carried by a block.
     *
     * @param blockIdentifier namespaced block identifier, e.g. {@code minecraft:chest}
     *
     * @return the block entity type identifier, or {@link Optional#empty()} when
     *         the block has no block entity
     */
    public static Optional<Key> typeIdentifier(final Key blockIdentifier) {
        final Key exact = EXACT_BLOCKS.get(blockIdentifier);
        if (exact != null) {
            return Optional.of(exact);
        }

        if (SUFFIX_EXCLUSIONS.contains(blockIdentifier)) {
            return Optional.empty();
        }

        for (final Map.Entry<String, Key> rule : SUFFIX_RULES.entrySet()) {
            if (blockIdentifier.value().endsWith(rule.getKey())) {
                return Optional.of(rule.getValue());
            }
        }

        return Optional.empty();
    }

    /**
     * Returns the protocol ID of the block entity carried by a block.
     *
     * @param blockIdentifier namespaced block identifier
     *
     * @return the protocol ID, or {@link BlockEntityTypeIds#UNKNOWN} when the
     *         block carries no block entity, or when the resolved type is absent
     *         from the current registry
     */
    public static int protocolId(final Key blockIdentifier) {

        return typeIdentifier(blockIdentifier)
                .map(BlockEntityTypeIds::id)
                .orElse(BlockEntityTypeIds.UNKNOWN);
    }

    /**
     * Returns whether a block carries a block entity known to the current registry.
     *
     * @param blockIdentifier namespaced block identifier
     *
     * @return {@code true} when a protocol ID could be resolved
     */
    public static boolean hasBlockEntity(final Key blockIdentifier) {
        return protocolId(blockIdentifier) != BlockEntityTypeIds.UNKNOWN;
    }

    private static Map<String, Key> suffixRules() {

        final Map<String, Key> rules = new LinkedHashMap<>();

        rules.put("_hanging_sign", Key.key("hanging_sign"));
        rules.put("_wall_sign", Key.key("sign"));
        rules.put("_sign", Key.key("sign"));
        rules.put("_bed", Key.key("bed"));
        rules.put("_banner", Key.key("banner"));
        rules.put("_shulker_box", Key.key("shulker_box"));
        rules.put("_skull", Key.key("skull"));
        rules.put("_head", Key.key("skull"));
        rules.put("_campfire", Key.key("campfire"));
        rules.put("_beehive", Key.key("beehive"));
        rules.put("_command_block", Key.key("command_block"));

        /*
         * Iteration order is significant, so the map cannot be copied into an
         * unordered Map.copyOf view.
         */
        return Collections.unmodifiableMap(rules);
    }

    private static Map<Key, Key> exactBlocks() {

        final Map<Key, Key> blocks = new LinkedHashMap<>();

        put(blocks, "barrel", "barrel");
        put(blocks, "beacon", "beacon");
        put(blocks, "bee_nest", "beehive");
        put(blocks, "beehive", "beehive");
        put(blocks, "bell", "bell");
        put(blocks, "blast_furnace", "blast_furnace");
        put(blocks, "brewing_stand", "brewing_stand");
        put(blocks, "calibrated_sculk_sensor", "calibrated_sculk_sensor");
        put(blocks, "campfire", "campfire");
        put(blocks, "chest", "chest");
        put(blocks, "chiseled_bookshelf", "chiseled_bookshelf");
        put(blocks, "command_block", "command_block");
        put(blocks, "comparator", "comparator");
        put(blocks, "conduit", "conduit");
        put(blocks, "crafter", "crafter");
        put(blocks, "creaking_heart", "creaking_heart");
        put(blocks, "daylight_detector", "daylight_detector");
        put(blocks, "decorated_pot", "decorated_pot");
        put(blocks, "dispenser", "dispenser");
        put(blocks, "dropper", "dropper");
        put(blocks, "enchanting_table", "enchanting_table");
        put(blocks, "end_gateway", "end_gateway");
        put(blocks, "end_portal", "end_portal");
        put(blocks, "ender_chest", "ender_chest");
        put(blocks, "furnace", "furnace");
        put(blocks, "hopper", "hopper");
        put(blocks, "jigsaw", "jigsaw");
        put(blocks, "jukebox", "jukebox");
        put(blocks, "lectern", "lectern");
        put(blocks, "moving_piston", "piston");
        put(blocks, "sculk_catalyst", "sculk_catalyst");
        put(blocks, "sculk_sensor", "sculk_sensor");
        put(blocks, "sculk_shrieker", "sculk_shrieker");
        put(blocks, "shulker_box", "shulker_box");
        put(blocks, "skull", "skull");
        put(blocks, "smoker", "smoker");
        put(blocks, "soul_campfire", "campfire");
        put(blocks, "spawner", "mob_spawner");
        put(blocks, "structure_block", "structure_block");
        put(blocks, "suspicious_gravel", "brushable_block");
        put(blocks, "suspicious_sand", "brushable_block");
        put(blocks, "test_block", "test_block");
        put(blocks, "test_instance_block", "test_instance_block");
        put(blocks, "trapped_chest", "trapped_chest");
        put(blocks, "trial_spawner", "trial_spawner");
        put(blocks, "vault", "vault");

        return Map.copyOf(blocks);
    }

    private static void put(final Map<Key, Key> blocks, final String block, final String blockEntityType) {
        blocks.put(Key.key(block), Key.key(blockEntityType));
    }
}
