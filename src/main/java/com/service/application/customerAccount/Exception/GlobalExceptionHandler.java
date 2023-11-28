package com.service.application.customerAccount.Exception;

import com.service.application.customerAccount.model.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleSecurityException(Exception exception) {
        ErrorResponse customErrorResponse = null;

        exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {
            customErrorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "The username or password is incorrect");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(customErrorResponse);
        }

        if (exception instanceof AccountStatusException) {
            customErrorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), "The account is locked");
        }

        if (exception instanceof AccessDeniedException) {
            customErrorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), "You are not authorized to access this resource");
        }

        if (exception instanceof SignatureException) {
            customErrorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), "The JWT signature is invalid");
        }

        if (exception instanceof ExpiredJwtException) {
            customErrorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), "The JWT token has expired");
        }

        if(exception instanceof MethodArgumentNotValidException){
            customErrorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad request format email");
        }

        // Add other custom exception checks here...

        if (customErrorResponse == null) {
            customErrorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unknown internal server error.");
        }

        return ResponseEntity.status(customErrorResponse.getStatusCode()).body(customErrorResponse);
    }
}