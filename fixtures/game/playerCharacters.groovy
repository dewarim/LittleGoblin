import de.dewarim.goblin.UserAccount
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.social.MailBox
import de.dewarim.goblin.social.MailBoxType

def user = UserAccount.findByUsername('anon')
def count = 0
def ore = ItemType.findByName('item.iron.ore')
def iron = ItemType.findByName('item.iron.bar')

fixture{
    
    gob(PlayerCharacter, name:'Gobli', user:user, gold:100, xp:10)
    MailBoxType.list().each{box ->
        "box${count++}"(MailBox, owner:gob, boxType:box)
    }
    gobOre(Item, type: ore, amount: 10, owner: gob)
    gobIron(Item, type: iron, amount: 10, owner: gob)
    
}