package de.dewarim.goblin.mob;

import de.dewarim.goblin.Creature

class MobTemplate extends Creature {
	
	// TODO: translate mob name when displaying
	static hasMany = [mobs:Mob, mobImages:MobImage, encounterMobs:EncounterMob]

	Long xpValue = 1

	MobImage selectImage(){
		if(mobImages?.isEmpty()){
			return null
		}
		// TODO: looks like asList creates a _new_ list every time.
		return mobImages.asList().get( (Integer) (Math.random() * mobImages.size()) )
	}
}
