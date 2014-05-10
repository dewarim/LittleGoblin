package de.dewarim.goblin

import de.dewarim.goblin.asset.Asset
import de.dewarim.goblin.asset.AssetHistory
import de.dewarim.goblin.asset.AssetStatus
import de.dewarim.goblin.asset.IAsset
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.quest.QuestTemplate

class AssetService {

    static assetControllers = [
//            "${ItemType.class.getSimpleName()}": [controller: 'itemAdmin', action: 'edit'],
            "ItemType": [controller: 'itemAdmin', action: 'edit'],
            "QuestTemplate": [controller: 'questAdmin', action: 'edit']
    ]
    
    Map getAssetControllerAndAction(Asset asset) {
        log.debug("fetch assetController for ${asset.assetClass.simpleName}")
        log.debug("${assetControllers.dump()}")
        return assetControllers.get(asset.assetClass.simpleName)
    }
    
    /*
     * Create a new Asset for the asset workflow.
     * Set the assetCandidate to inactive.
     */
    void addAsset(IAsset assetCandidate, UserAccount user){
        def asset = new Asset(assetId: assetCandidate.myId,
                        assetClass: assetCandidate.class,
                name:assetCandidate.name
        )
        asset.save()
        assetCandidate.active = false
        def history = new AssetHistory(asset:asset, userAccount: user)
        history.note = "new asset created by ${user.username}"
        history.save()
    }

    /**
     * Update an asset: allow a user to make changes, for example to 
     * fix an item's description before it goes live.
     */
    void updateAsset(IAsset myAsset, UserAccount user){
        def asset = Asset.findByAssetIdAndAssetClass(myAsset.myId, myAsset.class)
        if(! asset){
            // no asset found: may happen if object was generated outside of
            // normal workflow, for example by scripted events.
            asset = new Asset(assetId: myAsset.myId, assetClass: myAsset.class,
            name:myAsset.name)
            asset.save()
        }
        asset.status = AssetStatus.UNDECIDED
        def history = new AssetHistory(asset:asset, userAccount: user)
        history.note = "asset was modified by ${user.username}"
        history.save()
    }
    
    boolean isLive(IAsset myAsset){
        def asset = Asset.findByAssetIdAndAssetClass(myAsset.myId, myAsset.class)
        if(! asset){
            return false
        }
        return asset.status == AssetStatus.ACTIVE
    }
    
}
