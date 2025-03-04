package com.kino.reservierungssystem.exception;

public class UngültigeBuchungException extends RuntimeException {
    public UngültigeBuchungException(String message) {
        super(message);
    }
}