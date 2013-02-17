# Testing roadmap for Little Goblin

Version: 2013-02-17 #2

## Introduction

This document will collect the necessary tasks to add automated unit and integration tests for Little Goblin.
The goal is to have automated tests which cover at least all functional aspects of the reference implementation.

## Current State

There is only one unit and one integration test (for Ticks)

There are some old webtests:

* ProductionWebTests: learn a skill, craft an item.
* ShopWebTests: buy an item
* EquipWebTests: buy a weapon, go on a quest, equip item.
* FightWebTests: go on a quest, start a fight, run away.

grails test-app will now run.

## Planned State:

As a first step, it would be good to use the code from BootStrap.groovy to create unit tests.
The BootStrap code, upon finding an empty database, generates the reference 
implementation of a game that uses every available feature. 
So there is already much that can be reused in tests.

## Unit tests

While writing the basic unit tests, it would be good to read up and implement current 
best practices and employ new features available in Grails 2.2. For example, when I started
with Grails development, I ran into some problems with domain validation objects in Grails 1.3.x,
so I mostly abandoned this approach. But now, years later, it looks like that would be useful
for stuff like "ensure that: assert name.trim().length() != 0".

A second aspect of the new unit tests will be to help refactoring the dependencies between domain classes.
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

Those referenced objects have to be loaded every time their collection is updated. In a browser game
with potentially hundreds (or thousands) of concurrent users, it is certainly not advisable to load
large collections (pcMessages, products, jobs) into memory whenever an element is added. Also, some
parts may possible move to plugins (for example, a crafting plugin with domain classes and services).

Links:

* http://burtbeckwith.com/blog/?p=169
* http://mrpaulwoods.wordpress.com/2011/02/07/implementing-burt-beckwiths-gorm-performance-no-collections/
* http://grails.1312388.n4.nabble.com/Why-does-adding-an-instance-to-a-hasMany-rel-have-to-load-everything-td3490008.html
* http://stackoverflow.com/questions/7503615/should-i-convert-my-grails-domain-relations-to-use-hibernate-bags
* http://burtbeckwith.com/blog/?p=1029

## Functional tests

The old integration tests were written with Canoo webtest as the browser automation and testing framework.
It would be good to examine which new frameworks and plugins for writing test code have appeared since then
and if they offer any improvements compared to the rarely updated webtest plugin.

Potential candidates:

* http://grails.org/plugin/spock
* http://grails.org/plugin/webtest
* http://grails.org/plugin/geb
* http://grails.org/plugin/functional-test

To test the JavaScript code in LittleGoblin:

* https://www.assembla.com/spaces/sumatra/wiki
* http://grails.org/plugin/jasmine

## Continuous integration and continuous delivery

Little Goblin should have a CI server and a CD solution which deploys new versions automatically to the
public test server. Prerequisites for this are working tests and more research in how to best setup
a comprehensive solution. This will probably entail the setup of a Jenkins server, an Artifactory instance
and Puppet.

