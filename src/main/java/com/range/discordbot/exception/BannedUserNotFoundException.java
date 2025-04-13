package com.range.discordbot.exception;

public class BannedUserNotFoundException extends RuntimeException {
    public BannedUserNotFoundException(String message) {
        super(message);
    }
}
