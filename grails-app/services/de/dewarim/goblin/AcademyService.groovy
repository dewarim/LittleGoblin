package de.dewarim.goblin

import de.dewarim.goblin.town.AcademyLevel
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.guild.Guild
import de.dewarim.goblin.town.GuildAcademy
import de.dewarim.goblin.town.Academy
import de.dewarim.goblin.pc.skill.SkillSet
import de.dewarim.goblin.town.AcademySkillSet
import de.dewarim.goblin.pc.skill.LearningQueueElement

/**
 * When a player character joins a guild, he or she gains the access to all
 * academies of that guild. When the pc leaves, he looses this access along
 * with the AcademyLevels gaines so far.
 */
class AcademyService {

    def globalConfigService

    void leaveGuild(PlayerCharacter pc, Guild guild){
        /*
            Find the AcademyLevels for which the following is true:
            1. it belongs to the pc
            2. the Academy access is granted by this guild
         */

        log.debug("leaveGuild: $pc, $guild")

        Collection<AcademyLevel> academyLevels =
            AcademyLevel.findAll("from AcademyLevel as al where al.pc=:pc and al.academy in \
            (select ga.academy from GuildAcademy as ga where ga.guild=:guild)",
                [guild:guild, pc:pc])

        academyLevels.each{al ->
            // look if there is another guild which also grants this access.
            def alternatives = AcademyLevel.executeQuery("select count(ga) from GuildAcademy as ga where ga.academy=:academy \
        and ga.guild in (select gm.guild from GuildMember as gm where gm.pc=:pc)",
            [academy:al.academy, pc:pc])
            if(alternatives && (Integer) alternatives[0] == 1){
                // access comes only by this guild.
                al.academy.removeFromAcademyLevels al
                al.pc.removeFromAcademyLevels al
                al.delete()
            }
        }
    }

    void joinGuild(PlayerCharacter pc, Guild guild){   

        guild.guildAcademies.each {ga ->
            Academy academy = ga.academy
            if(! pc.academyLevels.contains(academy)){
                AcademyLevel al = new AcademyLevel(pc:pc, academy:academy)
                pc.addToAcademyLevels(al)
                academy.addToAcademyLevels(al)
                al.save()
            }
        }
    }

    /**
     * Check if academy requires a guild
     * @param pc a PlayerCharacter
     * @param academy an Academy
     * @return true if player may access this Academy, false if he lacks access (which is granted
     * by guild membership)
     */
    Boolean checkPlayerAccess(PlayerCharacter pc, Academy academy){

        if(academy.guildAcademies.size() > 0){
            def pcGuilds = pc.guildMemberships.collect{it.guild}
            def academyGuilds = academy.guildAcademies.collect{it.guild}
            return academyGuilds.minus(pcGuilds).size() != academyGuilds.size()
        }
        else{
            // if an academy has no guildAcademies, it is freely accessible.
            /*
             * If the pc does not already have an AcademyLevel with this academy,
             * add one.
             */
            if(! AcademyLevel.findWhere(pc:pc, academy:academy)){
                AcademyLevel al = createAcademyLevel(pc,academy)
                log.debug("created AcademyLevel $al")
            }
            return true
        }
    }

    AcademyLevel createAcademyLevel(pc,academy){
        AcademyLevel nal = new AcademyLevel(pc: pc, academy: academy)
        pc.addToAcademyLevels(nal)
        academy.addToAcademyLevels(nal)
        nal.save()
        return nal
    }

    Collection<AcademySkillSet> filterSkillSets(PlayerCharacter pc, Academy academy){
        AcademyLevel al = AcademyLevel.findWhere(pc:pc, academy:academy)
        if(! al){
            al = createAcademyLevel( pc, academy)
        }
        def asses = AcademySkillSet.findAll("from AcademySkillSet as ass where ass.academy=:academy and ass.requiredLevel = :level order by ass.requiredLevel ",
                [academy:academy, level:al.level], [max:10] )
        return asses
    }

    /**
     * Find all Academies to which a player has access - this includes both the ones which are
     * open to anybody and the ones to which the player has access through her guild memberships.
     * @param pc the PlayerCharacter
     * @param max maximum number of academies per page
     * @param offset start with academy #offset
     * @return a collection of academies which are accessible to the player character - limited by max and offset.
     */
    Collection<Academy> fetchAccessibleAcademies(PlayerCharacter pc, Integer max, Integer offset){
        def accessibleAcademiesHQL =
        "from Academy as ac where ac not in (select ga.academy from GuildAcademy as ga) or \
        ac in (select al.academy from AcademyLevel as al where al.pc=:pc)"
        
        def academies = Academy.findAll( accessibleAcademiesHQL,
                [pc:pc],[max:max, offset:offset])
        return academies
    }

    Integer fetchAccessibleAcademyCount(PlayerCharacter pc){
        def x = Academy.executeQuery("""select count(a) from Academy as a where (a.id not in (select ga.academy.id  from GuildAcademy as ga))
                or (a.id in (select al.academy.id from AcademyLevel as al where al.pc=:pc))""", [pc:pc])
        return (Integer) (x ? x[0] : 0)
    }

    Boolean payForSkillSet(PlayerCharacter pc, AcademySkillSet ass){
        if(pc.gold >= ass.fetchGoldPrice() &&
                pc.user.coins >= ass.fetchCoinPrice() &&
                (pc.xp - pc.spentExperience) >= ass.fetchXpPrice()){
            pc.gold = pc.gold - ass.fetchGoldPrice()
            pc.user.coins = pc.user.coins - ass.fetchCoinPrice()
            pc.spentExperience = pc.spentExperience + ass.fetchXpPrice()
            return true
        }
        else{            
            return false
        }
    }

    void refundLearningCost(LearningQueueElement queueElement){
        def pc = queueElement.pc
        Integer refundPercentage = globalConfigService.fetchValueAsInt('academy.refund.percentage')
        AcademySkillSet ass = queueElement.academySkillSet
        pc.gold = pc.gold + (int) ass.fetchGoldPrice() * refundPercentage / 100
        pc.spentExperience = pc.spentExperience - (int) ass.fetchXpPrice() * refundPercentage / 100
        pc.user.coins = pc.user.coins + (int) ass.fetchCoinPrice() * refundPercentage / 100        
    }

    LearningQueueElement addSkillSetToLearningQueue(PlayerCharacter pc, AcademySkillSet ass){
        LearningQueueElement queueElement = new LearningQueueElement(pc:pc, academySkillSet:ass )
        Long learningTime = ass.skillSet.learningTime
        if(pc.learningQueueElements.isEmpty()){
            queueElement.finished = new Date(new Date().getTime() + learningTime)
        }
        else{
            LearningQueueElement newest = LearningQueueElement.find("from LearningQueueElement as l where l.pc=:pc order by finished desc", [pc:pc])
            queueElement.finished = new Date(newest.finished.getTime() + learningTime)
        }
        pc.addToLearningQueueElements(queueElement)
        ass.addToLearningQueueElements(queueElement)
        queueElement.save()
        return queueElement
    }

    /**
     * Remove an element from the player character's learning queue and move the other
     * elements up by recalculating their finishing time.
     * @param pc
     * @param ass
     */
    void removeSkillSetFromLearningQueue(PlayerCharacter pc, AcademySkillSet ass){
        LearningQueueElement queueElement =
            LearningQueueElement.find("from LearningQueueElement as l where l.pc=:pc and l.academySkillSet=:ass", [pc:pc, ass:ass])
        List<LearningQueueElement> newerElements =
            LearningQueueElement.findAll("from LearningQueueElement where l.pc=:pc and l.finished > :qe.finished order by finished", [pc:pc, qe:queueElement])
        Long currentTime = new Date().time
        newerElements.each{element ->
            element.finished = new Date(currentTime + element.academySkillSet.tellRequiredTime())
            currentTime = element.finished.time
        }
    }

}
