package de.dewarim.goblin

import de.dewarim.goblin.shop.ShopCategory
import de.dewarim.goblin.item.ItemCategory

/**
 * Categories allow the game designer to create groups of items and shops which buy / sell them.
 * An ItemType or Shop may have null..many categories.
 * Note-1: If the amount of ItemTypes grows dramatically,
 * it may be necessary to create a category hierarchy with parent-child relations.
 * Note-2: It may be useful to have a mapping from Category.class to Feature.class, to
 * enable group-wide features of items.
 */
class Category{

    static hasMany = [itemCategories:ItemCategory, shopCategories:ShopCategory]

    String name

    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        if (name != category.name) return false;

        return true;
    }

    int hashCode() {
        return (name != null ? name.hashCode() : 0);
    }
}