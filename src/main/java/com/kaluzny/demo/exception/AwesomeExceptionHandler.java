package com.kaluzny.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AwesomeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ThereIsNoSuchAutoException.class)
    public ResponseEntity<AwesomeException> handleThereIsNoSuchUserException() {
        return new ResponseEntity<>(new AwesomeException("There is no such automobile"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AutoWasDeletedException.class)
    public ResponseEntity<AwesomeException> handleDeleteException() {
        return new ResponseEntity<>(new AwesomeException("This auto was deleted"), HttpStatus.NOT_FOUND);
    }

    private static class AwesomeException {
        String message;

        public AwesomeException(String message) {
            this.message = message;
        }
    }
}
