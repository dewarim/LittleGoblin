package de.dewarim.goblin;

import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.pc.PlayerCharacter

class PlayerCharacterController extends BaseController{
	
	def session
	def playerMessageService
    
	@Secured(['ROLE_USER'])
	def show = {
        def pc = fetchPc(session)

		return [
		        pc:pc,
                combatSkills: pc.fetchCombatSkills(),
                productionSkills: pc.fetchProductionSkills()
		]
	}

    @Secured(['ROLE_USER'])
    def save = {
        def user = fetchUser()
        if(! params.name){
            return redirect(controller:'portal', action:'start', params:[createError:'pc.name.empty'])
        }
        params.name = params.name.encodeAsHTML() // do not allow arbitrary HTML sequences.
        if(params.name.length() > 32){
            return redirect(controller:'portal', action:'start', params:[createError:'pc.name.too_long'])
        }
        if(params.name.length() < 3){
            return redirect(controller:'portal', action:'start', params:[createError:'pc.name.too_short']) 
        }

        def checkName = PlayerCharacter.findByName(params.name)
        if(checkName){
            return redirect(controller:'portal', action:'start', params:[createError:'pc.name.exists'])
        }

        PlayerCharacter pc = new PlayerCharacter(name:params.name)
        pc.user = user
        pc.initializePlayerCharacter()
        if(pc.save(flush:true)){
            flash.message = message(code:"pc.create.success", args:[pc.name])
            return redirect(controller:'town', action:'show',params:[pc:pc.id] )
        }
        else{            
            return redirect(controller:'portal', action:'start')
        }

    }

    @Secured(['ROLE_USER'])
    def editDescription = {
        def pc = fetchPc(session)
        if(! pc){
            return render(status:503, text:message(code:'error.player_not_found'))
        }
        return render(template:'/playerCharacter/editDescription', model:[pc:pc])
    }

    @Secured(['ROLE_USER'])
    def saveDescription = {
        def pc = fetchPc(session)
        if(! pc){
            render(status:503, text:message(code:'error.player_not_found'))
        }
        if(! params.description ){
            pc.description = ""
        }
        else{
            pc.description = params.description
        }
        return render(template:'/playerCharacter/showDescription', model:[pc:pc])
    }

    @Secured(['ROLE_USER'])
    def fetchMessages = {
        def pc = fetchPc(session)
        def pcMessages = null
        if(pc){
            pcMessages = playerMessageService.fetchPlayerMessages(pc)
        }
        return render(template:'/shared/playerMessages', model:[pcMessages:pcMessages, pc:pc])
    }

    @Secured(['ROLE_USER'])
    def fetchEquipment = {
        def pc = fetchPc(session)
        if(pc){
            return render(template: '/shared/equipment', model:[pc:pc])
        }
        else{
            return render(status:500, text:'error.player.not.found')
        }
    }
}
