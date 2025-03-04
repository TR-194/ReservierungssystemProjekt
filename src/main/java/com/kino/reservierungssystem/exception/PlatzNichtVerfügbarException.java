package com.kino.reservierungssystem.exception;

public class PlatzNichtVerfügbarException extends RuntimeException {
    public PlatzNichtVerfügbarException(String message) {
        super(message);
    }
}