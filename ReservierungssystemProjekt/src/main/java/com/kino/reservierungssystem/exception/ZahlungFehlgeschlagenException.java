package com.kino.reservierungssystem.exception;

public class ZahlungFehlgeschlagenException extends RuntimeException {
    public ZahlungFehlgeschlagenException(String message) {
        super(message);
    }
}
