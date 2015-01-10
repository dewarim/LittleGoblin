package de.dewarim.goblin;

/**
 */
public class InvalidFormException extends RuntimeException{

    private static final long serialVersionUID = 2681378907930109715L;

    public InvalidFormException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFormException(String message) {
        super(message);
    }
}
