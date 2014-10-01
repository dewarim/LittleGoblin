import de.dewarim.goblin.Dice
import de.dewarim.goblin.shop.ShopOwner

fixture{
    defOwner(ShopOwner, name:'shop.owner.invisible',
                description:'shop.owner.invisible.description',
            priceModifierDice: Dice.findByName('d6')
    )
}