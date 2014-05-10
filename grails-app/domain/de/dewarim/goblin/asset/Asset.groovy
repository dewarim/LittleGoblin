package de.dewarim.goblin.asset

class Asset {

    static constraints = {
    }
    
    Class assetClass
    Long assetId
    AssetStatus status = AssetStatus.UNDECIDED
    String name

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Asset)) return false

        Asset asset = (Asset) o

        if (assetClass != asset.assetClass) return false
        if (assetId != asset.assetId) return false
        if (status != asset.status) return false

        return true
    }

    int hashCode() {
        int result
        result = assetClass.hashCode()
        result = 31 * result + assetId.hashCode()
        return result
    }
}
