package de.dewarim

import org.springframework.validation.FieldError
import spock.lang.Specification

/**
 * see: http://www.christianoestreich.com/2012/11/domain-constraints-grails-spock-updated/
 */
class ConstraintUnitSpec extends Specification{

    void validateConstraints(obj, field, error) {
        def validated = obj.validate()
        if (error && error != 'valid') {
            assert !validated
            assert obj.errors.hasFieldErrors(field)
            obj.errors.getFieldErrors(field).each{FieldError fieldError ->
                fieldError.codes.contains(error)
            }
        } else {
            assert !obj.errors[field]
        }
    }

}
