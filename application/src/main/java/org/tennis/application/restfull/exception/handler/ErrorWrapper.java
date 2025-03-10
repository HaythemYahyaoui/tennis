package org.tennis.application.restfull.exception.handler;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorWrapper {

    private final String attribute;
    private final String message;

}
