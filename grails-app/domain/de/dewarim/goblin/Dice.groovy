package de.dewarim.goblin;

class Dice {

    static constraints = {
        name nullable:false, unique:true
    }

    String name = UUID.randomUUID().toString()
	Integer sides = 1
	Integer amount = 1
	Integer bonus = 0

	Integer roll(){
		Integer result = bonus
		for(int x = 0; x<amount; x++){
			result = result + singleDie()		
		}
		return result
	}
	
	Integer singleDie(){
		return (Integer) ( (Math.random() * sides) +1)
	}

    String toString(){
        if(amount == 0){
            return "+$bonus"
        }
        return "${amount} \u2685 ${sides} +${bonus}"
    }

}
