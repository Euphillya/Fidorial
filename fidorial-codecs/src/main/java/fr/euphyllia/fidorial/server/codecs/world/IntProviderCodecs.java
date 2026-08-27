package fr.euphyllia.fidorial.server.codecs.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.euphyllia.fidorial.server.codecs.DispatchCodecs;
import fr.fidorial.world.dimension.IntProvider;

import java.util.List;

public final class IntProviderCodecs {

    public static final Codec<IntProvider> INT_PROVIDER =
            Codec.recursive("fidorial:int_provider", IntProviderCodecs::createCodec);

    private IntProviderCodecs() {
        throw new UnsupportedOperationException("IntProviderCodecs cannot be instantiated.");
    }

    private static Codec<IntProvider> createCodec(final Codec<IntProvider> self) {
        final MapCodec<IntProvider.Constant> constantCodec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.INT.fieldOf("value").forGetter(IntProvider.Constant::value)
        ).apply(instance, IntProvider.Constant::new));

        final MapCodec<IntProvider.Uniform> uniformCodec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.INT.fieldOf("min_inclusive").forGetter(IntProvider.Uniform::minInclusive),
                Codec.INT.fieldOf("max_inclusive").forGetter(IntProvider.Uniform::maxInclusive)
        ).apply(instance, IntProvider.Uniform::new));

        final MapCodec<IntProvider.BiasedToBottom> biasedCodec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.INT.fieldOf("min_inclusive").forGetter(IntProvider.BiasedToBottom::minInclusive),
                Codec.INT.fieldOf("max_inclusive").forGetter(IntProvider.BiasedToBottom::maxInclusive)
        ).apply(instance, IntProvider.BiasedToBottom::new));

        final MapCodec<IntProvider.Clamped> clampedCodec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.INT.fieldOf("min_inclusive").forGetter(IntProvider.Clamped::minInclusive),
                Codec.INT.fieldOf("max_inclusive").forGetter(IntProvider.Clamped::maxInclusive),
                self.fieldOf("source").forGetter(IntProvider.Clamped::source)
        ).apply(instance, IntProvider.Clamped::new));

        final MapCodec<IntProvider.ClampedNormal> clampedNormalCodec =
                RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Codec.FLOAT.fieldOf("mean").forGetter(IntProvider.ClampedNormal::mean),
                        Codec.FLOAT.fieldOf("deviation").forGetter(IntProvider.ClampedNormal::deviation),
                        Codec.INT.fieldOf("min_inclusive").forGetter(IntProvider.ClampedNormal::minInclusive),
                        Codec.INT.fieldOf("max_inclusive").forGetter(IntProvider.ClampedNormal::maxInclusive)
                ).apply(instance, IntProvider.ClampedNormal::new));

        final Codec<IntProvider.WeightedList.Entry> entryCodec = RecordCodecBuilder.create(instance -> instance.group(
                self.fieldOf("data").forGetter(IntProvider.WeightedList.Entry::data),
                Codec.INT.fieldOf("weight").forGetter(IntProvider.WeightedList.Entry::weight)
        ).apply(instance, IntProvider.WeightedList.Entry::new));

        final MapCodec<IntProvider.WeightedList> weightedListCodec = entryCodec.listOf()
                .fieldOf("distribution")
                .xmap(IntProvider.WeightedList::new, IntProvider.WeightedList::distribution);

        final Codec<IntProvider> dispatch = DispatchCodecs.<IntProvider>matcher("type", List.of(
                DispatchCodecs.Variant.of("constant", null, true, IntProvider.Constant.class, constantCodec),
                DispatchCodecs.Variant.of("uniform", null, true, IntProvider.Uniform.class, uniformCodec),
                DispatchCodecs.Variant.of(
                        "biased_to_bottom", null, true, IntProvider.BiasedToBottom.class, biasedCodec),
                DispatchCodecs.Variant.of("clamped", null, true, IntProvider.Clamped.class, clampedCodec),
                DispatchCodecs.Variant.of(
                        "clamped_normal", null, true, IntProvider.ClampedNormal.class, clampedNormalCodec),
                DispatchCodecs.Variant.of(
                        "weighted_list", null, true, IntProvider.WeightedList.class, weightedListCodec)
        )).codec();

        return Codec.either(Codec.INT, dispatch).xmap(
                either -> either.map(IntProvider.Constant::new, provider -> provider),
                provider -> provider instanceof IntProvider.Constant(int value)
                        ? com.mojang.datafixers.util.Either.left(value)
                        : com.mojang.datafixers.util.Either.right(provider));
    }
}
