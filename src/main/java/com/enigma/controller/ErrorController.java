package com.enigma.controller;

import com.enigma.exceptions.TokenRefreshException;
import com.enigma.model.response.ErrorResponse;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("502", e.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrorList = e.getBindingResult().getFieldErrors();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        List<String> errors = new ArrayList<>();

        for (FieldError error : fieldErrorList) {
            String errorMessage = String.format("Field '%s': %s", error.getField(), error.getDefaultMessage());
            errors.add(errorMessage);
        }

        for (ObjectError error : allErrors) {
            if (!(error instanceof FieldError)) {
                errors.add(error.getDefaultMessage());
            }
        }

        ErrorResponse errorResponse = new ErrorResponse("400", String.join("; ", errors));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.toString(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}
