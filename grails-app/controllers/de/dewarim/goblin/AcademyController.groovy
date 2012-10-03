package de.dewarim.goblin

import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.town.Academy
import de.dewarim.goblin.town.AcademySkillSet
import groovy.xml.MarkupBuilder
import de.dewarim.goblin.pc.skill.LearningQueueElement

class AcademyController extends BaseController {
    transient jmsTemplate

    def academyService
    def messageSenderService

    /**
     * Provide an overview of available academies
     */
    @Secured(['ROLE_USER'])
    def index = {
        def pc = fetchPc()

        Integer max = new Integer(params.max ?: 10)
        Integer offset = new Integer(params.offset ?: 0)
        def academies = academyService.fetchAccessibleAcademies(pc, max, offset)
        def academyCount = academyService.fetchAccessibleAcademyCount(pc)
        return [
                pc:pc,
                academies:academies,
                academyCount:academyCount,
                max:max,
                offset:offset
        ]
    }

    /**
       * Show a single academy
       */
    @Secured(['ROLE_USER'])
    def show = {
        def pc = fetchPc()

        def academy = Academy.get(params.academy)
        if(! academy){
            flash.message = message(code: 'error.academy.not_found')
              return redirect(controller: 'town', action: 'show')
        }

        if (academyService.checkPlayerAccess(pc, academy)) {
            Map<AcademySkillSet, LearningQueueElement> queue = new HashMap<AcademySkillSet, LearningQueueElement>()
            pc.learningQueueElements.each{
                queue.put(it.academySkillSet, it)
            }

            return [
                    pc: pc,
                    queue:queue,
                    academy: academy,                    
                    academySkillSets:academyService.filterSkillSets(pc, academy)
            ]
        }
        else {
            flash.message = message(code: 'error.academy.no.member')
            return redirect(controller: 'town', action: 'show')
        }

    }

    /**
     * Show the description of an academy. [AJAX]
     */
    @Secured(['ROLE_USER'])
    def describe = {
        Academy academy = Academy.get(params.academy)
        if(! academy){
            return render(status:503, text:message(code:'error.academy.not_found'))
        }
        return render(template:"/academy/academy_description", model:[academy:academy])
    }

    /**
     * List academies  [Ajax]
     */
    @Secured(['ROLE_USER'])
    def list = {
        def pc = fetchPc()

        def max = new Integer(params.max ?: 10)
        def offset = new Integer(params.offset ?: 0)
        render(template: '/academy/academy_list',
                model: [
                        pc: pc,
                        max: max,
                        offset: offset,
                        academies: academyService.fetchAccessibleAcademies(pc, max, offset),
                        academyCount: academyService.fetchAccessibleAcademyCount(pc)
                ]
        )
    }

    @Secured(['ROLE_USER'])
    def learnSkillSet = {
        def pc = fetchPc()
        def academy = Academy.get(params.academy)
        if(! academy){
            flash.message = message(code: 'error.academy.not_found')
            return redirect(controller: 'town', action: 'show')
        }
        if (academyService.checkPlayerAccess(pc, academy)) {
            AcademySkillSet ass = AcademySkillSet.get(params.ass)
            if(! ass ){
                flash.message = message(code: 'error.skillset.not_found')
                return redirect(controller:'town', action:'show')
            }
            def academySkillSets = academyService.filterSkillSets(pc, academy)
            if(! academySkillSets.find{it.equals(ass)}){
                flash.message = message(code: 'error.skillset.foreign')
                return redirect(controller:'town', action:'show')
            }
            def alreadyLearning = pc.learningQueueElements.find{it.academySkillSet.equals(ass)}
            if(alreadyLearning){
                flash.message = message(code: 'error.already.learning')
                return redirect(controller:'academy', action:'show', params:[academy:params.academy])
            }

            // time to pay for this ass:
            if(academyService.payForSkillSet(pc, ass)){
                LearningQueueElement queueElement = academyService.addSkillSetToLearningQueue(pc, ass)
//                messageSenderService.sendLearningMessage(createLearningMessage(queueElement))
                flash.message = message(code:'skillSet.start.learning', args:[message(code:ass.skillSet.name)])
            }
            else{
                flash.message = message(code:'error.insufficient.funds')
            }

            return redirect(action:'show', controller:'academy', params:[academy:academy.id])
        }
        else {
            flash.message = message(code: 'error.academy.no.member')
            return redirect(controller: 'town', action: 'show')
        }
    }

    @Secured(['ROLE_USER'])
    def stopLearning = {
        def pc = fetchPc()

        LearningQueueElement queueElement = LearningQueueElement.get(params.queueElement)
        if(! queueElement){
            flash.message = message(code:'error.queueElement.not_found')
            return redirect(controller:'town', action:'show')
        }
        if(pc.learningQueueElements.contains(queueElement)){
            def ass = queueElement.academySkillSet
            academyService.refundLearningCost queueElement
            pc.removeFromLearningQueueElements queueElement
            ass.removeFromLearningQueueElements queueElement
            queueElement.delete()                       
            return redirect(controller:'academy', action:'show', params:[academy:ass.academy.id])
        }
        else{
            flash.message = message(code:'error.object.foreign')
            return redirect(controller:'town', action:'show')
        }

    }

    protected String createLearningMessage(LearningQueueElement queueElement){
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml.learningMessage(id: queueElement.id) {
            delay( "${queueElement.academySkillSet.tellRequiredTime()}")
        }
        return writer.toString()
    }

}
