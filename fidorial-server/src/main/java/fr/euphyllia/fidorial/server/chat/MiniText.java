package fr.euphyllia.fidorial.server.chat;

import fr.euphyllia.fidorial.server.world.nbt.*;

import java.util.*;
import java.util.regex.Pattern;

public class MiniText {

    private static final Set<String> COLORS = Set.of(
            "black", "dark_blue", "dark_green", "dark_aqua", "dark_red",
            "dark_purple", "gold", "gray", "dark_gray", "blue", "green",
            "aqua", "red", "light_purple", "yellow", "white");

    private static final Map<String, String> COLOR_ALIASES = Map.of(
            "grey", "gray",
            "dark_grey", "dark_gray");

    private static final Map<String, String> DECORATION_ALIASES = Map.of(
            "b", "bold",
            "i", "italic",
            "em", "italic",
            "u", "underlined",
            "underline", "underlined",
            "st", "strikethrough",
            "obf", "obfuscated");

    private static final Set<String> DECORATIONS = Set.of(
            "bold", "italic", "underlined", "strikethrough", "obfuscated");

    private static final Map<String, String> CLICK_ACTIONS = Map.of(
            "open_url", "url",
            "run_command", "command",
            "suggest_command", "command",
            "copy_to_clipboard", "value",
            "change_page", "page");

    private static final Pattern HEX_RGB = Pattern.compile("#[0-9a-fA-F]{6}");
    private static final Pattern HEX_ARGB = Pattern.compile("#[0-9a-fA-F]{8}");

    private static final Map<String, String> ANSI_COLORS = Map.ofEntries(
            Map.entry("black", "\u001b[30m"),
            Map.entry("dark_blue", "\u001b[34m"),
            Map.entry("dark_green", "\u001b[32m"),
            Map.entry("dark_aqua", "\u001b[36m"),
            Map.entry("dark_red", "\u001b[31m"),
            Map.entry("dark_purple", "\u001b[35m"),
            Map.entry("gold", "\u001b[33m"),
            Map.entry("gray", "\u001b[37m"),
            Map.entry("dark_gray", "\u001b[90m"),
            Map.entry("blue", "\u001b[94m"),
            Map.entry("green", "\u001b[92m"),
            Map.entry("aqua", "\u001b[96m"),
            Map.entry("red", "\u001b[91m"),
            Map.entry("light_purple", "\u001b[95m"),
            Map.entry("yellow", "\u001b[93m"),
            Map.entry("white", "\u001b[97m"));

    private static final Map<String, String> ANSI_DECORATIONS = Map.of(
            "bold", "\u001b[1m",
            "italic", "\u001b[3m",
            "underlined", "\u001b[4m",
            "strikethrough", "\u001b[9m",
            "obfuscated", "\u001b[5m");

    private static final String ANSI_RESET = "\u001b[0m";

    private MiniText() {
    }


    public static Nbt parse(String input) {
        NbtList extra = new NbtList(NbtType.COMPOUND);
        StringBuilder current = new StringBuilder();
        Deque<Frame> stack = newStack();

        int i = 0;
        while (i < input.length()) {
            char c = input.charAt(i);
            if (c == '\\' && i + 1 < input.length() && input.charAt(i + 1) == '<') {
                current.append('<');
                i += 2;
                continue;
            }
            if (c == '<') {
                int end = findTagEnd(input, i);
                if (end > i) {
                    Style before = stack.peek().style();
                    if (applyTag(input.substring(i + 1, end), stack)) {
                        flush(extra, current, before);
                        i = end + 1;
                        continue;
                    }
                }
            }
            current.append(c);
            i++;
        }
        flush(extra, current, stack.peek().style());

        if (extra.size() == 0) {
            return new NbtString("");
        }
        if (extra.size() == 1 && extra.get(0) instanceof NbtCompound only && isPlain(only)) {
            return only.get("text");
        }
        NbtCompound root = new NbtCompound();
        root.putString("text", "");
        root.put("extra", extra);
        return root;
    }

    public static String toAnsi(String input) {
        StringBuilder out = new StringBuilder();
        Deque<Frame> stack = newStack();
        boolean colored = false;

        int i = 0;
        while (i < input.length()) {
            char c = input.charAt(i);
            if (c == '\\' && i + 1 < input.length() && input.charAt(i + 1) == '<') {
                out.append('<');
                i += 2;
                continue;
            }
            if (c == '<') {
                int end = findTagEnd(input, i);
                if (end > i && applyTag(input.substring(i + 1, end), stack)) {
                    out.append(ANSI_RESET).append(ansiFor(stack.peek().style()));
                    colored = true;
                    i = end + 1;
                    continue;
                }
            }
            out.append(c);
            i++;
        }
        if (colored) {
            out.append(ANSI_RESET);
        }
        return out.toString();
    }

    public static String stripTags(String input) {
        StringBuilder out = new StringBuilder();
        Deque<Frame> stack = newStack();

        int i = 0;
        while (i < input.length()) {
            char c = input.charAt(i);
            if (c == '\\' && i + 1 < input.length() && input.charAt(i + 1) == '<') {
                out.append('<');
                i += 2;
                continue;
            }
            if (c == '<') {
                int end = findTagEnd(input, i);
                if (end > i && applyTag(input.substring(i + 1, end), stack)) {
                    i = end + 1;
                    continue;
                }
            }
            out.append(c);
            i++;
        }
        return out.toString();
    }

    public static boolean isTag(String tag) {
        String content = tag.trim();
        if (content.startsWith("/")) {
            content = content.substring(1);
        }
        return applyTag(content, newStack());
    }

    private static Deque<Frame> newStack() {
        Deque<Frame> stack = new ArrayDeque<>();
        stack.push(new Frame("", Style.EMPTY));
        return stack;
    }

    private static int findTagEnd(String input, int start) {
        char quote = 0;
        for (int i = start + 1; i < input.length(); i++) {
            char c = input.charAt(i);
            if (quote != 0) {
                if (c == quote) {
                    quote = 0;
                }
            } else if (c == '\'' || c == '"') {
                quote = c;
            } else if (c == '>') {
                return i;
            } else if (c == '<') {
                return -1; // balise mal formee
            }
        }
        return -1;
    }

    private static List<String> splitArgs(String raw) {
        List<String> parts = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        char quote = 0;
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (quote != 0) {
                if (c == quote) {
                    quote = 0;
                } else {
                    cur.append(c);
                }
            } else if (c == '\'' || c == '"') {
                quote = c;
            } else if (c == ':') {
                parts.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        parts.add(cur.toString());
        return parts;
    }

    private static boolean applyTag(String raw, Deque<Frame> stack) {
        String content = raw.trim();
        boolean closing = content.startsWith("/");
        if (closing) {
            content = content.substring(1);
        }

        List<String> args = splitArgs(content);
        String name = normalize(args.getFirst().toLowerCase(Locale.ROOT));

        if (name.equals("reset")) {
            if (closing) {
                return false;
            }
            stack.clear();
            stack.push(new Frame("", Style.EMPTY));
            return true;
        }

        if (closing) {
            return closeTag(name, stack);
        }

        Style base = stack.peek().style();

        // <red>, <#ff8800>, <color:red>, <color:#ff8800>
        if (name.equals("color") || name.equals("colour")) {
            if (args.size() < 2) {
                return false;
            }
            String value = normalize(args.get(1).toLowerCase(Locale.ROOT));
            if (!COLORS.contains(value) && !HEX_RGB.matcher(value).matches()) {
                return false;
            }
            stack.push(new Frame("color", base.withColor(value)));
            return true;
        }
        if (COLORS.contains(name)) {
            stack.push(new Frame(name, base.withColor(name)));
            return true;
        }
        if (HEX_RGB.matcher(name).matches()) {
            stack.push(new Frame(name, base.withColor(name)));
            return true;
        }

        if (DECORATIONS.contains(name)) {
            stack.push(new Frame(name, base.withDecoration(name)));
            return true;
        }

        switch (name) {
            case "font" -> {
                if (args.size() < 2) {
                    return false;
                }
                // <font:uniform> ou <font:minecraft:uniform>
                String font = String.join(":", args.subList(1, args.size()));
                stack.push(new Frame("font", base.withFont(font)));
                return true;
            }
            case "shadow_color" -> {
                if (args.size() < 2) {
                    return false;
                }
                String value = args.get(1);
                int argb;
                if (HEX_ARGB.matcher(value).matches()) {
                    argb = (int) Long.parseLong(value.substring(1), 16);
                } else if (HEX_RGB.matcher(value).matches()) {
                    argb = 0xFF000000 | Integer.parseInt(value.substring(1), 16);
                } else {
                    return false;
                }
                stack.push(new Frame("shadow_color", base.withShadowColor(argb)));
                return true;
            }
            case "insertion" -> {
                if (args.size() < 2) {
                    return false;
                }
                stack.push(new Frame("insertion", base.withInsertion(args.get(1))));
                return true;
            }
            case "click" -> {
                if (args.size() < 3 || !CLICK_ACTIONS.containsKey(args.get(1))) {
                    return false;
                }
                stack.push(new Frame("click", base.withClick(args.get(1), args.get(2))));
                return true;
            }
            case "hover" -> {
                // seul show_text est supporte pour l'instant
                if (args.size() < 3 || !args.get(1).equals("show_text")) {
                    return false;
                }
                stack.push(new Frame("hover", base.withHover(args.get(2))));
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    private static boolean closeTag(String name, Deque<Frame> stack) {
        boolean found = false;
        Iterator<Frame> it = stack.iterator();
        while (it.hasNext()) {
            Frame frame = it.next();
            if (frame.tag().isEmpty()) {
                break; // racine
            }
            if (matchesClose(name, frame.tag())) {
                found = true;
                break;
            }
        }
        if (!found) {
            return false;
        }
        while (!stack.peek().tag().isEmpty()) {
            Frame popped = stack.pop();
            if (matchesClose(name, popped.tag())) {
                break;
            }
        }
        return true;
    }

    private static boolean matchesClose(String closeName, String frameTag) {
        if (closeName.equals(frameTag)) {
            return true;
        }
        // </color> ferme <red>, <#ff8800>, <color:...>
        return (closeName.equals("color") || closeName.equals("colour"))
                && (COLORS.contains(frameTag)
                || frameTag.startsWith("#")
                || frameTag.equals("color"));
    }

    private static String normalize(String tag) {
        String color = COLOR_ALIASES.get(tag);
        if (color != null) {
            return color;
        }
        String deco = DECORATION_ALIASES.get(tag);
        return deco != null ? deco : tag;
    }


    private static void flush(NbtList extra, StringBuilder current, Style style) {
        if (current.isEmpty()) {
            return;
        }
        NbtCompound part = new NbtCompound();
        part.putString("text", current.toString());
        current.setLength(0);
        if (style == null) {
            extra.add(part);
            return;
        }
        if (style.color() != null) {
            part.putString("color", style.color());
        }
        for (String deco : style.decorations()) {
            part.putBoolean(deco, true);
        }
        if (style.font() != null) {
            part.putString("font", style.font());
        }
        if (style.shadowColor() != null) {
            part.putInt("shadow_color", style.shadowColor());
        }
        if (style.insertion() != null) {
            part.putString("insertion", style.insertion());
        }
        if (style.clickAction() != null) {
            NbtCompound click = new NbtCompound();
            click.putString("action", style.clickAction());
            String field = CLICK_ACTIONS.get(style.clickAction());
            if (field.equals("page")) {
                try {
                    click.putInt("page", Integer.parseInt(style.clickValue().trim()));
                } catch (NumberFormatException e) {
                    click.putInt("page", 1);
                }
            } else {
                click.putString(field, style.clickValue());
            }
            part.put("click_event", click);
        }
        if (style.hoverText() != null) {
            NbtCompound hover = new NbtCompound();
            hover.putString("action", "show_text");
            // le texte du tooltip peut lui-meme contenir des balises
            hover.put("value", parse(style.hoverText()));
            part.put("hover_event", hover);
        }
        extra.add(part);
    }

    private static boolean isPlain(NbtCompound part) {
        return part.tags().size() == 1 && part.contains("text");
    }

    private static String ansiFor(Style style) {
        if (style == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (style.color() != null) {
            if (style.color().startsWith("#")) {
                int rgb = Integer.parseInt(style.color().substring(1), 16);
                sb.append("\u001b[38;2;")
                        .append((rgb >> 16) & 0xFF).append(';')
                        .append((rgb >> 8) & 0xFF).append(';')
                        .append(rgb & 0xFF).append('m');
            } else {
                sb.append(ANSI_COLORS.getOrDefault(style.color(), ""));
            }
        }
        for (String deco : style.decorations()) {
            sb.append(ANSI_DECORATIONS.getOrDefault(deco, ""));
        }
        return sb.toString();
    }
}
