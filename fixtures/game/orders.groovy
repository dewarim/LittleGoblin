import de.dewarim.goblin.pc.GoblinOrder
import de.dewarim.goblin.social.ChatterBox

fixture{
    // would like to add Gobli as leader, but that leads to an unexplainable unsaved transient instance exception.
    // so the leader is set in BootStrap.groovy
    ebonOrder(GoblinOrder, name:'order.ebon', description:'order.ebon.description')
    chatterBox(ChatterBox, name:'chatterbox.default.name', goblinOrder:ebonOrder)
}