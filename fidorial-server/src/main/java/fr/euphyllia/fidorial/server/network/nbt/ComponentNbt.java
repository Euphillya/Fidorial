package fr.euphyllia.fidorial.server.network.nbt;

import fr.euphyllia.fidorial.server.world.nbt.Nbt;
import fr.euphyllia.fidorial.server.world.nbt.NbtByte;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import fr.euphyllia.fidorial.server.world.nbt.NbtInt;
import fr.euphyllia.fidorial.server.world.nbt.NbtIntArray;
import fr.euphyllia.fidorial.server.world.nbt.NbtList;
import fr.euphyllia.fidorial.server.world.nbt.NbtString;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class ComponentNbt {

    private ComponentNbt() {
    }

    public static Nbt write(final Component component) {
        if (component instanceof TextComponent text
                && component.children().isEmpty()
                && component.style().isEmpty()) {
            return new NbtString(text.content());
        }

        NbtCompound tag = new NbtCompound();
        writeContent(tag, component);
        writeStyle(tag, component.style());

        if (!component.children().isEmpty()) {
            NbtList extra = new NbtList();
            for (Component child : component.children()) extra.add(write(child));
            tag.put("extra", extra);
        }
        return tag;
    }

    private static void writeContent(NbtCompound tag, Component component) {
        switch (component) {
            case TextComponent t -> tag.putString("text", t.content());

            case TranslatableComponent t -> {
                tag.putString("translate", t.key());
                if (t.fallback() != null) tag.putString("fallback", t.fallback());
                if (!t.arguments().isEmpty()) {
                    NbtList with = new NbtList();
                    for (var arg : t.arguments()) with.add(write(arg.asComponent()));
                    tag.put("with", with);
                }
            }

            case KeybindComponent t -> tag.putString("keybind", t.keybind());

            case ScoreComponent t -> {
                NbtCompound score = new NbtCompound();
                score.putString("name", t.name());
                score.putString("objective", t.objective());
                tag.put("score", score);
            }

            case SelectorComponent t -> {
                tag.putString("selector", t.pattern());
                if (t.separator() != null) tag.put("separator", write(t.separator()));
            }

            case ObjectComponent t -> {
                if (t.fallback() != null) tag.put("fallback", write(t.fallback()));
                tag.putString("type", "object");
                switch (t.contents()) {
                    case SpriteObjectContents sprite -> {
                        tag.putString("object", "atlas");
                        tag.putString("atlas", sprite.atlas().asString());
                        tag.putString("sprite", sprite.sprite().asString());
                    }
                    case PlayerHeadObjectContents player -> {
                        tag.putString("object", "player");
                        NbtCompound playerTag = new NbtCompound();
                        if (player.name() != null) playerTag.putString("name", player.name());
                        UUID id = player.id();
                        if (id != null) {
                            playerTag.put("id", new NbtIntArray(new int[]{
                                    (int) (id.getMostSignificantBits() >> 32),
                                    (int) (id.getMostSignificantBits()),
                                    (int) (id.getLeastSignificantBits() >> 32),
                                    (int) (id.getLeastSignificantBits())
                            }));
                        }
                        if (!player.profileProperties().isEmpty()) {
                            NbtList properties = new NbtList();
                            for (var prop : player.profileProperties()) {
                                NbtCompound propTag = new NbtCompound();
                                propTag.putString("name", prop.name());
                                propTag.putString("value", prop.value());
                                if (prop.signature() != null) propTag.putString("signature", prop.signature());
                                properties.add(propTag);
                            }
                            playerTag.put("properties", properties);
                        }
                        if (player.texture() != null) playerTag.putString("texture", player.texture().asString());
                        tag.put("player", playerTag);
                        if (!player.hat()) {
                            tag.put("hat", new NbtByte((byte) 0));
                        }
                    }
                    default -> throw new UnsupportedOperationException(
                            "Unsupported object contents: " + t.contents().getClass());
                }
            }

            case BlockNBTComponent t -> {
                writeNbtComponent(tag, t);
                tag.putString("block", t.pos().asString());
            }

            case EntityNBTComponent t -> {
                writeNbtComponent(tag, t);
                tag.putString("entity", t.selector());
            }

            case StorageNBTComponent t -> {
                writeNbtComponent(tag, t);
                tag.putString("storage", t.storage().asString());
            }

            default -> throw new UnsupportedOperationException(
                    "Unsupported component type: " + component.getClass());
        }
    }

    private static void writeStyle(NbtCompound tag, Style style) {
        TextColor color = style.color();
        if (color != null) {
            tag.putString("color", color instanceof NamedTextColor named
                    ? named.toString()
                    : color.asHexString());
        }

        if (style.shadowColor() != null) {
            tag.put("shadow_color", new NbtInt(style.shadowColor().value()));
        }

        putDecoration(tag, style, TextDecoration.BOLD, "bold");
        putDecoration(tag, style, TextDecoration.ITALIC, "italic");
        putDecoration(tag, style, TextDecoration.UNDERLINED, "underlined");
        putDecoration(tag, style, TextDecoration.STRIKETHROUGH, "strikethrough");
        putDecoration(tag, style, TextDecoration.OBFUSCATED, "obfuscated");

        if (style.clickEvent() != null) tag.put("click_event", writeClick(style.clickEvent()));
        if (style.hoverEvent() != null) tag.put("hover_event", writeHover(style.hoverEvent()));
        if (style.insertion() != null) tag.putString("insertion", style.insertion());
        if (style.font() != null) tag.putString("font", style.font().asString());
    }

    private static void putDecoration(NbtCompound tag, Style style, TextDecoration deco, String key) {
        TextDecoration.State state = style.decoration(deco);
        if (state != TextDecoration.State.NOT_SET) {
            tag.put(key, new NbtByte((byte) (state == TextDecoration.State.TRUE ? 1 : 0)));
        }
    }

    private static Nbt writeClick(ClickEvent<?> event) {
        NbtCompound t = new NbtCompound();
        switch (event.action()) {
            case ClickEvent.Action.OpenUrl _ -> {
                t.putString("action", "open_url");
                t.putString("url", ((ClickEvent.Payload.Text) event.payload()).value());
            }

            case ClickEvent.Action.OpenFile _ -> {
                t.putString("action", "open_file");
                t.putString("path", ((ClickEvent.Payload.Text) event.payload()).value());
            }

            case ClickEvent.Action.RunCommand _ -> {
                t.putString("action", "run_command");
                t.putString("command", ((ClickEvent.Payload.Text) event.payload()).value());
            }

            case ClickEvent.Action.SuggestCommand _ -> {
                t.putString("action", "suggest_command");
                t.putString("command", ((ClickEvent.Payload.Text) event.payload()).value());
            }

            case ClickEvent.Action.ChangePage _ -> {
                t.putString("action", "change_page");
                t.put("page", new NbtInt(((ClickEvent.Payload.Int) event.payload()).integer()));
            }

            case ClickEvent.Action.CopyToClipboard _ -> {
                t.putString("action", "copy_to_clipboard");
                t.putString("value", ((ClickEvent.Payload.Text) event.payload()).value());
            }

            case ClickEvent.Action.Custom _ -> {
                ClickEvent.Payload.Custom custom = (ClickEvent.Payload.Custom) event.payload();
                t.putString("action", "custom");
                t.putString("id", custom.key().asString());
                if (custom.nbt() != null) {
                    t.putString("payload", custom.nbt().string());
                }
            }

            default -> throw new UnsupportedOperationException("Not supported yet.");
        }
        return t;
    }

    private static Nbt writeHover(HoverEvent<?> event) {
        NbtCompound root = new NbtCompound();

        if (event.action() == HoverEvent.Action.SHOW_TEXT) {
            root.putString("action", "show_text");
            root.put("value", write((Component) event.value()));

        } else if (event.action() == HoverEvent.Action.SHOW_ENTITY) {
            HoverEvent.ShowEntity entity = (HoverEvent.ShowEntity) event.value();

            root.putString("action", "show_entity");
            root.putString("id", entity.type().asString());

            UUID uuid = entity.id();
            root.put("uuid", new NbtIntArray(new int[]{
                    (int) (uuid.getMostSignificantBits() >> 32),
                    (int) uuid.getMostSignificantBits(),
                    (int) (uuid.getLeastSignificantBits() >> 32),
                    (int) uuid.getLeastSignificantBits()
            }));

            if (entity.name() != null) {
                root.put("name", write(entity.name()));
            }

        } else if (event.action() == HoverEvent.Action.SHOW_ITEM) {
            HoverEvent.ShowItem item = (HoverEvent.ShowItem) event.value();

            root.putString("action", "show_item");
            root.putString("id", item.item().asString());
            root.put("count", new NbtInt(item.count()));
            // TODO: implement logic for this when we have data components `if (!item.dataComponents().isEmpty()) {`
            root.put("components", new NbtCompound());

        } else {
            throw new UnsupportedOperationException();
        }

        return root;
    }

    private static void writeNbtComponent(NbtCompound tag, NBTComponent<?> component) {
        tag.putString("nbt", component.nbtPath());

        if (component.interpret()) {
            tag.put("interpret", new NbtByte((byte) 1));
        }

        if (component.separator() != null) {
            tag.put("separator", write(component.separator()));
        }
    }

    public static Component read(Nbt raw) {
        if (raw instanceof NbtString(String value)) return Component.text(value);

        if (raw instanceof NbtList list) {
            if (list.size() == 0) return Component.empty();
            Iterator<Nbt> it = list.iterator();
            Component first = read(it.next());
            List<Component> rest = new ArrayList<>();
            it.forEachRemaining(child -> rest.add(read(child)));
            return rest.isEmpty() ? first : first.children(rest);
        }

        if (!(raw instanceof NbtCompound tag)) {
            throw new IllegalArgumentException("Expected string, list, or compound, got " + raw.type());
        }

        Component component = readContent(tag);
        component = component.style(readStyle(tag));

        if (tag.tags().get("extra") instanceof NbtList list) {
            List<Component> children = new ArrayList<>();
            for (Nbt child : list) children.add(read(child));
            component = component.children(children);
        }
        return component;
    }

    private static Component readContent(NbtCompound tag) {
        var t = tag.tags();
        String nbtPath = t.get("nbt") instanceof NbtString(String s) ? s : null;

        boolean interpret = t.get("interpret") instanceof NbtByte(byte b) && b != 0;

        Component separator = t.get("separator") instanceof Nbt sep
                ? read(sep)
                : null;

        if (t.get("text") instanceof NbtString(String value)) return Component.text(value);

        if (t.get("translate") instanceof NbtString(String value)) {
            List<Component> args = List.of();
            if (t.get("with") instanceof NbtList with) {
                List<Component> parsed = new ArrayList<>();
                for (Nbt arg : with) parsed.add(read(arg));
                args = parsed;
            }
            TranslatableComponent tc = Component.translatable(value, args);
            if (t.get("fallback") instanceof NbtString(String value1)) tc = tc.fallback(value1);
            return tc;
        }

        if (t.get("score") instanceof NbtCompound score) {
            var s = score.tags();
            return Component.score(((NbtString) s.get("name")).value(), ((NbtString) s.get("objective")).value());
        }

        if (t.get("selector") instanceof NbtString(String value)) {
            Component separatorComponent = t.get("separator") instanceof Nbt sepTag ? read(sepTag) : null;
            return Component.selector(value, separatorComponent);
        }

        if (t.get("keybind") instanceof NbtString(String value)) {
            return Component.keybind(value);
        }

        if (t.get("block") instanceof NbtString(String pos)) {
            return Component.blockNBT()
                    .nbtPath(nbtPath)
                    .interpret(interpret)
                    .separator(separator)
                    .pos(BlockNBTComponent.Pos.fromString(pos))
                    .build();
        }

        if (t.get("entity") instanceof NbtString(String selector)) {
            return Component.entityNBT()
                    .nbtPath(nbtPath)
                    .interpret(interpret)
                    .separator(separator)
                    .selector(selector)
                    .build();
        }

        if (t.get("storage") instanceof NbtString(String storage)) {
            return Component.storageNBT()
                    .nbtPath(nbtPath)
                    .interpret(interpret)
                    .separator(separator)
                    .storage(Key.key(storage))
                    .build();
        }

        if (t.get("player") instanceof NbtCompound playerTag) {
            var p = playerTag.tags();
            var builder = ObjectContents.playerHead();

            if (p.get("name") instanceof NbtString(String name)) builder.name(name);

            if (p.get("id") instanceof NbtIntArray(int[] u)) {
                UUID id = new UUID(
                        ((long) u[0] << 32) | (u[1] & 0xFFFFFFFFL),
                        ((long) u[2] << 32) | (u[3] & 0xFFFFFFFFL));
                builder.id(id);
            }

            if (p.get("properties") instanceof NbtList properties) {
                List<PlayerHeadObjectContents.ProfileProperty> props = new ArrayList<>();
                for (Nbt propNbt : properties) {
                    if (propNbt instanceof NbtCompound propTag) {
                        var pp = propTag.tags();
                        String name = ((NbtString) pp.get("name")).value();
                        String value = ((NbtString) pp.get("value")).value();
                        String signature = pp.get("signature") instanceof NbtString(String sig) ? sig : null;
                        props.add(PlayerHeadObjectContents.property(name, value, signature));
                    }
                }
                builder.profileProperties(props);
            }

            if (p.get("texture") instanceof NbtString(String tex)) builder.texture(Key.key(tex));

            boolean hat = !(t.get("hat") instanceof NbtByte(byte hatVal) && hatVal == 0);
            builder.hat(hat);

            ObjectComponent object = Component.object(builder.build());
            if (t.get("fallback") instanceof Nbt fb) object = object.fallback(read(fb));
            return object;
        }

        if (t.get("sprite") instanceof NbtString(String value)) {
            Key atlas = t.get("atlas") instanceof NbtString(String value1)
                    ? Key.key(value1) : SpriteObjectContents.DEFAULT_ATLAS;
            ObjectComponent object = Component.object(ObjectContents.sprite(atlas, Key.key(value)));
            if (t.get("fallback") instanceof Nbt fb) object = object.fallback(read(fb));
            return object;
        }

        throw new IllegalArgumentException("Unrecognized/unsupported component content: " + tag);
    }

    private static Style readStyle(NbtCompound tag) {
        var t = tag.tags();
        Style.Builder style = Style.style();

        if (t.get("color") instanceof NbtString(String v)) {
            style.color(v.startsWith("#") ? TextColor.fromHexString(v) : NamedTextColor.NAMES.value(v));
        }

        if (t.get("shadow_color") instanceof NbtInt(int value)) {
            style.shadowColor(ShadowColor.shadowColor(value));
        }

        applyDecoration(t, style, "bold", TextDecoration.BOLD);
        applyDecoration(t, style, "italic", TextDecoration.ITALIC);
        applyDecoration(t, style, "underlined", TextDecoration.UNDERLINED);
        applyDecoration(t, style, "strikethrough", TextDecoration.STRIKETHROUGH);
        applyDecoration(t, style, "obfuscated", TextDecoration.OBFUSCATED);

        if (t.get("click_event") instanceof NbtCompound click) style.clickEvent(readClick(click));
        if (t.get("hover_event") instanceof NbtCompound hover) style.hoverEvent(readHover(hover));
        if (t.get("insertion") instanceof NbtString(String value)) style.insertion(value);
        if (t.get("font") instanceof NbtString(String value)) style.font(Key.key(value));

        return style.build();
    }

    private static void applyDecoration(Map<String, Nbt> t, Style.Builder style, String key, TextDecoration deco) {
        if (t.get(key) instanceof NbtByte(byte value)) style.decoration(deco, value != 0);
    }

    private static ClickEvent<?> readClick(NbtCompound tag) {
        var t = tag.tags();
        if (!(t.get("action") instanceof NbtString(String action))) {
            throw new IllegalArgumentException("Missing click action");
        }

        return switch (action) {
            case "open_url" -> ClickEvent.openUrl(((NbtString) t.get("url")).value());
            case "open_file" -> ClickEvent.openFile(((NbtString) t.get("path")).value());
            case "run_command" -> ClickEvent.runCommand(((NbtString) t.get("command")).value());
            case "suggest_command" -> ClickEvent.suggestCommand(((NbtString) t.get("command")).value());
            case "change_page" -> ClickEvent.changePage(((NbtInt) t.get("page")).value());
            case "copy_to_clipboard" -> ClickEvent.copyToClipboard(((NbtString) t.get("value")).value());
            case "custom" -> {
                String snbt = t.get("payload") instanceof NbtString(String s) ? s : "{}";
                yield ClickEvent.custom(Key.key(((NbtString) t.get("id")).value()), BinaryTagHolder.binaryTagHolder(snbt));
            }
            default -> throw new UnsupportedOperationException("Unsupported click action: " + action);
        };
    }

    private static HoverEvent<?> readHover(NbtCompound tag) {
        var t = tag.tags();
        if (!(t.get("action") instanceof NbtString(String action))) {
            throw new IllegalArgumentException("Missing hover action");
        }

        return switch (action) {
            case "show_text" -> HoverEvent.showText(read(t.get("value")));

            case "show_entity" -> {
                int[] u = ((NbtIntArray) t.get("uuid")).value();
                UUID uuid = new UUID(
                        ((long) u[0] << 32) | (u[1] & 0xFFFFFFFFL),
                        ((long) u[2] << 32) | (u[3] & 0xFFFFFFFFL)
                );
                Component name = t.get("name") instanceof Nbt n ? read(n) : null;
                yield HoverEvent.showEntity(
                        Key.key(((NbtString) t.get("id")).value()),
                        uuid,
                        name
                );
            }

            case "show_item" -> {
                int count = t.get("count") instanceof NbtInt i ? i.value() : 1;
                yield HoverEvent.showItem(
                        Key.key(((NbtString) t.get("id")).value()),
                        count
                );
            }

            default -> throw new UnsupportedOperationException("Unsupported hover action: " + action);
        };
    }

    // for debug purposes; DO NOT USE
    public static void dump(Nbt nbt, String indent) {
        switch (nbt) {
            case NbtCompound compound -> {
                System.out.println(indent + "Compound");
                compound.tags().forEach((k, v) -> {
                    System.out.println(indent + "  " + k + ":");
                    dump(v, indent + "    ");
                });
            }

            case NbtList list -> {
                System.out.println(indent + "List size=" + list.size()
                        + " elementType=" + list.elementType());
                int i = 0;
                for (Nbt child : list) {
                    System.out.println(indent + "  [" + i++ + "]");
                    dump(child, indent + "    ");
                }
            }

            default -> System.out.println(indent + nbt);
        }
    }
}
