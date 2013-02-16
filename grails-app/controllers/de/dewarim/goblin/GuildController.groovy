package de.dewarim.goblin

import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.guild.Guild

@Secured(['ROLE_USER'])
class GuildController extends BaseController{

    def academyService
    def guildMemberService

    /**
     * Main overview of guilds
     */
    def index() {
        def pc = fetchPc()

        def max = new Integer(params.max ?: 10)
        def offset = new Integer(params.offset ?: 0)
        return [pc:pc,
                guilds:Guild.listOrderByName(max:max, offset:offset),
                guildMemberService:guildMemberService,
                max:max,
                offset:offset,
        ]
    }

    def show() {
        def pc = fetchPc()

        def guild = Guild.get(params.guild)
        if(! guild){
            flash.message = message(code:'error.guild.not_found')
            redirect(controller:'town', action:'index')
            return
        }
        if(! guildMemberService.checkMembership(pc, guild)){
            flash.message = message(code:'error.guild.no.member')
            redirect(controller:'guild', action:'show')
            return
        }

        return [
                guild:guild,
                pc:pc
        ]
    }

    def showMyGuilds() {
        def pc = fetchPc()

        def max = new Integer(params.max ?: 10)
        def offset = new Integer(params.offset ?: 0)
        return [pc:pc,
                guilds:guildMemberService.fetchGuilds(pc, max, offset),
                max:max,
                offset:offset,
        ]
    }

    /**
     * Join a guild
     */
    def join() {
        def pc = fetchPc()

        Guild guild = Guild.get(params.guild)
        if(! guild){
            flash.message = message(code:'error.guild.not.found')
            redirect(action:'index', controller:'guild')
            return
        }
        if( guildMemberService.checkMembership(pc, guild)){
            flash.message = message(code:'error.guild.is.member')
            redirect(action:'index', controller:'guild')
            return
        }
        if( pc.gold < guild.entryFee){
            flash.message = message(code:'error.insufficient.gold')
            redirect(action:'index', controller:'guild')
            return
        }
        else{
            pc.gold = pc.gold - guild.entryFee
        }

        // setup guild relationships
        guildMemberService.joinGuild(pc,guild)

        // add AcademyLevels
        academyService.joinGuild(pc,guild)

        flash.message = message(code:'guild.join.success', args:[message(code:guild.name)])
        redirect(action:'show', controller:'guild',params:[pc:pc.id, guild:guild.id])
        return
    }

    /**
    * Leave a guild
     */
    def leave() {
        def pc = fetchPc()

        Guild guild = Guild.get(params.guild)
        if(! guild){
            flash.message = message(code:'error.guild.not.found')
            redirect(action:'index', controller:'guild')
            return
        }
        if( !  guildMemberService.checkMembership(pc, guild) ){
            flash.message = message(code:'error.guild.no.member')
            redirect(action:'index', controller:'guild')
            return
        }

        // check if the PC is expelled from one or more academies:
        // if he has no more sponsoring guilds, he looses the academy access (and level!)
        academyService.leaveGuild(pc,guild)

        guildMemberService.leaveGuild(pc,guild)

        flash.message = message(code:'guild.leave.success', args:[message(code:guild.name)])
        redirect(action:'index', controller:'guild',params:[pc:pc.id])
        return
    }

    /**
     * List guilds  [Ajax]
     */
    def list() {
        def pc = fetchPc()

        def max = new Integer(params.max ?: 10)
        def offset = new Integer(params.offset ?: 0)
        render(template:'/guild/guild_list',
                model:[
                pc:pc,
                max:max,
                offset:offset,
                orders:Guild.listGuildByName(max:max, offset:offset),
                ]
                )
    }

    def describe() {
        def pc = fetchPc()

        Guild guild = Guild.get(params.guild)
        if(! guild){
            render(status:503, text:message(code:'error.guild.not_found'))
            return
        }
        render(template:"/guild/guild_description", model:[guild:guild])
    }
}
