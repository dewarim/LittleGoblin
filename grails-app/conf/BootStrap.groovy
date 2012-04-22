import de.dewarim.goblin.UserAccount
import de.dewarim.goblin.pc.PlayerCharacter
import de.dewarim.goblin.mob.MobTemplate
import de.dewarim.goblin.mob.MobImage
import de.dewarim.goblin.Dice
import de.dewarim.goblin.item.ItemType
import de.dewarim.goblin.Feature
import de.dewarim.goblin.Role
import de.dewarim.goblin.EquipmentSlotType
import de.dewarim.goblin.RequiredSlot
import de.dewarim.goblin.town.Town
import de.dewarim.goblin.quest.Encounter
import de.dewarim.goblin.quest.QuestStep
import de.dewarim.goblin.quest.QuestTemplate
import de.dewarim.goblin.Artist
import de.dewarim.goblin.shop.ShopOwner
import de.dewarim.goblin.shop.Shop
import de.dewarim.goblin.quest.script.PickupItem
import de.dewarim.goblin.quest.script.DeliverItem
import de.dewarim.goblin.quest.script.GetReward
import de.dewarim.goblin.combat.CombatAttributeType
import de.dewarim.goblin.combat.WeaponAttribute
import de.dewarim.goblin.CreatureAttribute
import de.dewarim.goblin.reputation.Faction
import de.dewarim.goblin.reputation.ReputationMessageMap
import de.dewarim.goblin.reputation.ReputationMessage
import de.dewarim.goblin.pc.GoblinOrder
import de.dewarim.goblin.GlobalConfigEntry
import de.dewarim.goblin.social.MailBoxType
import de.dewarim.goblin.social.MailBox
import de.dewarim.goblin.social.ChatterBox
import de.dewarim.goblin.town.Academy
import de.dewarim.goblin.guild.Guild
import de.dewarim.goblin.town.GuildAcademy
import de.dewarim.goblin.pc.skill.SkillSet
import de.dewarim.goblin.town.AcademySkillSet
//import org.apache.camel.CamelContext
//import javax.jms.ConnectionFactory
//import org.apache.camel.component.jms.JmsComponent
//import org.apache.camel.ProducerTemplate
//import org.apache.activemq.ActiveMQConnectionFactory
//import org.apache.activemq.camel.component.ActiveMQComponent


import grails.util.Environment
import de.dewarim.goblin.pc.skill.CombatSkill

import de.dewarim.goblin.pc.skill.ProductionSkill
import de.dewarim.goblin.pc.crafting.Product
import de.dewarim.goblin.pc.crafting.ProductCategory
import de.dewarim.goblin.pc.crafting.SkillRequirement
import de.dewarim.goblin.pc.crafting.Component
import de.dewarim.goblin.item.Item
import de.dewarim.goblin.ComponentType
import de.dewarim.goblin.Help
import de.dewarim.goblin.UserRole
import de.dewarim.goblin.item.ItemTypeFeature
import de.dewarim.goblin.Category
import de.dewarim.goblin.item.ItemCategory
import de.dewarim.goblin.shop.ShopCategory
import de.dewarim.goblin.License
import de.dewarim.goblin.Image
import de.dewarim.goblin.quest.QuestGiver
import de.dewarim.goblin.GoblinScript
import de.dewarim.goblin.mob.EncounterMob
import de.dewarim.goblin.quest.StepChild

class BootStrap {
    def springSecurityService
    def guildMemberService
    def academyService
    def grailsApplication

//    def camelContext
//    def brokerService

    def connectionFactoryLocal

    def init = { servletContext ->
        // currently disabled
//        brokerService.start()
//        camelContext.addComponent("activemq", ActiveMQComponent.activeMQComponent("vm://localhost?broker.persistent=false"));
//        ((CamelContext) camelContext).addRoutes (new GoblinRouteBuilder())
//        camelContext.start()
//        ProducerTemplate template =  camelContext.createProducerTemplate();
//        template.sendBody('activemq:learning', 'boo')

        if (!UserAccount.list().isEmpty()) {
            return
        }

        log.debug("LITTLE_GOBLIN_HOME: ${System.env.LITTLE_GOBLIN_HOME}")
        log.debug("Facebook.config: ${grailsApplication.config.facebook}")

        log.debug("create security roles")
        // create Security roles:
        Role adminRole = new Role(description: 'Administrator', name: 'ROLE_ADMIN')
        adminRole.save(flush: true, failOnError: true)
        Role userRole = new Role(description: "User", name: 'ROLE_USER')
        userRole.save(flush: true, failOnError: true);

        log.debug("create admin user")
        UserAccount admin = new UserAccount(username: 'admin', userRealName: 'Admin', enabled: true);
        admin.passwd = 'admin' // for local testing
        admin.save(flush: true, failOnError: true)
        UserRole adminAdminRole = new UserRole(user: admin, role: adminRole)
        adminAdminRole.save()
        UserRole adminUserRole = new UserRole(user: admin, role: userRole)
        adminUserRole.save()

        log.debug("create test UserAccount 'anon'")
        UserAccount user = new UserAccount(username: 'anon', userRealName: 'Anonymous', enabled: true)
        user.passwd = 'anon'
        user.coins = 100
        user.save(flush: true, failOnError: true)
        UserRole userUserRole = new UserRole(user: user, role: userRole)
        userUserRole.save()

        Dice d20 = new Dice(name: 'd20', sides: 20)
        d20.save()
        def d6 = new Dice(name: 'd6', sides: 6)
        d6.save()

        initScripts()
        initLicenses()
        initEquipmentSlotTypes()
        Town town = initTown()

        initMailBoxTypes()
        PlayerCharacter gob = initPlayer(user)

        initCombatModifiers()
        if (MobTemplate.list().size() == 0) {
            initMobTemplates()
        }

        initCategories()
        initItems(gob)
        initWeapons()
        initArmor()
        initQuests()
        initShop()
        initFactions()
        initOrders(gob)
        initGlobalSettings()
        initGuilds()
        initAcademy(town)
        initHelp()
    }

    def destroy = {

    }

    void initScripts(){
        log.debug("create scripts")
        def scripts = ['script.pickupItem': PickupItem.class, 'script.deliverItem': DeliverItem.class,
                'script.getReward': GetReward.class
        ]
        scripts.each{name,clazz ->
            GoblinScript script = new GoblinScript(name:name, clazz:clazz)
            script.save()
        }
    }

    void initLicenses() {
        log.debug("create licenses")
        License customLicense = new License(name: 'license.custom.image',
                description: 'license.custom.image.description'
        )
        customLicense.save()
    }

    void initCategories() {
        def categories = ['weapon', 'armor', 'metal', 'potion', 'mushroom', 'magic']
        categories.each {
            Category cat = new Category(name: it)
            cat.save()
        }
    }

    void initGuilds() {
        log.debug("initialize guilds")
        Guild storyteller = new Guild(name: 'guild.storyteller',
                description: 'guild.storyteller.description')
        storyteller.save()
    }

    void initAcademy(Town town) {
        log.debug("initialize academies")
        def academies = ['academy.default.combat', 'academy.default.crafting', 'academy.default.storyteller']
        academies.each { name ->
            Academy academy = new Academy(name: name, description: "${name}.description", town: town)
            town.addToAcademies(academy)
            academy.save()
        }
        Academy storyteller = Academy.findByName('academy.default.storyteller')
        Guild guild = Guild.findByName('guild.storyteller')
        GuildAcademy ga = new GuildAcademy(guild: guild, academy: storyteller)
        storyteller.addToGuildAcademies(ga)
        guild.addToGuildAcademies(ga)
        ga.save()

        def fairySkills = ['lumberjack', 'sweet', 'gold']
        def counter = 1
        fairySkills.each {name ->
            SkillSet s = new SkillSet(name: 'skillSet.fairy.' + name, description: 'skillSet.fairy.default',
                    learningTime: 60000)
            s.save()
            AcademySkillSet ass = new AcademySkillSet(academy: storyteller, skillSet: s, requiredLevel: counter++)
            storyteller.addToAcademySkillSets(ass)
            ass.save()
        }

        Dice d1 = new Dice(amount: 0, sides: 1, bonus: 1)
        d1.save()
        CombatSkill skill = new CombatSkill(
                name: "skill.combat.basic",
                strike: d1,
                parry: d1,
                damage: d1,
                initiative: d1
        )
        skill.save()

        Academy combat = Academy.findByName('academy.default.combat')
        (1..3).each { count ->
            SkillSet s = new SkillSet(name: 'skillSet.combat.fight' + count, description: 'skillSet.combat.default',
                    learningTime: 1000)
            s.addToSkills(skill)
            s.save()
            AcademySkillSet ass = new AcademySkillSet(academy: combat, skillSet: s, requiredLevel: count)
            combat.addToAcademySkillSets(ass)
            ass.save()
        }

        def craftingSkills = ['iron', 'sword', 'shield']
        Academy crafting = Academy.findByName('academy.default.crafting')
        (1..3).each { count ->
            ProductionSkill ps = new ProductionSkill(name: "skill.crafting.${craftingSkills[count - 1]}")
            SkillSet s = new SkillSet(name: 'skillSet.production.craft' + count, description: 'skillSet.production.default',
                    learningTime: 1000)
            s.addToSkills(ps)
            s.save()
            AcademySkillSet ass = new AcademySkillSet(academy: combat, skillSet: s, requiredLevel: count)
            crafting.addToAcademySkillSets(ass)
            ass.save()
        }

        // init product categories:
        def basicCategory = new ProductCategory(name: 'product.category.basic')
        basicCategory.save()
        def weaponCategory = new ProductCategory(name: 'product.category.weapon')
        weaponCategory.save()
        def armorCategory = new ProductCategory(name: 'product.category.armor')
        armorCategory.save()

        // init products for crafting:
        def ironBar = new Product(name: 'product.iron.bar', timeNeeded: 1000, category: basicCategory)
        def ore = ItemType.findByName('item.iron.ore')
        def oreComponent = new Component(itemType: ore, amount: 3, product: ironBar, type: ComponentType.INPUT)
        ironBar.addToComponents(oreComponent)
        def iron = ItemType.findByName('item.iron.bar')
        def ironComponent = new Component(itemType: iron, amount: 1, product: ironBar, type: ComponentType.OUTPUT)
        ironBar.addToComponents(ironComponent)
        ironBar.save()

        ProductionSkill ironSkill = ProductionSkill.findByName('skill.crafting.iron')
        SkillRequirement sr = new SkillRequirement(product: ironBar, skill: ironSkill)
        ironSkill.addToSkillRequirements(sr)
        ironBar.addToRequiredSkills(sr)
        sr.save()


        def sword = new Product(name: 'product.weapon.sword', timeNeeded: 1000, category: weaponCategory)
        sword.save()
        ProductionSkill swordSkill = ProductionSkill.findByName('skill.crafting.sword')
        sr = new SkillRequirement(product: sword, skill: swordSkill)
        sword.addToRequiredSkills(sr)
        def ironBarInput = new Component(itemType: iron, amount: 3, product: sword, type: ComponentType.INPUT)
        def swordItem = ItemType.findByName('weapon.short_sword')
        def swordOutput = new Component(itemType: swordItem, amount: 1, product: sword, type: ComponentType.OUTPUT)
        sword.addToComponents(ironBarInput)
        sword.addToComponents(swordOutput)
        swordSkill.addToSkillRequirements(sr)
        sr.save()

        def armor = new Product(name: 'product.armor.shield', timeNeeded: 1000, category: armorCategory)
        armor.save()
        ProductionSkill armorSkill = ProductionSkill.findByName('skill.crafting.shield')
        sr = new SkillRequirement(product: armor, skill: armorSkill)
        def ironBarInput2 = new Component(itemType: iron, amount: 3, product: armor, type: ComponentType.INPUT)
        def shield = ItemType.findByName('item.armor.shield')
        def shieldComp = new Component(itemType: shield, amount: 1, product: armor, type: ComponentType.OUTPUT)
        armor.addToComponents(shieldComp)
        armor.addToComponents(ironBarInput2)
        armor.addToRequiredSkills(sr)
        armorSkill.addToSkillRequirements(sr)
        sr.save()

    }

    PlayerCharacter initPlayer(user) {
        log.debug("initialize player character")
        PlayerCharacter gob = new PlayerCharacter(name: 'Gobli', user: user, gold: 100, xp: 10)
        gob.save()
        gob.initializePlayerCharacter()

        def boxes = MailBoxType.list()
        boxes.each {boxType ->
            MailBox mailBox = new MailBox(owner: gob, boxType: boxType)
            mailBox.save()
        }

        return gob
    }

    void initCombatModifiers() {
        log.debug("initialize combat modifiers")
        def attributes = ['fire', 'acid', 'water', 'cold', 'electricity', 'death',
                'light', 'chaos', 'sound', 'poison', 'shards', 'normal']
        attributes.each {
            CombatAttributeType cat = new CombatAttributeType(name: "attribute.$it")
            cat.save()
        }
    }

    void initShop() {
        log.debug("initialize shops")
        ShopOwner owner = new ShopOwner(name: "shop.owner.invisible",
                description: "shop.owner.invisible.description",
                priceModifierDice: Dice.findByName('d6'))
        owner.save()
        Shop shop = new Shop(name: 'shop.general.store',
                description: 'shop.general.store.description',
                owner: owner,
        )

        Town town = Town.findByName('town.default.name')
        town.addToShops(shop)
        shop.save()

        def categories = Category.list()
        categories.each {category ->
            ShopCategory sc = new ShopCategory(shop, category)
            sc.save()
        }
    }

    void initEquipmentSlotTypes() {
        log.debug("initialize EquipmentSlotTypes")
        String[] names = ['head', 'neck', 'body', 'hand',
                'finger', 'legs', 'feet']
        names.each {name ->
            new EquipmentSlotType(name: "slot.${name}").save(failOnError: true)
        }

        //		new EquipmentSlotType(name:'slot.cod').save() // save the russian codpiece for later.
    }

    void initArmor() {
        log.debug("initialize armor")
        EquipmentSlotType body = EquipmentSlotType.findByName('slot.body')
        ItemType cloth = new ItemType(name: 'armor.cloth', baseValue: 4)
        RequiredSlot rs = new RequiredSlot(body, cloth);
        cloth.save()

        EquipmentSlotType head = EquipmentSlotType.findByName('slot.head')

        ItemType cap = new ItemType(name: 'armor.leather_cap', baseValue: 4)
        rs = new RequiredSlot(head, cap);
        cap.save()

        EquipmentSlotType hand = EquipmentSlotType.findByName('slot.hand')
        ItemType shield = new ItemType(name: 'item.armor.shield', availability: 500, baseValue: 20)
        rs = new RequiredSlot(hand, shield)
        shield.save()

        def armory = [cloth, cap, shield]
        armory.each {armor ->
            ItemCategory armorCat = new ItemCategory(armor, 'armor')
        }
    }

    void initItems(PlayerCharacter littleGoblin) {
        log.debug("initialize items")
        Feature healing = new Feature(name: 'Healing', internalName: 'heal_1w6',
                script: de.dewarim.goblin.item.script.HealSelf_1W6)
        healing.save()

        ItemType potion = new ItemType(name: 'item.healing_potion',
                usable: true, uses: 1, availability: 999, baseValue: 5)
        potion.save()
        ItemCategory ic = new ItemCategory(potion, 'potion')
        ic.save()
        ItemCategory mp = new ItemCategory(potion, 'magic')
        mp.save()
        ItemTypeFeature itemFeature = new ItemTypeFeature(potion, healing)
        itemFeature.save()

        ItemType redShroom = new ItemType(name: 'item.mushroom.red', availability: 0, baseValue: 2)
        redShroom.save()
        ItemCategory mushroomCat = new ItemCategory(redShroom, 'mushroom')

        ItemType blackShroom = new ItemType(name: 'item.mushroom.black', availability: 0, baseValue: 2)
        blackShroom.save()
        ItemCategory bsCat = new ItemCategory(blackShroom, 'mushroom')
        bsCat.save()

        // init items for products:
        def ore = new ItemType(name: 'item.iron.ore', availability: 900, baseValue: 10, stackable: true)
        ore.save();
        ItemCategory oreCat = new ItemCategory(ore, 'metal')
        oreCat.save()
        def iron = new ItemType(name: 'item.iron.bar', availability: 900, baseValue: 40, stackable: true)
        iron.save();
        ItemCategory ironCat = new ItemCategory(iron, 'metal')
        ironCat.save()

        def someOre = new Item(type: ore, amount: 10, owner: littleGoblin)
        def someBars = new Item(type: iron, amount: 10, owner: littleGoblin)
//        littleGoblin.addToItems(someOre)
//        littleGoblin.addToItems(someBars)
        someOre.save()
        someBars.save()

    }

    void initWeapons() {
        log.debug("initialize weapons")
        EquipmentSlotType hand = EquipmentSlotType.findByName('slot.hand')

        Dice die = new Dice(sides: 6, amount: 2, bonus: 2)
        die.save()
        ItemType fireWhip = new ItemType(name: 'weapon.fire_whip', availability: 1000,
                baseValue: 50, combatDice: die)
        RequiredSlot rs = new RequiredSlot(hand, fireWhip);
        CombatAttributeType catFire = CombatAttributeType.findByName('attribute.fire')
        WeaponAttribute fire = new WeaponAttribute(damageModifier: 2.0, combatAttributeType: catFire)
        fireWhip.addToCombatAttributes(fire)
        fireWhip.save()

        CombatAttributeType catNormal = CombatAttributeType.findByName('attribute.normal')
        WeaponAttribute normalDamage = new WeaponAttribute(damageModifier: 1.0, combatAttributeType: catNormal)
        die = new Dice(sides: 6, amount: 1)
        die.save()
        ItemType shortSword = new ItemType(name: 'weapon.short_sword', availability: 750,
                baseValue: 5, combatDice: die)
        RequiredSlot rsSword = new RequiredSlot(hand, shortSword);
        shortSword.addToCombatAttributes(normalDamage)
        shortSword.save()

        die = new Dice(sides: 6, amount: 2)
        die.save()
        ItemType longSword = new ItemType(name: 'weapon.long_sword', availability: 750,
                baseValue: 30, combatDice: die)
        RequiredSlot rsLongSword = new RequiredSlot(hand, longSword);
        RequiredSlot rsLongSword2 = new RequiredSlot(hand, longSword);
        longSword.addToCombatAttributes(normalDamage)
        longSword.save()

        die = new Dice(sides: 5, amount: 2, bonus: 2)
        die.save()
        ItemType longStaff = new ItemType(name: 'weapon.long_staff', availability: 350,
                baseValue: 23, combatDice: die)
        RequiredSlot rsLongStaff = new RequiredSlot(hand, longStaff);
        RequiredSlot rsLongStaff2 = new RequiredSlot(hand, longStaff);
        longStaff.addToCombatAttributes(normalDamage)
        longStaff.save()

        die = new Dice(sides: 1, amount: 1, bonus: 6)
        die.save()
        ItemType toothpick = new ItemType(name: 'weapon.toothpick_of_death', availability: 500,
                baseValue: 10, combatDice: die)
        CombatAttributeType catDeath = CombatAttributeType.findByName('attribute.death')
        RequiredSlot rsPick = new RequiredSlot(hand, toothpick);
        def death = new WeaponAttribute(damageModifier: 2.0, combatAttributeType: catDeath)
        toothpick.addToCombatAttributes death
        toothpick.save()

        die = new Dice(sides: 4, amount: 1)
        die.save()
        ItemType torch = new ItemType(name: 'weapon.torch', availability: 1000,
                baseValue: 1, combatDice: die)
        RequiredSlot rsTorch = new RequiredSlot(hand, torch)
        WeaponAttribute torchFire = new WeaponAttribute(damageModifier: 1.25, combatAttributeType: catFire)
        torch.addToCombatAttributes(torchFire)
        torch.save()

        def weapons = [fireWhip, shortSword, longSword, longStaff, toothpick, torch]
        weapons.each {weapon ->
            ItemCategory weaponCategory = new ItemCategory(weapon, 'weapon')
            weaponCategory.save()
        }
        def magicWeapons = [fireWhip, toothpick]
        magicWeapons.each {weapon ->
            ItemCategory mc = new ItemCategory(weapon, 'magic')
            mc.save()
        }
    }

    void initMobTemplates() {
        log.debug("initialize mob templates")
        def d20 = Dice.findByName('d20')
        def iniDice = new Dice(name: 'initiative', sides: 20)
        iniDice.save()

        def d6 = Dice.findByName('d6') // this works, as long as there are no other Dice.
        Dice d6x4p4 = new Dice(sides: 6, amount: 4, bonus: 4)
        d6x4p4.save()

        Dice d1 = new Dice()
        d1.save()

        Dice d6x2 = new Dice(sides: 6, amount: 2)
        d6x2.save()

        Dice d6x5p5 = new Dice(sides: 6, amount: 5, bonus: 5)
        d6x5p5.save()

        Dice d4 = new Dice(name: 'd4', sides: 4)
        d4.save()

        MobTemplate mt = new MobTemplate(name: 'Orc',
                strike: d20, parry: d20, initiative: iniDice, damage: d6,
                hp: 10, xpValue: 4)
        mt.save()

        // TODO: perhaps a default list of artists would be useful.
        Artist rm = new Artist(name: 'Rich Morris',
                website: 'http://portfolio.shipsinker.com/')
        rm.save()

        Artist lp = new Artist(name: 'Lucas Pandolfelli',
                website: 'http://lucaspandolfelli.blogspot.com/')
        lp.save()

        License custom = License.findByName("license.custom.image")
        Image orcImage = new Image(url: 'http://images.schedim.de/mobs/orc.jpg',
                name: 'orc',
                artist: lp,
                description: 'image.orc',
                sourceUrl: null,
                width: 500, height: 375,
                license: custom,
        )
        orcImage.save()
        MobImage orcMobImage = new MobImage(mt, orcImage)
        orcMobImage.save()

        MobTemplate mt2 = new MobTemplate(name: 'Troll',
                strike: d20, parry: d20, initiative: iniDice, damage: d6x2,
                hp: 20,
                xpValue: 8)
        mt2.save()

        MobTemplate mt3 = new MobTemplate(name: 'Red Dragon',
                strike: d20, parry: d20, initiative: iniDice, damage: d6x5p5,
                hp: 100,
                xpValue: 2000)
        mt3.save()

//		MobImage mobImage = new MobImage(filename:'kirchner_drache_small.jpg',
//				name:'dragon',
//				description:'image.dragon',
//				sourceUrl:'http://de.wikipedia.org/wiki/Datei:Kirchner-Drache.jpg',
//				artist:null,
//				mobTemplate:mt3
//		)
//		mobImage.save(failOnError:true)

        MobTemplate mt4 = new MobTemplate(name: 'Angry Hornet',
                strike: d6x4p4, parry: d6x4p4, initiative: iniDice, damage: d1,
                hp: 1, xpValue: 1)
        mt4.save()

        MobTemplate mt5 = new MobTemplate(name: 'Rabid Rabbit',
                strike: d20, parry: d6, initiative: iniDice, damage: d6x2,
                hp: 3, xpValue: 4)
        mt5.save()

        MobTemplate mt6 = new MobTemplate(name: 'Kobold',
                strike: d20, parry: d20, initiative: iniDice, damage: d6,
                hp: 7, xpValue: 3)
        mt6.save()


        MobTemplate mt7 = new MobTemplate(name: 'Halfling',
                strike: d1, parry: d1, initiative: d1, damage: d1,
                hp: 1, xpValue: 1)
        mt7.save()

        MobTemplate mt8 = new MobTemplate(name: 'mob.training_puppet',
                strike: d1, parry: d1, initiative: d1, damage: d1,
                hp: 500, xpValue: 5)
        mt8.save()

        MobTemplate strawMan = new MobTemplate(name: 'mob.straw_man',
                strike: d20, parry: d1, initiative: d1, damage: d6,
                hp: 100, xpValue: 30)
        CombatAttributeType fireCat = CombatAttributeType.findByName('attribute.fire')
        fireCat.save()
        CreatureAttribute fireAtt = new CreatureAttribute(combatAttributeType: fireCat, damageModifier: 2.0)
        strawMan.addToResistanceAttributes(fireAtt)
        strawMan.save()
    }

    Town initTown() {
        log.debug("initialize town")
        Town startingCity = new Town(name: 'town.default.name',
                shortDescription: 'town.default.short_description',
                description: 'town.default.description'
        )
        startingCity.save()
        return startingCity
    }

    void initQuests() {
        log.debug("initialize quests")

        QuestGiver mayor = new QuestGiver(name:'giver.mayor', description:'giver.mayor.desc')
        mayor.save()

        QuestTemplate quest = new QuestTemplate(name: 'quest.generic.name',
                level: 100,
                description: 'quest.orc.desc',
                giver:mayor
        )
        quest.save()

        // Orc quest:
        MobTemplate orcTemplate = MobTemplate.findByName('Orc')

        Encounter orcEncounter = new Encounter(name: 'encounter.orc')
        orcEncounter.save()
        EncounterMob orcMob = new EncounterMob(orcEncounter, orcTemplate)
        orcMob.save()
        QuestStep qs = new QuestStep(title: 'quest.generic.fight',
                description: 'quest.generic.description',
                endOfQuest: true, firstStep: true,
                questTemplate: quest, encounter: orcEncounter,
                name:'step.fight.orc'
        )
        quest.addToSteps(qs)
        orcEncounter.addToSteps(qs)
        qs.save()

        //  Red dragon quest:
        QuestGiver king = new QuestGiver(name: 'giver.king', description: 'giver.king.desc')
        king.save()
        MobTemplate redDragon = MobTemplate.findByName('Red Dragon')
        Encounter dragonEncounter = new Encounter(name: 'encounter.red.dragon')
        dragonEncounter.save()
        EncounterMob dragonMob = new EncounterMob(dragonEncounter, redDragon)
        dragonMob.save()
        quest = new QuestTemplate(name: 'quest.test.dragon',
                level: 10000,
                description: 'quest.red.dragon.desc',
                giver:king
        )
        quest.save()

        qs = new QuestStep(title: 'quest.generic.fight',
                description: 'quest.generic.description',
                endOfQuest: true, firstStep: true,
                name:'step.fight.dragon',
                questTemplate: quest, encounter: dragonEncounter
        )
        quest.addToSteps(qs)
        dragonEncounter.addToSteps(qs)
        qs.save()

        //  halfling quest:
        QuestGiver urchin = new QuestGiver(name: 'giver.urchin', description: 'giver.urchin.desc')
        urchin.save()
        MobTemplate halfling = MobTemplate.findByName('Halfling')
        Encounter halfEncounter = new Encounter(name: 'encounter.halfling')
        halfEncounter.save()
        EncounterMob halflingMob = new EncounterMob(halfEncounter, halfling)
        halflingMob.save()
        quest = new QuestTemplate(name: 'quest.test.halfling',
                level: 50,
                description: 'quest.halfling.desc',
                giver:urchin
        )
        quest.save()
        qs = new QuestStep(title: 'quest.generic.fight',
                description: 'quest.generic.description',
                endOfQuest: true, firstStep: true,
                name:'step.fight.halfling',
                questTemplate: quest, encounter: halfEncounter
        )
        quest.addToSteps(qs)
        halfEncounter.addToSteps(qs)
        qs.save()

        //  training puppet quest:
        QuestGiver smith = new QuestGiver(name: 'giver.smith', description: 'giver.smith.desc')
        smith.save()
        MobTemplate puppet = MobTemplate.findByName('mob.training_puppet')
        Encounter puppetEncounter = new Encounter(name: 'encounter.training.puppet')
        puppetEncounter.save()
        EncounterMob puppetMob = new EncounterMob(puppetEncounter,puppet)
        puppetMob.save()
        QuestTemplate puppetQuest = new QuestTemplate(name: 'quest.test.puppet',
                level: 500,
                description: 'quest.puppet.desc',
                giver:smith
        )
        puppetQuest.save()

        QuestStep fightPuppet = new QuestStep(title: 'quest.generic.fight',
                description: 'quest.generic.description',
                firstStep: true, endOfQuest: true,
                name:'step.fight.puppet',
                questTemplate: puppetQuest, encounter: puppetEncounter
        )
        puppetQuest.addToSteps(fightPuppet)
        puppetEncounter.addToSteps(fightPuppet)
        fightPuppet.save()

        initMushroomQuest()
        initStrawManQuest()
        initElfQuest()
    }

    void initStrawManQuest() {
        log.debug("initialize StrawManQuest")
        QuestGiver philosopher = new QuestGiver(name: 'giver.philosopher', description: 'giver.philosopher.desc')
        philosopher.save()
        QuestTemplate quest = new QuestTemplate(name: 'quest.test.straw_man',
                level: 150,
                description: 'quest.test.straw_man.description',
                giver:philosopher
        )
        quest.save()

        MobTemplate strawMan = MobTemplate.findByName('mob.straw_man')
        Encounter strawManEncounter = new Encounter(name: 'encounter.straw_man')
        strawManEncounter.save()
        EncounterMob strawManMob = new EncounterMob(strawManEncounter, strawMan)
        strawManMob.save()

        QuestStep strawManFight = new QuestStep(title: 'quest.generic.fight',
                description: 'quest.generic.description',
                endOfQuest: false, encounter: strawManEncounter,
                firstStep: true, questTemplate: quest,
                name:'step.fight.straw_man',
        )
        quest.addToSteps(strawManFight)
        strawManEncounter.addToSteps(strawManFight)
        strawManFight.save()

        ItemType stone = new ItemType(name: 'item.philosopher_stone', availability: 0,
                baseValue: 43)
        stone.save()
        Encounter philReward = new Encounter(includesCombat: false,
                script: GoblinScript.findByName('PickupItem'),
                config: '<params><items><item>item.philosopher_stone</item></items></params>',
                name: 'encounter.philosopher_stone'
        )
        philReward.save()
        QuestStep reward = new QuestStep(title: 'quest.straw_man.reward.title',
                description: 'quest.straw_man.reward',
                encounter: philReward, questTemplate: quest,
                endOfQuest: true,
                name:'step.straw_man.reward',
        )
        philReward.addToSteps(reward)
        quest.addToSteps(reward)
        reward.save()
        StepChild stepChild = new StepChild(strawManFight, reward)
        stepChild.save()
    }

    void initMushroomQuest() {
        log.debug("initialize MushroomQuest")
        QuestGiver crone = new QuestGiver(name: 'giver.crone', description: 'giver.crone.desc')
        crone.save()
        QuestTemplate quest = new QuestTemplate(name: 'quest.test.shrooms',
                level: 250,
                description: 'quest.test.shrooms.description',
                giver:crone
        )
        quest.save()

        // non-combat encounter: pick a mushroom
        Encounter encounter = new Encounter(includesCombat: false,
                name: 'encounter.find.mushroom'
        )
        encounter.save()
        QuestStep qs1 = new QuestStep(title: 'quest.shroom.title.found',
                description: 'quest.found.mushrooms',
                encounter: encounter, questTemplate: quest, firstStep: true,
                name:'step.shroom.1',
        )
        quest.addToSteps qs1
        encounter.addToSteps qs1
        qs1.save()

        // red mushroom encounter / QuestStep
        Encounter encounterRedShroom = new Encounter(includesCombat: false,
                script: GoblinScript.findByName('script.pickupItem'),
                config: '<params><items><item>item.mushroom.red</item></items></params>',
                name: 'encounter.red.mushroom'
        )
        encounterRedShroom.save()
        QuestStep qs2 = new QuestStep(title: 'quest.shroom.title.pick.red',
                description: 'quest.shroom.pick.red.shroom',
                encounter: encounterRedShroom, questTemplate: quest,
                name:'step.shroom.2',
        )
        quest.addToSteps qs2
        encounterRedShroom.addToSteps qs2
        qs2.save()
        StepChild stepChild = new StepChild(qs1,qs2)
        stepChild.save()

        // black mushroom encounter / QuestStep
        Encounter encounterBlackShroom = new Encounter(includesCombat: false,
                script: GoblinScript.findByName('script.pickupItem'),
                config: '<params><items><item>item.mushroom.black</item></items></params>',
                name: 'encounter.black.mushroom'
        )
        encounterBlackShroom.save()
        QuestStep qs3 = new QuestStep(title: 'quest.shroom.title.pick.black',
                description: 'quest.shroom.pick.black.shroom',
                encounter: encounterBlackShroom, questTemplate: quest,
                name:'step.shroom.3',
        )
        quest.addToSteps qs3
        encounterBlackShroom.addToSteps qs3
        qs3.save()
        StepChild qs1qs3 = new StepChild(qs1,qs3)
        qs1qs3.save()

        // delivering the mushroom
        Encounter checkItem = new Encounter(includesCombat: false,
                script: GoblinScript.findByName('script.deliverItem'),
                config: """<params><items><item>item.mushroom.red</item></items>
                    <steps>
                    <success>quest.step.shroom.success.title</success>
                    <fail>quest.step.shroom.fail.title</fail>
                    </steps>
                    </params>""",
                name: 'encounter.deliver.mushroom'
        )
        checkItem.save()
        QuestStep qs4 = new QuestStep(title: 'quest.shroom.title.deliver',
                description: 'quest.shroom.deliver',
                endOfQuest: false,
                encounter: checkItem,
                automaticTransition: true,
                questTemplate: quest,
                name:'step.shroom.deliver',
        )
        quest.addToSteps qs4
        checkItem.addToSteps qs4
        qs4.save()
        StepChild qs2qs4 = new StepChild(qs2,qs4)
        qs2qs4.save()
        StepChild qs3qs4 = new StepChild(qs3,qs4)
        qs3qs4.save()

        Encounter questRewardSuccess = new Encounter(includesCombat: false,
                script: GoblinScript.findByName('script.getReward'),
                config: '<params><gold>100</gold><xp>10</xp></params>',
                name: 'encounter.get.gold.100'
        )
        questRewardSuccess.save()
        QuestStep shroomSuccess = new QuestStep(title: 'quest.step.shroom.success.title',
                description: 'quest.step.shroom.success',
                endOfQuest: true,
                encounter: questRewardSuccess, questTemplate: quest,
                name:'step.shroom.success',
        )
        quest.addToSteps shroomSuccess
        questRewardSuccess.addToSteps shroomSuccess
        shroomSuccess.save()

        Encounter questRewardFail = new Encounter(includesCombat: false,
                script: GoblinScript.findByName('script.getReward'),
                config: '<params><xp>10</xp></params>',
                name: 'encounter.get.xp.10'
        )
        questRewardFail.save()
        QuestStep shroomFail = new QuestStep(title: 'quest.step.shroom.fail.title',
                description: 'quest.step.shroom.fail',
                encounter: questRewardFail, questTemplate: quest,
                endOfQuest: true,
                name:'step.shroom.fail',
        )
        quest.addToSteps shroomFail
        questRewardFail.addToSteps shroomFail
        shroomFail.save()

        StepChild qs4success = new StepChild(qs4, shroomSuccess)
        qs4success.save()
        StepChild qs4fail = new StepChild(qs4, shroomFail)
        qs4fail.save()
        qs4.save()
    }

    void initFactions() {
        log.debug("initialize Factions")

        def repMessages = ['unknown': [0], 'good': [1], 'very.good': [11],
                'very.very.good': [21],
                'best': [41],
                'bad': [-1], 'very.bad': [-11],
                'extremely.bad': [-21],
                'worst': [-41]]

        ReputationMessageMap rmmDwarfs = new ReputationMessageMap(name: 'rmm.dwarves')
        ReputationMessageMap rmmElves = new ReputationMessageMap(name: 'rmm.elves')
        rmmElves.save()
        rmmDwarfs.save()


        repMessages.each {messageId, repRange ->
            ReputationMessage rmd = new ReputationMessage(messageId: "reputation.$messageId", reputation: repRange[0],
                    repMessageMap: rmmDwarfs)
            rmmDwarfs.addToRepMessages(rmd)
            rmd.save()
            ReputationMessage rme = new ReputationMessage(messageId: "reputation.$messageId", reputation: repRange[0],
                    repMessageMap: rmmElves)
            rmmElves.addToRepMessages(rme)
            rme.save()
        }

        Faction elves = new Faction(name: 'faction.elves', description: 'faction.elves.description', repMessageMap: rmmElves)
        elves.save()
        rmmElves.faction = elves

        Faction dwarfs = new Faction(name: 'faction.dwarves', description: 'faction.dwarves.description', repMessageMap: rmmDwarfs)
        dwarfs.save()
        rmmDwarfs.faction = dwarfs
    }

    void initElfQuest() {
        log.debug("initialize ElfQuest")
        QuestGiver mayor = QuestGiver.findByName('giver.mayor')
        QuestTemplate quest = new QuestTemplate(name: 'quest.test.elf',
                level: 125,
                description: 'quest.test.elf.description',
                giver:mayor
        )
        quest.save()

        // non-combat encounter: encounter an elf in distress
        Encounter encounterMaid = new Encounter(includesCombat: false,
                name: 'encounter.elf.distress')
        encounterMaid.save()
        QuestStep qs1 = new QuestStep(title: 'quest.elf.distress.title',
                description: 'quest.elf.distress',
                encounter: encounterMaid, questTemplate: quest,
                firstStep: true,
                name:'step.elf.1',
        )
        quest.addToSteps qs1
        encounterMaid.addToSteps qs1
        qs1.save()

        //  help the elf
        Encounter nice = new Encounter(includesCombat: false,
                script: GoblinScript.findByName('script.getReward'),
                config: '''<params><gold>30</gold><reputation><faction><name>faction.elves</name><level>10</level></faction>
            <faction><name>faction.dwarves</name><level>-1</level></faction></reputation>
</params>''',
                name: 'encounter.elf.reward'
        )
        nice.save()
        QuestStep qs2 = new QuestStep(title: 'quest.elf.nice.title',
                description: 'quest.elf.nice',
                encounter: nice, questTemplate: quest,
                endOfQuest: true,
                intro: 'quest.elf.help',
                name:'step.elf.nice',
        )
        quest.addToSteps qs2
        nice.addToSteps qs2
        qs2.save()
        StepChild qs1qs2 = new StepChild(qs1, qs2)
        qs1qs2.save()

        // call the dwarves
        Encounter mean = new Encounter(includesCombat: false,
                script: GoblinScript.findByName('script.getReward'),
                config: '''<params><gold>75</gold><reputation><faction><name>faction.dwarves</name><level>9</level></faction>
                            <faction><name>faction.elves</name><level>-20</level></faction></reputation>
                            </params>
                            ''',
                name: 'encounter.dwarf.reward    '
        )
        mean.save()
        QuestStep qs3 = new QuestStep(title: 'quest.elf.mean.title',
                description: 'quest.elf.mean',
                encounter: mean, questTemplate: quest,
                endOfQuest: true,
                intro: 'quest.elf.go_home',
                name:'step.elf.mean',
        )
        quest.addToSteps qs3
        mean.addToSteps qs3
        qs3.save()
        StepChild qs1qs3 = new StepChild(qs1, qs3)
        qs1qs3.save()
    }

    void initOrders(PlayerCharacter pc) {
        log.debug("initialize Orders")

        GoblinOrder order = new GoblinOrder(name: 'Order of the Ebon Hand', leader: pc, description: 'The legendary Order of the Ebon Hand')
        order.addToMembers(pc)
        order.save()
        pc.goblinOrder = order

        // create some orders to test previous/next functionality in order list:
        (0..20).each {
            order = new GoblinOrder(name: "Unnumbered Order #$it", leader: pc, description: 'Yet another order.')
            order.addToMembers(pc)
            order.save()
            ChatterBox box = new ChatterBox(goblinOrder: order, name: 'chatterbox.default.name')
            order.addToChatterBoxes(box)
            box.save()
        }
    }

    void initGlobalSettings() {
        log.debug("initialize global settings")

        def settings = ['coins.price.create_order': '10',
                'order.chatterboxes': '3',
                'order.chatterbox.max.messages': '10', // for testing; later: 100 or such.
                'coins.price.chatterbox': '10',
                'academy.refund.percentage': '100', // the % a pc will get back when canceling a course in an academy.
                'username.min.length':3,
                'password.min.length':6,
                'server_url':
                // TODO: should use grailsApplication.config.serverUrl
                Environment.current.equals(Environment.DEVELOPMENT) ? 'http://127.0.0.1:8080' : 'http://schedim.de'
        ]

        settings.each {k, v ->
            GlobalConfigEntry entry = new GlobalConfigEntry(name: k, entryValue: v);
            entry.save()
        }
    }

    void initMailBoxTypes() {
        log.debug("initialize mail box types")

        def boxTypes = ['mail.inbox', 'mail.outbox', 'mail.archive']
        boxTypes.each {name ->
            MailBoxType type = new MailBoxType(name: name)
            type.save()
        }
    }

    void initHelp() {
        Help academyHelp = new Help(messageId: 'help.skillSets')
        academyHelp.save()
    }
} 