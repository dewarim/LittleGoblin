package de.dewarim.goblin;

/**
 */
public class InvalidFormException extends RuntimeException{

    public InvalidFormException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFormException(String message) {
        super(message);
    }
}
