package org.tennis.business.exception;

public class InfrastructureException extends Exception {
    public InfrastructureException(String message, Throwable cause) {
        super(message, cause);
    }

    public InfrastructureException(String message) {
        super(message);
    }

}
