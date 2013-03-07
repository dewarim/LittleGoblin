import de.dewarim.goblin.CreatureAttribute
import de.dewarim.goblin.Dice
import de.dewarim.goblin.Image
import de.dewarim.goblin.combat.CombatAttributeType
import de.dewarim.goblin.mob.MobImage
import de.dewarim.goblin.mob.MobTemplate

Dice dice(String name) {
    return Dice.findByName(name)
}

def d1 = dice('d0x1p1')
def d20 = dice('d20')
def d6 = dice('d6')
def iniDice = dice('initiative')
def d2x6 = dice('d2x6')

CombatAttributeType fireCat = CombatAttributeType.findByName('attribute.fire')

fixture {

    orc(MobTemplate, name: 'mob.orc',
            strike: d20, parry: d20, initiative: iniDice, damage: d6,
            hp: 10, xpValue: 4
    )
    imageOrc(MobImage, mobTemplate: orc, image: Image.findByName('orc'))

    troll(MobTemplate, name: 'mob.troll',
            strike: d20, parry: d20, initiative: iniDice, damage: d2x6,
            hp: 20, xpValue: 8
    )

    redDragon(MobTemplate, name: 'mob.red.dragon',
            strike: d20, parry: d20, initiative: iniDice, damage: dice('d5x6p5'),
            hp: 100, xpValue: 2000
    )

    hornet(MobTemplate, name: 'mob.angry.hornet', strike: dice('d6x4p4'),
            parry: dice('d6x4p4'), initiative: iniDice, damage: d1,
            hp: 1, xpValue: 1
    )

    rabbit(MobTemplate, name: 'mob.rabid.rabbit', strike: d20, parry: d6,
            initiative: iniDice, damage: d2x6,
            hp: 3, xpValue: 4)

    kobold(MobTemplate, name: 'mob.kobold', strike: d20, parry: d20,
            initiative: iniDice, damage: d6, hp: 7, xpValue: 3)

    halfling(MobTemplate, name: 'mob.halfling', strike: d1, parry: d1,
            initiative: d1, damage: d1, hp: 1, xpValue: 1)

    puppet(MobTemplate, name: 'mob.training_puppet', strike: d1, parry: d1,
            initiative: d1, damage: d1, hp: 500, xpValue: 5)
    
    strawMan(MobTemplate, name:'mob.straw_man', strike: d20, parry: d1,
            initiative: d1, damage: d6, hp: 100, xpValue: 30)
    // The straw man is vulnerable to fire:
    // Note: this does not work yet.
    fireAttr(CreatureAttribute, combatAttributeType: fireCat, damageModifier: 2.0, creature:strawMan)
    
}