package com.kino.reservierungssystem.exception;

public class UngueltigeBuchungException extends RuntimeException {
    public UngueltigeBuchungException(String message) {
        super(message);
    }
}