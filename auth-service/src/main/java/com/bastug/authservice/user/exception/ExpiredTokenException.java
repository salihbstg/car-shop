package com.bastug.authservice.user.exception;

public class ExpiredTokenException extends RuntimeException{
    public ExpiredTokenException() {
        super("Token süresi dolmuştur!");
    }
}
