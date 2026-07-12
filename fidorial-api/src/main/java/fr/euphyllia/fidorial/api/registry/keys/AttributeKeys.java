// GENERATED CODE - DO NOT EDIT.
// Regenerate: python3 tool/registry-generator/generate.py <project-root>
package fr.euphyllia.fidorial.api.registry.keys;

import fr.euphyllia.fidorial.api.registry.Key;
import fr.euphyllia.fidorial.api.registry.RegistryKey;
import fr.euphyllia.fidorial.api.registry.TypedKey;
import fr.euphyllia.fidorial.api.registry.data.Attribute;

/**
 * Generated {@link TypedKey} constants for the {@code minecraft:attribute} registry.
 */
public final class AttributeKeys {

    private AttributeKeys() {
    }

    public static final TypedKey<Attribute> AIR_DRAG_MODIFIER = create("air_drag_modifier");
    public static final TypedKey<Attribute> ARMOR = create("armor");
    public static final TypedKey<Attribute> ARMOR_TOUGHNESS = create("armor_toughness");
    public static final TypedKey<Attribute> ATTACK_DAMAGE = create("attack_damage");
    public static final TypedKey<Attribute> ATTACK_KNOCKBACK = create("attack_knockback");
    public static final TypedKey<Attribute> ATTACK_SPEED = create("attack_speed");
    public static final TypedKey<Attribute> BELOW_NAME_DISTANCE = create("below_name_distance");
    public static final TypedKey<Attribute> BLOCK_BREAK_SPEED = create("block_break_speed");
    public static final TypedKey<Attribute> BLOCK_INTERACTION_RANGE = create("block_interaction_range");
    public static final TypedKey<Attribute> BOUNCINESS = create("bounciness");
    public static final TypedKey<Attribute> BURNING_TIME = create("burning_time");
    public static final TypedKey<Attribute> CAMERA_DISTANCE = create("camera_distance");
    public static final TypedKey<Attribute> EXPLOSION_KNOCKBACK_RESISTANCE = create("explosion_knockback_resistance");
    public static final TypedKey<Attribute> ENTITY_INTERACTION_RANGE = create("entity_interaction_range");
    public static final TypedKey<Attribute> FALL_DAMAGE_MULTIPLIER = create("fall_damage_multiplier");
    public static final TypedKey<Attribute> FLYING_SPEED = create("flying_speed");
    public static final TypedKey<Attribute> FOLLOW_RANGE = create("follow_range");
    public static final TypedKey<Attribute> FRICTION_MODIFIER = create("friction_modifier");
    public static final TypedKey<Attribute> GRAVITY = create("gravity");
    public static final TypedKey<Attribute> JUMP_STRENGTH = create("jump_strength");
    public static final TypedKey<Attribute> KNOCKBACK_RESISTANCE = create("knockback_resistance");
    public static final TypedKey<Attribute> LUCK = create("luck");
    public static final TypedKey<Attribute> MAX_ABSORPTION = create("max_absorption");
    public static final TypedKey<Attribute> MAX_HEALTH = create("max_health");
    public static final TypedKey<Attribute> MINING_EFFICIENCY = create("mining_efficiency");
    public static final TypedKey<Attribute> MOVEMENT_EFFICIENCY = create("movement_efficiency");
    public static final TypedKey<Attribute> MOVEMENT_SPEED = create("movement_speed");
    public static final TypedKey<Attribute> NAME_TAG_DISTANCE = create("name_tag_distance");
    public static final TypedKey<Attribute> OXYGEN_BONUS = create("oxygen_bonus");
    public static final TypedKey<Attribute> SAFE_FALL_DISTANCE = create("safe_fall_distance");
    public static final TypedKey<Attribute> SCALE = create("scale");
    public static final TypedKey<Attribute> SNEAKING_SPEED = create("sneaking_speed");
    public static final TypedKey<Attribute> SPAWN_REINFORCEMENTS = create("spawn_reinforcements");
    public static final TypedKey<Attribute> STEP_HEIGHT = create("step_height");
    public static final TypedKey<Attribute> SUBMERGED_MINING_SPEED = create("submerged_mining_speed");
    public static final TypedKey<Attribute> SWEEPING_DAMAGE_RATIO = create("sweeping_damage_ratio");
    public static final TypedKey<Attribute> TEMPT_RANGE = create("tempt_range");
    public static final TypedKey<Attribute> WATER_MOVEMENT_EFFICIENCY = create("water_movement_efficiency");
    public static final TypedKey<Attribute> WAYPOINT_TRANSMIT_RANGE = create("waypoint_transmit_range");
    public static final TypedKey<Attribute> WAYPOINT_RECEIVE_RANGE = create("waypoint_receive_range");

    private static TypedKey<Attribute> create(String value) {
        return TypedKey.create(RegistryKey.ATTRIBUTE, Key.minecraft(value));
    }
}
