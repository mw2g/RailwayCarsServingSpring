package com.browarna.railwaycarsserving.exceptions;

public class RailwayCarsServingException extends RuntimeException {
    public RailwayCarsServingException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public RailwayCarsServingException(String exMessage) {
        super(exMessage);
    }
}
