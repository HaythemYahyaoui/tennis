package org.tennis.application.restfull.exception.handler;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;

@Builder
@Getter
public class BadRequestResponse {

    private final int code;
    private final String message;
    private final Collection<ErrorWrapper> errors;

}
