package com.bastug.authservice.user.exception;

public class TokenIsNotRefreshException extends RuntimeException {
    public TokenIsNotRefreshException() {
        super("İletilen token refresh tokeni değildir!");
    }
}
