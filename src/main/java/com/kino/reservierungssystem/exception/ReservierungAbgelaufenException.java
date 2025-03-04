package com.kino.reservierungssystem.exception;

public class ReservierungAbgelaufenException extends RuntimeException {
    public ReservierungAbgelaufenException(String message) {
        super(message);
    }
}