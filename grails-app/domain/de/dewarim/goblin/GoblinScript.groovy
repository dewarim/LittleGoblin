package de.dewarim.goblin

/**
 * There are some objects that may reference Groovy or Java script classes, for example
 * instances of class "Encounter". To avoid having the admin enter the direct name of the
 * script class every time he wants to create a new Encounter, this class is used.<br>
 * GoblinScripts should probably only be created by the programmers.
 */
class GoblinScript {

    String name
    Class clazz

}
