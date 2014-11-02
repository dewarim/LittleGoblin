package de.dewarim

/**
 * Result class object using an Optional<T>. 
 * This helps reducing the places where problems are communicated via RuntimeException
 * and also reduces cases where nullable objects are returned and not properly handled.
 */
class OptionalResult<T> {
    
    List<String> errors
    
    private T resultObject

    OptionalResult() {
        
    }

    OptionalResult(T resultObject) {
        this.resultObject = result
    }
    
    OptionalResult(List<String> errors){
        this.errors =errors 
    }
    
    OptionalResult(String error){
        addError(error)
    }

    Optional<T> getResult(){
        return Optional.ofNullable(resultObject)
    }
    
    Boolean hasErrors(){
        return errors?.isEmpty()
    }
        
    void addError(String error){
        if(errors == null){
            errors = [error]
        }
        else{
            errors.add(error)
        }
    }
}
