package org.example.restspringbootudemy.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleAllExceptions(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleNotFoundExceptions(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(RequiredObjectIsNullException.class)
    public ResponseEntity<StandardError> handleRequiredObjectIsNullExceptions(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public ResponseEntity<StandardError> handleInvalidJwtAuthenticationExceptions(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(error, status);
    }

}
