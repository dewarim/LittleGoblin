import de.dewarim.goblin.Category

def catNames = ['weapon', 'armor', 'metal', 'potion', 'mushroom', 'magic']

fixture{
    
    catNames.each{name ->
        "$name"(Category, name:name)
    }
    
}