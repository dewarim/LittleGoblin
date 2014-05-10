package de.dewarim.goblin.asset;

/**
 * Interface implemented by classes that are manageable as assets.
 * Assets may be created by users and go through a workflow where they are
 * either accepted or denied by an administrator.
 */
// Note: I would have liked a better name without leading I, but could not think of one.
public interface IAsset {
    
    Long getMyId();
    
    Boolean getActive();

    /**
     * After an item has been accepted during the asset workflow, it's status 
     * will be set to active.
     * @param active set to true to allow this item to be used by other
     *               users of the application.
     */
    void setActive(Boolean active);

    /**
     * An Asset should have a name or at least a descriptive text to be used
     * in a link to this object.
     * @return the asset's name or an appropriate short text
     */
    String getName();
    
    Long getAssetVersion();
}
