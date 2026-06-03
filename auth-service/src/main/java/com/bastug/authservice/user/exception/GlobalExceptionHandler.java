package com.bastug.authservice.user.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> emailAlreadyExists(
            EmailAlreadyExistsException ex
    ) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundException(
            UserNotFoundException ex
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> badCredentialsException(
            BadCredentialsException ex
    ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(PhoneAlreadyExistsException.class)
    public ResponseEntity<String> phoneAlreadyExistsException(
            PhoneAlreadyExistsException ex
    ) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> usernameAlreadyExistsException(
            UsernameAlreadyExistsException usernameAlreadyExistsException
    ){
        return ResponseEntity.badRequest().body(usernameAlreadyExistsException.getMessage());
    }
    @ExceptionHandler(TokenIsNotRefreshException.class)
    public ResponseEntity<String> tokenIsNotRefreshException(
            TokenIsNotRefreshException ex
    ){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<String> expiredTokenException(
            ExpiredTokenException ex
    ){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
