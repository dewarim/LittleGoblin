import de.dewarim.goblin.pc.GoblinOrder
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.social.ChatterBox

fixture{
    ebonOrder(GoblinOrder, name:'order.ebon', description:'order.ebon.description', 
            leader: PlayerCharacter.findByName('Gobli'))
    chatterBox(ChatterBox, name:'chatterbox.default.name', goblinOrder:ebonOrder)
}