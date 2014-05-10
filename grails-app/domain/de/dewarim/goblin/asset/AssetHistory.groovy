package de.dewarim.goblin.asset

import de.dewarim.goblin.UserAccount

/**
 * An entry in the history of an asset, from creation by a user to the (final) decision of
 * the asset manager.
 */

class AssetHistory {

    static constraints = {
        userAccount nullable: true        
    }   
    
    Asset asset
    UserAccount userAccount
    AssetStatus status = AssetStatus.UNDECIDED
    Boolean allowObjections = true
    Date dateCreated
    String note = ''

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof AssetHistory)) return false

        AssetHistory that = (AssetHistory) o

        if (allowObjections != that.allowObjections) return false
        if (asset != that.asset) return false
        if (dateCreated != that.dateCreated) return false
        if (note != that.note) return false
        if (status != that.status) return false
        if (userAccount != that.userAccount) return false

        return true
    }

    int hashCode() {
        int result
        result = asset.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + dateCreated.hashCode()
        return result
    }
}
