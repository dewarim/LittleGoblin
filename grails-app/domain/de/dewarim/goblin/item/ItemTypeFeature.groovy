package de.dewarim.goblin.item

import de.dewarim.goblin.Feature

/**
 * Mapping class between ItemType and Feature. The config field can help to parametrize the selected
 * Feature class, so you can have a "Heal Player" feature, which is configured to heal 1 or 10 points, depending
 * on the item type it is combined with.
 */
class ItemTypeFeature {

    static belongsTo = [itemType:ItemType, feature:Feature]
    static constraints = {
        config size: 1..65536 // somewhat arbitrary, but should be larger than 2^16 -1
        // (otherwise, Hibernate with MySQL may assign a broken varchar column instead of a text field of some kind)
    }
    ItemTypeFeature(){}

    /**
     * Configuration parameters for the associated feature. You should use XML.
     */
    String config = "<config />"

    /**
     * Constructor to create a correctly initialized ItemTypeFeature instance.
     * @param itemType the ItemType
     * @param feature the Feature
     */
    ItemTypeFeature(ItemType itemType, Feature feature){
        this.itemType = itemType
        this.feature = feature
        itemType.addToItemTypeFeatures this
        feature.addToItemTypeFeatures this
    }

    boolean equals(o) {
        if (is(o)) return true
        if (!(o instanceof ItemTypeFeature)) return false

        ItemTypeFeature that = o

        if (config != that.config) return false
        if (feature != that.feature) return false
        if (itemType != that.itemType) return false

        return true
    }

    int hashCode() {
        int result
        result = (config != null ? config.hashCode() : 0)
        result = 31 * result + (itemType != null ? itemType.hashCode() : 0)
        result = 31 * result + (feature != null ? feature.hashCode() : 0)
        return result
    }
}
