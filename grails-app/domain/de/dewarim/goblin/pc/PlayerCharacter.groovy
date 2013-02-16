package de.dewarim.goblin.pc

import de.dewarim.goblin.Creature
import de.dewarim.goblin.Dice
import de.dewarim.goblin.EquipmentSlot
import de.dewarim.goblin.EquipmentSlotType
import de.dewarim.goblin.UserAccount
import de.dewarim.goblin.combat.Combat
import de.dewarim.goblin.combat.Melee
import de.dewarim.goblin.guild.GuildMember
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.pc.crafting.Component
import de.dewarim.goblin.pc.crafting.PlayerProduct
import de.dewarim.goblin.pc.crafting.ProductionJob
import de.dewarim.goblin.pc.skill.LearningQueueElement
import de.dewarim.goblin.quest.Quest
import de.dewarim.goblin.reputation.Reputation
import de.dewarim.goblin.social.MailBox
import de.dewarim.goblin.social.MailBoxType
import de.dewarim.goblin.town.AcademyLevel
import de.dewarim.goblin.town.Town

class PlayerCharacter extends Creature {

    static hasMany = [
            reputations: Reputation,
            pcMessages: PlayerMessage,
            mailBoxes: MailBox,
            productionJobs: ProductionJob,
            guildMemberships: GuildMember,
            academyLevels: AcademyLevel,
            learningQueueElements: LearningQueueElement,
            playerProducts: PlayerProduct
    ]

    static mapping = {
    }

    static belongsTo = [user: UserAccount,
            town: Town,
            currentCombat: Combat,
            currentQuest: Quest,
            goblinOrder: GoblinOrder,
    ]

    static constraints = {
        currentMelee(nullable: true)
        town nullable: true
        currentCombat nullable: true
        currentQuest nullable: true
        goblinOrder nullable: true
    }

    Long xp = 0
    Long spentExperience = 0
    Boolean alive = true
    Integer deaths = 0
    Integer victories = 0
    Long questLevel = 0
    Long level = 1

    Melee currentMelee

    void resurrect() {
        if (xp > 0) {
            xp--
        }
        hp = maxHp
        alive = true
    }

    void initializePlayerCharacter() {
        Dice d20 = Dice.findByName('d20')
        gold = 20
        strike = d20
        parry = d20
        initiative = d20
        Dice d6 = Dice.findByName('d6')
        damage = d6
        town = Town.findByName('town.default.name')
        initializeEquipmentSlots()
        initializeMailboxes()
    }

    void initializeMailboxes() {
        def boxes = ['mail.inbox', 'mail.outbox', 'mail.archive']
        boxes.each {name ->
            def type = MailBoxType.findByName(name)
            def box = new MailBox(this, type)
            box.save()
        }
    }

    /**
     * Check if the player character has enough of a specific component to
     * produce an item.
     * Note: this is a very rudimentary way of checking. It will need
     * tweaking if we add crafting recipes for items with special qualities.
     * @param component
     * @return true if the player has the needed amount of this component
     */
    Boolean checkComponent(Component component) {
        ItemType type = component.itemType
        Integer amount = component.amount
        Integer total = calculateSumOfItems(type)
        return total >= amount
    }

    /**
     * Calculate the sum of all items of a specific type.
     * @param type the item type
     * @return the sum of all items of this type the player character owns
     */
    Integer calculateSumOfItems(ItemType type) {
        def items = Item.findAll("from Item i where i.owner=:owner and i.type=:type", [owner: this, type: type])
        Integer total = 0
        items.each {total = total + it.amount}
        return total
    }

    /**
     * A player character should have individual EquipmentSlots, as
     * they may very well be removed... (eg, loose your right hand)
     */
    void initializeEquipmentSlots() {
        // TODO: change from hard coded to soft coded and make them applicable for generic creatures, too.
        def names = [head: 'slot.head', neck: 'slot.neck', body: 'slot.body',
                'left.hand': 'slot.hand', 'left.ringfinger': 'slot.finger',
                'right.hand': 'slot.hand', 'right.ringfinger': 'slot.finger',
                'legs': 'slot.legs', feet: 'slot.feet']
        Integer rank = 1
        names.each {key, value ->
            EquipmentSlotType est = EquipmentSlotType.findByName(value)
            EquipmentSlot slot = new EquipmentSlot(name: key, type: est, rank: rank++, creature: this)
            est.addToEquipmentSlots slot
//            slot.save()
            addToSlots(slot)
        }
    }

    MailBox fetchInbox() {
        return mailBoxes.find {it.boxType.name.equals('mail.inbox')}
    }

    MailBox fetchOutBox() {
        return mailBoxes.find {it.boxType.name.equals('mail.outbox')}
    }

    MailBox fetchArchiveBox() {
        return mailBoxes.find {it.boxType.name.equals('mail.archive')}
    }


    boolean equals(o) {
        if (is(o)) return true

        if (!(o instanceof PlayerCharacter)) return false

        PlayerCharacter that = o

        if (alive != that.alive) return false
        if (deaths != that.deaths) return false
        if (description != that.description) return false
        if (level != that.level) return false
        if (name != that.name) return false
        if (questLevel != that.questLevel) return false
        if (spentExperience != that.spentExperience) return false
        if (victories != that.victories) return false
        if (xp != that.xp) return false

        return true
    }

    int hashCode() {
        return (name != null ? name.hashCode() : 0)
    }

    List<Quest> fetchOpenQuests() {
        return Quest.findAll("from Quest q where q.playerCharacter=:pc and q.finished=false", [pc: this])
    }
}
