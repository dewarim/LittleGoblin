package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.asset.Asset
import de.dewarim.goblin.asset.AssetStatus
import org.springframework.security.access.annotation.Secured

@Secured(["ROLE_ADMIN"])
class AssetController extends BaseController{
    
    def assetService
    
    def index() {
        try{
        def waitingAsses = Asset.findAllByStatus(AssetStatus.UNDECIDED)
        def assetLinkMap = [:]
        waitingAsses.each { ass ->
            def controllerAndAction = assetService.getAssetControllerAndAction(ass)
            if(controllerAndAction == null){
                throw new RuntimeException("Cannot find assetController for ${ass.name}, ${ass.assetClass.simpleName}")
            }
            controllerAndAction.put("id", ass.assetId)
            assetLinkMap.put(ass.id, controllerAndAction)
        }
        return [waitingAsses: waitingAsses, assetLinkMap:assetLinkMap]
        }
        catch (Exception e){
            log.debug("Failed to show index:",e)
            renderException(e)
        }
        

    }


}
