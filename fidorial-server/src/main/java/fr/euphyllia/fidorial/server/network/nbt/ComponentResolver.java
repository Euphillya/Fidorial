package fr.euphyllia.fidorial.server.network.nbt;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.euphyllia.fidorial.server.command.brigadier.argument.entity.EntitySelector;
import fr.fidorial.command.CommandSource;
import fr.fidorial.entity.Entity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.SelectorComponent;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public final class ComponentResolver {

    public static Component resolve(final Component component, final CommandSource source) {
        Component resolvedContent = switch (component) {
            case SelectorComponent sel -> resolveSelector(sel, source);
            //case ScoreComponent score -> resolveScore(score, source); TBD
            //case NBTComponent<?> nbt -> resolveNbt(nbt, source); TBD
            default -> component;
        };

        if (!resolvedContent.children().isEmpty()) {
            List<Component> resolvedChildren = new ArrayList<>();
            for (final Component child : resolvedContent.children()) {
                resolvedChildren.add(resolve(child, source));
            }
            resolvedContent = resolvedContent.children(resolvedChildren);
        }
        return resolvedContent;
    }

    private static Component resolveSelector(final SelectorComponent sel, final CommandSource source) {
        final EntitySelector selector;
        try {
            selector = EntitySelector.parse(sel.pattern());
        } catch (final CommandSyntaxException e) {
            return Component.empty();
        }

        final List<Entity> matches;
        try {
            matches = new ArrayList<>(selector.findEntities(source));
        } catch (final CommandSyntaxException e) {
            return Component.empty();
        }

        if (matches.isEmpty()) {
            return Component.empty();
        }

        final Component separator = sel.separator() != null
                ? sel.separator()
                : Component.text(", ").color(NamedTextColor.GRAY);

        Component result = Component.empty();
        for (int i = 0; i < matches.size(); i++) {
            final Entity entity = matches.get(i);

            final Component name = entity.displayName().hoverEvent(entity.asHoverEvent());

            if (i != 0) {
                result = result.append(separator);
            }
            result = result.append(name);
        }

        return result;
    }
}
