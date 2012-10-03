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

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Dice)) return false

        Dice dice = (Dice) o

        if (amount != dice.amount) return false
        if (bonus != dice.bonus) return false
        if (name != dice.name) return false
        if (sides != dice.sides) return false

        return true
    }

    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        result = 31 * result + (sides != null ? sides.hashCode() : 0)
        result = 31 * result + (amount != null ? amount.hashCode() : 0)
        result = 31 * result + (bonus != null ? bonus.hashCode() : 0)
        return result
    }
}
