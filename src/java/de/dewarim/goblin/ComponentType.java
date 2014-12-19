package de.dewarim.goblin;

/**
 * Types of components used for crafting and production.
 */
public enum ComponentType {

    /**
     * Components of type INPUT will be used up during production.
     */
    INPUT,

    /**
     * Components of type OUTPUT will be created by the production process.
     */
    OUTPUT,

    /**
     * Components of type TOOL are required for a production process, but will
     * not be consumed by it. Example: an anvil may be used to create a sword.
     */
    TOOL

}
