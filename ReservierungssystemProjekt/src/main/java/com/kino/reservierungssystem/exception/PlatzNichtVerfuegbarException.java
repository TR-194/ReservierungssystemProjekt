package com.kino.reservierungssystem.exception;

public class PlatzNichtVerfuegbarException extends RuntimeException {
    public PlatzNichtVerfuegbarException(String message) {
        super(message);
    }
}