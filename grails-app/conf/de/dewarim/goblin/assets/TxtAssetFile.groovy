package de.dewarim.goblin.assets

import asset.pipeline.AbstractAssetFile

/**
 * Asset file for handling static text files
 * TODO: FIXME: since the last plugin upgrade (or before?), this class is broken.
 */
class TxtAssetFile extends AbstractAssetFile{

    static final String contentType = 'text/plain'
    static extensions = ['txt']
    static compiledExtension = 'txt'
    static processors = []
    
//    @Override
    String directiveForLine(String line) {
        return null
    }
    
}
