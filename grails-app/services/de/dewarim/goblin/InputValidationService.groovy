package de.dewarim.goblin

import de.dewarim.goblin.asset.IAsset
import org.codehaus.groovy.grails.plugins.codecs.HTMLCodec
import org.codehaus.groovy.grails.support.encoding.Encoder

/**
 * This class is responsible for input validation and encoding.
 */
class InputValidationService {
    
    // use static encoder to make testing easier.
    static Encoder htmlEncoder = new HTMLCodec().encoder
    static transactional = false

    /**
     * Check if name is not null and does not consist of whitespace.
     * Then check if there is already another instance of myObject with this name
     * in the database. Return the HTML-encoded name afterwards.
     * @param name raw input String from client
     * @param myObject an object whose class is GORM-enabled and has a "name" field.
     * @return the HTML-encoded name.
     * @throws RuntimeException either "error.missing.name", if the name is null or invalid,
     *  or "error.name.not.unique" there already exists another object with the same name in the database.
     */
    // TODO: does it make sense to have the encoding part of this method?
    // All output should be encoded properly anyway, and we do not want HTML encoded user/object names anyway
    // in the database [which will further mess up encoding the output again somewhere later]
    String checkAndEncodeName(String name, myObject) {
        if (!name || name.trim().length() == 0) {
            throw new RuntimeException('error.missing.name')
        }
        name = name.encodeAsHTML()
        def testName = myObject.class.findByName(name)
        if (testName && !testName.id.equals(myObject.id)) {
            if(myObject instanceof IAsset){
               // an asset may have more than one version, all having the same name.
               def assetVersion = myObject.class.findByAssetVersion(myObject.assetVersion)
               if(! assetVersion){
                   return name
               } 
            }
            throw new RuntimeException('error.name.not.unique')
        }
        return name
    }

    /**
     * Ensure that a given input field is not empty or blank and encode its content.
     * @param params Map of HTTP-Request params
     * @param fieldName the input field name
     * @param fieldLabel the label for the input field (usually a message-id)
     * @return the HTML-encoded input string
     */
    String checkAndEncodeText(params, String fieldName, String fieldLabel) {
        String txt = params."${fieldName}"
        if (!txt || txt.trim().length() == 0) {
            throw new RuntimeException('error.empty.field')
        }
        return htmlEncoder.encode(txt)
    }

    Integer checkAndEncodeInteger(params, String fieldName, String fieldLabel) {
        String txt = params."${fieldName}"
        if (!txt || txt.trim().length() == 0) {
            throw new RuntimeException('error.empty.field')
        }
        Integer number
        try {
            number = Integer.parseInt(txt)
        }
        catch (NumberFormatException e) {
            throw new RuntimeException('error.invalid.integer')
        }
        return number
    }

    /**
     * Load an object of the given class by its ID.
     * @param clazz class of the object
     * @param id string representation of the id, which is parsed as a Long value.
     * @return either the requested object or a RuntimeException if the object could not be found.
     */
    Object checkObject(clazz, id) {
        checkObject(clazz, id, false)
    }

    /**
     * Load an object of the given class by its ID.
     * @param clazz class of the object
     * @param id string representation of the id, which is parsed as a Long value.
     * @param nullAllowed boolean whether to return a null if the parameter is null or the id is invalid.
     * @return the requested object or, if the object could not be found, a RuntimeException. If nullAllowed
     * evaluates to true, in case of a missing object / invalid id this method just returns null.
     */
    Object checkObject(clazz, String id, nullAllowed) {
        log.debug("nullAllowed: ${nullAllowed}, id:${id}")
        if (id == null || id == 'null' || !id || id.trim().length() == 0) {
            log.debug("id == null")
            if (nullAllowed) {
                return null
            }
            else {
                throw new RuntimeException("error.missing.id")
            }
        }
        def obj = clazz.get(Long.parseLong(id))
        if (nullAllowed && (!obj)) {
            return null
        }

        if (!obj) {
            throw new RuntimeException("error.invalid.object")
        }
        return obj
    }

    Enum checkEnum(clazz, name) {
        if (!name) {
            throw new RuntimeException("error.missing.enum")
        }
        if (!clazz) {
            throw new RuntimeException("error.missing.enum.class")
        }
        try {
            return Enum.valueOf(clazz, name)
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException("error.invalid.enum")
        }
    }

    Boolean checkAndEncodeBoolean(params, String fieldName, String fieldLabel) {
        String txt = params."${fieldName}"
        Boolean bool
        try {
            if (txt?.equalsIgnoreCase('on')) {
                bool = true
            }
            else {
                bool = Boolean.parseBoolean(txt)
            }
        }
        catch (Exception e) {
            throw new RuntimeException('error.invalid.boolean')
        }
        return bool
    }

    String checkXmlText(xmlString) {
        // this should throw an exception if parsing fails.
        new XmlParser(false, false).parseText(xmlString)
        return xmlString
    }
}
