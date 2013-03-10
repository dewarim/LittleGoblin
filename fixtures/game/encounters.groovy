import de.dewarim.goblin.GoblinScript
import de.dewarim.goblin.mob.EncounterMob
import de.dewarim.goblin.mob.MobTemplate
import de.dewarim.goblin.quest.Encounter

def findMob(String name) {
    return MobTemplate.findByName(name)
}

fixture {

    orcEncounter(Encounter, name: 'encounter.orc')
    orcEnMob(EncounterMob, encounter: orcEncounter, mob: findMob('mob.orc'))

    dragonEncounter(Encounter, name: 'encounter.red.dragon')
    dragonEnMob(EncounterMob, encounter: dragonEncounter, mob: findMob('mob.red.dragon'))

    halflingEncounter(Encounter, name: 'encounter.halfling')
    halflingEnMob(EncounterMob, encounter: halflingEncounter, mob: findMob('mob.halfling'))

    puppetEncounter(Encounter, name: 'encounter.puppet')
    puppetEnMob(EncounterMob, encounter: puppetEncounter, mob: findMob('mob.training_puppet'))

    strawmanEncounter(Encounter, name: 'encounter.straw_man')
    strawmanEnMob(EncounterMob, encounter: strawmanEncounter, mob: findMob('mob.straw_man'))

    philReward(Encounter, name: 'encounter.philosopher_stone', includesCombat: false,
            script: GoblinScript.findByName('PickupItem'),
            config: '<params><items><item>item.philosopher_stone</item></items></params>'
    )
    
    findShroomEncounter(Encounter, name:'encounter.find.mushroom', includesCombat: false)
    redShroomEncounter(Encounter, name:'encounter.red.mushroom', includesCombat: false,
            script: GoblinScript.findByName('script.pickupItem'),
            config: '<params><items><item>item.mushroom.red</item></items></params>'
    )
    blackShroomEncounter(Encounter, name:'encounter.black.mushroom', includesCombat: false,
            script: GoblinScript.findByName('script.pickupItem'),
            config: '<params><items><item>item.mushroom.black</item></items></params>'
    )
    deliveryEncounter(Encounter, name:'encounter.deliver.mushroom',includesCombat: false,
            script: GoblinScript.findByName('script.deliverItem'),
            config: """<params><items><item>item.mushroom.red</item></items>
                    <steps>
                    <success>quest.step.shroom.success.title</success>
                    <fail>quest.step.shroom.fail.title</fail>
                    </steps>
                    </params>""", 
    
    )
    shroomRewardEncounter(Encounter, includesCombat: false,
            script: GoblinScript.findByName('script.getReward'),
            config: '<params><gold>100</gold><xp>10</xp></params>',
            name: 'encounter.get.gold.100')
    shroomFailEncounter(Encounter, includesCombat: false,
            script: GoblinScript.findByName('script.getReward'),
            config: '<params><xp>10</xp></params>',
            name: 'encounter.get.xp.10'
    )
    
    maidEncounter(Encounter, includesCombat: false, name: 'encounter.elf.distress')
    niceElfEncounter(Encounter, includesCombat: false,
            script: GoblinScript.findByName('script.getReward'),
            config: '''<params><gold>30</gold><reputation><faction><name>faction.elves</name><level>10</level></faction>
            <faction><name>faction.dwarves</name><level>-1</level></faction></reputation>
</params>''',
            name: 'encounter.elf.reward'
    )
    meanElfEncounter(Encounter, includesCombat: false,
            script: GoblinScript.findByName('script.getReward'),
            config: '''<params><gold>75</gold><reputation><faction><name>faction.dwarves</name><level>9</level></faction>
                            <faction><name>faction.elves</name><level>-20</level></faction></reputation>
                            </params>
                            ''',
            name: 'encounter.dwarf.reward'
    )
}