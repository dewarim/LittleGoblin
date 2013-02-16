package de.dewarim.goblin

import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.pc.PlayerCharacter

@Secured(['ROLE_USER'])
class PlayerCharacterController extends BaseController{

	def playerMessageService

	def show() {
        def pc = fetchPc()

		return [
		        pc:pc,
                combatSkills: pc.fetchCombatSkills(),
                productionSkills: pc.fetchProductionSkills()
		]
	}

    def save() {
        def user = fetchUser()
        if(! params.name){
            redirect(controller:'portal', action:'start', params:[createError:'pc.name.empty'])
            return
        }
        params.name = params.name.encodeAsHTML() // do not allow arbitrary HTML sequences.
        if(params.name.length() > 32){
            redirect(controller:'portal', action:'start', params:[createError:'pc.name.too_long'])
            return
        }
        if(params.name.length() < 3){
            redirect(controller:'portal', action:'start', params:[createError:'pc.name.too_short'])
            return
        }

        def checkName = PlayerCharacter.findByName(params.name)
        if(checkName){
            redirect(controller:'portal', action:'start', params:[createError:'pc.name.exists'])
            return
        }

        PlayerCharacter pc = new PlayerCharacter(name:params.name)
        pc.user = user
        if(pc.save(flush:true)){
            pc.initializePlayerCharacter()
            flash.message = message(code:"pc.create.success", args:[pc.name])
            redirect(controller:'town', action:'show',params:[pc:pc.id] )
            return
        }
        else{
            redirect(controller:'portal', action:'start')
            return
        }
    }

    def editDescription() {
        def pc = fetchPc()
        if(! pc){
            render(status:503, text:message(code:'error.player_not_found'))
            return
        }
        render(template:'/playerCharacter/editDescription', model:[pc:pc])
    }

    def saveDescription() {
        def pc = fetchPc()
        if(! pc){
            render(status:503, text:message(code:'error.player_not_found'))
        }
        if(! params.description ){
            pc.description = ""
        }
        else{
            pc.description = params.description
        }
        render(template:'/playerCharacter/showDescription', model:[pc:pc])
    }

    def fetchMessages() {
        def pc = fetchPc()
        def pcMessages = null
        if(pc){
            pcMessages = playerMessageService.fetchPlayerMessages(pc)
        }
        render(template:'/shared/playerMessages', model:[pcMessages:pcMessages, pc:pc])
    }

    def fetchEquipment() {
        def pc = fetchPc()
        if(pc){
            render(template: '/shared/equipment', model:[pc:pc])
        }
        else{
            render(status:500, text:'error.player.not.found')
        }
    }
}
