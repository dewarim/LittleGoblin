import de.dewarim.goblin.Image

include('game/artists')
include('system/licenses')

fixture {
    orcImage(Image, url: 'http://images.schedim.de/mobs/orc.jpg',
            name: 'orc',
            artist: lucas,
            description: 'image.orc',
            sourceUrl: null, width: 500, height: 375,
            license: customImageLicense)
    
    goblin(Image, url: 'http://images.schedim.de/LittleGoblinSmall.png',
            name: 'goblin',
            artist: rich,
            description: 'image.goblin',
            license: customImageLicense,
            height:316, width:264, sourceUrl: null)
    
}