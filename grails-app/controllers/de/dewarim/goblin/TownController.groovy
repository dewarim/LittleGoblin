package de.dewarim.goblin;
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.quest.QuestTemplate
import de.dewarim.goblin.social.MailBox;

class TownController extends BaseController {

    def questService

    @Secured(['ROLE_USER'])
    def show = {
        def pc = fetchPc(session)
        if(! pc){
            return redirect(controllerName: 'portal', actionName:'start')
        }
        if (!pc.alive) {
            pc.resurrect()
            flash.message = message(code: 'character.resurrected', args: [pc.name])
        }

        session['pc'] = pc.id
        
        if (pc.currentQuest) {
            return redirect(action: 'show', controller: 'quest', params: [pc: pc.id])
        }

        // possible optimization: cache the results or use just one query.

        MailBox inBox = pc.fetchInbox()
        def mailCount = inBox.countUnreadMail()

        return [
                // TODO: filter list of quests as appropriate (Level, previous quests, quest Requirements etc)
                pc: pc,
                town: pc.town,
                mailCount:mailCount,
                openQuests : pc.fetchOpenQuests(),
                questMasters: questService.listQuestGivers(pc, true),
                questService:questService
        ]
    }

}
