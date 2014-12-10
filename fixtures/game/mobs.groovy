import de.dewarim.goblin.CreatureAttribute
import de.dewarim.goblin.Dice
import de.dewarim.goblin.Image
import de.dewarim.goblin.combat.CombatAttributeType
import de.dewarim.goblin.mob.MobImage
import de.dewarim.goblin.mob.MobTemplate

Dice dice(String name) {
    def d = Dice.findByName(name)
    if (d == null) {
        throw new RuntimeException("Failed to load dice $name!")
    }
    return d
}

def d1 = dice('1d1')
def d20 = dice('d20')
def d6 = dice('d6')
def iniDice = dice('initiative')
def d2x6 = dice('2d6')

CombatAttributeType fireCat = CombatAttributeType.findByName('attribute.fire')

fixture {

    orc(MobTemplate, name: 'mob.orc',
            strike: d20, parry: d20, initiative: iniDice, damage: d6,
            maxHp: 10, xpValue: 4
    )
    imageOrc(MobImage, mobTemplate: orc, image: Image.findByName('orc'))

    troll(MobTemplate, name: 'mob.troll',
            strike: d20, parry: d20, initiative: iniDice, damage: d2x6,
            maxHp: 20, xpValue: 8
    )

    redDragon(MobTemplate, name: 'mob.red.dragon',
            strike: d20, parry: d20, initiative: iniDice, damage: dice('6d6+5'),
            maxHp: 100, xpValue: 2000
    )

    hornet(MobTemplate, name: 'mob.angry.hornet', strike: dice('6d4+4'),
            parry: dice('6d4+4'), initiative: iniDice, damage: d1,
            maxHp: 1, xpValue: 1
    )

    rabbit(MobTemplate, name: 'mob.rabid.rabbit', strike: d20, parry: d6,
            initiative: iniDice, damage: d2x6,
            maxHp: 3, xpValue: 4)

    kobold(MobTemplate, name: 'mob.kobold', strike: d20, parry: d20,
            initiative: iniDice, damage: d6, maxHp: 7, xpValue: 3)

    halfling(MobTemplate, name: 'mob.halfling', strike: d1, parry: d1,
            initiative: d1, damage: d1, maxHp: 1, xpValue: 1)

    puppet(MobTemplate, name: 'mob.training_puppet', strike: d1, parry: d1,
            initiative: d1, damage: d1, maxHp: 500, xpValue: 5)

    strawMan(MobTemplate, name: 'mob.straw_man', strike: d20, parry: d1,
            initiative: d1, damage: d6, maxHp: 100, xpValue: 30)
    // The straw man is vulnerable to fire:
    // Note: this does not work yet.
    fireAttr(CreatureAttribute, combatAttributeType: fireCat, damageModifier: 2.0, creature: strawMan)

}