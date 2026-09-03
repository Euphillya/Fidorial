package fr.euphyllia.fidorial.server.codecs.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.fidorial.item.component.AttackRange;

public final class ItemComponentCodecs {

    private static final Codec<Float> REACH_CODEC = Codec.floatRange(0.0F, AttackRange.MAX_REACH_LIMIT);

    public static final Codec<AttackRange> ATTACK_RANGE_CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    REACH_CODEC.optionalFieldOf("min_reach", AttackRange.DEFAULT_MIN_REACH)
                            .forGetter(AttackRange::minReach),
                    REACH_CODEC.optionalFieldOf("max_reach", AttackRange.DEFAULT_MAX_REACH)
                            .forGetter(AttackRange::maxReach),
                    REACH_CODEC.optionalFieldOf("min_creative_reach", AttackRange.DEFAULT_MIN_CREATIVE_REACH)
                            .forGetter(AttackRange::minCreativeReach),
                    REACH_CODEC.optionalFieldOf("max_creative_reach", AttackRange.DEFAULT_MAX_CREATIVE_REACH)
                            .forGetter(AttackRange::maxCreativeReach),
                    Codec.floatRange(0.0F, AttackRange.MAX_HITBOX_MARGIN)
                            .optionalFieldOf("hitbox_margin", AttackRange.DEFAULT_HITBOX_MARGIN)
                            .forGetter(AttackRange::hitboxMargin),
                    Codec.floatRange(0.0F, AttackRange.MAX_MOB_FACTOR)
                            .optionalFieldOf("mob_factor", AttackRange.DEFAULT_MOB_FACTOR)
                            .forGetter(AttackRange::mobFactor)
            ).apply(instance, AttackRange::new));

    private ItemComponentCodecs() {
        throw new UnsupportedOperationException("ItemComponentCodecs cannot be instantiated.");
    }
}
