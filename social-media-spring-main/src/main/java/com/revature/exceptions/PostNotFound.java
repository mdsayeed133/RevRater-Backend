package com.revature.exceptions;
//this
public class PostNotFound extends RuntimeException{
    public PostNotFound() {
    }

    public PostNotFound(String message) {
        super(message);
    }

    public PostNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public PostNotFound(Throwable cause) {
        super(cause);
    }

    public PostNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
