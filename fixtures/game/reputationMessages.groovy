import de.dewarim.goblin.reputation.ReputationMessage
import de.dewarim.goblin.reputation.ReputationMessageMap

def repMessages = ['unknown': 0, 'good': 1, 'very.good': 11,
        'very.very.good': 21,
        'best': 41,
        'bad': -1, 'very.bad': -11,
        'extremely.bad': -21,
        'worst': -41]
def count = 0

fixture{
    
    rmmDwarves(ReputationMessageMap, name:'rmm.dwarves')
    rmmElves(ReputationMessageMap, name:'rmm.elves')
   
    repMessages.each {messageId, repRange ->
        "rm${count++}"(ReputationMessage, messageId:"reputation.$messageId",
                repMessageMap: rmmDwarves,
                reputation: repRange)
        "rm${count++}"(ReputationMessage, messageId:"reputation.$messageId",
                repMessageMap: rmmElves,
                reputation: repRange)        
    }

}