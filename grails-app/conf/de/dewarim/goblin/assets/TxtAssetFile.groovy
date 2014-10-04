package de.dewarim.goblin.assets

import asset.pipeline.AbstractAssetFile

/**
 * Asset file for handling static text files
 */
class TxtAssetFile extends AbstractAssetFile{

    static final String contentType = 'text/plain'
    static extensions = ['txt']
    static compiledExtension = 'txt'
    static processors = []
    
    @Override
    String directiveForLine(String line) {
        return null
    }
    
}
