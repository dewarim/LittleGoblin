import de.dewarim.goblin.reputation.Faction
import de.dewarim.goblin.reputation.ReputationMessageMap


include('game/reputationMessages')

fixture{
    
    dwarvesFaction(Faction, name:'faction.dwarves', description:'faction.dwarves.description',
                repMessageMap:rmmDwarves
    )
    elvesFaction(Faction, name:'faction.elves', description:'faction.elves.description',
                repMessageMap: rmmElves
    )
    
}