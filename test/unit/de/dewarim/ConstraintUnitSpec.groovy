package de.dewarim

import spock.lang.Specification

/**
 * see: http://www.christianoestreich.com/2012/11/domain-constraints-grails-spock-updated/
 */
class ConstraintUnitSpec extends Specification{

    void validateConstraints(obj, field, error) {
        def validated = obj.validate()
        if (error && error != 'valid') {
            assert !validated
            assert obj.errors[field]
            assert error == obj.errors[field]
        } else {
            assert !obj.errors[field]
        }
    }

}
