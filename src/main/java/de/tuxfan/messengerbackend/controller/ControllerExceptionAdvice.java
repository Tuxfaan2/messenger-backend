package de.tuxfan.messengerbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionAdvice.class);

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final Exception exception) {
        ErrorResponse errorResponse = ErrorResponse.builder(exception,
                HttpStatus.INTERNAL_SERVER_ERROR, "An internal server error occurred").build();
        log.error(exception.getMessage(), exception);
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handeUnauthorizedException(final BadCredentialsException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder(exception, HttpStatus.UNAUTHORIZED,
                exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
