package de.dewarim.goblin

import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.quest.Encounter
import de.dewarim.goblin.quest.QuestGiver
import de.dewarim.goblin.quest.QuestStep
import de.dewarim.goblin.quest.QuestTemplate

/**
 *
 */
class QuestService {

    void executeEncounterScript(QuestStep step, PlayerCharacter pc){
        if(step?.encounter?.script){
            executeScript(step.encounter, pc)
		}
    }

    void executeScript(Encounter encounter,PlayerCharacter pc){
        IEncounterScript encounterScript
		try{
			encounterScript = (IEncounterScript) encounter.script.script.newInstance()
		}
        catch (ClassNotFoundException e){
            log.warn("Problem during executeScript: '${encounter.script.name}'",e)
            throw new RuntimeException(e)
        }
		catch (IllegalArgumentException e) {
			throw new RuntimeException(e)
		} catch (InstantiationException e) {
			throw new RuntimeException(e)
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e)
		}

		encounterScript.execute(pc, pc.currentQuest, encounter.config)
    }

    // currently, we return all quest givers.
    List<QuestGiver> listQuestGivers(PlayerCharacter pc, Boolean filterForAvailableQuests){
        List<QuestGiver> questMasters = QuestGiver.list()
        if(filterForAvailableQuests){
            questMasters = questMasters.findAll{questMaster ->
                fetchAvailableQuestsByQuestGiver(questMaster, pc)?.size() > 0
            }
        }
        return questMasters
    }

    List<QuestTemplate> fetchAvailableQuestsByQuestGiver(QuestGiver questGiver, PlayerCharacter pc){
        return QuestTemplate.findAll("from QuestTemplate qt where qt.giver=:questMaster and qt not in (select q.template from Quest q where q.playerCharacter=:pc)", [questMaster:questGiver, pc:pc])
    }
}
