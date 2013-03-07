import de.dewarim.goblin.shop.Shop
import de.dewarim.goblin.shop.ShopCategory
import de.dewarim.goblin.town.Town

include('game/shopOwners')

def categories = de.dewarim.goblin.Category.list()

fixture{
    
    generalStore(Shop, name:'shop.general.store',
                description:'shop.general.store.description',
            owner:defOwner, town:Town.findByName('town.default.name')
    )
    categories.each {category ->
        "sc${category.name}"(ShopCategory, shop:generalStore, category:category)
    }
}


