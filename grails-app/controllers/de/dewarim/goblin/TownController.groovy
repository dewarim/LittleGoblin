package de.dewarim.goblin

import grails.plugin.springsecurity.annotation.Secured
import de.dewarim.goblin.social.MailBox

@Secured(['ROLE_USER'])
class TownController extends BaseController {

    def questService

    def show() {
        def pc = fetchPc()
        if(! pc){
            redirect(controller: 'portal', action:'start')
            return
        }
        if (!pc.alive) {
            pc.resurrect()
            flash.message = message(code: 'character.resurrected', args: [pc.name])
        }

        session['pc'] = pc.id

        if (pc.currentQuest) {
            redirect(action: 'show', controller: 'quest', params: [pc: pc.id])
            return
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
