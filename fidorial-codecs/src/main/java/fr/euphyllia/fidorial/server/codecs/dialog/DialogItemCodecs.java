package fr.euphyllia.fidorial.server.codecs.dialog;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.fidorial.inventory.ItemStack;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.Optional;

import static fr.euphyllia.fidorial.server.codecs.CommonCodecs.KEY_CODEC;
import static fr.euphyllia.fidorial.server.codecs.adventure.ComponentCodecs.COMPONENT_CODEC;

public class DialogItemCodecs {

    private static final MapCodec<ItemComponents> COMPONENTS_CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    COMPONENT_CODEC.optionalFieldOf("custom_name")
                            .forGetter(ItemComponents::customName),
                    COMPONENT_CODEC.optionalFieldOf("item_name")
                            .forGetter(ItemComponents::itemName),
                    COMPONENT_CODEC.listOf().optionalFieldOf("lore")
                            .forGetter(ItemComponents::lore)
            ).apply(instance, ItemComponents::new));

    static final Codec<ItemStack> ITEM_STACK_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            KEY_CODEC.fieldOf("id").forGetter(ItemStack::id),
            Codec.INT.optionalFieldOf("count", 1).forGetter(ItemStack::count),
            COMPONENTS_CODEC.codec().optionalFieldOf("components")
                    .forGetter(stack -> {
                        final ItemComponents components = ItemComponents.of(stack);
                        return components.isEmpty() ? Optional.empty() : Optional.of(components);
                    })
    ).apply(instance, (id, count, components) -> {
        final ItemComponents resolved = components.orElse(ItemComponents.EMPTY);
        return new ItemStack(
                id,
                count,
                resolved.customName().orElse(null),
                resolved.itemName().orElse(null),
                resolved.lore().orElse(List.of()),
                List.of());
    }));

    private DialogItemCodecs() {
        throw new UnsupportedOperationException("DialogItemCodecs cannot be instantiated.");
    }

    private record ItemComponents(
            Optional<Component> customName,
            Optional<Component> itemName,
            Optional<List<Component>> lore
    ) {

        static final ItemComponents EMPTY = new ItemComponents(Optional.empty(), Optional.empty(), Optional.empty());

        static ItemComponents of(final ItemStack stack) {
            return new ItemComponents(
                    Optional.ofNullable(stack.customName()),
                    Optional.ofNullable(stack.itemName()),
                    stack.lore().isEmpty() ? Optional.empty() : Optional.of(stack.lore()));
        }

        boolean isEmpty() {
            return customName.isEmpty() && itemName.isEmpty() && lore.isEmpty();
        }
    }
}
