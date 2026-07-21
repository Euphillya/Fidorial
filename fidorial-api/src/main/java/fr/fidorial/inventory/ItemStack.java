package fr.fidorial.inventory;

import fr.fidorial.attribute.AttributeModifier;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ItemStack {

    private static final Key AIR = Key.key("air");
    public static final ItemStack EMPTY = new ItemStack(AIR, 0);

    private final Key id;
    private final int count;
    private final @Nullable Component customName;
    private final @Nullable Component itemName;
    private final List<Component> lore;
    private final List<AttributeModifier> attributeModifiers;

    public ItemStack(Key id, int count) {
        this(id, count, null, null, List.of(), List.of());
    }

    public ItemStack(
            Key id,
            int count,
            @Nullable Component customName,
            @Nullable Component itemName,
            List<Component> lore,
            List<AttributeModifier> attributeModifiers
    ) {
        this.id = Objects.requireNonNull(id, "id");
        this.count = count;
        this.customName = customName;
        this.itemName = itemName;
        this.lore = List.copyOf(lore);
        this.attributeModifiers = List.copyOf(attributeModifiers);
    }

    public static ItemStack of(Key key, int count) {
        return new ItemStack(key, count);
    }

    public Key id() {
        return id;
    }

    public int count() {
        return count;
    }

    public @Nullable Component customName() {
        return customName;
    }

    public @Nullable Component itemName() {
        return itemName;
    }

    public List<Component> lore() {
        return lore;
    }

    public List<AttributeModifier> attributeModifiers() {
        return attributeModifiers;
    }

    public boolean hasCustomName() {
        return customName != null;
    }

    public boolean hasItemName() {
        return itemName != null;
    }

    public boolean hasLore() {
        return !lore.isEmpty();
    }

    public boolean hasAttributeModifiers() {
        return !attributeModifiers.isEmpty();
    }

    public boolean isEmpty() {
        return count <= 0 || id.equals(AIR);
    }

    public ItemStack withCount(int newCount) {
        return newCount == count ? this : new ItemStack(id, newCount, customName, itemName, lore, attributeModifiers);
    }

    public ItemStack withCustomName(Component name) {
        return new ItemStack(id, count, name, itemName, lore, attributeModifiers);
    }

    public ItemStack withItemName(Component name) {
        return new ItemStack(id, count, customName, name, lore, attributeModifiers);
    }

    public ItemStack withLore(@Nullable List<Component> newLore) {
        return new ItemStack(
                id, count, customName, itemName, newLore == null ? List.of() : newLore, attributeModifiers);
    }

    public ItemStack withLoreLine(Component line) {
        Objects.requireNonNull(line, "line");
        List<Component> copy = new ArrayList<>(lore);
        copy.add(line);
        return new ItemStack(id, count, customName, itemName, copy, attributeModifiers);
    }

    public ItemStack withAttributeModifiers(@Nullable List<AttributeModifier> modifiers) {
        return new ItemStack(id, count, customName, itemName, lore, modifiers == null ? List.of() : modifiers);
    }

    public ItemStack withAttributeModifier(AttributeModifier modifier) {
        Objects.requireNonNull(modifier, "modifier");
        List<AttributeModifier> copy = new ArrayList<>(attributeModifiers);
        copy.add(modifier);
        return new ItemStack(id, count, customName, itemName, lore, copy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemStack other)) return false;
        return count == other.count
                && id.equals(other.id)
                && Objects.equals(customName, other.customName)
                && Objects.equals(itemName, other.itemName)
                && lore.equals(other.lore)
                && attributeModifiers.equals(other.attributeModifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, count, customName, itemName, lore, attributeModifiers);
    }

    @Override
    public String toString() {
        return "ItemStack{" + id.asString() + " x" + count
                + (hasCustomName() ? ", customName" : "")
                + (hasLore() ? ", lore=" + lore.size() : "")
                + (hasAttributeModifiers() ? ", attributes=" + attributeModifiers.size() : "")
                + "}";
    }
}
