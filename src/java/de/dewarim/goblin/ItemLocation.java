package de.dewarim.goblin;

/**
 * Location of items owned by a player character.
 */
public enum ItemLocation {

    /**
     * Item is in the player character's base or home. 
     * It should only be usable for crafting / trading.
     */
    AT_HOME, 
    
    /**
     * Item is being carried around and may be used or equipped.
     */ 
    ON_PERSON

}
