import de.dewarim.goblin.quest.QuestStep
import de.dewarim.goblin.quest.QuestTemplate
import de.dewarim.goblin.quest.StepChild

include('game/questGivers')
include('game/encounters')

fixture {

    orcQuest(QuestTemplate, name: 'quest.generic.name',
            level: 100, description: 'quest.orc.desc', giver: mayor)
    orcQs(QuestStep, title:'quest.generic.fight',
            description: 'quest.generic.description',
            endOfQuest: true, firstStep: true,
            questTemplate: orcQuest, encounter: orcEncounter,
            name:'step.fight.orc'    
    )
    
    dragonQuest(QuestTemplate, name:'quest.test.dragon',
            level: 10000,
            description: 'quest.red.dragon.desc',
            giver:king
    )
    dragonQs(QuestStep, title: 'quest.generic.fight',
            description: 'quest.generic.description',
            endOfQuest: true, firstStep: true,
            name:'step.fight.dragon',
            questTemplate: dragonQuest, encounter: dragonEncounter
    )
    
    halflingQuest(QuestTemplate, name: 'quest.test.halfling',
            level: 50, description: 'quest.halfling.desc', giver: urchin
    )
    halflingQs(QuestStep, title: 'quest.generic.fight',
            description: 'quest.generic.description',
            endOfQuest: true, firstStep: true,
            name:'step.fight.halfling',
            questTemplate: halflingQuest, encounter: halflingEncounter
    )
    
    puppetQuest(QuestTemplate, name: 'quest.test.puppet',
            level: 500, description: 'quest.puppet.desc', giver: smith
    )
    puppetQs(QuestStep, title: 'quest.generic.fight',
            description: 'quest.generic.description',
            firstStep: true, endOfQuest: true,
            name:'step.fight.puppet',
            questTemplate: puppetQuest, encounter: puppetEncounter
    )
    
    strawmanQuest(QuestTemplate, name: 'quest.test.straw_man',
            level: 150, description: 'quest.test.straw_man.description', giver: philosopher
    )
    strawmanQs(QuestStep,title: 'quest.generic.fight',
            description: 'quest.generic.description',
            endOfQuest: false, encounter: strawmanEncounter,
            firstStep: true, questTemplate: strawmanQuest,
            name:'step.fight.straw_man'
    )
    strawmanRewardQs(QuestStep, title: 'quest.straw_man.reward.title',
            description: 'quest.straw_man.reward',
            encounter: philReward, questTemplate: strawmanQuest,
            endOfQuest: true,
            name:'step.straw_man.reward',    
    )
    strawSc(StepChild, parent: strawmanQs, child: strawmanRewardQs)
    
    mushroomQuest(QuestTemplate, name: 'quest.test.shrooms',
            level: 250, description: 'quest.test.shrooms.description', giver: crone)
    croneQs1(QuestStep, title: 'quest.shroom.title.found',
            description: 'quest.found.mushrooms',
            encounter: findShroomEncounter, questTemplate: mushroomQuest, firstStep: true,
            name:'step.shroom.1')
    // 2nd step in quest with step stepChild connection Qs1 -> Qs2
    croneQs2(QuestStep, title: 'quest.shroom.title.pick.red',
            description: 'quest.shroom.pick.red.shroom',
            encounter: redShroomEncounter, questTemplate: mushroomQuest,
            name:'step.shroom.2')
    croneSc1(StepChild, parent:croneQs1, child:croneQs2)
    
    // 2nd step in quest with step stepChild connection Qs1 -> Qs3
    croneQs3(QuestStep,title: 'quest.shroom.title.pick.black',
            description: 'quest.shroom.pick.black.shroom',
            encounter: blackShroomEncounter, questTemplate: mushroomQuest,
            name:'step.shroom.3')
    croneSc2(StepChild, parent: croneQs1, child:croneQs3)
    
    croneQs4(QuestStep, title: 'quest.shroom.title.deliver',
            description: 'quest.shroom.deliver',
            endOfQuest: false,
            encounter: deliveryEncounter,
            automaticTransition: true,
            questTemplate: mushroomQuest,
            name:'step.shroom.deliver')
    croneSc2_4(StepChild, parent:croneQs2, child:croneQs4)
    croneSc3_4(StepChild, parent:croneQs3, child:croneQs4)
    croneQs5(QuestStep, title: 'quest.step.shroom.success.title',
            description: 'quest.step.shroom.success',
            endOfQuest: true,
            encounter: shroomRewardEncounter, questTemplate: mushroomQuest,
            name:'step.shroom.success'
    )
    croneQs6(QuestStep, title: 'quest.step.shroom.fail.title',
            description: 'quest.step.shroom.fail',
            encounter: shroomFailEncounter, questTemplate: mushroomQuest,
            endOfQuest: true,
            name:'step.shroom.fail'
    )
    croneSc4success(StepChild, parent:croneQs4, child:croneQs5)
    croneSc4fail(StepChild, parent:croneQs4, child:croneQs6)
    
    elfQuest(QuestTemplate, name: 'quest.test.elf',
            level: 125,
            description: 'quest.test.elf.description',
            giver:mayor
    )
    elfQs1(QuestStep, title: 'quest.elf.distress.title',
            description: 'quest.elf.distress',
            encounter: maidEncounter, questTemplate: elfQuest,
            firstStep: true,
            name:'step.elf.1',)    
    elfQs2(QuestStep, title: 'quest.elf.nice.title',
            description: 'quest.elf.nice',
            encounter: niceElfEncounter, questTemplate: elfQuest,
            endOfQuest: true,
            intro: 'quest.elf.help',
            name:'step.elf.nice')
    elfQs1Sc1(StepChild, parent: elfQs1, child:elfQs2)
    
    elfQs3(QuestStep, title:'quest.elf.mean.title',
            description: 'quest.elf.mean',
            encounter: meanElfEncounter, questTemplate: elfQuest,
            endOfQuest: true,
            intro: 'quest.elf.go_home',
            name:'step.elf.mean'
    )
    elfQs1Sc2(StepChild, parent:elfQs1, child:elfQs3)
    
}