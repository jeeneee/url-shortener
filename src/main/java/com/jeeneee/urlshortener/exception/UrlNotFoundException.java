package com.jeeneee.urlshortener.exception;

public class UrlNotFoundException extends RuntimeException {

    public UrlNotFoundException() {
        super("The url does not exist.");
    }
}
