package com.revature.exceptions;

// Exception to be thrown by the AuthAspect
// Will be handled by a Spring Exception Handler to return a 401
public class ProfanityException extends RuntimeException {

    public ProfanityException() {
    }

    public ProfanityException(String message) {
        super(message);
    }

    public ProfanityException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProfanityException(Throwable cause) {
        super(cause);
    }

    public ProfanityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
