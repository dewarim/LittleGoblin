# Testing roadmap for Little Goblin

Version: 2013-01-29 #1

## Introduction

This document will collect the necessary tasks to add automated unit and integration tests for Little Goblin.
The goal is to have automated tests which cover at least all functional aspects of the reference implementaton.

## Current State

There are no unit tests, the existing files are all the auto-generated empty files with dummy methods.

There are some old webtests:

* ProductionWebTests: learn a skill, craft an item.
* ShopWebTests: buy an item
* EquipWebTests: buy a weapon, go on a quest, equip item.
* FightWebTests: go on a quest, start a fight, run away.

grails test functional: will not run because of misconfigured database:
Cannot create JDBC driver of class 'org.h2.Driver' for connect URL 'jdbc:hsqldb:mem:testDb'
java.sql.SQLException: No suitable driver

The tests will probably not run, even if the database driver issue is fixed.

## Planned State:

As a first step, it would be good to use the code from BootStrap.groovy to create unit tests.
The BootStrap code, upon finding an empty database, genereates the reference 
implementation of a game that uses every available feature. 
So there is already much that can be reused in tests.

## Unit tests

While writing the basic unit tests, it would be good to read up and implement current 
best practices and employ new features availalbe in Grails 2.2. For example, when I started
with Grails development, I ran into some problems with domain validation objects in Grails 1.3.x,
so I mostly abandoned this approach. But now, years later, it looks like that would be useful
for stuff like "ensure that: assert name.trim().length() != 0".

A second aspect of the new unit tests will be to help refactoring the depenencies between domain classes.
There are probably too many tight couplings, especially in PlayerCharacter:

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

Those objects have to be loaded every time the player object is required.

Links:

* http://burtbeckwith.com/blog/?p=169
* http://mrpaulwoods.wordpress.com/2011/02/07/implementing-burt-beckwiths-gorm-performance-no-collections/
* http://grails.1312388.n4.nabble.com/Why-does-adding-an-instance-to-a-hasMany-rel-have-to-load-everything-td3490008.html
* http://stackoverflow.com/questions/7503615/should-i-convert-my-grails-domain-relations-to-use-hibernate-bags
* http://burtbeckwith.com/blog/?p=1029

## Functional tests

TODO



