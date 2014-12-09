package de.dewarim.goblin

/**
 */
class CreatureLife {

    static constraints = {
        points validator: {
            return it != null && it >= 0
        }
    }

    Creature creature
    Integer points

    boolean isAlive() {
        return points > 0
    }

    boolean isDead() {
        return points == 0
    }

    int heal(int amount, int max) {
        amount = Math.abs(amount)
        int diff
        if (points + amount < max) {
            diff = amount - points
            points = points + amount
        }
        else {
            diff = max - points
            points = max
        }
        return diff
    }

    void dealDamage(int amount) {
        amount = Math.abs(amount)
        points = amount > points ? 0 : points - amount
    }

}
