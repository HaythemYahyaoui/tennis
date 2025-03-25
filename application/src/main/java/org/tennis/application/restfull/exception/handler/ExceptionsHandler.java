package org.tennis.application.restfull.exception.handler;


import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tennis.application.exception.ApplicationException;
import org.tennis.business.exception.DomainException;
import org.tennis.business.exception.InfrastructureException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

@Log4j2
@RestControllerAdvice
public class ExceptionsHandler {

    public static final String BAD_REQUEST = "BAD REQUEST";

    @ExceptionHandler({Exception.class})
    public ResponseEntity<FailResponse> exception(Exception exception) {
        FailResponse failResponse = FailResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(exception.getMessage()).build();
        log.error("", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(failResponse);
    }

    @ExceptionHandler({InfrastructureException.class, DomainException.class})
    public ResponseEntity<FailResponse> InfrastructureAndDomainException(Exception exception) {
        FailResponse failResponse = FailResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(exception.getMessage()).build();
        log.debug("", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(failResponse);
    }

    @ExceptionHandler({ApplicationException.class})
    public ResponseEntity<FailResponse> presentationException(ApplicationException presentationException) {
        FailResponse failResponse = FailResponse.builder().code(HttpStatus.BAD_REQUEST.value()).message(presentationException.getMessage()).build();
        log.debug("", presentationException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failResponse);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<BadRequestResponse> constraintViolationException(ConstraintViolationException constraintViolationException) {
        Collection<ErrorWrapper> errors = constraintViolationException.getConstraintViolations().stream().collect(ArrayList::new, (errorWrappers, constraintViolation) -> {
            errorWrappers.add(ErrorWrapper.builder().attribute(constraintViolation.getPropertyPath().toString()).message(constraintViolation.getMessage()).build());
        }, ArrayList::addAll);
        BadRequestResponse badRequestResponse = BadRequestResponse.builder().code(HttpStatus.BAD_REQUEST.value())
                .message(BAD_REQUEST)
                .errors(errors)
                .build();
        log.debug("", constraintViolationException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<BadRequestResponse> methodArgumentNotValidException(Locale locale, MethodArgumentNotValidException methodArgumentNotValidException) {
        Collection<ErrorWrapper> errors = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream().collect(ArrayList::new, (errorWrappers, constraintViolation) -> {
            errorWrappers.add(ErrorWrapper.builder().attribute(constraintViolation.getField()).message(constraintViolation.getDefaultMessage()).build());
        }, ArrayList::addAll);
        BadRequestResponse badRequestResponse = BadRequestResponse.builder().code(HttpStatus.BAD_REQUEST.value())
                .message(BAD_REQUEST)
                .errors(errors)
                .build();
        log.debug("", methodArgumentNotValidException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
    }

}
