package org.tennis.business.exception;

public class DomainException extends Exception {
    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
    public DomainException(String message) {
        super(message);
    }
}
