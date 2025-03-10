package org.tennis.application.exception;

public class PresentationException extends Exception {

    public PresentationException(String message) {
        super(message);
    }

    public PresentationException(String message, Throwable cause) {
        super(message, cause);
    }

}
