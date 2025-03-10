package org.tennis.application.restfull.exception.handler;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FailResponse {

    private final int code;
    private final String message;

}
