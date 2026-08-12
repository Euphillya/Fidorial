package fr.euphyllia.fidorial.server.codecs.adventure;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.BlockNBTComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.EntityNBTComponent;
import net.kyori.adventure.text.KeybindComponent;
import net.kyori.adventure.text.NBTComponent;
import net.kyori.adventure.text.ObjectComponent;
import net.kyori.adventure.text.ScoreComponent;
import net.kyori.adventure.text.SelectorComponent;
import net.kyori.adventure.text.StorageNBTComponent;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.TranslationArgument;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.ShadowColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.object.ObjectContents;
import net.kyori.adventure.text.object.PlayerHeadObjectContents;
import net.kyori.adventure.text.object.SpriteObjectContents;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class ComponentCodecs {

    private ComponentCodecs() {
    }

    public static final Codec<UUID> UUID_CODEC = Codec.INT_STREAM.comapFlatMap(
            stream -> {
                final int[] i = stream.toArray();
                if (i.length != 4) {
                    return DataResult.error(() -> "Expected 4 ints for a UUID, got " + i.length);
                }
                return DataResult.success(new UUID(
                        ((long) i[0] << 32) | (i[1] & 0xFFFFFFFFL),
                        ((long) i[2] << 32) | (i[3] & 0xFFFFFFFFL)));
            },
            uuid -> IntStream.of(
                    (int) (uuid.getMostSignificantBits() >> 32),
                    (int) uuid.getMostSignificantBits(),
                    (int) (uuid.getLeastSignificantBits() >> 32),
                    (int) uuid.getLeastSignificantBits())
    );

    public static final Codec<Key> KEY_CODEC = Codec.STRING.comapFlatMap(
            s -> Key.parseable(s)
                    ? DataResult.success(Key.key(s))
                    : DataResult.error(() -> "Not a valid key: " + s),
            Key::asString
    );

    public static final Codec<TextColor> COLOR_CODEC = Codec.STRING.comapFlatMap(
            s -> {
                final TextColor color = s.startsWith("#") ? TextColor.fromHexString(s) : NamedTextColor.NAMES.value(s);
                return color != null
                        ? DataResult.success(color)
                        : DataResult.error(() -> "Unknown color: " + s);
            },
            color -> color instanceof final NamedTextColor named ? named.toString() : color.asHexString()
    );

    public static final Codec<Component> COMPONENT_CODEC =
            Codec.recursive("adventure:component", ComponentCodecs::createCodec);

    public static final Codec<ClickEvent<?>> CLICK_EVENT_CODEC = Codec.STRING.dispatch(
            "action",
            ComponentCodecs::clickActionName,
            ComponentCodecs::clickMapCodecFor
    );

    private static String clickActionName(final ClickEvent<?> event) {
        return switch (event.action()) {
            case ClickEvent.Action.OpenUrl _ -> "open_url";
            case ClickEvent.Action.OpenFile _ -> "open_file";
            case ClickEvent.Action.RunCommand _ -> "run_command";
            case ClickEvent.Action.SuggestCommand _ -> "suggest_command";
            case ClickEvent.Action.ChangePage _ -> "change_page";
            case ClickEvent.Action.CopyToClipboard _ -> "copy_to_clipboard";
            case ClickEvent.Action.Custom _ -> "custom";
            default -> throw new UnsupportedOperationException("Unsupported click action: " + event.action());
        };
    }

    private static MapCodec<? extends ClickEvent<?>> clickMapCodecFor(final String action) {
        return switch (action) {
            case "open_url" -> Codec.STRING.fieldOf("url")
                    .xmap(ClickEvent::openUrl, e -> ((ClickEvent.Payload.Text) e.payload()).value());
            case "open_file" -> Codec.STRING.fieldOf("path")
                    .xmap(ClickEvent::openFile, e -> ((ClickEvent.Payload.Text) e.payload()).value());
            case "run_command" -> Codec.STRING.fieldOf("command")
                    .xmap(ClickEvent::runCommand, e -> ((ClickEvent.Payload.Text) e.payload()).value());
            case "suggest_command" -> Codec.STRING.fieldOf("command")
                    .xmap(ClickEvent::suggestCommand, e -> ((ClickEvent.Payload.Text) e.payload()).value());
            case "change_page" -> Codec.INT.validate(page -> page > 0
                            ? DataResult.success(page)
                            : DataResult.error(() -> "Page must be positive: " + page))
                    .fieldOf("page")
                    .xmap(ClickEvent::changePage, e -> ((ClickEvent.Payload.Int) e.payload()).integer());
            case "copy_to_clipboard" -> Codec.STRING.fieldOf("value")
                    .xmap(ClickEvent::copyToClipboard, e -> ((ClickEvent.Payload.Text) e.payload()).value());
            case "custom" -> RecordCodecBuilder.mapCodec(instance -> instance.group(
                    KEY_CODEC.fieldOf("id").forGetter(
                            e -> ((ClickEvent.Payload.Custom) e.payload()).key()),
                    Codec.STRING.optionalFieldOf("payload").forGetter(e -> {
                        final BinaryTagHolder nbt = ((ClickEvent.Payload.Custom) e.payload()).nbt();
                        return Optional.ofNullable(nbt).map(BinaryTagHolder::string);
                    })
            ).apply(instance, (id, payload) -> ClickEvent.custom(
                    id, payload.map(BinaryTagHolder::binaryTagHolder).orElse(null))));
            default -> throw new IllegalArgumentException("Unsupported click action: " + action);
        };
    }

    public static final Codec<HoverEvent<?>> HOVER_EVENT_CODEC = Codec.STRING.dispatch(
            "action",
            ComponentCodecs::hoverActionName,
            ComponentCodecs::hoverMapCodecFor
    );

    private static String hoverActionName(final HoverEvent<?> event) {
        if (event.action() == HoverEvent.Action.SHOW_TEXT) return "show_text";
        if (event.action() == HoverEvent.Action.SHOW_ENTITY) return "show_entity";
        if (event.action() == HoverEvent.Action.SHOW_ITEM) return "show_item";
        throw new UnsupportedOperationException("Unsupported hover action: " + event.action());
    }

    private static MapCodec<? extends HoverEvent<?>> hoverMapCodecFor(final String action) {
        return switch (action) {
            case "show_text" -> COMPONENT_CODEC.fieldOf("value")
                    .xmap(HoverEvent::showText, HoverEvent::value);

            case "show_entity" -> RecordCodecBuilder.mapCodec(instance -> instance.group(
                    KEY_CODEC.fieldOf("id").forGetter(
                            e -> ((HoverEvent.ShowEntity) e.value()).type()),
                    UUID_CODEC.fieldOf("uuid").forGetter(
                            e -> ((HoverEvent.ShowEntity) e.value()).id()),
                    COMPONENT_CODEC.optionalFieldOf("name").forGetter(
                            e -> Optional.ofNullable(((HoverEvent.ShowEntity) e.value()).name()))
            ).apply(instance, (id, uuid, name) ->
                    HoverEvent.showEntity(id, uuid, name.orElse(null))));

            case "show_item" -> RecordCodecBuilder.mapCodec(instance -> instance.group(
                    KEY_CODEC.fieldOf("id").forGetter(
                            e -> ((HoverEvent.ShowItem) e.value()).item()),
                    Codec.INT.fieldOf("count").forGetter(
                            e -> ((HoverEvent.ShowItem) e.value()).count())
                    // TODO: data components.
            ).apply(instance, HoverEvent::showItem));

            default -> throw new IllegalArgumentException("Unsupported hover action: " + action);
        };
    }

    public static final MapCodec<Style> STYLE_MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            COLOR_CODEC.optionalFieldOf("color").forGetter(s -> Optional.ofNullable(s.color())),
            Codec.INT.optionalFieldOf("shadow_color").forGetter(s ->
                    Optional.ofNullable(s.shadowColor()).map(ShadowColor::value)),
            Codec.BOOL.optionalFieldOf("bold").forGetter(s -> decorationOf(s, TextDecoration.BOLD)),
            Codec.BOOL.optionalFieldOf("italic").forGetter(s -> decorationOf(s, TextDecoration.ITALIC)),
            Codec.BOOL.optionalFieldOf("underlined").forGetter(s -> decorationOf(s, TextDecoration.UNDERLINED)),
            Codec.BOOL.optionalFieldOf("strikethrough").forGetter(s -> decorationOf(s, TextDecoration.STRIKETHROUGH)),
            Codec.BOOL.optionalFieldOf("obfuscated").forGetter(s -> decorationOf(s, TextDecoration.OBFUSCATED)),
            CLICK_EVENT_CODEC.optionalFieldOf("click_event").forGetter(s -> Optional.ofNullable(s.clickEvent())),
            HOVER_EVENT_CODEC.optionalFieldOf("hover_event").forGetter(s -> Optional.ofNullable(s.hoverEvent())),
            Codec.STRING.optionalFieldOf("insertion").forGetter(s -> Optional.ofNullable(s.insertion())),
            KEY_CODEC.optionalFieldOf("font").forGetter(s -> Optional.ofNullable(s.font()))
    ).apply(instance, ComponentCodecs::buildStyle));

    private static Optional<Boolean> decorationOf(final Style style, final TextDecoration decoration) {
        final TextDecoration.State state = style.decoration(decoration);
        return state == TextDecoration.State.NOT_SET
                ? Optional.empty()
                : Optional.of(state == TextDecoration.State.TRUE);
    }

    private static Style buildStyle(
            final Optional<TextColor> color,
            final Optional<Integer> shadowColor,
            final Optional<Boolean> bold,
            final Optional<Boolean> italic,
            final Optional<Boolean> underlined,
            final Optional<Boolean> strikethrough,
            final Optional<Boolean> obfuscated,
            final Optional<ClickEvent<?>> clickEvent,
            final Optional<HoverEvent<?>> hoverEvent,
            final Optional<String> insertion,
            final Optional<Key> font
    ) {
        final Style.Builder builder = Style.style();
        color.ifPresent(builder::color);
        shadowColor.ifPresent(v -> builder.shadowColor(ShadowColor.shadowColor(v)));
        bold.ifPresent(v -> builder.decoration(TextDecoration.BOLD, v));
        italic.ifPresent(v -> builder.decoration(TextDecoration.ITALIC, v));
        underlined.ifPresent(v -> builder.decoration(TextDecoration.UNDERLINED, v));
        strikethrough.ifPresent(v -> builder.decoration(TextDecoration.STRIKETHROUGH, v));
        obfuscated.ifPresent(v -> builder.decoration(TextDecoration.OBFUSCATED, v));
        clickEvent.ifPresent(builder::clickEvent);
        hoverEvent.ifPresent(builder::hoverEvent);
        insertion.ifPresent(builder::insertion);
        font.ifPresent(builder::font);
        return builder.build();
    }

    private static final MapCodec<TextComponent> TEXT_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("text").forGetter(TextComponent::content)
    ).apply(instance, Component::text));

    private static final MapCodec<TranslatableComponent> TRANSLATABLE_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("translate").forGetter(TranslatableComponent::key),
            Codec.STRING.optionalFieldOf("fallback").forGetter(t -> Optional.ofNullable(t.fallback())),
            COMPONENT_CODEC.listOf().optionalFieldOf("with", List.of()).forGetter(
                    t -> t.arguments().stream().map(TranslationArgument::asComponent).toList())
    ).apply(instance, (key, fallback, with) ->
            Component.translatable(key, with).fallback(fallback.orElse(null))));

    private static final MapCodec<KeybindComponent> KEYBIND_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("keybind").forGetter(KeybindComponent::keybind)
    ).apply(instance, Component::keybind));

    private record ScoreData(String name, String objective) {
    }

    private static final Codec<ScoreData> SCORE_DATA_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(ScoreData::name),
            Codec.STRING.fieldOf("objective").forGetter(ScoreData::objective)
    ).apply(instance, ScoreData::new));

    private static final MapCodec<ScoreComponent> SCORE_CODEC = SCORE_DATA_CODEC.fieldOf("score").xmap(
            d -> Component.score(d.name(), d.objective()),
            s -> new ScoreData(s.name(), s.objective()));

    private static final MapCodec<SelectorComponent> SELECTOR_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("selector").forGetter(SelectorComponent::pattern),
            COMPONENT_CODEC.optionalFieldOf("separator").forGetter(s -> Optional.ofNullable(s.separator()))
    ).apply(instance, (selector, separator) -> Component.selector(selector, separator.orElse(null))));

    private static final MapCodec<ObjectComponent> SPRITE_OBJECT_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            KEY_CODEC.optionalFieldOf("atlas", SpriteObjectContents.DEFAULT_ATLAS)
                    .forGetter(o -> ((SpriteObjectContents) o.contents()).atlas()),
            KEY_CODEC.fieldOf("sprite").forGetter(o -> ((SpriteObjectContents) o.contents()).sprite())
    ).apply(instance, (atlas, sprite) -> Component.object(ObjectContents.sprite(atlas, sprite))));

    private static final Codec<PlayerHeadObjectContents.ProfileProperty> PROFILE_PROPERTY_CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("name").forGetter(PlayerHeadObjectContents.ProfileProperty::name),
                    Codec.STRING.fieldOf("value").forGetter(PlayerHeadObjectContents.ProfileProperty::value),
                    Codec.STRING.optionalFieldOf("signature").forGetter(p -> Optional.ofNullable(p.signature()))
            ).apply(instance, (name, value, signature) ->
                    PlayerHeadObjectContents.property(name, value, signature.orElse(null))));

    private static final Codec<PlayerHeadObjectContents> PLAYER_HEAD_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("name").forGetter(c -> Optional.ofNullable(c.name())),
            UUID_CODEC.optionalFieldOf("id").forGetter(c -> Optional.ofNullable(c.id())),
            PROFILE_PROPERTY_CODEC.listOf().optionalFieldOf("properties", List.of()).forGetter(PlayerHeadObjectContents::profileProperties),
            KEY_CODEC.optionalFieldOf("texture").forGetter(c -> Optional.ofNullable(c.texture()))
    ).apply(instance, (name, id, properties, texture) -> ObjectContents.playerHead()
            .name(name.orElse(null))
            .id(id.orElse(null))
            .profileProperties(properties)
            .texture(texture.orElse(null))
            .build()));

    private static final MapCodec<ObjectComponent> PLAYER_OBJECT_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            PLAYER_HEAD_CODEC.fieldOf("player").forGetter(o -> (PlayerHeadObjectContents) o.contents()),
            Codec.BOOL.optionalFieldOf("hat", true).forGetter(o -> ((PlayerHeadObjectContents) o.contents()).hat())
    ).apply(instance, (contents, hat) -> Component.object(contents.toBuilder().hat(hat).build())));

    private static final MapCodec<ObjectComponent> OBJECT_CONTENTS_CODEC = vanillaMatcher("object", List.of(
            Variant.of("atlas", null, true,
                    o -> o.contents() instanceof SpriteObjectContents, SPRITE_OBJECT_CODEC),
            Variant.of("player", null, true,
                    o -> o.contents() instanceof PlayerHeadObjectContents, PLAYER_OBJECT_CODEC)));

    private static final MapCodec<ObjectComponent> OBJECT_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            OBJECT_CONTENTS_CODEC.forGetter(Function.identity()),
            COMPONENT_CODEC.optionalFieldOf("fallback").forGetter(o -> Optional.ofNullable(o.fallback()))
    ).apply(instance, (object, fallback) -> fallback.map(object::fallback).orElse(object)));

    private static final MapCodec<BlockNBTComponent> BLOCK_NBT_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("nbt").forGetter(BlockNBTComponent::nbtPath),
            Codec.BOOL.optionalFieldOf("interpret", false).forGetter(BlockNBTComponent::interpret),
            COMPONENT_CODEC.optionalFieldOf("separator").forGetter(c -> Optional.ofNullable(c.separator())),
            Codec.STRING.fieldOf("block").forGetter(c -> c.pos().asString())
    ).apply(instance, (nbt, interpret, separator, block) -> Component.blockNBT()
            .nbtPath(nbt)
            .interpret(interpret)
            .separator(separator.orElse(null))
            .pos(BlockNBTComponent.Pos.fromString(block))
            .build()));

    private static final MapCodec<EntityNBTComponent> ENTITY_NBT_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("nbt").forGetter(EntityNBTComponent::nbtPath),
            Codec.BOOL.optionalFieldOf("interpret", false).forGetter(EntityNBTComponent::interpret),
            COMPONENT_CODEC.optionalFieldOf("separator").forGetter(c -> Optional.ofNullable(c.separator())),
            Codec.STRING.fieldOf("entity").forGetter(EntityNBTComponent::selector)
    ).apply(instance, (nbt, interpret, separator, entity) -> Component.entityNBT()
            .nbtPath(nbt)
            .interpret(interpret)
            .separator(separator.orElse(null))
            .selector(entity)
            .build()));

    private static final MapCodec<StorageNBTComponent> STORAGE_NBT_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("nbt").forGetter(StorageNBTComponent::nbtPath),
            Codec.BOOL.optionalFieldOf("interpret", false).forGetter(StorageNBTComponent::interpret),
            COMPONENT_CODEC.optionalFieldOf("separator").forGetter(c -> Optional.ofNullable(c.separator())),
            KEY_CODEC.fieldOf("storage").forGetter(StorageNBTComponent::storage)
    ).apply(instance, (nbt, interpret, separator, storage) -> Component.storageNBT()
            .nbtPath(nbt)
            .interpret(interpret)
            .separator(separator.orElse(null))
            .storage(storage)
            .build()));

    private static final MapCodec<NBTComponent<?>> NBT_CODEC = vanillaMatcher("source", List.of(
            Variant.of("block", "block", false, BlockNBTComponent.class, BLOCK_NBT_CODEC),
            Variant.of("entity", "entity", false, EntityNBTComponent.class, ENTITY_NBT_CODEC),
            Variant.of("storage", "storage", false, StorageNBTComponent.class, STORAGE_NBT_CODEC)));

    private static final MapCodec<Component> CONTENT_CODEC = vanillaMatcher("type", List.of(
            Variant.of("text", "text", false, TextComponent.class, TEXT_CODEC),
            Variant.of("translatable", "translate", false, TranslatableComponent.class, TRANSLATABLE_CODEC),
            Variant.of("keybind", "keybind", false, KeybindComponent.class, KEYBIND_CODEC),
            Variant.of("score", "score", false, ScoreComponent.class, SCORE_CODEC),
            Variant.of("selector", "selector", false, SelectorComponent.class, SELECTOR_CODEC),
            Variant.of("nbt", "nbt", false, c -> c instanceof NBTComponent<?>, NBT_CODEC),
            Variant.of("object", null, true, ObjectComponent.class, OBJECT_CODEC)));

    private static Codec<Component> createCodec(final Codec<Component> self) {
        final Codec<Component> direct = RecordCodecBuilder.create(instance -> instance.group(
                CONTENT_CODEC.forGetter(Function.identity()),
                self.listOf().optionalFieldOf("extra", List.of()).forGetter(Component::children),
                STYLE_MAP_CODEC.forGetter(Component::style)
        ).apply(instance, (content, children, style) -> content.children(children).style(style)));

        return Codec.either(Codec.either(Codec.STRING, self.listOf()), direct).xmap(
                either -> either.map(
                        stringOrList -> stringOrList.map(Component::text, ComponentCodecs::fromList),
                        Function.identity()),
                component -> {
                    final String collapsed = collapseToString(component);
                    return collapsed != null
                            ? Either.left(Either.left(collapsed))
                            : Either.right(component);
                });
    }

    private static Component fromList(final List<Component> list) {
        if (list.isEmpty()) return Component.empty();
        Component result = list.getFirst();
        for (int i = 1; i < list.size(); i++) {
            result = result.append(list.get(i));
        }
        return result;
    }

    private static @Nullable String collapseToString(final Component component) {
        if (component instanceof final TextComponent text
                && component.children().isEmpty()
                && component.style().isEmpty()) {
            return text.content();
        }
        return null;
    }

    private record Variant<T>(
            String id,
            @Nullable String key,
            boolean explicit,
            Predicate<? super T> test,
            MapCodec<T> codec
    ) {
        @SuppressWarnings("unchecked")
        static <T, C extends T> Variant<T> of(final String id, final @Nullable String key, final boolean explicit, final Class<C> type, final MapCodec<C> codec) {
            return new Variant<>(id, key, explicit, type::isInstance, (MapCodec<T>) codec);
        }

        @SuppressWarnings("unchecked")
        static <T, C extends T> Variant<T> of(final String id, final @Nullable String key, final boolean explicit, final Predicate<? super T> test, final MapCodec<C> codec) {
            return new Variant<>(id, key, explicit, test, (MapCodec<T>) codec);
        }
    }

    private static <T> MapCodec<T> vanillaMatcher(final String typeField, final List<Variant<T>> variants) {
        return new MapCodec<>() {
            @Override
            public <O> Stream<O> keys(final DynamicOps<O> ops) {
                return Stream.concat(
                        Stream.of(ops.createString(typeField)),
                        variants.stream().flatMap(v -> v.codec().keys(ops)));
            }

            @Override
            public <O> DataResult<T> decode(final DynamicOps<O> ops, final MapLike<O> input) {
                final O type = input.get(typeField);
                if (type != null) {
                    final Optional<String> id = ops.getStringValue(type).result();
                    if (id.isPresent()) {
                        for (final Variant<T> v : variants) {
                            if (v.id().equals(id.get())) {
                                return v.codec().decode(ops, input);
                            }
                        }
                        return DataResult.error(() -> "Unknown '" + typeField + "': " + id.get());
                    }
                }
                for (final Variant<T> v : variants) {
                    if (v.key() != null && input.get(v.key()) != null) {
                        return v.codec().decode(ops, input);
                    }
                }
                return DataResult.error(() -> "Could not infer component type from fields");
            }

            @Override
            public <O> RecordBuilder<O> encode(final T input, final DynamicOps<O> ops, final RecordBuilder<O> prefix) {
                for (final Variant<T> v : variants) {
                    if (v.test().test(input)) {
                        final RecordBuilder<O> builder = v.codec().encode(input, ops, prefix);
                        return v.explicit()
                                ? builder.add(typeField, ops.createString(v.id()))
                                : builder;
                    }
                }
                return prefix.withErrorsFrom(DataResult.error(() -> "Unsupported component: " + input));
            }
        };
    }
}
