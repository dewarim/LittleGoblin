import de.dewarim.goblin.GoblinScript
import de.dewarim.goblin.quest.script.DeliverItem
import de.dewarim.goblin.quest.script.GetReward
import de.dewarim.goblin.quest.script.PickupItem

fixture{
    
    pickUp(GoblinScript, name:'script.pickupItem', script:PickupItem)
    deliverItem(GoblinScript, name:'script.deliverItem', script:DeliverItem)
    reward(GoblinScript, name:'script.getReward', script:GetReward)
    
}