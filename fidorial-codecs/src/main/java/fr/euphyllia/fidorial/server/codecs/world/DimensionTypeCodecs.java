package fr.euphyllia.fidorial.server.codecs.world;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.euphyllia.fidorial.server.codecs.CommonCodecs;
import fr.fidorial.world.dimension.CardinalLight;
import fr.fidorial.world.dimension.DimensionTypeDefinition;
import fr.fidorial.world.dimension.Skybox;
import fr.fidorial.world.dimension.TimelineReference;
import fr.fidorial.world.environment.EnvironmentAttributes;
import io.papermc.adventurex.nbt.dfu.BinaryTagOps;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public final class DimensionTypeCodecs {

    public static final Codec<Skybox> SKYBOX = byId(Skybox.values(), Skybox::id, "skybox");

    public static final Codec<CardinalLight> CARDINAL_LIGHT =
            byId(CardinalLight.values(), CardinalLight::id, "cardinal light");

    public static final Codec<Key> TAG_KEY = Codec.STRING.comapFlatMap(
            s -> s.startsWith("#") && Key.parseable(s.substring(1))
                    ? DataResult.success(Key.key(s.substring(1)))
                    : DataResult.error(() -> "Expected a #tag, got: " + s),
            key -> "#" + key.asString());

    private static final Codec<TimelineReference> SINGLE_TIMELINE = Codec.STRING.comapFlatMap(
            s -> {
                final String raw = s.startsWith("#") ? s.substring(1) : s;
                if (!Key.parseable(raw)) {
                    return DataResult.error(() -> "Not a valid key: " + s);
                }
                final Key key = Key.key(raw);
                return DataResult.success(s.startsWith("#") ? TimelineReference.tag(key) : TimelineReference.id(key));
            },
            timeline -> switch (timeline) {
                case final TimelineReference.Id id -> id.key().asString();
                case final TimelineReference.Tag tag -> "#" + tag.key().asString();
            });

    private static final Codec<List<TimelineReference>> TIMELINES = Codec.either(
            SINGLE_TIMELINE.listOf(), SINGLE_TIMELINE
    ).xmap(
            either -> either.map(list -> list, List::of),
            list -> list.size() == 1 ? Either.right(list.getFirst()) : Either.left(list));

    private record Extras(Skybox skybox, CardinalLight cardinalLight, @Nullable Key defaultClock, List<TimelineReference> timelines) { }

    private static final MapCodec<Extras> EXTRAS = RecordCodecBuilder.mapCodec(instance -> instance.group(
            SKYBOX.optionalFieldOf("skybox", Skybox.OVERWORLD).forGetter(Extras::skybox),
            CARDINAL_LIGHT.optionalFieldOf("cardinal_light", CardinalLight.DEFAULT).forGetter(Extras::cardinalLight),
            CommonCodecs.KEY_CODEC.optionalFieldOf("default_clock")
                    .forGetter(e -> Optional.ofNullable(e.defaultClock())),
            TIMELINES.optionalFieldOf("timelines", List.of()).forGetter(Extras::timelines)
    ).apply(instance, (skybox, cardinalLight, defaultClock, timelines) ->
            new Extras(skybox, cardinalLight, defaultClock.orElse(null), timelines)));

    private DimensionTypeCodecs() {
        throw new UnsupportedOperationException("DimensionTypeCodecs cannot be instantiated.");
    }

    public static Codec<DimensionTypeDefinition> codec(final Key key) {
        return RecordCodecBuilder.create(instance -> instance.group(
                Codec.DOUBLE.validate(scale -> scale >= 0.00001D && scale <= 30_000_000.0D
                                ? DataResult.success(scale)
                                : DataResult.error(() -> "coordinate_scale out of range: " + scale))
                        .fieldOf("coordinate_scale").forGetter(DimensionTypeDefinition::coordinateScale),
                Codec.BOOL.fieldOf("has_skylight").forGetter(DimensionTypeDefinition::hasSkylight),
                Codec.BOOL.fieldOf("has_ceiling").forGetter(DimensionTypeDefinition::hasCeiling),
                Codec.BOOL.fieldOf("has_ender_dragon_fight")
                        .forGetter(DimensionTypeDefinition::hasEnderDragonFight),
                Codec.FLOAT.fieldOf("ambient_light").forGetter(DimensionTypeDefinition::ambientLight),
                Codec.BOOL.optionalFieldOf("has_fixed_time", false)
                        .forGetter(DimensionTypeDefinition::hasFixedTime),
                Codec.intRange(0, 15).fieldOf("monster_spawn_block_light_limit")
                        .forGetter(DimensionTypeDefinition::monsterSpawnBlockLightLimit),
                IntProviderCodecs.INT_PROVIDER.fieldOf("monster_spawn_light_level")
                        .forGetter(DimensionTypeDefinition::monsterSpawnLightLevel),
                Codec.INT.fieldOf("logical_height").forGetter(DimensionTypeDefinition::logicalHeight),
                Codec.INT.fieldOf("min_y").forGetter(DimensionTypeDefinition::minY),
                Codec.INT.fieldOf("height").forGetter(DimensionTypeDefinition::height),
                TAG_KEY.fieldOf("infiniburn").forGetter(DimensionTypeDefinition::infiniburn),
                EnvironmentAttributeCodecs.ATTRIBUTES.optionalFieldOf("attributes", EnvironmentAttributes.EMPTY)
                        .forGetter(DimensionTypeDefinition::attributes),
                EXTRAS.forGetter(d -> new Extras(d.skybox(), d.cardinalLight(), d.defaultClock(), d.timelines()))
        ).apply(instance, (scale, skylight, ceiling, dragonFight, ambient, fixedTime, blockLightLimit,
                           lightLevel, logicalHeight, minY, height, infiniburn, attributes, extras) ->
                new DimensionTypeDefinition(
                        key, scale, skylight, ceiling, dragonFight, ambient, fixedTime, blockLightLimit,
                        lightLevel, logicalHeight, minY, height, infiniburn, extras.skybox(), extras.cardinalLight(),
                        attributes, extras.defaultClock(), extras.timelines())));
    }

    public static CompoundBinaryTag encodeNbt(final DimensionTypeDefinition dimensionType) {
        final BinaryTag tag = codec(dimensionType.key())
                .encodeStart(BinaryTagOps.binaryTagOps(), dimensionType)
                .getOrThrow(message -> new IllegalStateException(
                        "Failed to encode dimension type " + dimensionType.key().asString() + ": " + message));

        if (!(tag instanceof final CompoundBinaryTag compound)) {
            throw new IllegalStateException(
                    "Dimension type " + dimensionType.key().asString() + " did not encode to a compound tag");
        }

        return compound;
    }

    public static DimensionTypeDefinition fromJson(final Key key, final String json) {
        final JsonElement element;
        try {
            element = JsonParser.parseString(json);
        } catch (final RuntimeException exception) {
            throw new IllegalArgumentException("Dimension type " + key.asString() + " is not valid JSON", exception);
        }

        return codec(key)
                .parse(JsonOps.INSTANCE, element)
                .getOrThrow(message -> new IllegalArgumentException(
                        "Failed to read dimension type " + key.asString() + ": " + message));
    }

    private static <E extends Enum<E>> Codec<E> byId(
            final E[] values,
            final Function<E, String> id,
            final String what
    ) {
        return Codec.STRING.comapFlatMap(
                name -> Arrays.stream(values)
                        .filter(value -> id.apply(value).equals(name))
                        .findFirst()
                        .map(DataResult::success)
                        .orElseGet(() -> DataResult.error(() -> "Unknown " + what + ": " + name)),
                id);
    }
}
