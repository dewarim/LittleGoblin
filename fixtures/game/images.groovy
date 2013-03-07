import de.dewarim.goblin.Image
import de.dewarim.goblin.License

include('game/artists')

def findLicense(String name){
    return License.findByName(name)
}

fixture {
    orcImage(Image, url: 'http://images.schedim.de/mobs/orc.jpg',
            name: 'orc',
            artist: lucas,
            description: 'image.orc',
            sourceUrl: null, width: 500, height: 375,
            license: findLicense('license.custom.image')
    )
    
    goblin(Image, url: 'http://images.schedim.de/LittleGoblinSmall.png',
            name: 'goblin',
            artist: rich,
            description: 'image.goblin',
            license: findLicense('license.custom.image'),
            height:316, width:264, sourceUrl: null
    )
    
}