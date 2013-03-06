import de.dewarim.goblin.Dice

fixture{
    
    d20(Dice, name:'d20', sides:20)
    d6(Dice, name:'d6', sides:6)
    d0x1p1(Dice, name:'1d1', amount:0, sides:1, bonus:1)
    d2x6p2(Dice, name:'2d6+2', amount:2, sides:6, bonus:2)
    d2x6(Dice, name:'2d6', amount:2, sides: 6)
    d2x5(Dice, name:'2d5', amount:2, sides: 5)
    d1x1p6(Dice, name:'1d1+6', amount: 1, sides: 1)
    d4(Dice, name:'1d4', sides: 4)
    initiative(Dice, name:'initiative', sides:20)
    d5x6p5(Dice, name:'6d6+5', amount: 5, sides: 6, bonus: 5)
    d4x4(Dice, name:'4d4', amount: 4, sides: 4)
    d6x4p4(Dice, name:'6d4', amount: 6, sides: 4)
    
}